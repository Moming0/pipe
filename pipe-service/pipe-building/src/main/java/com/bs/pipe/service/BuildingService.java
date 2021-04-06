package com.bs.pipe.service;

import com.bs.pipe.entity.po.Building;
import com.bs.pipe.entity.vo.BuildingVO;

import java.util.List;

public interface BuildingService {
	
	/**
	 * 获得Building（建筑）多记录（ID，名字模糊，区域ID，经纬度）
	 * @param building
	 * @return
	 */
	List<Building> selectBuildingList(Building building);
	
	/**
	 * 查询（建筑）单条信息
	 * @param building
	 * @return
	 */
	Building selectBuilding(Building building);

	/**
	 * 添加Building（建筑）
	 * @param building
	 */
	void insertBuilding(Building building);

	/**
	 * 修改Building（建筑）
	 * @param building
	 */
	void updateBuilding(Building building);

	/**
	 * 删除Building（建筑）
	 * @param id
	 */
	void deleteBuilding(Integer id);
	
	/**
	 * 获得建筑(一层顶层)及对应高程多记录
	 * @param building
	 * @return
	 */
	List<Building> selectBuildingAndElevation(Building building);
	
	/**
	 * 获取建筑(一层顶层)相对压力值记录
	 * @param building	
	 * @param startTime	开始时间
	 * @param endTime	结束时间
	 * @param type	单位类型：0小时，1日
	 * @param buildingCateory	建筑类型：0底层，1顶层
	 * @return
	 */
	List<BuildingVO> selectBuildingAndPressure(Building building, String startTime, String endTime, String type, String buildingCateory);

	/**
	 * 查询建筑的压力标准上下限范围信息（ID，名字模糊，区域ID，经纬度）
	 * @param building
	 * @param buildingCateory	建筑类型：0底层，1顶层
	 * @return
	 * 标准共享报警限值信息表RtaAlarmStandard
	 */
	List selectBuildingPressureScale(Building building, String buildingCateory);
	
}
