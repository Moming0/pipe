package com.bs.pipe.service.impl;

import java.util.*;

import com.bs.pipe.entity.po.RtaLegendStandard;
import com.bs.pipe.exception.BusinessException;
import com.bs.pipe.mapper.RtaLegendStandardMapper;
import com.bs.pipe.service.RtaLegendStandardService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class RtaLegendStandardImpl implements RtaLegendStandardService {

	@Resource
	private RtaLegendStandardMapper rtaLegendStandardMapper;

	@Override
	public List<RtaLegendStandard> selectRtaLegendStandardList(RtaLegendStandard rtaLegendStandard) {
		// TODO Auto-generated method stub
		List<RtaLegendStandard> list = rtaLegendStandardMapper.selectRtaLegendStandardList(rtaLegendStandard);
		return CollectionUtils.isEmpty(list) ? Collections.emptyList() : list;
	}

	@Override
	public RtaLegendStandard selectRtaLegendStandard(RtaLegendStandard rtaLegendStandard) {
		// TODO Auto-generated method stub
		return rtaLegendStandardMapper.selectRtaLegendStandard(rtaLegendStandard);
	}

	@Override
	public void insertRtaLegendStandard(RtaLegendStandard rtaLegendStandard) {
		// TODO Auto-generated method stub
		if(this.rtaLegendStandardExistWhenCreate(rtaLegendStandard)){
			throw new BusinessException("该统计类型已被设置");
		} else {
			if(rtaLegendStandardMapper.insertRtaLegendStandard(rtaLegendStandard) != 1){
				throw new BusinessException("添加失败");
			}
		}
		
	}

	@Override
	public void updateRtaLegendStandard(RtaLegendStandard rtaLegendStandard) {
		// TODO Auto-generated method stub
		if(this.rtaLegendStandardExistWhenUpdate(rtaLegendStandard)){
			throw new BusinessException("该统计类型已被设置");
		} else {
			if(rtaLegendStandardMapper.updateRtaLegendStandard(rtaLegendStandard) != 1){
				throw new BusinessException("修改失败");
			}
		}
	}

	@Override
	public void deleteRtaLegendStandard(Integer id) {
		// TODO Auto-generated method stub
		if(rtaLegendStandardMapper.deleteRtaLegendStandard(id) != 1){
			throw new BusinessException("删除失败");
		}
	}

	private boolean rtaLegendStandardExistWhenCreate(RtaLegendStandard rtaLegendStandard) {
		return rtaLegendStandardMapper.rtaLegendStandardExistWhenCreate(rtaLegendStandard);
	}

	private boolean rtaLegendStandardExistWhenUpdate(RtaLegendStandard rtaLegendStandard) {
		return rtaLegendStandardMapper.rtaLegendStandardExistWhenUpdate(rtaLegendStandard);
	}

}
