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
    @ApiModel(value="TrackingUser对象", description="")
    public class TrackingUser implements Serializable {

    private static final long serialVersionUID = 1L;

            @ApiModelProperty(value = "如果是代理（代理账号），如果是会员代表（洗码号）")
    private String account;

            @ApiModelProperty(value = "如果是代理（代理卡号），如果是会员（洗码卡号）")
    private String cardId;

            @ApiModelProperty(value = "用户类型-引用user_type")
    private Integer userType;

            @ApiModelProperty(value = "上级代理的账号")
    private String referrer;

            @ApiModelProperty(value = "姓名（昵称，不是真实姓名）")
    private String username;

            @ApiModelProperty(value = "手机号")
    private String phone;

            @ApiModelProperty(value = "占成（占股）")
    private BigDecimal proportion;

            @ApiModelProperty(value = "洗码率")
    private BigDecimal washCodeRatio;

            @ApiModelProperty(value = "上次平单时间")
    private LocalDateTime lastAccountStatementTime;

            @ApiModelProperty(value = "返点收益率")
    private BigDecimal rebatesEarnings;

            @ApiModelProperty(value = "0下线 1上线")
    private Integer state;

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
