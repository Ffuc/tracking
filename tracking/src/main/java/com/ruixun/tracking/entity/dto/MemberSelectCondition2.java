package com.ruixun.tracking.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Program: tracking
 * <p>
 * Description:
 *
 * @Date: 2020-03-29 10:00
 **/
@Data
public class MemberSelectCondition2 {
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
    private Integer bootId;

    @ApiModelProperty("铺号")
    private Integer waterId;
    //(游戏)类型
    @ApiModelProperty("游戏类型")
    private Integer gameType;
    //注码
    @ApiModelProperty("注码")
    private Integer noteCode;
    //洗码号
    @ApiModelProperty("下注方式")
    private Integer betWay;

    private String account;

    private String userName;

    private Integer page;
    private Integer size;

}
