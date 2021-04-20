package com.bs.pipe.service;

import com.bs.pipe.dto.PiezometerDTO;
import com.bs.pipe.entity.po.Piezometer;
import com.bs.pipe.entity.vo.PiezometerVO;

import java.util.List;

public interface PiezometerService {
	
	/**
	 * 获得Piezometer（压力点）信息（ID，名称模糊，区域，经纬度，地址，管网类型）
	 * @param piezometer
	 * @return
	 */
	List<Piezometer> selectPiezometerList(Piezometer piezometer);

    /**
     * 查询压力监测点信息和最后一次压力及时间(ID，名称模糊，区域，经纬度)
     * @param piezometer	ID，名称模糊，区域，经纬度
     * @return
     */
    List<PiezometerDTO> selectPiezometerListAndLastLog(Piezometer piezometer);

    /**
	 * 查询（压力点）单条信息
	 * @param piezometer
	 * @return
	 */
	Piezometer selectPiezometer(Piezometer piezometer);
	
	/**
	 * 根据编号snumber查询单条数据
	 * @param snumber
	 * @return
	 */
	Piezometer selectPiezometerBySnumber(String snumber);
	
	/**
	 * 根据名称name查询单条数据
	 * @param name
	 * @return
	 */
	Piezometer selectPiezometerByName(String name);

	/**
	 * 添加Piezometer（压力点）
	 * @param piezometer
	 */
	void insertPiezometer(Piezometer piezometer);

	/**
	 * 修改Piezometer（压力点）
	 * @param piezometer
	 */
	void updatePiezometer(Piezometer piezometer);

	/**
	 * 删除Piezometer（压力点）
	 * @param id
	 */
	void deletePiezometer(Integer id);
	
	/**
	 * 查询压力点信息及历史压力值（ID，名称模糊，区域，经纬度，时间范围，地址，管网类型）
	 * @param piezometer
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	List<PiezometerVO> selectPressureLogSearch(Piezometer piezometer, String startTime, String endTime);
	
	/**
	 * 查询压力点信息及历史压力值平均值（ID，名称模糊，区域，地址，管网类型，经纬度，时间范围，时间单位-1:原始数据，0:小时，1:日）
	 * 		自定义时间
	 * 			单位日：每天数据,每天内四个节点小时的平均值，即为当天数据
	 * 			单位小时：每小时数据，每小时内几个数据的平均值，即为该小时数据
	 * @param piezometer
	 * @param startTime
	 * @param endTime
	 * @param type   -1:原始数据，0:小时，1:日
	 * @return
	 */
	List<PiezometerVO> selectPressureLogAvgSearch(Piezometer piezometer, String startTime, String endTime, String type);
	
	/**
	 * 查询压力点的压力标准上下限范围信息（ID，名称模糊，区域，经纬度）
	 * @param piezometer
	 * @return
	 * 标准共享报警限值信息表RtaAlarmStandard
	 */
	List selectPiezometerPressureScale(Piezometer piezometer);
	
}
