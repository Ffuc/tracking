package com.ruixun.tracking.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.joda.time.LocalDateTime;

import java.io.Serializable;

/**
 * @author suitao
 * @description GameController的查询DTO
 */
@Data
@ApiModel("龙虎和局")
public class GameSelectCondition2 implements Serializable {


    @ApiModelProperty("账号")
    private String account;
    @ApiModelProperty("牌")
    private Integer page;
    @ApiModelProperty("大小")
    private Integer size;

}
