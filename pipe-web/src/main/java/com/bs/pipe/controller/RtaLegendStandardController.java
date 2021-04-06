package com.bs.pipe.controller;

import java.util.List;

import com.bs.pipe.entity.po.RtaLegendStandard;
import com.bs.pipe.service.RtaLegendStandardService;
import com.bs.pipe.utils.ResObject;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/rtaLegendStandard")
public class RtaLegendStandardController {
	
	@Resource
	private RtaLegendStandardService rtaLegendStandardService;

	/**
	 * 所有图例标准设置信息(ID,类型)
	 * @param rtaLegendStandard
	 * @return
	 */
	@GetMapping
	public ResObject selectRtaLegendStandardList(RtaLegendStandard rtaLegendStandard) {
		List<RtaLegendStandard> list = rtaLegendStandardService.selectRtaLegendStandardList(rtaLegendStandard);
		return ResObject.ok(list);
	}
	
	/**
	 * 添加图例标准设置信息
	 * @param rtaLegendStandard
	 * @return
	 */
	@PostMapping
	public ResObject insertRtaLegendStandard(RtaLegendStandard rtaLegendStandard) {
		rtaLegendStandardService.insertRtaLegendStandard(rtaLegendStandard);
		return ResObject.ok();
	}

	/**
	 * 修改图例标准设置信息
	 * @param rtaLegendStandard
	 * @return
	 */
	@PutMapping
	public ResObject updateRtaLegendStandard(RtaLegendStandard rtaLegendStandard) {
		rtaLegendStandardService.updateRtaLegendStandard(rtaLegendStandard);
		return ResObject.ok();
	}

	/**
	 * 删除图例标准设置信息
	 * 
	 * @param id
	 * @return
	 */
    @DeleteMapping("/{id}")
	public ResObject deleteRtaLegendStandard(@PathVariable("id") Integer id) {
		rtaLegendStandardService.deleteRtaLegendStandard(id);
		return ResObject.ok();
	}

}
