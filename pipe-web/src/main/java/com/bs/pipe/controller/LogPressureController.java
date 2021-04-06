package com.bs.pipe.controller;

import java.util.List;

import com.bs.pipe.entity.po.LogPressure;
import com.bs.pipe.service.LogPressureService;
import com.bs.pipe.utils.ResObject;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/logPressure")
public class LogPressureController {
	
	@Resource
	private LogPressureService logPressureService;

	/**
	 * 所有压力值信息
	 * @param logPressure
	 * @return
	 */
	@GetMapping
	public ResObject selectLogPressureList(LogPressure logPressure, String startTime, String endTime) {
		List<LogPressure> list = logPressureService.selectLogPressureList(logPressure, startTime, endTime);
		return ResObject.ok(list);
	}
	
	/**
	 * 添加压力值信息
	 * @param logPressure
	 * @return
	 */
	@PostMapping
	public ResObject insertLogPressure(LogPressure logPressure) {
		logPressureService.insertLogPressure(logPressure);
		return ResObject.ok();
	}

	/**
	 * 修改压力值信息
	 * @param logPressure
	 * @return
	 */
	@PutMapping
	public ResObject updateLogPressure(LogPressure logPressure) {
		logPressureService.updateLogPressure(logPressure);
		return ResObject.ok();
	}

	/**
	 * 删除压力值信息
	 * 
	 * @param id
	 * @return
	 */
    @DeleteMapping("/{id}")
	public ResObject deleteLogPressure(@PathVariable("id") Integer id) {
		logPressureService.deleteLogPressure(id);
		return ResObject.ok();
	}

}
