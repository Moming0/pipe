package com.bs.pipe.entity.vo;

import com.bs.pipe.entity.po.LogPressure;
import com.bs.pipe.entity.po.Piezometer;
import lombok.Data;

import java.util.List;

@Data
public class PiezometerVO extends Piezometer {
	
	private List<LogPressure> logPressure;

}