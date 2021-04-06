package com.bs.pipe.service;

import com.bs.pipe.entity.po.ElevationNode;

import java.util.List;

public interface ElevationNodeService {
	
	/**
	 * 获得ElevationNode（高程点）多记录（ID，名称模糊，区域，经纬度）
	 * @param elevationNode
	 * @return
	 */
	List<ElevationNode> selectElevationNodeList(ElevationNode elevationNode);
	
	/**
	 * 查询（高程点）单条信息
	 * @param elevationNode
	 * @return
	 */
	ElevationNode selectElevationNode(ElevationNode elevationNode);

	/**
	 * 添加ElevationNode（高程点）
	 * @param elevationNode
	 */
	void insertElevationNode(ElevationNode elevationNode);

	/**
	 * 修改ElevationNode（高程点）
	 * @param elevationNode
	 */
	void updateElevationNode(ElevationNode elevationNode);

	/**
	 * 删除ElevationNode（高程点）
	 * @param id
	 */
	void deleteElevationNode(Integer id);
}
