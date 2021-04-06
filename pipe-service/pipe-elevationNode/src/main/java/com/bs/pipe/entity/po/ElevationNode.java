package com.bs.pipe.entity.po;

import java.util.Date;

import com.bs.pipe.base.Base;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

@Data
public class ElevationNode extends Base{
	
    private Integer id;
    
    private String name;

    private Integer regionId;

    private Double longitude;

    private Double latitude;

    private Double elevation;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date setTime;
    
    private Boolean status;
    
    private String tag;

    private String remark;

}