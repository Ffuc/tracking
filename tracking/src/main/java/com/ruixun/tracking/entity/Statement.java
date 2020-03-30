package com.ruixun.tracking.entity;

    import java.math.BigDecimal;
    import com.baomidou.mybatisplus.annotation.TableName;
    import com.baomidou.mybatisplus.annotation.IdType;
    import com.baomidou.mybatisplus.annotation.TableId;
    import java.time.LocalDateTime;
    import java.io.Serializable;
    import io.swagger.annotations.ApiModel;
    import io.swagger.annotations.ApiModelProperty;
    import lombok.Data;
    import lombok.EqualsAndHashCode;
    import lombok.experimental.Accessors;

/**
* <p>
    * 
    * </p>
*
* @author pig
* @since 2020-03-30
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    @TableName("tracking_statement")
    @ApiModel(value="Statement对象", description="")
    public class Statement implements Serializable {

    private static final long serialVersionUID = 1L;

            @ApiModelProperty(value = "无意义Id")
            @TableId(value = "id", type = IdType.AUTO)
    private Long id;

            @ApiModelProperty(value = "会员账号（洗码号）")
    private String memberId;

            @ApiModelProperty(value = "会员姓名")
    private String memberName;

            @ApiModelProperty(value = "总洗码")
    private BigDecimal totalWashCode;

            @ApiModelProperty(value = "洗码率")
    private BigDecimal washCodeRate;

            @ApiModelProperty(value = "已结洗码费")
    private BigDecimal hadMoney;

            @ApiModelProperty(value = "币种")
    private Integer moneyType;

            @ApiModelProperty(value = "总洗码费")
    private BigDecimal totalMoney;

            @ApiModelProperty(value = "应结洗码费")
    private BigDecimal shouldMoney;

            @ApiModelProperty(value = "未结洗码费")
    private BigDecimal notFinishMoney;

            @ApiModelProperty(value = "结账时间")
    private LocalDateTime checkoutTime;

            @ApiModelProperty(value = "是否结账（0：未结账，1：已结账）")
    private Integer isCheckout;


}
