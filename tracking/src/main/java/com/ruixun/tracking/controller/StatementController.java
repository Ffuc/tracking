package com.ruixun.tracking.controller;


import com.ruixun.tracking.common.utils.Result;
import com.ruixun.tracking.common.utils.ResultResponseUtil;
import com.ruixun.tracking.entity.dto.SelectStatementDto;
import com.ruixun.tracking.service.IStatementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author pig
 * @since 2020-03-30
 */
@RestController
@RequestMapping("/tracking/statement")
@Api("洗码结账")
public class StatementController{
    @Autowired
    private IStatementService statementService;
    @CrossOrigin
    @RequestMapping
    @ApiModelProperty("洗码结账条件查询 四个条件必须输入 gameType:类型，account:id，moneyType:注码,jz_time:结束时间")
    public Result selectByCondition(@RequestBody SelectStatementDto statementDto){
        if(statementDto.getAccount()==null||statementDto.getAccount()==""){
            return ResultResponseUtil.ok().msg("id不能为空");
        }
        if(statementDto.getGameType()==null){
            return ResultResponseUtil.ok().msg("游戏不能为空");
        }
        if(statementDto.getMoneyType()==null||statementDto.getAccount()==""){
            return ResultResponseUtil.ok().msg("注码不能为空");
        }
        if(statementDto.getJz_time()==null){
            return ResultResponseUtil.ok().msg("结账时间不能为空");
        }
        return statementService.findByCondition(statementDto);
    }
}
