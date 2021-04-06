package com.bs.pipe.mapper;


import com.bs.pipe.entity.po.RtaLegendStandard;

import java.util.List;

public interface RtaLegendStandardMapper {
	
	/**
	 * ID，类型
	 * @param rtaLegendStandard
	 * @return
	 */
	List<RtaLegendStandard> selectRtaLegendStandardList(RtaLegendStandard rtaLegendStandard);
	
	RtaLegendStandard selectRtaLegendStandard(RtaLegendStandard rtaLegendStandard);

	int insertRtaLegendStandard(RtaLegendStandard rtaLegendStandard);

	int updateRtaLegendStandard(RtaLegendStandard rtaLegendStandard);

	int deleteRtaLegendStandard(Integer id);

	boolean rtaLegendStandardExistWhenCreate(RtaLegendStandard rtaLegendStandard);
	
	boolean rtaLegendStandardExistWhenUpdate(RtaLegendStandard rtaLegendStandard);
}