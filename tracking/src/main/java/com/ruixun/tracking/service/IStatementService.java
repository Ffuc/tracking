package com.ruixun.tracking.service;

import com.ruixun.tracking.common.utils.Result;
import com.ruixun.tracking.entity.Statement;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruixun.tracking.entity.dto.SelectStatementDto;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author pig
 * @since 2020-03-30
 */
public interface IStatementService extends IService<Statement> {

    Result findByCondition(SelectStatementDto statementDto);
}
