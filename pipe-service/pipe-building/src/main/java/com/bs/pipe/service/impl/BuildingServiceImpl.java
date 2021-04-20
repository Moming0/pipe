package com.bs.pipe.service.impl;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.bs.pipe.entity.po.*;
import com.bs.pipe.entity.vo.BuildingVO;
import com.bs.pipe.entity.vo.PiezometerVO;
import com.bs.pipe.entity.vo.WaterregionVO;
import com.bs.pipe.enums.BuildingCategory;
import com.bs.pipe.enums.PizometerCategory;
import com.bs.pipe.exception.BusinessException;
import com.bs.pipe.mapper.BuildingMapper;
import com.bs.pipe.service.*;
import com.bs.pipe.utils.ArithmeticUtil;
import com.bs.pipe.utils.DistanceUtil;
import com.bs.pipe.utils.depCopyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class BuildingServiceImpl implements BuildingService {

	@Resource
	private BuildingMapper buildingMapper;
	@Resource
	private ElevationNodeService elevationNodeService;
	@Resource
	private PiezometerService piezometerService;
	@Resource
	private WaterregionService waterregionService;
	@Resource
	private RtaAlarmStandardService rtaAlarmStandardService;

    @Override
    public List<Building> selectBuildingList(Building building) {
        // TODO Auto-generated method stub
        List<Building> list = buildingMapper.selectBuildingList(building);
        List<Waterregion> waterregionList = waterregionService.selectWaterregionList(null);
        for (Building build : list) {
            for (Waterregion waterregion : waterregionList) {
                if(build.getRegionId().equals(waterregion.getId())){
                    build.setRegionName(waterregion.getName());
                }
            }
        }
        return CollectionUtils.isEmpty(list) ? Collections.emptyList() : list;
    }

    @Override
    public Building selectBuilding(Building building) {
        // TODO Auto-generated method stub
        return buildingMapper.selectBuilding(building);
    }

    @Override
    public void insertBuilding(Building building) {
        // TODO Auto-generated method stub
        if(buildingMapper.insertBuilding(building) != 1){
            throw new BusinessException("添加失败");
        }
    }

    @Override
    public void updateBuilding(Building building) {
        // TODO Auto-generated method stub
        if(buildingMapper.updateBuilding(building) != 1){
            throw new BusinessException("修改失败");
        }
    }

    @Override
    public void deleteBuilding(Integer id) {
        // TODO Auto-generated method stub
        if(buildingMapper.deleteBuilding(id) != 1){
            throw new BusinessException("删除失败");
        }
    }

    @Override
    public List<Building> selectBuildingAndElevation(Building building) {
        // TODO Auto-generated method stub
        List<Building> buildingList = this.selectBuildingList(building);
        if(CollectionUtils.isEmpty(buildingList)){
            return Collections.emptyList();
        } else {
            List<ElevationNode> elevationNodeList = elevationNodeService.selectElevationNodeList(null);
            if(CollectionUtils.isEmpty(elevationNodeList)){
                return buildingList;
            } else {
                for (Building b : buildingList) {
                    Double dSum = null;
                    for (ElevationNode eNode : elevationNodeList) {
                        double distance = DistanceUtil.getDistance(b.getLongitude(), b.getLatitude(), eNode.getLongitude(), eNode.getLatitude());
                        if(dSum == null){
                            dSum = distance;
                            b.setElevation(eNode.getElevation());
                        } else {
                            if(distance <= dSum){
                                dSum = distance;
                                b.setElevation(eNode.getElevation());
                            }
                        }
                    }
                }
                return buildingList;
            }
        }
    }

    @Override
    public List<BuildingVO> selectBuildingAndPressure(Building building, String startTime, String endTime, String type, String buildingCateory) {
        // TODO Auto-generated method stub
        if((StringUtils.isEmpty(startTime) || StringUtils.isEmpty(endTime)) || (!isDateTime(startTime) || !isDateTime(endTime))){
            throw new BusinessException("请正确选择时间");
        }
        //List<Building> buildingList = this.selectBuildingAndElevation(building);	//实时计算高程
        List<Building> buildingList = this.selectBuildingList(building);	//建筑信息默认高程
        if(CollectionUtils.isEmpty(buildingList)){
            return Collections.emptyList();
        } else {
            Piezometer piezometer = new Piezometer();
            piezometer.setPipeCategory(PizometerCategory.NORMAL_PIPE.getCode());
            if(building != null && building.getRegionId() != null){
                piezometer.setRegionId(building.getRegionId());
            }
            List<PiezometerVO> piezometerList = piezometerService.selectPressureLogAvgSearch(piezometer, startTime, endTime, type);
            List<BuildingVO> buildingVOList = buildingList.stream().map(a -> builderBuildingVO(a)).collect(Collectors.toList());
            if(CollectionUtils.isEmpty(piezometerList)){
                return buildingVOList;
            } else {
                for (BuildingVO build : buildingVOList) {
                    Double dSum = null;
                    PiezometerVO mNode = new PiezometerVO();
                    for (PiezometerVO pNode : piezometerList) {
                        if(build.getRegionId().equals(pNode.getRegionId())){
                            double distance = DistanceUtil.getDistance(build.getLongitude(), build.getLatitude(), pNode.getLongitude(), pNode.getLatitude());
                            if(dSum == null){
                                dSum = distance;
                                mNode = pNode;
                            } else {
                                if(distance <= dSum){
                                    dSum = distance;
                                    mNode = pNode;
                                }
                            }
                        }
                    }
                    if(CollectionUtils.isNotEmpty(mNode.getLogPressure())){
                        if(build.getElevation() == null){
                            throw new BusinessException("建筑缺少必要信息“高程”");
                        }
                        if(mNode.getElevation() == null){
                            throw new BusinessException("压力监测点缺少必要信息“高程”");
                        }
                        if(StringUtils.equals(buildingCateory, BuildingCategory.BUILDING_TOP.getCode()) && build.getBuildingHeight() == null){
                            throw new BusinessException("建筑缺少必要信息“建筑高度”");
                        }
                        //根据高程差计算压力 1m = 0.01MPa(每升高1m降低0.01兆帕)（单位为1MPa * 10）
                        Double logDif = StringUtils.equals(buildingCateory, BuildingCategory.BUILDING_TOP.getCode()) ? (build.getElevation() + build.getBuildingHeight() - mNode.getElevation()) * 0.1 : (build.getElevation() - mNode.getElevation()) * 0.1;
                        //深拷贝集合对象
                        List<LogPressure> logPressureCopy = depCopyUtils.depCopy(mNode.getLogPressure());
                        logPressureCopy.forEach(m -> m.setReadNumber(m.getReadNumber() + (logDif > 0 ? logDif * -1 : logDif < 0 ? logDif * -1 : 0)));
                        build.setLogPressure(logPressureCopy);
                    }
                }
                return buildingVOList;
            }
        }
    }

    @Override
    public List<BuildingVO> selectHighestBuildingAndPressureByRegion(Building building, String startTime, String endTime, String type) {
        List<BuildingVO> buildingVO = this.selectBuildingAndPressure(building, startTime, endTime, type, BuildingCategory.BUILDING_TOP.getCode());
        Map<Integer, List<BuildingVO>> collect = buildingVO.stream().collect(Collectors.groupingBy(BuildingVO::getRegionId));
        List<BuildingVO> list = new ArrayList<BuildingVO>();
        collect.forEach((k,v) ->{
            list.add(v.stream().max(Comparator.comparingDouble(a -> a.getElevation() + a.getBuildingHeight())).get());
        });
        return list;
    }

    @Override
    public List selectBuildingPressureScale(Building building, String buildingCateory) {
        // TODO Auto-generated method stub
        //List<Building> bList = this.selectBuildingAndElevation(building);	//实时计算高程
        List<Building> bList = this.selectBuildingList(building);	//建筑信息默认高程
        List<RtaAlarmStandard> alarmStandardList = rtaAlarmStandardService.selectRtaAlarmStandardList(null);
        List<LinkedHashMap<String,Object>> list = new ArrayList<LinkedHashMap<String,Object>>();
        for (Building b : bList) {
            LinkedHashMap<String,Object> map = new LinkedHashMap<>();
            map.put("id", b.getId());
            map.put("name", b.getName());
            List<LinkedHashMap<String,Object>> pressureScale = new ArrayList<LinkedHashMap<String,Object>>();
            for (RtaAlarmStandard alarmStandard : alarmStandardList) {
                if(alarmStandard.getRegionId().equals(b.getRegionId())){
                    //根据高程差计算压力 1m = 0.01MPa
                    Double logDif = ArithmeticUtil.mul(ArithmeticUtil.sub(StringUtils.equals(buildingCateory, BuildingCategory.BUILDING_TOP.getCode()) ? ArithmeticUtil.add(b.getElevation(),b.getBuildingHeight()) : b.getElevation(), alarmStandard.getElevation()), 0.01);
                    Double minScale = ArithmeticUtil.add(alarmStandard.getMinScale(), (logDif > 0 ? logDif * -1 : logDif < 0 ? logDif * -1 : 0));
                    Double maxScale = ArithmeticUtil.add(alarmStandard.getMaxScale(), (logDif > 0 ? logDif * -1 : logDif < 0 ? logDif * -1 : 0));
                    LinkedHashMap<String,Object> mapScale = new LinkedHashMap<>();
                    mapScale.put("time", alarmStandard.getCategory());
                    mapScale.put("minScale", minScale);
                    mapScale.put("maxScale", maxScale);
                    pressureScale.add(mapScale);
                }
            }
            map.put("pressureScale", pressureScale);
            list.add(map);
        }
        return list;
    }

    @Override
    public void setBuildingElevation(Building building) {
        // TODO Auto-generated method stub
        List<Building> bList = this.selectBuildingAndElevation(building);	//实时计算高程
        for (Building b : bList) {
            Building updataBuilding = new Building();
            updataBuilding.setId(b.getId());
            updataBuilding.setElevation(b.getElevation());
            this.updateBuilding(updataBuilding);
        }
    }

    @Override
    public List<WaterregionVO> selectWaterregionAndPressure(Waterregion waterregion, String startTime, String endTime, String type) {
        if((StringUtils.isEmpty(startTime) || StringUtils.isEmpty(endTime)) || (!isDateTime(startTime) || !isDateTime(endTime))){
            throw new BusinessException("请正确选择时间");
        }
        List<Waterregion> waterregionList = waterregionService.selectWaterregionList(waterregion);
        if(CollectionUtils.isEmpty(waterregionList)){
            return Collections.emptyList();
        } else {
            List<BuildingVO> buildingVO = this.selectBuildingAndPressure(new Building(), startTime, endTime, type, BuildingCategory.BUILDING_TOP.getCode());
            List<WaterregionVO> waterregionVOList = waterregionList.stream().map(a -> this.builderWaterregionVO(a)).collect(Collectors.toList());
            if(CollectionUtils.isEmpty(buildingVO)){
                return waterregionVOList;
            } else {
                Map<Integer, List<BuildingVO>> collect = buildingVO.stream().collect(Collectors.groupingBy(BuildingVO::getRegionId));
                for (WaterregionVO waterregionVO : waterregionVOList) {
                    collect.forEach((k,v) ->{
                        if(k.equals(waterregionVO.getId())){
                            waterregionVO.setLogPressure(v.stream().max(Comparator.comparingDouble(a -> a.getElevation() + a.getBuildingHeight())).get().getLogPressure());
                        }
                    });
                }
                return waterregionVOList;
            }
        }
    }

    private WaterregionVO builderWaterregionVO(Waterregion waterregion){
        if (waterregion == null) {
            return null;
        } else {
            WaterregionVO waterregionVO = new WaterregionVO();
            BeanUtils.copyProperties(waterregion,waterregionVO);
            return waterregionVO;
        }

    }

    private BuildingVO builderBuildingVO(Building building) {
        if (building == null) {
            return null;
        } else {
            BuildingVO buildingVO = new BuildingVO();
            BeanUtils.copyProperties(building,buildingVO);
            return buildingVO;
        }
    }

    /**
     * 正则表达式时间“YYYY-MM-DD HH:mm:ss”和“YYYY-MM-DD”
     * @param datetime
     * @return
     */
    public static boolean isDateTime(String datetime) {
        Pattern p = Pattern.compile(
                "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1][0-9])|([2][0-4]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
        return p.matcher(datetime).matches();
    }

}
