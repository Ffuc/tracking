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
@ApiModel(value = "TrackingMemberCost对象", description = "")
public class TrackingMemberCost implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "会员的account")
    private String account;

    @ApiModelProperty(value = "代理的account")
    private String reference;

    @ApiModelProperty(value = "吃住")
    private BigDecimal lifeMoney;

    @ApiModelProperty(value = "机票")
    private BigDecimal ticketMoney;

    @ApiModelProperty(value = "车费")
    private BigDecimal carMoney;

    @ApiModelProperty(value = "娱乐及其他")
    private BigDecimal play;

    @ApiModelProperty(value = "总费用")
    private BigDecimal totalMoney;
    @ApiModelProperty(value = "币种")
    private Integer moneyType;
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "更新人")
    private String updatePerson;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "创建人")
    private String createPerson;

    @ApiModelProperty(value = "0可用 1不可用")
    private Integer isDelete;

    @ApiModelProperty(value = "备注")
    private String remark;


}
