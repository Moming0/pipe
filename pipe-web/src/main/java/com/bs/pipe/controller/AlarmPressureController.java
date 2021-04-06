package com.bs.pipe.controller;

import java.util.List;

import com.bs.pipe.entity.po.AlarmPressure;
import com.bs.pipe.service.AlarmPressureService;
import com.bs.pipe.utils.DPage;
import com.bs.pipe.utils.ResObject;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/alarmPressure")
public class AlarmPressureController {
	
	@Resource
	private AlarmPressureService alarmPressureService;

	/**
	 * 所有报警信息（ID,报警类型(10:压力点,20:建筑一层,30:和建筑顶层)，时间范围）
	 * @param alarmPressure
	 * @return
	 */
	@GetMapping
	public ResObject selectAlarmPressureList(
            @RequestParam(value = "pageNum", defaultValue = "1", required = true) Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "5", required = true) Integer pageSize,
            AlarmPressure alarmPressure, String startTime, String endTime) {
		List<AlarmPressure> list = alarmPressureService.selectAlarmPressureList(alarmPressure, startTime, endTime);
		DPage<AlarmPressure> dpage = new DPage<AlarmPressure>(list, pageNum, pageSize);
		return ResObject.ok(dpage);
	}
	
	/**
	 * 添加报警信息
	 * @param alarmPressure
	 * @return
	 */
	@PostMapping
	public ResObject insertAlarmPressure(AlarmPressure alarmPressure) {
		alarmPressureService.insertAlarmPressure(alarmPressure);
		return ResObject.ok();
	}

	/**
	 * 修改报警信息
	 * @param alarmPressure
	 * @return
	 */
	@PutMapping
	public ResObject updateAlarmPressure(AlarmPressure alarmPressure) {
		alarmPressureService.updateAlarmPressure(alarmPressure);
		return ResObject.ok();
	}

	/**
	 * 删除报警信息
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping("/{id}")
	public ResObject deleteAlarmPressure(@PathVariable("id") String id) {
		alarmPressureService.deleteAlarmPressure(id);
		return ResObject.ok();
	}
	
}
