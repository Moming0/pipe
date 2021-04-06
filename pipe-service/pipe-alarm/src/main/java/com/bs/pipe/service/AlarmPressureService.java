package com.bs.pipe.service;

import com.bs.pipe.entity.po.AlarmPressure;

import java.util.List;

public interface AlarmPressureService {
	
	/**
	 * 获得AlarmPressure多记录（ID,类型，时间范围）
	 * @param AlarmPressure
	 * @return
	 */
	List<AlarmPressure> selectAlarmPressureList(AlarmPressure AlarmPressure, String startTime, String endTime);
	
	/**
	 * 查询（报警）单条信息
	 * @param AlarmPressure
	 * @return
	 */
	AlarmPressure selectAlarmPressure(AlarmPressure AlarmPressure);

	/**
	 * 添加AlarmPressure（报警）
	 * @param AlarmPressure
	 */
	void insertAlarmPressure(AlarmPressure AlarmPressure);

	/**
	 * 修改AlarmPressure（报警）
	 * @param AlarmPressure
	 */
	void updateAlarmPressure(AlarmPressure AlarmPressure);

	/**
	 * 删除AlarmPressure（报警）
	 * @param id
	 */
	void deleteAlarmPressure(String id);
	
	/**
	 * 获得某日报警的最大自然增长数值
	 * @param time
	 * @return
	 */
	Integer getAlarmCodeByNum(String time);
	
	/**
	 * 生成报警codeId
	 * @param id		设备id
	 * @param category	类型
	 * @return
	 */
	String createAlarmCode(Integer id, String category);
	
}
