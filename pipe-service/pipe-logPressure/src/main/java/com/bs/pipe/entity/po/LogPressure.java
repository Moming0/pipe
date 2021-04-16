package com.bs.pipe.entity.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogPressure implements Serializable {
    @ApiModelProperty("id")
    private Integer id;
    @ApiModelProperty("压力点id")
    private Integer pressureId;
    @ApiModelProperty("数据时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date readTime;
    @ApiModelProperty("压力值")
    private Double readNumber;
    @ApiModelProperty("状态")
    private String status;
    @ApiModelProperty("标签")
    private String tag;
    @ApiModelProperty("备注")
    private Object remark;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" )
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;

    private Boolean deleted;

    public Double getReadNumber() {
        return readNumber = Double.parseDouble(new DecimalFormat("0.000").format(this.readNumber));
    }

    public void setReadNumber(Double readNumber) {
        this.readNumber = Double.parseDouble(new DecimalFormat("0.000").format(readNumber));
    }
}
