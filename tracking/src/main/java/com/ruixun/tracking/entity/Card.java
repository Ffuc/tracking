package com.ruixun.tracking.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Program: tracking_system
 * <p>
 * Description:
 *
 * @Date: 2020-03-24 15:38
 **/
@ApiModel("牌")
@Data
public class Card {
    @ApiModelProperty("序号")
    private int id;
    @ApiModelProperty("花色")
    private int color;
    @ApiModelProperty("大小")
    private int size;

}
