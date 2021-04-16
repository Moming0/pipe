package com.bs.pipe.controller;

import java.util.List;

import com.bs.pipe.entity.po.GridNode;
import com.bs.pipe.entity.vo.GridNodeVO;
import com.bs.pipe.service.GridNodeService;
import com.bs.pipe.utils.JsonUtil;
import com.bs.pipe.utils.ResObject;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/gridNode")
@Api(value="网格点信息接口",description="网格点信息接口")
public class GridNodeController {
	
	@Resource
	private GridNodeService gridNodeService;

	/**
	 * 所有网格点信息（ID，区域ID，经纬度）
	 * @param gridNode
	 * @return
	 */
	@ApiOperation(value="查询网格点信息",notes="可根据ID，区域，经纬度查询")
    @GetMapping
	public ResObject selectGridNodeList(GridNode gridNode) {
		List<GridNode> list = gridNodeService.selectGridNodeList(gridNode);
		return ResObject.ok(list);
	}
	
	/**
	 * 添加网格点信息
	 * @param gridNode
	 * @return
	 */
	@ApiOperation(value="添加网格点信息",notes="")
    @PostMapping
	public ResObject insertGridNode(GridNode gridNode) {
		gridNodeService.insertGridNode(gridNode);
		return ResObject.ok();
	}

	/**
	 * 修改网格点信息
	 * @param gridNode
	 * @return
	 */
	@ApiOperation(value="修改网格点信息",notes="")
    @PutMapping
	public ResObject updateGridNode(GridNode gridNode) {
		gridNodeService.updateGridNode(gridNode);
		return ResObject.ok();
	}

	/**
	 * 删除网格点信息
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping("/{id}")
	public ResObject deleteGridNode(@PathVariable("id") Integer id) {
		gridNodeService.deleteGridNode(id);
		return ResObject.ok();
	}
	
	/**
	 * 批量导入网格点
	 * @param json
	 * @return
	 */
    @PostMapping("/import")
	public ResObject importGridNode(@RequestBody String json) {
		List<GridNode> gridNodeList = JsonUtil.jsonToList(json, GridNode.class);
		gridNodeService.insertGridNodeList(gridNodeList);
		return ResObject.ok();
	}

	/**
	 * 获得网格点及对应高程多记录（ID，区域，经纬度）
	 * @param gridNode
	 * @return
	 */
	@ApiOperation(value="网格点及对应高程",notes="可根据ID，区域ID，时间点，网格点经纬度查询")
    @GetMapping("/elevation")
	public ResObject selectGridNodeAndElevation(GridNode gridNode) {
		List<GridNode> list = gridNodeService.selectGridNodeElevation(gridNode);
		return ResObject.ok(list);
	}
	
	/**
	 * 查询网格点高程相对压力值(平均值)记录（ID，区域ID，网格点经纬度，时间范围，时间单位-1:原始数据，0:小时，1:日）
	 * @param gridNode
	 * @return
	 */
	@ApiOperation(value="网格点压力值",notes="网格点高程差压力值平均值，可根据ID，区域ID，时间点，网格点经纬度查询")
	@ApiImplicitParams({
		@ApiImplicitParam(name="startTime",value="开始时间",dataType="String", paramType = "query"),
		@ApiImplicitParam(name="endTime",value="结束时间",dataType="String", paramType = "query"),
		@ApiImplicitParam(name="type",value="时间单位类型：0小时，1日",dataType="String", paramType = "query")
	})
    @GetMapping("/pressure")
	public ResObject selectGridNodeAndPressure(
			GridNode gridNode, String startTime, String endTime,String type) {
		List<GridNodeVO> list = gridNodeService.selectGridNodePressure(gridNode, startTime, endTime, type);
		return ResObject.ok(list);
	}

}
