package com.bs.pipe.entity.po;

import java.util.Date;

import com.bs.pipe.base.Base;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

@Data
public class AlarmPressure extends Base{
	
    private String id;
    
    private String category;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date alarmTime;
    
    private String address;
    
    private Double longitude;

    private Double latitude;
    
    private String source;
    
    private String caller;
    
    private Object alarmData;
    
    private String reportContent;
    
    private Object image;

    private Boolean status;
    
    private String tag;
    
    private String remark;

}