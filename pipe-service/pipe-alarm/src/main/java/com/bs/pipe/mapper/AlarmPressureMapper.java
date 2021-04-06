package com.bs.pipe.mapper;

import java.util.List;

import com.bs.pipe.entity.po.AlarmPressure;
import org.apache.ibatis.annotations.Param;

public interface AlarmPressureMapper {

	/**
	 * ID,类型，时间范围
	 * @param alarmPressure
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	List<AlarmPressure> selectAlarmPressureList(@Param("alarmPressure") AlarmPressure alarmPressure, @Param("startTime") String startTime, @Param("endTime") String endTime);
	
	AlarmPressure selectAlarmPressure(AlarmPressure alarmPressure);

	int insertAlarmPressure(AlarmPressure alarmPressure);

	int updateAlarmPressure(AlarmPressure alarmPressure);

	int deleteAlarmPressure(String id);

	/**
	 * 获得某日报警的最大自然增长数值
	 * @param time
	 * @return
	 */
	Integer getAlarmCodeByNum(String time);
	
}