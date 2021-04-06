package com.bs.pipe.mapper;

import java.util.List;

import com.bs.pipe.entity.po.LogPressure;
import org.apache.ibatis.annotations.Param;

public interface LogPressureMapper {
	/**
	 * ID,压力ID,时间范围
	 * 
	 * @param logPressure
	 * @return
	 */
	List<LogPressure> selectLogPressureList(@Param("logPressure") LogPressure logPressure,
                                            @Param("startTime") String startTime, @Param("endTime") String endTime);

	LogPressure selectLogPressure(LogPressure logPressure);

	int insertLogPressure(LogPressure logPressure);

	int updateLogPressure(LogPressure logPressure);

	int deleteLogPressure(Integer id);
	
	/**
	 * 批量插入数据list
	 * @param logPressureList
	 * @return
	 */
	int insertLogPressureList(List<LogPressure> logPressureList);
	
}