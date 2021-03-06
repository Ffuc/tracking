package com.ruixun.tracking.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ruixun.tracking.common.utils.Result;
import com.ruixun.tracking.common.utils.ResultResponseUtil;
import com.ruixun.tracking.entity.TrackingMemberCost;
import com.ruixun.tracking.entity.dto.TrackingAgencyAccountsDto;
import com.ruixun.tracking.service.ITrackingAgentAccounts;
import com.ruixun.tracking.service.ITrackingMemberCostService;
import com.ruixun.tracking.service.impl.TrackingAgentAccounts;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Program: tracking_system
 * <p>
 * Description:
 **/
@RestController()
@Api("代理账目")
@RequestMapping("/accounts")
public class AgentAccountsController {

    /**
     * 1.代理账目表-条件查询所有信息
     * 2.代理账目-结账-条件查询
     * 3.理账目-结账-导出代, 导出为excel
     * 4.账目-统计页面-查询代理
     */
//    @Autowired
//    private ITrackingMemberCostService trackingMemberCostService;
//
//    /**
//     * 1.代理账目表-条件查询所有信息
//     */
//    @PostMapping(value = "/SelectByCondition2")
//    @ApiOperation("龙虎和局-条件查询")
//    public Result agentAccount(){
//
//    }
    @Autowired
    private ITrackingAgentAccounts iTrackingAgentAccounts;


    @PostMapping("/findAgentcy")
    @ApiOperation("代理账目 1.代理账目表-条件查询所有信息")
    public Result findAgentcy(@RequestBody TrackingAgencyAccountsDto trackingAgencyAccountsDto){
        IPage<Map<String, Object>> all = iTrackingAgentAccounts.getAll(trackingAgencyAccountsDto);
        return ResultResponseUtil.ok().msg("查询成功").data(all);
    }


}
