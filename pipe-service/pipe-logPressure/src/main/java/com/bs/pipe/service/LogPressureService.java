package com.bs.pipe.service;

import com.bs.pipe.entity.po.LogPressure;

import java.util.List;

public interface LogPressureService {
	
	/**
	 * 获得LogPressure（压力数据）多记录（ID，压力点ID，时间范围）
	 * @param logPressure
	 * @return
	 */
	List<LogPressure> selectLogPressureList(LogPressure logPressure, String startTime, String endTime);
	
	/**
	 * 查询（压力数据）单条信息
	 * @param logPressure
	 * @return
	 */
	LogPressure selectLogPressure(LogPressure logPressure);

	/**
	 * 添加LogPressure（压力数据）
	 * @param logPressure
	 */
	void insertLogPressure(LogPressure logPressure);

	/**
	 * 修改LogPressure（压力数据）
	 * @param logPressure
	 */
	void updateLogPressure(LogPressure logPressure);

	/**
	 * 删除LogPressure（压力数据）
	 * @param id
	 */
	void deleteLogPressure(Integer id);
	
	/**
	 * 批量插入数据list
	 * @param logPressureList
	 */
	void insertLogPressureList(List<LogPressure> logPressureList);
}
