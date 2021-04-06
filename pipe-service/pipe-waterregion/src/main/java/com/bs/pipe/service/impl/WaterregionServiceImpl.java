package com.bs.pipe.service.impl;

import java.util.*;

import com.bs.pipe.entity.po.Waterregion;
import com.bs.pipe.exception.BusinessException;
import com.bs.pipe.mapper.WaterregionMapper;
import com.bs.pipe.service.WaterregionService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class WaterregionServiceImpl implements WaterregionService {

	@Resource
	private WaterregionMapper waterregionMapper;

	@Override
	public List<Waterregion> selectWaterregionList(Waterregion waterregion) {
		// TODO Auto-generated method stub
		List<Waterregion> list = waterregionMapper.selectWaterregionList(waterregion);
		return CollectionUtils.isEmpty(list) ? Collections.emptyList() : list;
	}

	@Override
	public Waterregion selectWaterregion(Waterregion waterregion) {
		// TODO Auto-generated method stub
		return waterregionMapper.selectWaterregion(waterregion);
	}

	@Override
	public void insertWaterregion(Waterregion waterregion) {
		// TODO Auto-generated method stub
		if(waterregionMapper.insertWaterregion(waterregion) != 1){
			throw new BusinessException("添加失败");
		}
	}

	@Override
	public void updateWaterregion(Waterregion waterregion) {
		// TODO Auto-generated method stub
		if(waterregionMapper.updateWaterregion(waterregion) != 1){
			throw new BusinessException("修改失败");
		}
	}

	@Override
	public void deleteWaterregion(Integer id) {
		// TODO Auto-generated method stub
		if(waterregionMapper.deleteWaterregion(id) != 1){
			throw new BusinessException("删除失败");
		}
	}

}
