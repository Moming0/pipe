package com.bs.pipe.service.impl;

import java.util.*;

import com.bs.pipe.entity.po.LogPressure;
import com.bs.pipe.entity.po.Piezometer;
import com.bs.pipe.entity.po.PressureData;
import com.bs.pipe.exception.BusinessException;
import com.bs.pipe.mapper.PressureDataMapper;
import com.bs.pipe.service.LogPressureService;
import com.bs.pipe.service.PiezometerService;
import com.bs.pipe.service.PressureDataService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class PressureDataServiceImpl implements PressureDataService {

	@Resource
	private PressureDataMapper pressureDataMapper;
	@Resource
	private PiezometerService piezometerService;
	@Resource
	private LogPressureService logPressureService;

	@Override
	public List<PressureData> selectPressureDataList(PressureData pressureData, String startTime, String endTime) {
		// TODO Auto-generated method stub
		List<PressureData> list = pressureDataMapper.selectPressureDataList(pressureData, startTime, endTime);
		return CollectionUtils.isEmpty(list) ? Collections.emptyList() : list;
	}

	@Override
	public PressureData selectPressureData(PressureData pressureData) {
		// TODO Auto-generated method stub
		return pressureDataMapper.selectPressureData(pressureData);
	}

	@Override
	public void insertPressureData(PressureData pressureData) {
		// TODO Auto-generated method stub
		if(pressureDataMapper.insertPressureData(pressureData) != 1){
			throw new BusinessException("添加失败");
		}
	}

	@Override
	public void updatePressureData(PressureData pressureData) {
		// TODO Auto-generated method stub
		if(pressureDataMapper.updatePressureData(pressureData) != 1){
			throw new BusinessException("修改失败");
		}
	}

	@Override
	public void deletePressureData(Integer id) {
		// TODO Auto-generated method stub
		if(pressureDataMapper.deletePressureData(id) != 1){
			throw new BusinessException("删除失败");
		}
	}

	@Override
	public Object insertPressureDataList(List<PressureData> pressureDataList) {
		// TODO Auto-generated method stub
		HashMap<String, Object> map = new HashMap<String, Object>();
		ArrayList<LogPressure> logPList = new ArrayList<LogPressure>();
		try {
			for (PressureData pressureData : pressureDataList) {
				if(StringUtils.isBlank(pressureData.getLocCode())){
					map.put("code", 100000);
					map.put("errorMsg", "表单校验异常");
					map.put("validMap", "设备编码不能为空");
					return map;
				}
				if(StringUtils.isBlank(pressureData.getLocName())){
					map.put("code", 100000);
					map.put("errorMsg", "表单校验异常");
					map.put("validMap", "位置名称不能为空");
					return map;
				}
				if(pressureData.getPressure() == null){
					map.put("code", 100000);
					map.put("errorMsg", "表单校验异常");
					map.put("validMap", "压力不能为空");
					return map;
				}
				if(pressureData.getDataTime() == null){
					map.put("code", 100000);
					map.put("errorMsg", "表单校验异常");
					map.put("validMap", "数据时刻不能为空");
					return map;
				}
				if(pressureData.getRequestTime() == null){
					map.put("code", 100000);
					map.put("errorMsg", "表单校验异常");
					map.put("validMap", "请求时间不能为空");
					return map;
				}
				Piezometer piezometer = piezometerService.selectPiezometerBySnumber(pressureData.getLocCode());
				if(piezometer == null){
					piezometer = piezometerService.selectPiezometerByName(pressureData.getLocName());
				}
				if(piezometer != null){
					LogPressure logP = new LogPressure();
					logP.setPressureId(piezometer.getId());
					logP.setReadTime(pressureData.getDataTime());
					//1 千帕(kpa) = 0.001 兆帕(mpa)
					logP.setReadNumber(pressureData.getPressure() * 0.001);
					logPList.add(logP);
				}
			}
			if(pressureDataMapper.insertPressureDataList(pressureDataList) <= 0){
				map.put("code", 500);
				map.put("errorMsg", "系统繁忙，请稍后再试！");
				map.put("techMsg", "服务pipe-server失去联系");
				return map;
			} else {
				try {
					if(CollectionUtils.isNotEmpty(logPList)){
						logPressureService.insertLogPressureList(logPList);
					}
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println("===============压力数据转换录入失败！！！===============");
				}
				
				map.put("code", 200);
				map.put("errorMsg", null);
				return map;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			map.put("code", 500);
			map.put("errorMsg", "系统繁忙，请稍后再试！");
			map.put("techMsg", "服务pipe-server失去联系");
			return map;
		}
	}

}
