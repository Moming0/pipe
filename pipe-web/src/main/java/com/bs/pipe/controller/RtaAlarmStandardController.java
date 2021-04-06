package com.bs.pipe.controller;

import java.util.List;

import com.bs.pipe.entity.po.RtaAlarmStandard;
import com.bs.pipe.service.RtaAlarmStandardService;
import com.bs.pipe.utils.DPage;
import com.bs.pipe.utils.ResObject;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/rtaAlarmStandard")
public class RtaAlarmStandardController {
	
	@Resource
	private RtaAlarmStandardService rtaAlarmStandardService;

	/**
	 * 所有报警限值设置信息(分页，ID，区域ID)
	 * @param rtaAlarmStandard
	 * @return
	 */
	@GetMapping
	public ResObject selectRtaAlarmStandardList(
			@RequestParam(value = "pageNum", defaultValue = "1", required = true) Integer pageNum,
			@RequestParam(value = "pageSize", defaultValue = "5", required = true) Integer pageSize,
			RtaAlarmStandard rtaAlarmStandard) {
		List<RtaAlarmStandard> list = rtaAlarmStandardService.selectRtaAlarmStandardList(rtaAlarmStandard);
		DPage<RtaAlarmStandard> dpage = new DPage<RtaAlarmStandard>(list, pageNum, pageSize);
		return ResObject.ok(dpage);
	}
	
	/**
	 * 添加报警限值设置信息
	 * @param rtaAlarmStandard
	 * @return
	 */
	@PostMapping
	public ResObject insertRtaAlarmStandard(RtaAlarmStandard rtaAlarmStandard) {
		rtaAlarmStandardService.insertRtaAlarmStandard(rtaAlarmStandard);
		return ResObject.ok();
	}

	/**
	 * 修改报警限值设置信息
	 * @param rtaAlarmStandard
	 * @return
	 */
	@PutMapping
	public ResObject updateRtaAlarmStandard(RtaAlarmStandard rtaAlarmStandard) {
		rtaAlarmStandardService.updateRtaAlarmStandard(rtaAlarmStandard);
		return ResObject.ok();
	}

	/**
	 * 删除报警限值设置信息
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping("/{id}")
	public ResObject deleteRtaAlarmStandard(@PathVariable("id") Integer id) {
		rtaAlarmStandardService.deleteRtaAlarmStandard(id);
		return ResObject.ok();
	}

}
