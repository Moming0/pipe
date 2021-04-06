package com.bs.pipe.mapper;

import java.util.List;

import com.bs.pipe.entity.po.PressureData;
import org.apache.ibatis.annotations.Param;

public interface PressureDataMapper {
	/**
	 * ID,压力ID,时间范围
	 * 
	 * @param pressureData
	 * @return
	 */
	List<PressureData> selectPressureDataList(@Param("pressureData") PressureData pressureData,
                                              @Param("startTime") String startTime, @Param("endTime") String endTime);
	
	PressureData selectPressureData(PressureData pressureData);

	int insertPressureData(PressureData pressureData);

	int updatePressureData(PressureData pressureData);

	int deletePressureData(Integer id);
	
	/**
	 * 批量插入数据list
	 * @param pressureDataList
	 * @return
	 */
	int insertPressureDataList(List<PressureData> pressureDataList);
	
}