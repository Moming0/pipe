package com.bs.pipe.entity.po;

import com.bs.pipe.base.Base;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("网格实体类")
@Data
public class GridNode extends Base{
	@ApiModelProperty("id")
    private Integer id;
	@ApiModelProperty("区域id")
    private Integer regionId;
    @ApiModelProperty("地址")
	private String address;
	@ApiModelProperty("经度")
    private Double longitude;
	@ApiModelProperty("纬度")
    private Double latitude;
	@ApiModelProperty("高程")
    private Double elevation;
	@ApiModelProperty("状态")
    private Boolean status;
	@ApiModelProperty("标签")
    private String tag;
	@ApiModelProperty("备注")
    private String remark;

}