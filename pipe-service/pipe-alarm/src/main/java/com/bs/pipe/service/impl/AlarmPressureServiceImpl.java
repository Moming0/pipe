package com.bs.pipe.service.impl;

import java.util.*;

import com.bs.pipe.entity.po.AlarmPressure;
import com.bs.pipe.exception.BusinessException;
import com.bs.pipe.mapper.AlarmPressureMapper;
import com.bs.pipe.service.AlarmPressureService;
import com.bs.pipe.utils.DateHelper;
import com.google.gson.Gson;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class AlarmPressureServiceImpl implements AlarmPressureService {
	@Resource
	private AlarmPressureMapper alarmPressureMapper;

	@Override
	public List<AlarmPressure> selectAlarmPressureList(AlarmPressure alarmPressure, String startTime, String endTime) {
		// TODO Auto-generated method stub
		List<AlarmPressure> list = alarmPressureMapper.selectAlarmPressureList(alarmPressure,startTime,endTime);
		Gson gson = new Gson();
		for (AlarmPressure alarmP : list) {
			try {
				List<HashMap<String, Object>> arrayList = new ArrayList<HashMap<String, Object>>();
				arrayList = gson.fromJson((String) alarmP.getAlarmData(), arrayList.getClass());
				alarmP.setAlarmData(arrayList);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				alarmP.setAlarmData(null);
			}
		}
		return CollectionUtils.isEmpty(list) ? Collections.emptyList() : list;
	}

	@Override
	public AlarmPressure selectAlarmPressure(AlarmPressure alarmPressure) {
		// TODO Auto-generated method stub
		return alarmPressureMapper.selectAlarmPressure(alarmPressure);
	}

	@Override
	public void insertAlarmPressure(AlarmPressure alarmPressure) {
		// TODO Auto-generated method stub
		if(alarmPressureMapper.insertAlarmPressure(alarmPressure) != 1){
			throw new BusinessException("添加失败");
		}
	}

	@Override
	public void updateAlarmPressure(AlarmPressure alarmPressure) {
		// TODO Auto-generated method stub
		if(alarmPressureMapper.updateAlarmPressure(alarmPressure) != 1){
			throw new BusinessException("修改失败");
		}
	}

	@Override
	public void deleteAlarmPressure(String id) {
		// TODO Auto-generated method stub
		if(alarmPressureMapper.deleteAlarmPressure(id) != 1){
			throw new BusinessException("删除失败");
		}
	}

	/**
	 * 获得某日报警的最大自然增长数值
	 * @param time
	 * @return
	 */
	public Integer getAlarmCodeByNum(String time){
		Integer alarmCodeByNum = alarmPressureMapper.getAlarmCodeByNum(time);
		return alarmCodeByNum == null ? 0 : alarmCodeByNum;
	}
	
	/**
	 * 生成报警Code
	 * @param category
	 * @param id
	 * @return
	 */
	public String createAlarmCode(Integer id, String category){
		/**
		 * alarmCode生成规则：
		 * 
		 * 首字母 + 三位数设施(设备)ID + 时间yyyyMMdd + 三位数的累计自然数(001~999)
		 * 
		 * 首字母<一/二位>：10:压力点,20:建筑一层,30:和建筑顶层
		 * 三位数设施(设备)ID<三位>：不够三位数的前面用0填充
		 * 时间<八位>：格式yyyyMMdd
		 * 三位自然数<三位>：三位数的累计自然数(001~999)，不够三位数的前面用0填充
		 */
		
		String alarmCode = null;
		switch(category){
			case "10":
				alarmCode = "P";	//Piezometer压力点
				break;
			case "20":
				alarmCode = "B";	//Building建筑一层
				break;
			case "30":
				alarmCode = "T";	//Building建筑顶层
				break;
		}
		
		if (id < 10 && id > 0) {
			alarmCode = alarmCode + "00" + id;
		} else if (id < 100 && id >= 10) {
			alarmCode = alarmCode + "0" + id;
		} else {
			alarmCode = alarmCode + id;
		}
		
		alarmCode = alarmCode + DateHelper.format(new Date(), "yyyyMMdd");
		
		Integer num = this.getAlarmCodeByNum(DateHelper.format(new Date()));
		if (num < 9 && num >= 0) {
			alarmCode = alarmCode + "00" + ++num;
		} else if (num < 99 && num >= 9) {
			alarmCode = alarmCode + "0" + ++num;
		} else {
			alarmCode = alarmCode + ++num;
		}
		
		return alarmCode;
	}

}
