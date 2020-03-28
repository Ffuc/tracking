package com.ruixun.tracking;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruixun.tracking.entity.DictionaryItem;

import com.ruixun.tracking.service.IDictionaryItemService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;


public class TrackingApplicationTests extends BaseTest {
    @Autowired
    IDictionaryItemService dictionaryItem;

    @Test
    public void contextLoads() {
        QueryWrapper<DictionaryItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne("dic_code", 2);

        List<Map<String, Object>> maps = dictionaryItem.listMaps(queryWrapper);
        Page<DictionaryItem> page = new Page<>(1, 10);


//        Page<DictionaryItem> page1 = dictionaryItem.pageMaps(page, queryWrapper);
        IPage<Map<String, Object>> aa = dictionaryItem.pageMaps(page, queryWrapper);
        for (int i = 0; i < aa.getRecords().size(); i++) {
            Map mao = aa.getRecords().get(i);

            mao.put("", ""); //做添加数据工具
        }

        System.out.println(1);
    }

}
