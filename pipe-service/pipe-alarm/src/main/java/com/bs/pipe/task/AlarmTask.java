package com.bs.pipe.task;

import com.alibaba.fastjson.JSON;
import com.bs.pipe.entity.po.*;
import com.bs.pipe.entity.vo.BuildingVO;
import com.bs.pipe.entity.vo.PiezometerVO;
import com.bs.pipe.enums.BuildingCategory;
import com.bs.pipe.enums.Category;
import com.bs.pipe.enums.StatisticsUnit;
import com.bs.pipe.service.AlarmPressureService;
import com.bs.pipe.service.BuildingService;
import com.bs.pipe.service.PiezometerService;
import com.bs.pipe.service.RtaAlarmStandardService;
import com.bs.pipe.utils.ArithmeticUtil;
import com.bs.pipe.utils.DateHelper;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

@Component
public class AlarmTask {

    @Resource
    private PiezometerService piezometerService;
    @Resource
    private BuildingService buildingService;
    @Resource
    private AlarmPressureService alarmPressureService;
    @Resource
    private RtaAlarmStandardService rtaAlarmStandardService;

    private static final Logger logger = LoggerFactory.getLogger(AlarmTask.class);

    //@Scheduled(cron = "0 */2 * * * ?")
    @Scheduled(cron = "0 5 0 * * ?")
    public void piezometerAlarmTask(){
        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        logger.info("===============武汉工程大学压力系统报警执行开始===============" + DateHelper.format(new Date()));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        String endTime = DateHelper.format(calendar.getTime(), "yyyy-MM-dd") + " 00:00:00";
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        String startTime = DateHelper.format(calendar.getTime(), "yyyy-MM-dd") + " 00:00:00";

        List<RtaAlarmStandard> alarmStandardList = rtaAlarmStandardService.selectRtaAlarmStandardList(null);
        try {
            List<PiezometerVO> pList = piezometerService.selectPressureLogAvgSearch(new Piezometer() , startTime, endTime, StatisticsUnit.HOUR_UNIT.getCode());
            for (PiezometerVO piezometerVO : pList) {
                boolean flag = false;
                List<LinkedHashMap<String,Object>> list = new ArrayList<LinkedHashMap<String,Object>>();
                AlarmPressure alarmPressure = new AlarmPressure(alarmPressureService.createAlarmCode(piezometerVO.getId(), Category.PIEZOMETER.getCode()), Category.PIEZOMETER.getCode(),
                        DateHelper.parseDateIgnoreError(endTime), piezometerVO.getName(), piezometerVO.getLongitude(), piezometerVO.getLatitude(),
                        "1", null, null, "压力点压力值超限报警", null, null, null, null);
                for (RtaAlarmStandard alarmStandard : alarmStandardList) {
                    if(alarmStandard.getRegionId().equals(piezometerVO.getRegionId())){
                        try {
                            //根据高程差计算压力 1m = 0.01MPa ( * 10)
                            Double logDif = ArithmeticUtil.mul(ArithmeticUtil.sub(piezometerVO.getElevation(), alarmStandard.getElevation()), 0.1);
                            Double minScale = ArithmeticUtil.add(alarmStandard.getMinScale(), (logDif > 0 ? logDif * -1 : logDif < 0 ? logDif * -1 : 0));
                            Double maxScale = ArithmeticUtil.add(alarmStandard.getMaxScale(), (logDif > 0 ? logDif * -1 : logDif < 0 ? logDif * -1 : 0));
                            //System.out.println("实际高程：" + piezometerVO.getElevation() + "标准高程： " + alarmStandard.getElevation() + "换算高程差：" + logDif + "标准下限：" + alarmStandard.getMinScale()  + "换算下限：" + minScale + "标准上限：" + alarmStandard.getMaxScale() + "换算上限：" + maxScale);
                            if(CollectionUtils.isNotEmpty(piezometerVO.getLogPressure())) {
                                for (LogPressure logP : piezometerVO.getLogPressure()) {
                                    if (alarmStandard.getCategory().equals("" + logP.getReadTime().getHours())) {
                                        //System.out.println("压力点id：" + piezometerVO.getId() + "压力点时间：" + DateHelper.format(logP.getReadTime()) + "报警类型时间点：" + alarmStandard.getCategory() + "压力值：" + logP.getReadNumber() + "标准下限：" + minScale + "标准上限：" + maxScale);
                                        if (logP.getReadNumber() < minScale || logP.getReadNumber() > maxScale) {
                                            flag = true;
                                            LinkedHashMap<String, Object> map = new LinkedHashMap<>();
                                            map.put("time", DateHelper.format(logP.getReadTime()));
                                            map.put("readNumber", logP.getReadNumber());
                                            map.put("minScale", minScale);
                                            map.put("maxScale", maxScale);
                                            list.add(map);
                                        }
                                    }
                                }
                            }
                        } catch (Exception e) {
                            // TODO: handle exception
                            e.printStackTrace();
                        }
                    }
                }
                if(flag){
                    alarmPressure.setAlarmData(JSON.toJSON(list).toString());
                    alarmPressureService.insertAlarmPressure(alarmPressure);
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        logger.info("===============武汉工程大学压力系统压力点报警执行结束===============" + DateHelper.format(new Date()));
        try {
            List<BuildingVO> bList = buildingService.selectBuildingAndPressure(new Building(), startTime, endTime, StatisticsUnit.HOUR_UNIT.getCode(), BuildingCategory.BUILDING_BOTTOM.getCode());
            for (BuildingVO buildingVO : bList) {
                boolean flag = false;
                List<LinkedHashMap<String,Object>> list = new ArrayList<LinkedHashMap<String,Object>>();
                AlarmPressure alarmPressure = new AlarmPressure(alarmPressureService.createAlarmCode(buildingVO.getId(), Category.BUILDING.getCode()), Category.BUILDING.getCode(),
                        DateHelper.parseDateIgnoreError(endTime), buildingVO.getName(), buildingVO.getLongitude(), buildingVO.getLatitude(),
                        "1", null, null, "建筑一层压力值超限报警", null, null, null, null);
                for (RtaAlarmStandard alarmStandard : alarmStandardList) {
                    if(alarmStandard.getRegionId().equals(buildingVO.getRegionId())){
                        try {
                            //根据高程差计算压力 1m = 0.01MPa
                            Double logDif = ArithmeticUtil.mul(ArithmeticUtil.sub(buildingVO.getElevation(), alarmStandard.getElevation()), 0.1);
                            Double minScale = ArithmeticUtil.add(alarmStandard.getMinScale(), (logDif > 0 ? logDif * -1 : logDif < 0 ? logDif * -1 : 0));
                            Double maxScale = ArithmeticUtil.add(alarmStandard.getMaxScale(), (logDif > 0 ? logDif * -1 : logDif < 0 ? logDif * -1 : 0));
                            if(CollectionUtils.isNotEmpty(buildingVO.getLogPressure())) {
                                for (LogPressure logP : buildingVO.getLogPressure()) {
                                    if (alarmStandard.getCategory().equals("" + logP.getReadTime().getHours())) {
                                        if (logP.getReadNumber() < minScale || logP.getReadNumber() > maxScale) {
                                            flag = true;
                                            LinkedHashMap<String, Object> map = new LinkedHashMap<>();
                                            map.put("time", DateHelper.format(logP.getReadTime()));
                                            map.put("readNumber", logP.getReadNumber());
                                            map.put("minScale", minScale);
                                            map.put("maxScale", maxScale);
                                            list.add(map);
                                        }
                                    }
                                }
                            }
                        } catch (Exception e) {
                            // TODO: handle exception
                            e.printStackTrace();
                        }
                    }
                }
                if(flag){
                    alarmPressure.setAlarmData(JSON.toJSON(list).toString());
                    alarmPressureService.insertAlarmPressure(alarmPressure);
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        logger.info("===============武汉工程大学压力系统建筑一层报警执行结束===============" + DateHelper.format(new Date()));
        try {
            List<BuildingVO> bList = buildingService.selectBuildingAndPressure(new Building(), startTime, endTime, StatisticsUnit.HOUR_UNIT.getCode(), BuildingCategory.BUILDING_BOTTOM.getCode());
            for (BuildingVO buildingVO : bList) {
                boolean flag = false;
                List<LinkedHashMap<String,Object>> list = new ArrayList<LinkedHashMap<String,Object>>();
                AlarmPressure alarmPressure = new AlarmPressure(alarmPressureService.createAlarmCode(buildingVO.getId(), Category.TOPBUILDING.getCode()), Category.TOPBUILDING.getCode(),
                        DateHelper.parseDateIgnoreError(endTime), buildingVO.getName(), buildingVO.getLongitude(), buildingVO.getLatitude(),
                        "1", null, null, "建筑顶层压力值超限报警", null, null, null, null);
                for (RtaAlarmStandard alarmStandard : alarmStandardList) {
                    if(alarmStandard.getRegionId().equals(buildingVO.getRegionId())){
                        try {
                            //根据高程差计算压力 1m = 0.01MPa
                            Double logDif = ArithmeticUtil.mul(ArithmeticUtil.sub(ArithmeticUtil.add(buildingVO.getElevation(),buildingVO.getBuildingHeight()), alarmStandard.getElevation()), 0.1);
                            Double minScale = ArithmeticUtil.add(alarmStandard.getMinScale(), (logDif > 0 ? logDif * -1 : logDif < 0 ? logDif * -1 : 0));
                            Double maxScale = ArithmeticUtil.add(alarmStandard.getMaxScale(), (logDif > 0 ? logDif * -1 : logDif < 0 ? logDif * -1 : 0));
                            if(CollectionUtils.isNotEmpty(buildingVO.getLogPressure())) {
                                for (LogPressure logP : buildingVO.getLogPressure()) {
                                    if (alarmStandard.getCategory().equals("" + logP.getReadTime().getHours())) {
                                        if (logP.getReadNumber() < minScale || logP.getReadNumber() > maxScale) {
                                            flag = true;
                                            LinkedHashMap<String, Object> map = new LinkedHashMap<>();
                                            map.put("time", DateHelper.format(logP.getReadTime()));
                                            map.put("readNumber", logP.getReadNumber());
                                            map.put("minScale", minScale);
                                            map.put("maxScale", maxScale);
                                            list.add(map);
                                        }
                                    }
                                }
                            }
                        } catch (Exception e) {
                            // TODO: handle exception
                            e.printStackTrace();
                        }
                    }
                }
                if(flag){
                    alarmPressure.setAlarmData(JSON.toJSON(list).toString());
                    alarmPressureService.insertAlarmPressure(alarmPressure);
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        logger.info("===============武汉工程大学压力系统建筑顶层报警执行结束===============" + DateHelper.format(new Date()));
        try {
            List<BuildingVO> bList = buildingService.selectHighestBuildingAndPressureByRegion(new Building(), startTime, endTime, StatisticsUnit.HOUR_UNIT.getCode());
            for (BuildingVO buildingVO : bList) {
                boolean flag = false;
                List<LinkedHashMap<String,Object>> list = new ArrayList<LinkedHashMap<String,Object>>();
                AlarmPressure alarmPressure = new AlarmPressure(alarmPressureService.createAlarmCode(buildingVO.getRegionId(), Category.WATERREGION.getCode()), Category.WATERREGION.getCode(),
                        DateHelper.parseDateIgnoreError(endTime), buildingVO.getRegionName(), buildingVO.getLongitude(), buildingVO.getLatitude(),
                        "1", null, null, "区域压力值超限报警", null, null, null, null);
                for (RtaAlarmStandard alarmStandard : alarmStandardList) {
                    if(alarmStandard.getRegionId().equals(buildingVO.getRegionId())){
                        try {
                            //根据高程差计算压力 1m = 0.01MPa
                            Double logDif = ArithmeticUtil.mul(ArithmeticUtil.sub(ArithmeticUtil.add(buildingVO.getElevation(),buildingVO.getBuildingHeight()), alarmStandard.getElevation()), 0.1);
                            Double minScale = ArithmeticUtil.add(alarmStandard.getMinScale(), (logDif > 0 ? logDif * -1 : logDif < 0 ? logDif * -1 : 0));
                            Double maxScale = ArithmeticUtil.add(alarmStandard.getMaxScale(), (logDif > 0 ? logDif * -1 : logDif < 0 ? logDif * -1 : 0));
                            if(CollectionUtils.isNotEmpty(buildingVO.getLogPressure())){
                                for (LogPressure logP : buildingVO.getLogPressure()) {
                                    if(alarmStandard.getCategory().equals("" + logP.getReadTime().getHours())){
                                        if(logP.getReadNumber() < minScale || logP.getReadNumber() > maxScale){
                                            flag = true;
                                            LinkedHashMap<String,Object> map = new LinkedHashMap<>();
                                            map.put("time", DateHelper.format(logP.getReadTime()));
                                            map.put("readNumber", logP.getReadNumber());
                                            map.put("minScale", minScale);
                                            map.put("maxScale", maxScale);
                                            list.add(map);
                                        }
                                    }
                                }
                            }
                        } catch (Exception e) {
                            // TODO: handle exception
                            e.printStackTrace();
                        }
                    }
                }
                if(flag){
                    alarmPressure.setAlarmData(JSON.toJSON(list).toString());
                    alarmPressureService.insertAlarmPressure(alarmPressure);
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        logger.info("===============武汉工程大学压力系统区域(建筑顶层)报警执行结束===============" + DateHelper.format(new Date()));
    }

}
