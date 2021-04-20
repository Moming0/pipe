package com.bs.pipe.controller;

import java.util.List;

import com.bs.pipe.entity.po.Waterregion;
import com.bs.pipe.entity.vo.WaterregionVO;
import com.bs.pipe.service.BuildingService;
import com.bs.pipe.service.WaterregionService;
import com.bs.pipe.utils.DPage;
import com.bs.pipe.utils.ResObject;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/waterregion")
public class WaterregionController {
	
	@Resource
	private WaterregionService waterregionService;
	@Resource
    private BuildingService buildingService;

	/**
	 * 所有区域信息（ID，名字模糊）
	 * @param waterregion
	 * @return
	 */
	@GetMapping
	public ResObject selectWaterregionList(Waterregion waterregion) {
		List<Waterregion> list = waterregionService.selectWaterregionList(waterregion);
		return ResObject.ok(list);
	}

	/**
	 * 添加区域信息
	 * @param waterregion
	 * @return
	 */
	@PostMapping
	public ResObject insertWaterregion(Waterregion waterregion) {
		waterregionService.insertWaterregion(waterregion);
		return ResObject.ok();
	}

	/**
	 * 修改区域信息
	 * @param waterregion
	 * @return
	 */
	@PutMapping
	public ResObject updateWaterregion(Waterregion waterregion) {
		waterregionService.updateWaterregion(waterregion);
		return ResObject.ok();
	}

	/**
	 * 删除区域信息
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping("/{id}")
	public ResObject deleteWaterregion(@PathVariable("id") Integer id) {
		waterregionService.deleteWaterregion(id);
		return ResObject.ok();
	}

    /**
     * 获取每个区域最高建筑相对压力值(代表区域压力)记录
     * @param pageNum   当前页
     * @param pageSize  当前页大小
     * @param waterregion
     * @param startTime	开始时间
     * @param endTime	结束时间
     * @param type	单位类型：-1:原始数据，0:小时，1:日
     * @return
     */
	@GetMapping("/pressure")
    public ResObject selectWaterregionAndPressure(
            @RequestParam(value = "pageNum", defaultValue = "1", required = true) Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "5", required = true) Integer pageSize,
            Waterregion waterregion, String startTime, String endTime, String type){
        List<WaterregionVO> list = buildingService.selectWaterregionAndPressure(waterregion, startTime, endTime, type);
        DPage<WaterregionVO> dpage = new DPage<WaterregionVO>(list, pageNum, pageSize);
        return ResObject.ok(dpage);

    }


}
