package com.bs.pipe.service;

import com.bs.pipe.entity.po.Waterregion;

import java.util.List;

public interface WaterregionService {
	
	/**
	 * 获得Waterregion（区域）多记录（ID，名字模糊）
	 * @param waterregion
	 * @return
	 */
	List<Waterregion> selectWaterregionList(Waterregion waterregion);
	
	/**
	 * 查询（区域）单条信息
	 * @param waterregion
	 * @return
	 */
	Waterregion selectWaterregion(Waterregion waterregion);

	/**
	 * 添加Waterregion（区域）
	 * @param waterregion
	 */
	void insertWaterregion(Waterregion waterregion);

	/**
	 * 修改Waterregion（区域）
	 * @param waterregion
	 */
	void updateWaterregion(Waterregion waterregion);

	/**
	 * 删除Waterregion（区域）
	 * @param id
	 */
	void deleteWaterregion(Integer id);
}
