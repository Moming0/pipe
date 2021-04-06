package com.bs.pipe.entity.vo;

import java.util.List;

import com.bs.pipe.entity.po.GridNode;
import com.bs.pipe.entity.po.LogPressure;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GridNodeVO extends GridNode {
	@ApiModelProperty("压力数据")
	private List<LogPressure> logPressure;

}