package com.ruixun.tracking.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Program: tracking_system
 * <p>
 * Description:
 *
 * @Date: 2020-03-24 15:38
 **/
@Data
@ApiModel("游戏结果")
public class GResult {
    @ApiModelProperty("期号")
    private Issue issue;
    @ApiModelProperty("牌")
    List<Card> cards;
    @ApiModelProperty("游戏类别,0是百家乐,1是龙虎")
    Integer gameType;
    @ApiModelProperty("期号结果,通过-拼接多选结果")
    String result;


}
