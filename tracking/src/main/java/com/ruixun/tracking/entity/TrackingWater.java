package com.ruixun.tracking.entity;

import com.baomidou.mybatisplus.annotation.TableId;
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
@ApiModel(value = "TrackingWater对象", description = "")
public class TrackingWater implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId
    @ApiModelProperty(value = "铺流水号")
    private String waterId;

    @ApiModelProperty(value = "台桌号")
    private String tableId;

    @ApiModelProperty(value = "靴号")
    private Integer boots;

    @ApiModelProperty(value = "对应靴号的次数")
    private Integer times;

    @ApiModelProperty(value = "牌库")
    private String cards;

    @ApiModelProperty(value = "游戏类别")
    private Integer gameType;

    @ApiModelProperty(value = "结束时间(开牌时间)")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "此局保险")

    private BigDecimal insurance;

    @ApiModelProperty(value = "荷官")
    private String dutchOfficer;

    @ApiModelProperty(value = "结果")
    private String result;

    @ApiModelProperty(value = "修改结果")
    private String modifiedResult;

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

    @ApiModelProperty(value = "下注方式")
    private Integer betWay;

    @ApiModelProperty(value = "钱的类型")
    private Integer moneyType;

    @ApiModelProperty(value = "盈亏")
    private BigDecimal profit;



}
