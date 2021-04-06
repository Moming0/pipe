package com.bs.pipe.mapper;

import com.bs.pipe.entity.po.Building;

import java.util.List;

public interface BuildingMapper {
	/**
	 * ID，名字模糊，区域ID，经纬度
	 * @param building
	 * @return
	 */
	List<Building> selectBuildingList(Building building);
	
	Building selectBuilding(Building building);

	int insertBuilding(Building building);

	int updateBuilding(Building building);

	int deleteBuilding(Integer id);
	
}