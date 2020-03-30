package com.ruixun.tracking.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Program: tracking_system
 * <p>
 * Description:
 *
 * @Date: 2020-03-26 11:10
 **/
@ApiModel("userController查询可能提供的参数")
@Data
public class UserParams2 {

    @ApiModelProperty("上级代理")
    String higherAgent;

    @ApiModelProperty("账号")
    String account;

    @ApiModelProperty("姓名")
    String name;


    @ApiModelProperty("用户类别")
    Integer type;

    @ApiModelProperty("用户页码")
    Integer page;

}
