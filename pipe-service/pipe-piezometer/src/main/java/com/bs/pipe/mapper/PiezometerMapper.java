package com.bs.pipe.mapper;

import java.util.List;

import com.bs.pipe.dto.PiezometerDTO;
import com.bs.pipe.entity.po.Piezometer;
import com.bs.pipe.entity.vo.PiezometerVO;
import org.apache.ibatis.annotations.Param;

public interface PiezometerMapper {
	/**
	 * ID，名称模糊，区域，经纬度，地址
	 * @param piezometer
	 * @return
	 */
	List<Piezometer> selectPiezometerList(Piezometer piezometer);
	
	Piezometer selectPiezometer(Piezometer piezometer);
	
	/**
	 * 根据编号snumber查询单条数据
	 * @param snumber
	 * @return
	 */
	Piezometer selectPiezometerBySnumber(@Param("snumber") String snumber);
	
	/**
	 * 根据名称name查询单条数据
	 * @param name
	 * @return
	 */
	Piezometer selectPiezometerByName(@Param("name") String name);

	int insertPiezometer(Piezometer piezometer);

	int updatePiezometer(Piezometer piezometer);

	int deletePiezometer(Integer id);
	
	/**
	 * 查询压力点信息及历史压力值（ID，名称模糊，区域，经纬度，时间范围）
	 * @param piezometer
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	List<PiezometerVO> selectPressureLogSearch(@Param("piezometer") Piezometer piezometer, @Param("startTime") String startTime,
                                               @Param("endTime") String endTime);
	
	boolean piezometerExistWhenCreate(Piezometer piezometer);
	
	boolean piezometerExistWhenUpdate(Piezometer piezometer);

    /**
     * 查询压力监测点信息和最后一次压力及时间(ID，名称模糊，区域，经纬度)
     * @param piezometer	ID，名称模糊，区域，经纬度
     * @return
     */
    List<PiezometerDTO> selectPiezometerListAndLastLog(Piezometer piezometer);

}