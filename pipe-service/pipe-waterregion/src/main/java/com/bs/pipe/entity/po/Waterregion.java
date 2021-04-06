package com.bs.pipe.entity.po;

import com.bs.pipe.base.Base;
import lombok.Data;

@Data
public class Waterregion extends Base{
	
    private Integer id;

    private String name;
    
    private String snumber;
    
    private String category;
    
    private String address;
    
    private Double area;
    
    private Integer population;
    
    private String statisticsType;
    
    private String tag;
    
    private String remark;

}