package com.ruixun.tracking.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
* <p>
    * 
    * </p>
*
* @author pig
* @since 2020-03-28
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    @ApiModel(value="TrackingTable对象", description="")
    public class TrackingTable implements Serializable {

    private static final long serialVersionUID = 1L;

            @ApiModelProperty(value = "台桌号")
    private String tableId;

            @ApiModelProperty(value = "台桌类型")
    private Integer tableType;

            @ApiModelProperty(value = "是否洗码")
    private Boolean couldWash;

            @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

            @ApiModelProperty(value = "更新人")
    private String updatePerson;

            @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

            @ApiModelProperty(value = "创建人")
    private String createPerson;

            @ApiModelProperty(value = "是否可用")
    private Integer isDelete;

            @ApiModelProperty(value = "备注")
    private String remark;


}
