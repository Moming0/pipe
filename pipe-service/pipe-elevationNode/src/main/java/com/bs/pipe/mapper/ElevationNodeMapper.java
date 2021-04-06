package com.bs.pipe.mapper;

import com.bs.pipe.entity.po.ElevationNode;

import java.util.List;

public interface ElevationNodeMapper {
	
	/**
	 * ID，名称模糊，区域，经纬度
	 * @param elevationNode
	 * @return
	 */
	List<ElevationNode> selectElevationNodeList(ElevationNode elevationNode);
	
	ElevationNode selectElevationNode(ElevationNode elevationNode);

	int insertElevationNode(ElevationNode elevationNode);

	int updateElevationNode(ElevationNode elevationNode);

	int deleteElevationNode(Integer id);

	boolean elevationNodeExistWhenCreate(ElevationNode elevationNode);
	
	boolean elevationNodeExistWhenUpdate(ElevationNode elevationNode);
	
}