package com.bs;

import com.bs.pipe.dto.PiezometerDTO;
import com.bs.pipe.entity.po.LogPressure;
import com.bs.pipe.entity.po.Piezometer;
import com.bs.pipe.entity.po.PressureData;
import com.bs.pipe.entity.vo.BuildingVO;
import com.bs.pipe.enums.BuildingCategory;
import com.bs.pipe.mapper.PiezometerMapper;
import com.bs.pipe.service.BuildingService;
import com.bs.pipe.service.LogPressureService;
import com.bs.pipe.service.PiezometerService;
import com.bs.pipe.service.PressureDataService;
import com.bs.pipe.utils.ArithmeticUtil;
import com.bs.pipe.utils.DateHelper;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@SpringBootTest
class PipeWebApplicationTests {
 /*   @Resource
    private PiezometerMapper piezometerMapper;
    @Resource
    private LogPressureService logPressureService;
    @Resource
    private PressureDataService pressureDataService;
    @Resource
    private PiezometerService piezometerService;
    @Resource
    private BuildingService buildingService;
	@Test
	void contextLoads() {

            Double d = 1.123456;
            LogPressure log1 = new LogPressure();
            log1.setReadNumber(d);
            System.out.println(log1);
            LogPressure log2 = new LogPressure(1, 2, new Date(), d, "a", "a", "a", new Date(), new Date(), false);

            System.out.println(log2);
	}

	@Test
    void pressureDataToLog(){

        List<Piezometer> piezometers = piezometerMapper.selectPiezometerList(null);
        ArrayList<LogPressure> logPList = new ArrayList<LogPressure>();
        for (Piezometer piezometer : piezometers) {

            PressureData pressureData = new PressureData();
            pressureData.setLocCode(piezometer.getSnumber());
            List<PressureData> pressureDataList = pressureDataService.selectPressureDataList(pressureData, "2021-04-07 11:32:55", "2021-04-07 11:35:52");
            for (PressureData pData : pressureDataList) {
                LogPressure logP = new LogPressure();
                logP.setPressureId(piezometer.getId());
                logP.setReadTime(pData.getDataTime());
                //1 千帕(kpa) = 0.001 兆帕(mpa)
                logP.setReadNumber(pData.getPressure() * 0.001);
                logPList.add(logP);
            }

        }
        System.out.println(logPList.toString());
        logPressureService.insertLogPressureList(logPList);
    }

    @Test
    void test2(){
        LogPressure logP = new LogPressure();
        logP.setReadTime(DateHelper.parseDateIgnoreError("2021-04-08 00:00:00"));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        System.out.println(logP.getReadTime().compareTo(calendar.getTime()) <= 0 && true);

        double sub = ArithmeticUtil.sub(1.1, 1.2);
        System.out.println(sub);

    }

    @Test
    public void number1(){
        //根据高程差计算压力 1m = 0.01MPa
        Double elevation = 10.123;
        Double logElevation = 5.321;
        Double logPressureNumber = 6.456;
        Double logDif = (elevation - logElevation) * 0.01;
        Double number = logPressureNumber + (logDif > 0 ? logDif * -1 : logDif < 0 ? logDif * -1 : 0);
        System.out.println(number);
    }

    @Test
    public void number2(){
        //根据高程差计算压力 1m = 0.01MPa
        Double elevation = 10.123;
        Double logElevation = 5.321;
        Double logPressureNumber = 6.456;
        Double logDif = ArithmeticUtil.mul(ArithmeticUtil.sub(elevation, logElevation), 0.01);
        Double minScale = ArithmeticUtil.add(logPressureNumber, (logDif > 0 ? logDif * -1 : logDif < 0 ? logDif * -1 : 0));
        Double maxScale = ArithmeticUtil.add(logPressureNumber, (logDif > 0 ? logDif * -1 : logDif < 0 ? logDif * -1 : 0));
        System.out.println(minScale);
        System.out.println(maxScale);
    }

    @Test
    public void selectMaxBuildingAndPressureByRegion(){
        List<BuildingVO> buildingVO = buildingService.selectBuildingAndPressure(null, "2021-04-09 14:00:00", "2021-04-09 15:00:00", "1", BuildingCategory.BUILDING_TOP.getCode());
        Map<Integer, List<BuildingVO>> collect = buildingVO.stream().collect(Collectors.groupingBy(BuildingVO::getRegionId));
        List<BuildingVO> list = new ArrayList<BuildingVO>();


        collect.forEach((k,v) ->{
            for (BuildingVO vo : v) {
                System.out.println("区域编号：" + vo.getRegionId() + "编号：" + vo.getId() + "高度：" + (vo.getElevation() + vo.getBuildingHeight()));
            }
            list.add(v.stream().max(Comparator.comparingDouble(a -> a.getElevation() + a.getBuildingHeight())).get());
        });
        for (BuildingVO vo : list) {
            System.out.println("区域编号：" + vo.getRegionId() + "编号：" + vo.getId() + "高度：" + (vo.getElevation() + vo.getBuildingHeight()));
            System.out.println(vo.getLogPressure());

        }
    }
    */

}
