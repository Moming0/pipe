package com.bs.pipe.service.impl;

import java.util.*;

import com.bs.pipe.entity.po.LogPressure;
import com.bs.pipe.exception.BusinessException;
import com.bs.pipe.mapper.LogPressureMapper;
import com.bs.pipe.service.LogPressureService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class LogPressureServiceImpl implements LogPressureService {

	@Resource
	private LogPressureMapper logPressureMapper;

	@Override
	public List<LogPressure> selectLogPressureList(LogPressure logPressure, String startTime, String endTime) {
		// TODO Auto-generated method stub
		List<LogPressure> list = logPressureMapper.selectLogPressureList(logPressure, startTime, endTime);
		return CollectionUtils.isEmpty(list) ? Collections.emptyList() : list;
	}

	@Override
	public LogPressure selectLogPressure(LogPressure logPressure) {
		// TODO Auto-generated method stub
		return logPressureMapper.selectLogPressure(logPressure);
	}

	@Override
	public void insertLogPressure(LogPressure logPressure) {
		// TODO Auto-generated method stub
		if(logPressureMapper.insertLogPressure(logPressure) != 1){
			throw new BusinessException("添加失败");
		}
	}

	@Override
	public void updateLogPressure(LogPressure logPressure) {
		// TODO Auto-generated method stub
		if(logPressureMapper.updateLogPressure(logPressure) != 1){
			throw new BusinessException("修改失败");
		}
	}

	@Override
	public void deleteLogPressure(Integer id) {
		// TODO Auto-generated method stub
		if(logPressureMapper.deleteLogPressure(id) != 1){
			throw new BusinessException("删除失败");
		}
	}

	@Override
	public void insertLogPressureList(List<LogPressure> logPressureList) {
		// TODO Auto-generated method stub
		if(logPressureMapper.insertLogPressureList(logPressureList) <= 0){
			throw new BusinessException("批量添加失败");
		}
	}

}
