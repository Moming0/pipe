package com.bs.pipe.service;

import com.bs.pipe.entity.po.GridNode;
import com.bs.pipe.entity.vo.GridNodeVO;

import java.util.List;

public interface GridNodeService {
	
	/**
	 * 获得GridNode（网格点）多记录（ID，区域，经纬度，地址）
	 * @param gridNode
	 * @return
	 */
	List<GridNode> selectGridNodeList(GridNode gridNode);
	
	/**
	 * 查询（网格点）单条信息
	 * @param gridNode
	 * @return
	 */
	GridNode selectGridNode(GridNode gridNode);

	/**
	 * 添加GridNode（网格点）
	 * @param gridNode
	 */
	void insertGridNode(GridNode gridNode);

	/**
	 * 修改GridNode（网格点）
	 * @param gridNode
	 */
	void updateGridNode(GridNode gridNode);

	/**
	 * 删除GridNode（网格点）
	 * @param id
	 */
	void deleteGridNode(Integer id);
	
	/**
	 * 批量插入GridNode（网格点）
	 * @param gridNodeList
	 */
	void insertGridNodeList(List<GridNode> gridNodeList);
	
	/**
	 * 获得网格点及对应高程多记录
	 * @param gridNode
	 * @return
	 */
	List<GridNode> selectGridNodeElevation(GridNode gridNode);

    /**
     * 获取网格点高程相对压力值记录
     * @param gridNode
     * @param startTime	开始时间
     * @param endTime	结束时间
     * @param type	单位类型：-1:原始数据，0:小时，1:日
     * @return
     */
	List<GridNodeVO> selectGridNodePressure(GridNode gridNode, String startTime, String endTime, String type);
}
