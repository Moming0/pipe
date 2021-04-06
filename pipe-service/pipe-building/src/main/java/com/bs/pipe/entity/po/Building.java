package com.bs.pipe.entity.po;

import com.bs.pipe.base.Base;
import lombok.Data;

@Data
public class Building extends Base{
	
    private Integer id;

    private String name;

    private String snumber;

    private String category;

    private Integer regionId;
    
    private String regionName;
    
    private String address;

    private Integer storeys;
    
    private Integer population;

    private Double buildingHeight;

    private Double buildingArea;

    private String buildingFunction;

    private Double coversArea;

    private Double longitude;

    private Double latitude;
    
    private Double elevation;

    private String tag;

    private String remark;

}