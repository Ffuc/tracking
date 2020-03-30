package com.ruixun.tracking.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruixun.tracking.common.utils.Result;
import com.ruixun.tracking.common.utils.ResultResponseUtil;
import com.ruixun.tracking.dao.StatementMapper;
import com.ruixun.tracking.dao.TrackingWaterDetailsMapper;
import com.ruixun.tracking.entity.Statement;
import com.ruixun.tracking.entity.TrackingWaterDetails;
import com.ruixun.tracking.entity.dto.SelectStatementDto;
import com.ruixun.tracking.service.IStatementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author pig
 * @since 2020-03-30
 */
@Service
public class StatementServiceImpl extends ServiceImpl<StatementMapper, Statement> implements IStatementService {

    @Autowired
    private TrackingWaterDetailsMapper waterDetailsMapper;
    @Autowired
    private IStatementService statementService;

    @Override
    public Result findByCondition(SelectStatementDto statementDto) {
        QueryWrapper<TrackingWaterDetails> wrapper_waterDetails = new QueryWrapper<TrackingWaterDetails>();
        wrapper_waterDetails.lambda().eq(TrackingWaterDetails::getAccount,statementDto.getAccount());
        /*查询未结账*/
        wrapper_waterDetails.lambda().eq(TrackingWaterDetails::getIsCheckout,1);
        List<TrackingWaterDetails> trackingWaterDetails = waterDetailsMapper.selectList(wrapper_waterDetails);
        /*没有未结账*/
        if(trackingWaterDetails==null||trackingWaterDetails.size()==0){
            QueryWrapper<Statement> wrapper_statement = new QueryWrapper<Statement>();
            wrapper_statement.lambda().eq(Statement::getMemberId,statementDto.getAccount())
            .le(Statement::getCheckoutTime,statementDto.getJz_time());
            List<Statement> statements = statementService.list(wrapper_statement);
            /*没有历史记录*/
            if(statements==null||statements.size()==0){
                return ResultResponseUtil.ok().msg("没有历史记录");
            }
//            /*返回历史记录*/
//            Statement statement = statements.get(0);
//            for (int i = 0; i < statements.size(); i++) {
//               if(statement.getCheckoutTime().toEpochSecond(ZoneOffset.of("+8"))<statements.get(i).getCheckoutTime().toEpochSecond(ZoneOffset.of("+8")){
//                   statement = statements.get(i);
//                }
//            }

            return ResultResponseUtil.ok().data(null);
        }
        return ResultResponseUtil.ok();
    }

}
