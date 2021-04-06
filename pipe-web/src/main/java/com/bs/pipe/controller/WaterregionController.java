package com.bs.pipe.controller;

import java.util.List;

import com.bs.pipe.entity.po.Waterregion;
import com.bs.pipe.service.WaterregionService;
import com.bs.pipe.utils.ResObject;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/waterregion")
public class WaterregionController {
	
	@Resource
	private WaterregionService waterregionService;

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

}
