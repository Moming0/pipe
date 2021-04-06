package com.bs.pipe.controller;

import java.util.List;

import com.bs.pipe.entity.po.ElevationNode;
import com.bs.pipe.service.ElevationNodeService;
import com.bs.pipe.utils.ResObject;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/elevationNode")
public class ElevationNodeController {
	
	@Resource
	private ElevationNodeService elevationNodeService;

	/**
	 * 所有高程点信息（ID，名称模糊，区域，经纬度）
	 * @param elevationNode
	 * @return
	 */
    @GetMapping
	public ResObject selectElevationNodeList(ElevationNode elevationNode) {
		List<ElevationNode> list = elevationNodeService.selectElevationNodeList(elevationNode);
		return ResObject.ok(list);
	}
	
	/**
	 * 添加高程点信息
	 * @param elevationNode
	 * @return
	 */
	@PostMapping
	public ResObject insertElevationNode(ElevationNode elevationNode) {
		elevationNodeService.insertElevationNode(elevationNode);
		return ResObject.ok();
	}

	/**
	 * 修改高程点信息
	 * @param elevationNode
	 * @return
	 */
	@PutMapping
	public ResObject updateElevationNode(ElevationNode elevationNode) {
		elevationNodeService.updateElevationNode(elevationNode);
		return ResObject.ok();
	}

	/**
	 * 删除高程点信息
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping("/{id}")
	public ResObject deleteElevationNode(@PathVariable("id") Integer id) {
		elevationNodeService.deleteElevationNode(id);
		return ResObject.ok();
	}

}
