package com.ruixun.tracking.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
* <p>
    * 字典类型条目表
    * </p>
*
* @author pig
* @since 2020-03-28
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    @ApiModel(value="DictionaryItem对象", description="字典类型条目表")
    public class DictionaryItem implements Serializable {

    private static final long serialVersionUID = 1L;

            @ApiModelProperty(value = "主键")
            @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

            @ApiModelProperty(value = "字段代码")
    private String dicCode;

            @ApiModelProperty(value = "字段名字")
    private String dicName;

            @ApiModelProperty(value = "对应的数字")
    private Integer codeIndex;

            @ApiModelProperty(value = "中文")
    private String indexNameCn;

            @ApiModelProperty(value = "英文")
    private String indexNameEn;


}
