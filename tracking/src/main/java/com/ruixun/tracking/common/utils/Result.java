package com.ruixun.tracking.common.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Program: Novel
 * <p>
 * Description:
 *
 * @Date: 2020-02-13 14:35
 **/
@ApiModel("Result-结果")
public class Result {
    @ApiModelProperty("状态")
    private Integer code;
    @ApiModelProperty("消息")
    private String msg;
    @ApiModelProperty("数据")
    private Object data;

    public Result(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
    
    public Result() {
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Result msg(String msg) {
        this.setMsg(msg);
        return this;
    }

    public Result data(Object object) {
        this.setData(object);
        return this;
    }
}
