package com.bs.pipe.controller;

import java.util.List;

import com.bs.pipe.entity.po.Piezometer;
import com.bs.pipe.entity.vo.PiezometerVO;
import com.bs.pipe.service.PiezometerService;
import com.bs.pipe.utils.DPage;
import com.bs.pipe.utils.ResObject;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/piezometer")
public class PiezometerController {
	
	@Resource
	private PiezometerService piezometerService;

	/**
	 * 所有压力点信息（ID，名称模糊，区域，经纬度）
	 * @param piezometer
	 * @return
	 */
	@GetMapping
	public ResObject selectPiezometerList(Piezometer piezometer) {
		List<Piezometer> list = piezometerService.selectPiezometerList(piezometer);
		return ResObject.ok(list);
	}
	
	/**
	 * 添加压力点信息
	 * @param piezometer
	 * @return
	 */
	@PostMapping
	public ResObject insertPiezometer(Piezometer piezometer) {
		piezometerService.insertPiezometer(piezometer);
		return ResObject.ok();
	}

	/**
	 * 修改压力点信息
	 * @param piezometer
	 * @return
	 */
	@PutMapping
	public ResObject updatePiezometer(Piezometer piezometer) {
		piezometerService.updatePiezometer(piezometer);
		return ResObject.ok();
	}

	/**
	 * 删除压力点信息
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping("/{id}")
	public ResObject deletePiezometer(@PathVariable("id") Integer id) {
		piezometerService.deletePiezometer(id);
		return ResObject.ok();
	}
	
    /**
     * 查询压力点信息及历史压力值（分页，ID，名称模糊，区域，经纬度，时间范围）
     * @param pageNum   当前页
     * @param pageSize  当前页大小
     * @param piezometer    (ID，名称模糊，区域，经纬度，时间范围)
     * @param startTime     开始时间
     * @param endTime       结束时间
     * @return
     */
    @GetMapping("/log")
	public ResObject selectPressureLogSearch(
			@RequestParam(value = "pageNum", defaultValue = "1", required = true) Integer pageNum,
			@RequestParam(value = "pageSize", defaultValue = "5", required = true) Integer pageSize, 
			Piezometer piezometer, String startTime, String endTime) {
		List<PiezometerVO> list = piezometerService.selectPressureLogSearch(piezometer, startTime, endTime);
		DPage<PiezometerVO> dpage = new DPage<>(list, pageNum, pageSize);
		return ResObject.ok(dpage);
	}
	
    /**
     * 查询压力点信息及历史压力值平均值（分页，ID，名称模糊，区域，经纬度，时间范围，时间单位0:小时，1:日）
     * @param pageNum   当前页
     * @param pageSize  当前页大小
     * @param piezometer（ID，名称模糊，区域，经纬度，时间范围）
     * @param startTime	开始时间
     * @param endTime	结束时间
     * @param type		0：小时单位，1日单位
     * @return
     */
	@GetMapping("/logAvg")
	public ResObject selectPressureLogAvgSearch(
			@RequestParam(value = "pageNum", defaultValue = "1", required = true) Integer pageNum,
			@RequestParam(value = "pageSize", defaultValue = "5", required = true) Integer pageSize, 
			Piezometer piezometer, String startTime, String endTime,String type) {
		List<PiezometerVO> list = piezometerService.selectPressureLogAvgSearch(piezometer, startTime, endTime, type);
		DPage<PiezometerVO> dpage = new DPage<>(list, pageNum, pageSize);
		return ResObject.ok(dpage);
	}

	/**
	 * 查询压力点的压力标准上下限范围信息（ID，名称模糊，区域，经纬度）
	 * @param piezometer
	 * @return
	 */
    @GetMapping("/scale")
	public ResObject selectPiezometerPressureScale(Piezometer piezometer) {
		List list = piezometerService.selectPiezometerPressureScale(piezometer);
		return ResObject.ok(list);
	}
	
}
