package com.bs.pipe.service;

import com.bs.pipe.entity.po.RtaLegendStandard;

import java.util.List;

public interface RtaLegendStandardService {
	
	/**
	 * 获得图例标准多记录(ID,类型)
	 * @param rtaLegendStandard
	 * @return
	 */
	List<RtaLegendStandard> selectRtaLegendStandardList(RtaLegendStandard rtaLegendStandard);
	
	/**
	 * 查询图例标准单条信息
	 * @param rtaLegendStandard
	 * @return
	 */
	RtaLegendStandard selectRtaLegendStandard(RtaLegendStandard rtaLegendStandard);

	/**
	 * 添加RtaLegendStandard（图例标准）
	 * @param rtaLegendStandard
	 */
	void insertRtaLegendStandard(RtaLegendStandard rtaLegendStandard);

	/**
	 * 修改RtaLegendStandard（图例标准）
	 * @param rtaLegendStandard
	 */
	void updateRtaLegendStandard(RtaLegendStandard rtaLegendStandard);

	/**
	 * 删除RtaLegendStandard（图例标准）
	 * @param id
	 */
	void deleteRtaLegendStandard(Integer id);
}
