package com.bs.pipe.service.impl;

import java.util.*;
import java.util.regex.Pattern;

import com.bs.pipe.entity.po.Waterregion;
import com.bs.pipe.exception.BusinessException;
import com.bs.pipe.mapper.WaterregionMapper;
import com.bs.pipe.service.WaterregionService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class WaterregionServiceImpl implements WaterregionService {

	@Resource
	private WaterregionMapper waterregionMapper;

	@Override
	public List<Waterregion> selectWaterregionList(Waterregion waterregion) {
		// TODO Auto-generated method stub
		List<Waterregion> list = waterregionMapper.selectWaterregionList(waterregion);
		return CollectionUtils.isEmpty(list) ? Collections.emptyList() : list;
	}

	@Override
	public Waterregion selectWaterregion(Waterregion waterregion) {
		// TODO Auto-generated method stub
		return waterregionMapper.selectWaterregion(waterregion);
	}

	@Override
	public void insertWaterregion(Waterregion waterregion) {
		// TODO Auto-generated method stub
		if(waterregionMapper.insertWaterregion(waterregion) != 1){
			throw new BusinessException("添加失败");
		}
	}

	@Override
	public void updateWaterregion(Waterregion waterregion) {
		// TODO Auto-generated method stub
		if(waterregionMapper.updateWaterregion(waterregion) != 1){
			throw new BusinessException("修改失败");
		}
	}

	@Override
	public void deleteWaterregion(Integer id) {
		// TODO Auto-generated method stub
		if(waterregionMapper.deleteWaterregion(id) != 1){
			throw new BusinessException("删除失败");
		}
	}


    /**
     * 正则表达式时间“YYYY-MM-DD HH:mm:ss”和“YYYY-MM-DD”
     * @param datetime
     * @return
     */
    public static boolean isDateTime(String datetime) {
        Pattern p = Pattern.compile(
                "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1][0-9])|([2][0-4]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
        return p.matcher(datetime).matches();
    }

}
