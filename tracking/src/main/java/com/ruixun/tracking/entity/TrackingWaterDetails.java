package com.ruixun.tracking.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
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
@ApiModel(value = "TrackingWaterDetails对象", description = "")
public class TrackingWaterDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "会员账户")
    private String account;

    @ApiModelProperty(value = "此局下注金额")
    private BigDecimal betMoney;
    @ApiModelProperty(value = "此局洗码量")

    private BigDecimal washCodeAmount;
    @ApiModelProperty(value = "此局赢得钱")

    private BigDecimal winMoney;
    @ApiModelProperty(value = "此局保险")

    private BigDecimal insurance;
    @ApiModelProperty(value = "派彩所赢")

    private BigDecimal lotteryWin;


    @ApiModelProperty(value = "上级代理人")
    private String referrer;

    @ApiModelProperty(value = "对应铺号")
    private String waterId;

    @ApiModelProperty(value = "下注目标")
    private Integer betTarget;

    @ApiModelProperty(value = "下注时间")
    private LocalDateTime betTime;

    @ApiModelProperty(value = "游戏类别")
    private Integer gameType;
    @ApiModelProperty(value = "洗码费")
    private BigDecimal washCodeMoney;

}
