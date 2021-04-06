package com.bs.pipe.service.impl;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.bs.pipe.entity.po.LogPressure;
import com.bs.pipe.entity.po.Piezometer;
import com.bs.pipe.entity.po.RtaAlarmStandard;
import com.bs.pipe.entity.po.Waterregion;
import com.bs.pipe.entity.vo.PiezometerVO;
import com.bs.pipe.exception.BusinessException;
import com.bs.pipe.mapper.PiezometerMapper;
import com.bs.pipe.service.*;
import com.bs.pipe.utils.ArithmeticUtil;
import com.bs.pipe.utils.DateHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class PiezometerServiceImpl implements PiezometerService {

	@Resource
	private PiezometerMapper piezometerMapper;
	@Resource
	private LogPressureService logPressureService;
	@Resource
	private ElevationNodeService elevationNodeService;
	@Resource
	private WaterregionService waterregionService;
	@Resource
	private RtaAlarmStandardService rtaAlarmStandardService;

	@Override
	public List<Piezometer> selectPiezometerList(Piezometer piezometer) {
		// TODO Auto-generated method stub
		List<Piezometer> list = piezometerMapper.selectPiezometerList(piezometer);
		List<Waterregion> waterregionList = waterregionService.selectWaterregionList(null);
		for (Piezometer pizeo : list) {
			for (Waterregion waterregion : waterregionList) {
				if(pizeo.getRegionId().equals(waterregion.getId())){
					pizeo.setRegionName(waterregion.getName());
				}
			}
		}
		return CollectionUtils.isEmpty(list) ? Collections.emptyList() : list;
	}

	@Override
	public Piezometer selectPiezometer(Piezometer piezometer) {
		// TODO Auto-generated method stub
		return piezometerMapper.selectPiezometer(piezometer);
	}

	@Override
	public Piezometer selectPiezometerBySnumber(String snumber) {
		// TODO Auto-generated method stub
		return piezometerMapper.selectPiezometerBySnumber(snumber);
	}

	@Override
	public Piezometer selectPiezometerByName(String name) {
		// TODO Auto-generated method stub
		return piezometerMapper.selectPiezometerByName(name);
	}

	@Override
	public void insertPiezometer(Piezometer piezometer) {
		// TODO Auto-generated method stub
		if(this.piezometerExistWhenCreate(piezometer)){
			throw new BusinessException("编号重复!");
		} else {
			if(piezometerMapper.insertPiezometer(piezometer) != 1){
				throw new BusinessException("添加失败");
			}
		}
	}

	@Override
	public void updatePiezometer(Piezometer piezometer) {
		// TODO Auto-generated method stub
		if(this.piezometerExistWhenUpdate(piezometer)){
			throw new BusinessException("编号重复!");
		} else {
			if(piezometerMapper.updatePiezometer(piezometer) != 1){
				throw new BusinessException("修改失败");
			}
		}
	}

	@Override
	public void deletePiezometer(Integer id) {
		// TODO Auto-generated method stub
		if(piezometerMapper.deletePiezometer(id) != 1){
			throw new BusinessException("删除失败");
		}
	}
	
	private boolean piezometerExistWhenCreate(Piezometer piezometer){
		return piezometerMapper.piezometerExistWhenCreate(piezometer);
	}
	
	private boolean piezometerExistWhenUpdate(Piezometer piezometer){
		return piezometerMapper.piezometerExistWhenUpdate(piezometer);
	}
	
	@Override
	public List<PiezometerVO> selectPressureLogSearch(Piezometer piezometer, String startTime,
                                                      String endTime) {
		// TODO Auto-generated method stub
		if((StringUtils.isEmpty(startTime) || StringUtils.isEmpty(endTime)) || (!isDateTime(startTime) || !isDateTime(endTime))){
			throw new BusinessException("请正确选择时间");
		}
		List<Piezometer> piezometerList = this.selectPiezometerList(piezometer);
		List<PiezometerVO> piezometerVOList = piezometerList.stream().map(a -> builderPiezometerVO(a)).collect(Collectors.toList());
		if(CollectionUtils.isEmpty(piezometerVOList)){
			return Collections.emptyList();
		}
		/*
		//计算压力点高程（高程点高程代替压力点高程）
		List<ElevationNode> elevationNodeList = elevationNodeService.selectElevationNodeList(null);
		if(CollectionUtils.isEmpty(elevationNodeList)){
			return piezometerVOList;
		} else {
			for (PiezometerVO pNode : piezometerVOList) {
				Double dSum = null;
				for (ElevationNode eNode : elevationNodeList) {
					double distance = DistanceUtil.getDistance(pNode.getLongitude(), pNode.getLatitude(), eNode.getLongitude(), eNode.getLatitude());
					if(dSum == null){
						dSum = distance;
						pNode.setElevation(eNode.getElevation());
					} else {
						if(distance <= dSum){
							dSum = distance;
							pNode.setElevation(eNode.getElevation());
						}
					}
				}
			}
		}
		*/
		LogPressure logPressure = new LogPressure();
		if(piezometer.getId() != null){
			logPressure.setPressureId(piezometer.getId());
		}
		List<LogPressure> logPressureList = logPressureService.selectLogPressureList(logPressure, startTime, endTime);
		Map<Integer, List<LogPressure>> logGroup = logPressureList.stream()
		        .collect(Collectors.groupingBy(LogPressure::getPressureId));
		for (PiezometerVO piezometerVO : piezometerVOList) {
			logGroup.forEach((k, v) -> {
				if(piezometerVO.getId().equals(k)){
					piezometerVO.setLogPressure(v);
				}
			});
		}
		return piezometerVOList;
	}

	@Override
	public List<PiezometerVO> selectPressureLogAvgSearch(Piezometer piezometer, String startTime, String endTime,
			String type) {
		// TODO Auto-generated method stub
		if((StringUtils.isEmpty(startTime) || StringUtils.isEmpty(endTime)) || (!isDateTime(startTime) || !isDateTime(endTime))){
			throw new BusinessException("请正确选择时间");
		}
		List<PiezometerVO> piezometerVOList = this.selectPressureLogSearch(piezometer, startTime, endTime);
		if(CollectionUtils.isEmpty(piezometerVOList)){
			return piezometerVOList;
		}
		for (PiezometerVO piezometerVO : piezometerVOList) {
			List<LogPressure> logPressureList = piezometerVO.getLogPressure();
			if(CollectionUtils.isNotEmpty(logPressureList)){
				List<LogPressure> logAvglist = new ArrayList<LogPressure>();
				//按照时间分组，每小时/每天为一组
				LinkedHashMap<String, List<LogPressure>> logGroup = (LinkedHashMap<String, List<LogPressure>>) logPressureList.stream().collect(Collectors
						.groupingBy(logPressure -> DateHelper.format(logPressure.getReadTime(), StringUtils.equals(type, "0") ? "yyyy-MM-dd HH" : "yyyy-MM-dd"), LinkedHashMap::new, Collectors.toList()));
				logGroup.forEach((k, v) -> {
					logAvglist.add(new LogPressure(v.get(0).getId(), v.get(0).getPressureId(),
							DateHelper.parseDateIgnoreError(k),
							v.stream().mapToDouble(LogPressure::getReadNumber).average().getAsDouble(), null, null, null,null,null,null));
				});
				piezometerVO.setLogPressure(logAvglist);
			}
		}
		return piezometerVOList;
	}
	
	@Override
	public List selectPiezometerPressureScale(Piezometer piezometer) {
		// TODO Auto-generated method stub
		List<Piezometer> pList = piezometerMapper.selectPiezometerList(piezometer);
		List<RtaAlarmStandard> alarmStandardList = rtaAlarmStandardService.selectRtaAlarmStandardList(null);
		List<LinkedHashMap<String,Object>> list = new ArrayList<LinkedHashMap<String,Object>>();
		for (Piezometer p : pList) {
			LinkedHashMap<String,Object> map = new LinkedHashMap<>();
			map.put("id", p.getId());
			map.put("name", p.getName());
			List<LinkedHashMap<String,Object>> pressureScale = new ArrayList<LinkedHashMap<String,Object>>();
			for (RtaAlarmStandard alarmStandard : alarmStandardList) {
				if(alarmStandard.getRegionId().equals(p.getRegionId())){
					//根据高程差计算压力 1m = 0.01MPa
					Double logDif = ArithmeticUtil.mul(ArithmeticUtil.sub(alarmStandard.getElevation(), p.getElevation()), 0.01);
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
	
	private PiezometerVO builderPiezometerVO(Piezometer piezometer) {
		if (piezometer == null) {
			return null;
		} else {
            PiezometerVO piezometerVO = new PiezometerVO();
            BeanUtils.copyProperties(piezometer,piezometerVO);
			return piezometerVO;
		}
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
