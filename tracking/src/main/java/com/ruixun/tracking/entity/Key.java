package com.ruixun.tracking.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

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
@ApiModel(value = "Key对象", description = "")
public class Key implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "公钥")
    private String pubicKey;

    @ApiModelProperty(value = "私钥")
    private String privateKey;


}
