package com.bs.pipe.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.bs.pipe.entity.po.*;
import com.bs.pipe.entity.vo.BuildingVO;
import com.bs.pipe.entity.vo.PiezometerVO;
import com.bs.pipe.exception.BusinessException;
import com.bs.pipe.mapper.BuildingMapper;
import com.bs.pipe.service.*;
import com.bs.pipe.utils.ArithmeticUtil;
import com.bs.pipe.utils.DistanceUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class BuildingServiceImpl implements BuildingService {

	@Resource
	private BuildingMapper buildingMapper;
	@Resource
	private ElevationNodeService elevationNodeService;
	@Resource
	private PiezometerService piezometerService;
	@Resource
	private WaterregionService waterregionService;
	@Resource
	private RtaAlarmStandardService rtaAlarmStandardService;

	@Override
	public List<Building> selectBuildingList(Building building) {
		// TODO Auto-generated method stub
		List<Building> list = buildingMapper.selectBuildingList(building);
		List<Waterregion> waterregionList = waterregionService.selectWaterregionList(null);
		for (Building build : list) {
			for (Waterregion waterregion : waterregionList) {
				if(build.getRegionId().equals(waterregion.getId())){
					build.setRegionName(waterregion.getName());
				}
			}
		}
		return CollectionUtils.isEmpty(list) ? Collections.emptyList() : list;
	}

	@Override
	public Building selectBuilding(Building building) {
		// TODO Auto-generated method stub
		return buildingMapper.selectBuilding(building);
	}

	@Override
	public void insertBuilding(Building building) {
		// TODO Auto-generated method stub
		if(buildingMapper.insertBuilding(building) != 1){
			throw new BusinessException("添加失败");
		}
	}

	@Override
	public void updateBuilding(Building building) {
		// TODO Auto-generated method stub
		if(buildingMapper.updateBuilding(building) != 1){
			throw new BusinessException("修改失败");
		}
	}

	@Override
	public void deleteBuilding(Integer id) {
		// TODO Auto-generated method stub
		if(buildingMapper.deleteBuilding(id) != 1){
			throw new BusinessException("删除失败");
		}
	}

	@Override
	public List<Building> selectBuildingAndElevation(Building building) {
		// TODO Auto-generated method stub
		List<Building> buildingList = this.selectBuildingList(building);
		if(CollectionUtils.isEmpty(buildingList)){
			return Collections.emptyList();
		} else {
			List<ElevationNode> elevationNodeList = elevationNodeService.selectElevationNodeList(null);
			if(CollectionUtils.isEmpty(elevationNodeList)){
				return buildingList;
			} else {
				for (Building b : buildingList) {
					Double dSum = null;
					for (ElevationNode eNode : elevationNodeList) {
						double distance = DistanceUtil.getDistance(b.getLongitude(), b.getLatitude(), eNode.getLongitude(), eNode.getLatitude());
						if(dSum == null){
							dSum = distance;
							b.setElevation(eNode.getElevation());
						} else {
							if(distance <= dSum){
								dSum = distance;
								b.setElevation(eNode.getElevation());
							}
						}
					}
				}
				return buildingList;
			}
		}
	}

	@Override
	public List<BuildingVO> selectBuildingAndPressure(Building building, String startTime, String endTime, String type, String buildingCateory) {
		// TODO Auto-generated method stub
		if((StringUtils.isEmpty(startTime) || StringUtils.isEmpty(endTime)) || (!isDateTime(startTime) || !isDateTime(endTime))){
			throw new BusinessException("请正确选择时间");
		}
		List<Building> buildingList = this.selectBuildingAndElevation(building);
		if(CollectionUtils.isEmpty(buildingList)){
			return Collections.emptyList();
		} else {
			List<BuildingVO> buildingVOList = buildingList.stream().map(a -> builderBuildingVO(a)).collect(Collectors.toList());
			Piezometer piezometer = new Piezometer();
			if(building.getRegionId() != null){
				piezometer.setRegionId(building.getRegionId());
			}
			List<PiezometerVO> piezometerList = piezometerService.selectPressureLogAvgSearch(piezometer, startTime, endTime, type);
			if(CollectionUtils.isEmpty(piezometerList)){
				return buildingVOList;
			} else {
				for (BuildingVO build : buildingVOList) {
					Double dSum = null;		
					PiezometerVO mNode = new PiezometerVO(); 
					for (PiezometerVO pNode : piezometerList) {
						if(build.getRegionId().equals(pNode.getRegionId())){
							double distance = DistanceUtil.getDistance(build.getLongitude(), build.getLatitude(), pNode.getLongitude(), pNode.getLatitude());
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
						//根据高程差计算压力 1m = 0.01MPa
						Double logDif = StringUtils.equals(buildingCateory, "1") ? (build.getElevation() + build.getBuildingHeight() - mNode.getElevation()) * 0.01 : (build.getElevation() - mNode.getElevation()) * 0.01;
						//深拷贝集合对象
						List<LogPressure> logPressureCopy = depCopy(mNode.getLogPressure());
						logPressureCopy.forEach(m -> m.setReadNumber(m.getReadNumber() + (logDif > 0 ? logDif * -1 : logDif < 0 ? logDif : 0)));
						build.setLogPressure(logPressureCopy);
					}
				}
				return buildingVOList;
			}
		}
	}

	@Override
	public List selectBuildingPressureScale(Building building, String buildingCateory) {
		// TODO Auto-generated method stub
		List<Building> bList = this.selectBuildingAndElevation(building);
		List<RtaAlarmStandard> alarmStandardList = rtaAlarmStandardService.selectRtaAlarmStandardList(null);
		List<LinkedHashMap<String,Object>> list = new ArrayList<LinkedHashMap<String,Object>>();
		for (Building b : bList) {
			LinkedHashMap<String,Object> map = new LinkedHashMap<>();
			map.put("id", b.getId());
			map.put("name", b.getName());
			List<LinkedHashMap<String,Object>> pressureScale = new ArrayList<LinkedHashMap<String,Object>>();
			for (RtaAlarmStandard alarmStandard : alarmStandardList) {
				if(alarmStandard.getRegionId().equals(b.getRegionId())){
					//根据高程差计算压力 1m = 0.01MPa
					Double logDif = ArithmeticUtil.mul(ArithmeticUtil.sub(alarmStandard.getElevation(), StringUtils.equals(buildingCateory, "1") ? ArithmeticUtil.add(b.getElevation(),b.getBuildingHeight()) : b.getElevation()), 0.01);
					Double minScale = ArithmeticUtil.add(alarmStandard.getMinScale(), logDif > 0 ? logDif : logDif < 0 ? logDif * -1 : 0);
					Double maxScale = ArithmeticUtil.add(alarmStandard.getMaxScale(), logDif > 0 ? logDif : logDif < 0 ? logDif * -1 : 0);
					LinkedHashMap<String,Object> mapScale = new LinkedHashMap<>();
					mapScale.put("time", alarmStandard.getCategory());
					mapScale.put("minScale", minScale);
					mapScale.put("maxScale", maxScale);
					pressureScale.add(mapScale);
				}
			}
			map.put("pressureScale", pressureScale);
			list.add(map);
		}
		return list;
	}
	
	private BuildingVO builderBuildingVO(Building building) {
		if (building == null) {
			return null;
		} else {
            BuildingVO buildingVO = new BuildingVO();
            BeanUtils.copyProperties(building,buildingVO);
			return buildingVO;
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
