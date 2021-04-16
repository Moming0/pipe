package com.bs.pipe.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.bs.pipe.dto.PiezometerDTO;
import com.bs.pipe.entity.po.LogPressure;
import com.bs.pipe.entity.po.Piezometer;
import com.bs.pipe.entity.po.PressureData;
import com.bs.pipe.exception.BusinessException;
import com.bs.pipe.mapper.PressureDataMapper;
import com.bs.pipe.service.LogPressureService;
import com.bs.pipe.service.PiezometerService;
import com.bs.pipe.service.PressureDataService;
import com.bs.pipe.utils.DateHelper;
import com.bs.pipe.utils.JsonUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class PressureDataServiceImpl implements PressureDataService {

	@Resource
	private PressureDataMapper pressureDataMapper;
	@Resource
	private PiezometerService piezometerService;
	@Resource
	private LogPressureService logPressureService;
    @Value("${PRESSURE_ISONLINE}")
    private String PRESSURE_ISONLINE;

    private static final Logger logger = LoggerFactory.getLogger(PressureDataServiceImpl.class);

	@Override
	public List<PressureData> selectPressureDataList(PressureData pressureData, String startTime, String endTime) {
		// TODO Auto-generated method stub
		List<PressureData> list = pressureDataMapper.selectPressureDataList(pressureData, startTime, endTime);
		return CollectionUtils.isEmpty(list) ? Collections.emptyList() : list;
	}

	@Override
	public PressureData selectPressureData(PressureData pressureData) {
		// TODO Auto-generated method stub
		return pressureDataMapper.selectPressureData(pressureData);
	}

	@Override
	public void insertPressureData(PressureData pressureData) {
		// TODO Auto-generated method stub
		if(pressureDataMapper.insertPressureData(pressureData) != 1){
			throw new BusinessException("添加失败");
		}
	}

	@Override
	public void updatePressureData(PressureData pressureData) {
		// TODO Auto-generated method stub
		if(pressureDataMapper.updatePressureData(pressureData) != 1){
			throw new BusinessException("修改失败");
		}
	}

	@Override
	public void deletePressureData(Integer id) {
		// TODO Auto-generated method stub
		if(pressureDataMapper.deletePressureData(id) != 1){
			throw new BusinessException("删除失败");
		}
	}
/*

	@Override
	public Object insertPressureDataList(String json) {
		// TODO Auto-generated method stub
        logger.info("武汉工程大学压力推送数据JSON==============" + json);
        List<PressureData> pressureDataList = JsonUtil.jsonToList(json, PressureData.class);
		HashMap<String, Object> map = new HashMap<String, Object>();
		ArrayList<LogPressure> logPList = new ArrayList<LogPressure>();
		try {
			for (PressureData pressureData : pressureDataList) {
				if(StringUtils.isBlank(pressureData.getLocCode())){
					map.put("code", 100000);
					map.put("errorMsg", "表单校验异常");
					map.put("validMap", "设备编码不能为空");
					return map;
				}
				if(StringUtils.isBlank(pressureData.getLocName())){
					map.put("code", 100000);
					map.put("errorMsg", "表单校验异常");
					map.put("validMap", "位置名称不能为空");
					return map;
				}
				if(pressureData.getPressure() == null){
					map.put("code", 100000);
					map.put("errorMsg", "表单校验异常");
					map.put("validMap", "压力不能为空");
					return map;
				}
				if(pressureData.getDataTime() == null){
					map.put("code", 100000);
					map.put("errorMsg", "表单校验异常");
					map.put("validMap", "数据时刻不能为空");
					return map;
				}
				if(pressureData.getRequestTime() == null){
					map.put("code", 100000);
					map.put("errorMsg", "表单校验异常");
					map.put("validMap", "请求时间不能为空");
					return map;
				}
				Piezometer piezometer = piezometerService.selectPiezometerBySnumber(pressureData.getLocCode());
				if(piezometer == null){
					piezometer = piezometerService.selectPiezometerByName(pressureData.getLocName());
				}
				if(piezometer != null){
					LogPressure logP = new LogPressure();
					logP.setPressureId(piezometer.getId());
					logP.setReadTime(pressureData.getDataTime());
                    //1 千帕(kpa) = 0.001 兆帕(mpa)（单位：兆帕(mpa) * 10）
                    logP.setReadNumber(pressureData.getPressure() * 0.001 * 10);
					logPList.add(logP);
				}
			}
			if(pressureDataMapper.insertPressureDataList(pressureDataList) <= 0){
				map.put("code", 500);
				map.put("errorMsg", "系统繁忙，请稍后再试！");
				map.put("techMsg", "服务pipe-server失去联系");
				return map;
			} else {
				try {
					if(CollectionUtils.isNotEmpty(logPList)){
						logPressureService.insertLogPressureList(logPList);
					}
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println("===============压力数据转换录入失败！！！===============");
				}

				map.put("code", 200);
				map.put("errorMsg", null);
				return map;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			map.put("code", 500);
			map.put("errorMsg", "系统繁忙，请稍后再试！");
			map.put("techMsg", "服务pipe-server失去联系");
			return map;
		}
	}
*/


    @Override
    public Object insertPressureDataList(String json) {
        // TODO Auto-generated method stub
        logger.info("武汉工程大学压力推送数据JSON==============" + json);
        List<PressureData> pressureDataList = JsonUtil.jsonToList(json, PressureData.class);
        HashMap<String, Object> map = new HashMap<String, Object>();
        ArrayList<LogPressure> logPList = new ArrayList<LogPressure>();
        try {
            List<PiezometerDTO> piezometerList = piezometerService.selectPiezometerListAndLastLog(null);
            for (PiezometerDTO piezometerDTO : piezometerList) {
                List<PressureData> collect = new ArrayList<PressureData>();
                collect = pressureDataList.stream().filter(p -> p.getLocCode().equals(piezometerDTO.getSnumber())).collect(Collectors.toList());
                if(CollectionUtils.isEmpty(collect)){
                    collect = pressureDataList.stream().filter(p -> p.getLocName().equals(piezometerDTO.getName())).collect(Collectors.toList());
                    if(CollectionUtils.isEmpty(collect)){
                        if((DateHelper.differHours(piezometerDTO.getLastReadTime(), new Date()) > Integer.parseInt(PRESSURE_ISONLINE)) && piezometerDTO.getIsOnline()){
                            //没有数据掉线
                            Piezometer piezometer = new Piezometer();
                            piezometer.setId(piezometerDTO.getId());
                            piezometer.setIsOnline(false);		//掉线
                            piezometerService.updatePiezometer(piezometer);
                            continue;
                        }
                    }
                }
                for (int i = 0; i < collect.size(); i++) {
                    PressureData pressureData = collect.get(i);
                    if(StringUtils.isBlank(pressureData.getLocCode())){
                        map.put("code", 100000);
                        map.put("errorMsg", "表单校验异常");
                        map.put("validMap", "设备编码不能为空");
                        return map;
                    }
                    if(StringUtils.isBlank(pressureData.getLocName())){
                        map.put("code", 100000);
                        map.put("errorMsg", "表单校验异常");
                        map.put("validMap", "位置名称不能为空");
                        return map;
                    }
                    if(pressureData.getPressure() == null){
                        map.put("code", 100000);
                        map.put("errorMsg", "表单校验异常");
                        map.put("validMap", "压力不能为空");
                        return map;
                    }
                    if(pressureData.getDataTime() == null){
                        map.put("code", 100000);
                        map.put("errorMsg", "表单校验异常");
                        map.put("validMap", "数据时刻不能为空");
                        return map;
                    }
                    if(pressureData.getRequestTime() == null){
                        map.put("code", 100000);
                        map.put("errorMsg", "表单校验异常");
                        map.put("validMap", "请求时间不能为空");
                        return map;
                    }
                    LogPressure logP = new LogPressure();
                    logP.setPressureId(piezometerDTO.getId());
                    logP.setReadTime(pressureData.getDataTime());
                    //1 千帕(kpa) = 0.001 兆帕(mpa)（单位：兆帕(mpa) * 10）
                    logP.setReadNumber(pressureData.getPressure() * 0.001 * 10);
                    logPList.add(logP);
                    if(i == collect.size()-1){
                        if((DateHelper.differHours(logP.getReadTime(), new Date()) > Integer.parseInt(PRESSURE_ISONLINE))){ //掉线
                            if(piezometerDTO.getIsOnline()){
                                Piezometer piezometer = new Piezometer();
                                piezometer.setId(piezometerDTO.getId());
                                piezometer.setIsOnline(false);
                                piezometerService.updatePiezometer(piezometer);
                            }
                        } else {    //在线
                            if(!piezometerDTO.getIsOnline()){
                                Piezometer piezometer = new Piezometer();
                                piezometer.setId(piezometerDTO.getId());
                                piezometer.setIsOnline(true);		//在线
                                piezometerService.updatePiezometer(piezometer);
                            }
                        }
                    }
                }
            }
            Integer num = pressureDataMapper.insertPressureDataList(pressureDataList);
            if(num < 0){
                logger.info("系统繁忙，请稍后再试！！！！！！");
                map.put("code", 500);
                map.put("errorMsg", "系统繁忙，请稍后再试！");
                map.put("techMsg", "服务pipe-server失去联系");
                return map;
            } else {
                if(num > 0){
                    try {
                        if(CollectionUtils.isNotEmpty(logPList)){
                            logPressureService.insertLogPressureList(logPList);
                        }
                    } catch (Exception e) {
                        // TODO: handle exception
                        logger.info("===============武汉工程大学压力数据(转换)录入失败！！！===============");
                    }
                    logger.info("===============武汉工程大学压力数据(转换)录入成功==============" + logPList.toString());
                } else if (num == 0){
                    logger.info("===============武汉工程大学压力推送数据录入无更新！！！==============");
                }
                map.put("code", 200);
                map.put("errorMsg", null);
                return map;
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            logger.info("===============武汉工程大学压力推送数据录入失败！！！==============");
            map.put("code", 500);
            map.put("errorMsg", "系统繁忙，请稍后再试！");
            map.put("techMsg", "服务pipe-server失去联系");
            return map;
        }
    }

}
