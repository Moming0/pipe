package com.bs.pipe.entity.vo;

import com.bs.pipe.entity.po.LogPressure;
import com.bs.pipe.entity.po.Waterregion;
import lombok.Data;

import java.util.List;

@Data
public class WaterregionVO extends Waterregion{

    private List<LogPressure> logPressure;

}
