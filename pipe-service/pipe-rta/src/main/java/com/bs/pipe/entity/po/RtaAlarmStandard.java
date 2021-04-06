package com.bs.pipe.entity.po;

import com.bs.pipe.base.Base;
import lombok.Data;

@Data
public class RtaAlarmStandard extends Base{

	private Integer id;
	
	private Integer regionId;
	
	private String regionName;
	
	private String category;
	
    private Double elevation;
    
    private Double minScale;
    
    private Double maxScale;
    
    private Boolean status;
    
    private String tag;

    private String remark;

}