package com.bs.pipe.mapper;

import com.bs.pipe.entity.po.RtaAlarmStandard;

import java.util.List;

public interface RtaAlarmStandardMapper {
	
	List<RtaAlarmStandard> selectRtaAlarmStandardList(RtaAlarmStandard rtaAlarmStandard);
	
	RtaAlarmStandard selectRtaAlarmStandard(RtaAlarmStandard rtaAlarmStandard);

	int insertRtaAlarmStandard(RtaAlarmStandard rtaAlarmStandard);

	int updateRtaAlarmStandard(RtaAlarmStandard rtaAlarmStandard);

	int deleteRtaAlarmStandard(Integer id);
	
	boolean rtaAlarmStandardExistWhenCreate(RtaAlarmStandard rtaAlarmStandard);
	
	boolean rtaAlarmStandardExistWhenUpdate(RtaAlarmStandard rtaAlarmStandard);
}