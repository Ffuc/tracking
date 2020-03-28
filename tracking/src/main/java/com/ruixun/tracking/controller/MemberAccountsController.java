package com.ruixun.tracking.controller;

import com.ruixun.tracking.common.utils.Result;
import com.ruixun.tracking.entity.dto.MemberSelectCondition;
import com.ruixun.tracking.service.ITrackingMemberCostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Program: tracking_system
 * <p>
 * Description:
 **/
@RestController
@RequestMapping("/tracking/member")
@Api("会员账目")
public class MemberAccountsController {

    @Autowired
    private ITrackingMemberCostService trackingMemberCostService;
    /**
     * 1.会员账目 条件查询
     * 2.会员账目 导出 -excel
     * 3.会员帐目-下单详情  条件查询
     */
//    public Result
//    @PostMapping(value = "/SelectByCondition")
//    @ApiOperation("会员账目 条件查询")
//    public Result SelectByCondition(@RequestBody MemberSelectCondition memberSelectCondition, Integer page, Integer size){
//        if(page==null){
//            page=1;
//        }
//        if(size==null){
//            size=10;
//        }
//        return trackingMemberCostService.selectByCondition(memberSelectCondition,page,size);
//    }
}
