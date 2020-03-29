package com.ruixun.tracking.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author suitao
 * @description MemberController的查询DTO
 */
@Data
@ApiModel("会员账目查询DTO")
public class MemberSelectCondition implements Serializable {
    //开始时间
    @ApiModelProperty("开始时间")
    private LocalDateTime beginTime;
    //结束时间
    @ApiModelProperty("结束时间")
    private LocalDateTime endTime;
    //桌号
    @ApiModelProperty("桌号")
    private String tableId;
    //靴号
    @ApiModelProperty("靴号")
    private String bootId;
    //(游戏)类型
    @ApiModelProperty("游戏类型")
    private Integer gameType;
    //注码
    @ApiModelProperty("注码")
    private Integer noteCode;
    //洗码号
    @ApiModelProperty("下注方式")
    private Integer betWay;

}
