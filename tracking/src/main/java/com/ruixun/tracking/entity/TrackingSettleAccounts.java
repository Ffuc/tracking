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
    @ApiModel(value="TrackingSettleAccounts对象", description="")
    public class TrackingSettleAccounts implements Serializable {

    private static final long serialVersionUID = 1L;

            @ApiModelProperty(value = "账户")
    private String account;

            @ApiModelProperty(value = "总共要结账的金额")
    private BigDecimal totalMoney;

            @ApiModelProperty(value = "已结账")
    private BigDecimal closedAccount;

            @ApiModelProperty(value = "本次扣除的花费金额")
    private BigDecimal memberCost;

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
