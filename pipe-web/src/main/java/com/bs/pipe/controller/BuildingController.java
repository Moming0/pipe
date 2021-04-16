package com.bs.pipe.controller;

import java.util.List;

import com.bs.pipe.entity.po.Building;
import com.bs.pipe.entity.vo.BuildingVO;
import com.bs.pipe.service.BuildingService;
import com.bs.pipe.utils.DPage;
import com.bs.pipe.utils.ResObject;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/building")
public class BuildingController {
	
	@Resource
	private BuildingService buildingService;

	/**
	 * 所有建筑信息（ID，名称模糊，区域ID，经纬度）
	 * @param building
	 * @return
	 */
	@GetMapping
	public ResObject selectBuildingList(Building building) {
		List<Building> list = buildingService.selectBuildingList(building);
		return ResObject.ok(list);
	}
	
	/**
	 * 添加建筑信息
	 * @param building
	 * @return
	 */
	@PostMapping
	public ResObject insertBuilding(Building building) {
		buildingService.insertBuilding(building);
		return ResObject.ok();
	}

	/**
	 * 修改建筑信息
	 * @param building
	 * @return
	 */
	@PutMapping
	public ResObject updateBuilding(Building building) {
		buildingService.updateBuilding(building);
		return ResObject.ok();
	}

	/**
	 * 删除建筑信息
	 * 
	 * @param id
	 * @return
	 */
    @DeleteMapping("/{id}")
	public ResObject deleteBuilding(@PathVariable("id") Integer id) {
		buildingService.deleteBuilding(id);
		return ResObject.ok();
	}
	
    /**
     * 获得建筑(一层顶层)及对应高程多记录（分页，ID，区域，经纬度）
     * @param pageNum   当前页
     * @param pageSize  当前页大小
     * @param Building  (ID，区域，经纬度)
     * @return
     */
    @GetMapping("/elevation")
	public ResObject selectBuildingAndElevation(
			@RequestParam(value = "pageNum", defaultValue = "1", required = true) Integer pageNum,
			@RequestParam(value = "pageSize", defaultValue = "5", required = true) Integer pageSize,
			Building Building) {
		List<Building> list = buildingService.selectBuildingAndElevation(Building);
		DPage<Building> dpage = new DPage<Building>(list, pageNum, pageSize);
		return ResObject.ok(dpage);
	}

    /**
     * 获取建筑高程相对一层/顶层压力值记录（分页，ID，名称模糊，区域，经纬度，时间范围，时间单位-1:原始数据，0:小时，1:日，建筑类型：0底层1顶层）
     * @param building
     * @param startTime	开始时间
     * @param endTime	结束时间
     * @param type	单位类型：-1:原始数据，0:小时，1:日
     * @param buildingCateory	建筑类型：0底层，1顶层
     * @return
     */
    @GetMapping("/pressure")
	public ResObject selectBuildinAndPressure(
			@RequestParam(value = "pageNum", defaultValue = "1", required = true) Integer pageNum,
			@RequestParam(value = "pageSize", defaultValue = "5", required = true) Integer pageSize,
			Building building, String startTime, String endTime, String type,
			String buildingCateory) {
		List<BuildingVO> list = buildingService.selectBuildingAndPressure(building, startTime, endTime, type, buildingCateory);
		DPage<BuildingVO> dpage = new DPage<BuildingVO>(list, pageNum, pageSize);
		return ResObject.ok(dpage);
	}

    /**
     * 获取每个区域最高建筑楼顶相对压力值记录（时间范围，时间单位-1:原始数据，0:小时，1:日）
     * @param startTime	开始时间
     * @param endTime	结束时间
     * @param type	单位类型：-1:原始数据，0:小时，1:日
     * @return
     */
    @GetMapping("/highest/pressure")
    public ResObject selectHighestBuildingAndPressureByRegion(String startTime, String endTime, String type) {
        List<BuildingVO> list = buildingService.selectHighestBuildingAndPressureByRegion(null, startTime, endTime, type);
        return ResObject.ok(list);
    }

	/**
	 * 查询建筑一层/顶层的压力标准上下限范围信息（ID，名称模糊，区域，经纬度，建筑类型：0底层1顶层）
	 * @param building
	 * @param buildingCateory	建筑类型：0底层，1顶层
	 * @return
	 */
    @GetMapping("/scale")
	public ResObject selectBuildingPressureScale(Building building, String buildingCateory) {
		List list = buildingService.selectBuildingPressureScale(building, buildingCateory);
		return ResObject.ok(list);
	}

    /**
     * 计算建筑高程信息自动录入(可设置单个建筑id)
     * @param building(建筑id)
     * @return
     */
    @GetMapping("/setBuildingElevation")
    public ResObject setBuildingElevation(Building building) {
        buildingService.setBuildingElevation(building);
        return ResObject.ok();
    }
}
