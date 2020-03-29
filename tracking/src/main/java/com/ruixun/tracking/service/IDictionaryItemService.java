package com.ruixun.tracking.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.ruixun.tracking.common.utils.Result;
import com.ruixun.tracking.entity.DictionaryItem;

/**
 * <p>
 * 字典类型条目表 服务类
 * </p>
 *
 * @author pig
 * @since 2020-03-28
 */
public interface IDictionaryItemService extends IService<DictionaryItem> {
    Result getGameType();

    String getGameCN(String code, int index);

}
