package com.ruixun.tracking.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Program: tracking_system
 * <p>
 * Description:
 *
 * @Date: 2020-03-25 09:33
 **/
@TableName("tracking_table")
@Data
@ApiModel("table-桌子")

public class Table implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("表的桌号")
    private String tableId;

    @ApiModelProperty("最新靴号")
    private Integer newestBootNum;

    private Integer newestIssueNum;

    private String issueResult;

    public Table() {
    }

    public Table(String tableId, Integer newestBootNum, Integer newestIssueNum, String issueResult) {
        this.tableId = tableId;
        this.newestBootNum = newestBootNum;
        this.newestIssueNum = newestIssueNum;
        this.issueResult = issueResult;
    }
}
