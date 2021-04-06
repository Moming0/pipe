package com.bs.pipe.service.impl;

import java.util.*;

import com.bs.pipe.entity.po.RtaAlarmStandard;
import com.bs.pipe.entity.po.Waterregion;
import com.bs.pipe.exception.BusinessException;
import com.bs.pipe.mapper.RtaAlarmStandardMapper;
import com.bs.pipe.service.RtaAlarmStandardService;
import com.bs.pipe.service.WaterregionService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class RtaAlarmStandardImpl implements RtaAlarmStandardService {

	@Resource
	private RtaAlarmStandardMapper rtaAlarmStandardMapper;
	@Resource
	private WaterregionService waterregionService;
	
	@Override
	public List<RtaAlarmStandard> selectRtaAlarmStandardList(RtaAlarmStandard rtaAlarmStandard) {
		// TODO Auto-generated method stub
		List<RtaAlarmStandard> list = rtaAlarmStandardMapper.selectRtaAlarmStandardList(rtaAlarmStandard);
		List<Waterregion> waterregionList = waterregionService.selectWaterregionList(null);
		for (RtaAlarmStandard alarmStandard : list) {
			for (Waterregion waterregion : waterregionList) {
				if(alarmStandard.getRegionId().equals(waterregion.getId())){
					alarmStandard.setRegionName(waterregion.getName());
				}
			}
		}
		return CollectionUtils.isEmpty(list) ? Collections.emptyList() : list;
	}

	@Override
	public RtaAlarmStandard selectRtaAlarmStandard(RtaAlarmStandard rtaAlarmStandard) {
		// TODO Auto-generated method stub
		return rtaAlarmStandardMapper.selectRtaAlarmStandard(rtaAlarmStandard);
	}

	@Override
	public void insertRtaAlarmStandard(RtaAlarmStandard rtaAlarmStandard) {
		// TODO Auto-generated method stub
		if (this.rtaAlarmStandardExistWhenCreate(rtaAlarmStandard)) {
			throw new BusinessException("该统计类型在此区域已被设置");
		} else {
			if(rtaAlarmStandardMapper.insertRtaAlarmStandard(rtaAlarmStandard) != 1){
				throw new BusinessException("添加失败");
			}
		}
		
	}

	@Override
	public void updateRtaAlarmStandard(RtaAlarmStandard rtaAlarmStandard) {
		// TODO Auto-generated method stub
		if (this.rtaAlarmStandardExistWhenUpdate(rtaAlarmStandard)) {
			throw new BusinessException("该统计类型在此区域已被设置");
		} else {
			if(rtaAlarmStandardMapper.updateRtaAlarmStandard(rtaAlarmStandard) != 1){
				throw new BusinessException("修改失败");
			}
		}
	}

	@Override
	public void deleteRtaAlarmStandard(Integer id) {
		// TODO Auto-generated method stub
		if(rtaAlarmStandardMapper.deleteRtaAlarmStandard(id) != 1){
			throw new BusinessException("删除失败");
		}
	}

	private boolean rtaAlarmStandardExistWhenCreate(RtaAlarmStandard rtaAlarmStandard) {
		return rtaAlarmStandardMapper.rtaAlarmStandardExistWhenCreate(rtaAlarmStandard);
	}

	private boolean rtaAlarmStandardExistWhenUpdate(RtaAlarmStandard rtaAlarmStandard) {
		return rtaAlarmStandardMapper.rtaAlarmStandardExistWhenUpdate(rtaAlarmStandard);
	}

}
