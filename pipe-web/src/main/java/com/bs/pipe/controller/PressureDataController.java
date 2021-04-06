package com.bs.pipe.controller;

import java.util.List;

import com.bs.pipe.entity.po.PressureData;
import com.bs.pipe.service.PressureDataService;
import com.bs.pipe.utils.JsonUtil;
import com.bs.pipe.utils.ResObject;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api")
public class PressureDataController {
	
	@Resource
	private PressureDataService pressureDataService;
	
	/**
	 * 所有原始数据信息(id,编号,名称模糊,时间范围)
	 * @param pressureData
	 * @return
	 */
    @GetMapping("/pressureData")
	public ResObject selectPressureDataList(PressureData pressureData, String startTime, String endTime) {
		List<PressureData> list = pressureDataService.selectPressureDataList(pressureData, startTime, endTime);
		return ResObject.ok(list);
	}
	
	/**
	 * 添加原始数据信息
	 * @param pressureData
	 * @return
	 */
    @PostMapping("/pressureData")
	public ResObject insertPressureData(PressureData pressureData) {
		pressureDataService.insertPressureData(pressureData);
		return ResObject.ok();
	}

	/**
	 * 修改原始数据信息
	 * @param pressureData
	 * @return
	 */
	@PutMapping("/pressureData")
	public ResObject updatePressureData(PressureData pressureData) {
		pressureDataService.updatePressureData(pressureData);
		return ResObject.ok();
	}

	/**
	 * 删除原始数据信息
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping("/pressureData/{id}")
	public ResObject deletePressureData(@PathVariable("id") Integer id) {
		pressureDataService.deletePressureData(id);
		return ResObject.ok();
	}

	/**
	 * 批量添加原始数据
	 * @param json
	 * @return
	 */
	@PostMapping("/input/pressureditydata")
	@ResponseBody
	public Object insertPressureDataList(@RequestBody String json) {
		List<PressureData> pressureDataList = JsonUtil.jsonToList(json, PressureData.class);
		return pressureDataService.insertPressureDataList(pressureDataList);
	}

}
