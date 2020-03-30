package com.ruixun.tracking.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author suitao
 * @description
 */
@ApiModel("洗码结账Dto")
@Data
public class SelectStatementDto implements Serializable {
    @ApiModelProperty(value = "游戏类别")
    private Integer gameType;
    @ApiModelProperty(value = "会员Id")
    private String account;
    @ApiModelProperty(value = "注码")
    private Integer moneyType;
    @ApiModelProperty(value = "结账时间")
    private LocalDateTime jz_time;

//    private BigDecimal

}
