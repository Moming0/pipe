package com.bs.pipe.service.impl;

import java.util.*;

import com.bs.pipe.entity.po.ElevationNode;
import com.bs.pipe.exception.BusinessException;
import com.bs.pipe.mapper.ElevationNodeMapper;
import com.bs.pipe.service.ElevationNodeService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class ElevationNodeServiceImpl implements ElevationNodeService {

	@Resource
	private ElevationNodeMapper elevationNodeMapper;

	@Override
	public List<ElevationNode> selectElevationNodeList(ElevationNode elevationNode) {
		// TODO Auto-generated method stub
		List<ElevationNode> list = elevationNodeMapper.selectElevationNodeList(elevationNode);
		return CollectionUtils.isEmpty(list) ? Collections.emptyList() : list;
	}

	@Override
	public ElevationNode selectElevationNode(ElevationNode elevationNode) {
		// TODO Auto-generated method stub
		return elevationNodeMapper.selectElevationNode(elevationNode);
	}

	@Override
	public void insertElevationNode(ElevationNode elevationNode) {
		// TODO Auto-generated method stub
		if (this.elevationNodeExistWhenCreate(elevationNode)) {
			throw new BusinessException("该经纬度已存在高程点");
		} else {
			if(elevationNodeMapper.insertElevationNode(elevationNode) != 1){
				throw new BusinessException("添加失败");
			}
		}
	}

	@Override
	public void updateElevationNode(ElevationNode elevationNode) {
		// TODO Auto-generated method stub
		if (this.elevationNodeExistWhenUpdate(elevationNode)) {
			throw new BusinessException("该经纬度已存在高程点");
		} else {
			if(elevationNodeMapper.updateElevationNode(elevationNode) != 1){
				throw new BusinessException("修改失败");
			}
		}
		
	}

	@Override
	public void deleteElevationNode(Integer id) {
		// TODO Auto-generated method stub
		if(elevationNodeMapper.deleteElevationNode(id) != 1){
			throw new BusinessException("删除失败");
		}
	}

	private boolean elevationNodeExistWhenCreate(ElevationNode elevationNode) {
		return elevationNodeMapper.elevationNodeExistWhenCreate(elevationNode);
	}

	private boolean elevationNodeExistWhenUpdate(ElevationNode elevationNode) {
		return elevationNodeMapper.elevationNodeExistWhenUpdate(elevationNode);
	}

}
