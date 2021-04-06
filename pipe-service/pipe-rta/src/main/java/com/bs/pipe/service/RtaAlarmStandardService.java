package com.bs.pipe.service;

import com.bs.pipe.entity.po.RtaAlarmStandard;

import java.util.List;

public interface RtaAlarmStandardService {
	
	/**
	 * 获得RtaAlarmStandard(报警限值)多记录
	 * @param rtaAlarmStandard
	 * @return
	 */
	List<RtaAlarmStandard> selectRtaAlarmStandardList(RtaAlarmStandard rtaAlarmStandard);
	
	/**
	 * 查询报警限值单条信息
	 * @param rtaAlarmStandard
	 * @return
	 */
	RtaAlarmStandard selectRtaAlarmStandard(RtaAlarmStandard rtaAlarmStandard);

	/**
	 * 添加RtaAlarmStandard（报警限值）
	 * @param rtaAlarmStandard
	 */
	void insertRtaAlarmStandard(RtaAlarmStandard rtaAlarmStandard);

	/**
	 * 修改RtaAlarmStandard（报警限值）
	 * @param rtaAlarmStandard
	 */
	void updateRtaAlarmStandard(RtaAlarmStandard rtaAlarmStandard);

	/**
	 * 删除RtaAlarmStandard（报警限值）
	 * @param id
	 */
	void deleteRtaAlarmStandard(Integer id);
}
