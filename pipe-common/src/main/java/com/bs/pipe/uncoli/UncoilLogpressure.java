package com.bs.pipe.uncoli;

import com.bs.pipe.utils.ArithmeticUtil;

import java.text.DecimalFormat;

/**
 * 根据高程差、模拟点压力，推算当前压力
 */
public class UncoilLogpressure {

    /**
     * 高程差计算实际高程下的压力值
     * @param elevation     实际求点高程
     * @param logElevation      基准点高程
     * @param logPressureNumber     基准点压力值
     * @return  实际求点压力值
     */
    public static Double uncoilLogpressure(Double elevation,Double logElevation,Double logPressureNumber){
        //根据高程差计算压力 1m = 0.01MPa
        Double logDif = ArithmeticUtil.mul(ArithmeticUtil.sub(elevation, logElevation), 0.01);
        Double number = logPressureNumber + (logDif > 0 ? logDif * -1 : logDif < 0 ? logDif * -1 : 0);
        return Double.parseDouble(new DecimalFormat("0.000").format(number));
    }

}
