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
    @ApiModel(value="TrackingBoot对象", description="")
    public class TrackingBoot implements Serializable {

    private static final long serialVersionUID = 1L;

            @ApiModelProperty(value = "靴号")
    private String bootId;

            @ApiModelProperty(value = "桌号")
    private String tableId;

            @ApiModelProperty(value = "靴号表")
    private Integer times;

            @ApiModelProperty(value = "创建人")
    private String createPerson;

            @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

            @ApiModelProperty(value = "是否可用 0 false")
    private Boolean isDeleted;

            @ApiModelProperty(value = "备注")
    private String remark;


}
