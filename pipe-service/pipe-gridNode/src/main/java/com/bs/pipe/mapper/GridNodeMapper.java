package com.bs.pipe.mapper;

import com.bs.pipe.entity.po.GridNode;

import java.util.List;

public interface GridNodeMapper {
	/**
	 * ID，区域ID，经纬度
	 * @param gridNode
	 * @return
	 */
	List<GridNode> selectGridNodeList(GridNode gridNode);
	
	GridNode selectGridNode(GridNode gridNode);

	int insertGridNode(GridNode gridNode);

	int updateGridNode(GridNode gridNode);

	int deleteGridNode(Integer id);

	boolean gridNodeExistWhenCreate(GridNode gridNode);
	
	boolean gridNodeExistWhenUpdate(GridNode gridNode);

	/**
	 * 批量插入网格点
	 * @param gridNodeList
	 * @return
	 */
	int insertGridNodeList(List<GridNode> gridNodeList);
}