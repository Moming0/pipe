package com.bs.pipe.mapper;

import com.bs.pipe.entity.po.Waterregion;

import java.util.List;

public interface WaterregionMapper {

	/**
	 * ID，名字模糊
	 * @param waterregion
	 * @return
	 */
	List<Waterregion> selectWaterregionList(Waterregion waterregion);
	
	Waterregion selectWaterregion(Waterregion waterregion);

	int insertWaterregion(Waterregion waterregion);

	int updateWaterregion(Waterregion waterregion);

	int deleteWaterregion(Integer id);
	
}