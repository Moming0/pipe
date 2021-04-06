package com.bs.pipe.entity.vo;

import com.bs.pipe.entity.po.Building;
import com.bs.pipe.entity.po.LogPressure;
import lombok.Data;

import java.util.List;

@Data
public class BuildingVO extends Building {
	
	private List<LogPressure> logPressure;

}