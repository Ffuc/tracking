package com.ruixun.tracking.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Program: tracking_system
 * <p>
 * Description:
 *
 * @Date: 2020-03-24 15:21
 **/
@ApiModel("快速传入期号")
@Data
public class Issue {

    @ApiModelProperty("桌号")
    private String tableId;
    @ApiModelProperty("靴号")
    private Integer bootId;
    @ApiModelProperty("期号")
    private Integer issueId;


}
