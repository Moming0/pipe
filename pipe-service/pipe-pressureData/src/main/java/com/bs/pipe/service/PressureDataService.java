package com.bs.pipe.service;

import com.bs.pipe.entity.po.PressureData;

import java.util.List;

public interface PressureDataService {
	
	/**
	 * 获得PressureData原始数据多记录(id,编号,名称模糊,时间范围)
	 * @param pressureData
	 * @return
	 */
	List<PressureData> selectPressureDataList(PressureData pressureData, String startTime, String endTime);
	
	/**
	 * 查询（压力原始数据）单条信息
	 * @param pressureData
	 * @return
	 */
	PressureData selectPressureData(PressureData pressureData);

	/**
	 * 添加PressureData（压力原始数据）
	 * @param pressureData
	 */
	void insertPressureData(PressureData pressureData);

	/**
	 * 修改PressureData（压力原始数据）
	 * @param pressureData
	 */
	void updatePressureData(PressureData pressureData);

	/**
	 * 删除PressureData（压力原始数据）
	 * @param id
	 */
	void deletePressureData(Integer id);
	
	/**
	 * 批量插入数据list
	 * @param pressureDataList
	 */
	Object insertPressureDataList(List<PressureData> pressureDataList);
	
}
