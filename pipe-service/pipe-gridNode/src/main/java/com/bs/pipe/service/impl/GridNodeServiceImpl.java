package com.bs.pipe.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.bs.pipe.entity.po.ElevationNode;
import com.bs.pipe.entity.po.GridNode;
import com.bs.pipe.entity.po.LogPressure;
import com.bs.pipe.entity.po.Piezometer;
import com.bs.pipe.entity.vo.GridNodeVO;
import com.bs.pipe.entity.vo.PiezometerVO;
import com.bs.pipe.exception.BusinessException;
import com.bs.pipe.mapper.GridNodeMapper;
import com.bs.pipe.service.ElevationNodeService;
import com.bs.pipe.service.GridNodeService;
import com.bs.pipe.service.PiezometerService;
import com.bs.pipe.utils.DistanceUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class GridNodeServiceImpl implements GridNodeService {

	@Resource
	private GridNodeMapper gridNodeMapper;
	@Resource
	private ElevationNodeService elevationNodeService;
	@Resource
	private PiezometerService piezometerService;
	
	@Override
	public List<GridNode> selectGridNodeList(GridNode gridNode) {
		// TODO Auto-generated method stub
		List<GridNode> list = gridNodeMapper.selectGridNodeList(gridNode);
		return CollectionUtils.isEmpty(list) ? Collections.emptyList() : list;
	}

	@Override
	public GridNode selectGridNode(GridNode gridNode) {
		// TODO Auto-generated method stub
		return gridNodeMapper.selectGridNode(gridNode);
	}

	@Override
	public void insertGridNode(GridNode gridNode) {
		// TODO Auto-generated method stub
		if(this.gridNodeExistWhenCreate(gridNode)){
			throw new BusinessException("该经纬度已存在网格点");
		} else {
			if(gridNodeMapper.insertGridNode(gridNode) != 1){
				throw new BusinessException("添加失败");
			}
		}
	}

	@Override
	public void updateGridNode(GridNode gridNode) {
		// TODO Auto-generated method stub
		if(this.gridNodeExistWhenUpdate(gridNode)){
			throw new BusinessException("该经纬度已存在网格点");
		} else {
			if(gridNodeMapper.updateGridNode(gridNode) != 1){
				throw new BusinessException("修改失败");
			}
		}
	}

	@Override
	public void deleteGridNode(Integer id) {
		// TODO Auto-generated method stub
		if(gridNodeMapper.deleteGridNode(id) != 1){
			throw new BusinessException("删除失败");
		}
	}
	
	@Override
	public void insertGridNodeList(List<GridNode> gridNodeList) {
		// TODO Auto-generated method stub
		if(gridNodeMapper.insertGridNodeList(gridNodeList) <= 0){
			throw new BusinessException("批量添加失败");
		}
	}

	@Override
	public List<GridNode> selectGridNodeElevation(GridNode gridNode) {
		// TODO Auto-generated method stub
		List<GridNode> gridNodeList = this.selectGridNodeList(gridNode);
		if(CollectionUtils.isEmpty(gridNodeList)){
			return Collections.emptyList();
		} else {
			List<ElevationNode> elevationNodeList = elevationNodeService.selectElevationNodeList(null);
			if(CollectionUtils.isEmpty(elevationNodeList)){
				return gridNodeList;
			} else {
				for (GridNode gNode : gridNodeList) {
					Double dSum = null;
					for (ElevationNode eNode : elevationNodeList) {
						double distance = DistanceUtil.getDistance(gNode.getLongitude(), gNode.getLatitude(), eNode.getLongitude(), eNode.getLatitude());
						if(dSum == null){
							dSum = distance;
							gNode.setElevation(eNode.getElevation());
						} else {
							if(distance <= dSum){
								dSum = distance;
								gNode.setElevation(eNode.getElevation());
							}
						}
					}
				}
				return gridNodeList;
			}
		}
	}

	@Override
	public List<GridNodeVO> selectGridNodePressure(GridNode gridNode, String startTime, String endTime, String type) {
		// TODO Auto-generated method stub
		if((StringUtils.isEmpty(startTime) || StringUtils.isEmpty(endTime)) || (!isDateTime(startTime) || !isDateTime(endTime))){
			throw new BusinessException("请正确选择时间");
		}
		List<GridNode> gridNodeList = this.selectGridNodeElevation(gridNode);
		if(CollectionUtils.isEmpty(gridNodeList)){
			return Collections.emptyList();
		} else {
			List<GridNodeVO> gridNodeVOList = gridNodeList.stream().map(a -> builderGridNodeVO(a)).collect(Collectors.toList());
			Piezometer piezometer = new Piezometer();
			if(gridNode.getRegionId() != null){
				piezometer.setRegionId(gridNode.getRegionId());
			}
			List<PiezometerVO> piezometerList = piezometerService.selectPressureLogAvgSearch(piezometer, startTime, endTime, type);
			if(CollectionUtils.isEmpty(piezometerList)){
				return gridNodeVOList;
			} else {
				for (GridNodeVO gNode : gridNodeVOList) {
					Double dSum = null;		
					PiezometerVO mNode = new PiezometerVO(); 
					for (PiezometerVO pNode : piezometerList) {
						if(gNode.getRegionId().equals(pNode.getRegionId())){
							double distance = DistanceUtil.getDistance(gNode.getLongitude(), gNode.getLatitude(), pNode.getLongitude(), pNode.getLatitude());
							//System.out.println("压力点ID：" + pNode.getId() + "========距离：" + distance);
							if(dSum == null){
								dSum = distance;
								mNode = pNode;
							} else {
								if(distance <= dSum){
									dSum = distance;
									mNode = pNode;
								}
							}
						}
					}
					if(CollectionUtils.isNotEmpty(mNode.getLogPressure())){
						//System.out.println("压力点ID：" + mNode.getId() + "========高程：" + mNode.getElevation() + "========压力值：" + mNode.getLogPressure());
						//根据高程差计算压力 1m = 0.01MPa
						Double logDif = (gNode.getElevation() - mNode.getElevation()) * 0.01;
						//深拷贝集合对象
						List<LogPressure> logPressureCopy = depCopy(mNode.getLogPressure());
						logPressureCopy.forEach(m -> m.setReadNumber(m.getReadNumber() + (logDif > 0 ? logDif * -1 : logDif < 0 ? logDif : 0)));
						//System.out.println("网格点ID：" + gNode.getId() + "========网格点高程：" + gNode.getElevation() + "========压力差：" + logDif + "========网格readNumber：" + mNode.getLogPressure());
						gNode.setLogPressure(logPressureCopy);
					}
				}
				return gridNodeVOList;
			}
		}
	}
	
	private boolean gridNodeExistWhenCreate(GridNode gridNode) {
		return gridNodeMapper.gridNodeExistWhenCreate(gridNode);
	}

	private boolean gridNodeExistWhenUpdate(GridNode gridNode) {
		return gridNodeMapper.gridNodeExistWhenUpdate(gridNode);
	}
	
	private GridNodeVO builderGridNodeVO(GridNode gridNode) {
		if (gridNode == null) {
			return null;
		} else {
            GridNodeVO gridNodeVO = new GridNodeVO();
            BeanUtils.copyProperties(gridNode,gridNodeVO);
			return gridNodeVO;
		}
	}
	
	/***
	 * 对集合进行深拷贝 注意需要对泛型类进行序列化(实现Serializable)
	 * 
	 * @param srcList
	 * @param <T>
	 * @return
	 */
	public static <T> List<T> depCopy(List<T> srcList) {
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		try {
			ObjectOutputStream out = new ObjectOutputStream(byteOut);
			out.writeObject(srcList);
 
			ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
			ObjectInputStream inStream = new ObjectInputStream(byteIn);
			List<T> destList = (List<T>) inStream.readObject();
			return destList;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 正则表达式时间“YYYY-MM-DD HH:mm:ss”和“YYYY-MM-DD”
	 * @param datetime
	 * @return
	 */
	public static boolean isDateTime(String datetime) {
		Pattern p = Pattern.compile(
				"^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1][0-9])|([2][0-4]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
		return p.matcher(datetime).matches();
	}

}
