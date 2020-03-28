package com.ruixun.tracking.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
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
    @ApiModel(value="TrackingManager对象", description="")
    public class TrackingManager implements Serializable {

    private static final long serialVersionUID = 1L;

            @ApiModelProperty(value = "唯一id")
    private String uuid;

            @ApiModelProperty(value = "账号")
    private String account;

            @ApiModelProperty(value = "密码")
    private String password;

            @ApiModelProperty(value = "权限")
    private String menuList;

            @ApiModelProperty(value = "状态")
    private Integer state;

            @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

            @ApiModelProperty(value = "最后一次登陆")
    private LocalDateTime lastLoginTime;


}
