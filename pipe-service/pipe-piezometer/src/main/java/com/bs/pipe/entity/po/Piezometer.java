package com.bs.pipe.entity.po;

import java.util.Date;

import com.bs.pipe.base.Base;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

@Data
public class Piezometer extends Base{
	
    private Integer id;
    
    private String name;

    private Integer regionId;
    
    private String regionName;
    
    private String snumber;
    
    private String masterPc;
    
    private String category;
    
    private String pipeCategory;
    
    private Boolean isOpen;
    
    private Boolean isOnline;
    
    private String dimension;
    
    private String setAddress;
    
    private String setDepartement;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date setDateTime;
    
    private String producter;
    
    private String productSn;

    private Double longitude;

    private Double latitude;

    private Double elevation;

    private Boolean status;
    
    private String tag;
    
    private String remark;

}