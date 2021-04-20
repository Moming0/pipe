package com.bs.pipe.reckon;

import com.bs.pipe.entity.po.LogPressure;
import com.bs.pipe.entity.po.RtaLegendStandard;
import com.bs.pipe.entity.vo.GridNodeVO;
import com.bs.pipe.entity.vo.PiezometerVO;
import com.bs.pipe.exception.BusinessException;
import com.bs.pipe.uncoli.UncoilLogpressure;
import com.bs.pipe.utils.DistanceUtil;
import com.bs.pipe.utils.depCopyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.Future;

@Component
public class ReckonThreadGridNode {

    @Async("executorP")
    public Future<List<GridNodeVO>> runThreadGridNodeLogPressure(List<GridNodeVO> gList, List<PiezometerVO> pList) {
        //声明future对象
        Future <List<GridNodeVO>> result = new AsyncResult<List<GridNodeVO>>(gList);

        PiezometerVO mNode = new PiezometerVO();
        for (GridNodeVO gNode : gList) {
            Double dSum = null;
            for (PiezometerVO pNode : pList) {
                if(gNode.getRegionId().equals(pNode.getRegionId())){
                    double distance = DistanceUtil.getDistance(gNode.getLongitude(), gNode.getLatitude(), pNode.getLongitude(), pNode.getLatitude());
                    //System.out.println("压力点ID：" + pNode.getId() + "========距离：" + distance);
                    if(dSum == null || distance <= dSum){
                        dSum = distance;
                        mNode = pNode;
                    }
                }
            }
            if(CollectionUtils.isNotEmpty(mNode.getLogPressure())){
                //System.out.println("压力点ID：" + mNode.getId() + "========高程：" + mNode.getElevation() + "========压力值：" + mNode.getLogPressure());
                //根据高程差计算压力 1m = 0.01MPa(每升高1m降低0.01兆帕)（单位为1MPa * 10）
                Double logDif = (gNode.getElevation() - mNode.getElevation()) * 0.1;
                //深拷贝集合对象
                List<LogPressure> logPressureCopy = depCopyUtils.depCopy(mNode.getLogPressure());
                logPressureCopy.forEach(m -> m.setReadNumber(m.getReadNumber() + (logDif > 0 ? logDif * -1 : logDif < 0 ? logDif * -1 : 0)));
                //System.out.println("网格点ID：" + gNode.getId() + "========网格点高程：" + gNode.getElevation() + "========压力差：" + logDif + "========网格readNumber：" + mNode.getLogPressure());
                gNode.setLogPressure(logPressureCopy);
            }
        }
        return result;
    }
}
