package com.ruixun.tracking.entity.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Program: tracking
 * <p>
 * Description:
 *
 * @Date: 2020-03-28 18:34
 **/
@ApiModel("代理")
public class Agent {

    @ApiModelProperty("上级代理卡号")
    String superAgent;

    @ApiModelProperty("账号")
    String Account;

    @ApiModelProperty("姓名")
    String name;

    @ApiModelProperty("卡号")
    String card_id;

    @ApiModelProperty("电话")
    String phone;

    @ApiModelProperty("记录")

    String remark;

    @ApiModelProperty("洗码率")
    String wash_code_ratio;


}
