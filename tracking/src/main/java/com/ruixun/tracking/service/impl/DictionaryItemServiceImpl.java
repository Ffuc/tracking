package com.ruixun.tracking.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruixun.tracking.common.utils.Result;
import com.ruixun.tracking.common.utils.ResultResponseUtil;
import com.ruixun.tracking.dao.DictionaryItemMapper;
import com.ruixun.tracking.entity.DictionaryItem;
import com.ruixun.tracking.service.IDictionaryItemService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 字典类型条目表 服务实现类
 * </p>
 *
 * @author pig
 * @since 2020-03-28
 */
@Service
public class DictionaryItemServiceImpl extends ServiceImpl<DictionaryItemMapper, DictionaryItem> implements IDictionaryItemService {

    @Override
    public Result getGameType() {
        QueryWrapper<DictionaryItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().and(i -> i.eq(DictionaryItem::getDicCode, "game").ne(DictionaryItem::getCodeIndex, 2));

        List<DictionaryItem> list = list(queryWrapper);
        return ResultResponseUtil.ok().msg("查询成功").data(list);
    }
}
