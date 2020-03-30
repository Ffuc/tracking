package com.ruixun.tracking;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruixun.tracking.dao.TrackingWaterMapper;
import com.ruixun.tracking.entity.DictionaryItem;

import com.ruixun.tracking.entity.TrackingWater;
import com.ruixun.tracking.entity.dto.TrackingAgencyAccountsDto;
import com.ruixun.tracking.service.IDictionaryItemService;
import com.ruixun.tracking.service.ITrackingAgentAccounts;
import io.swagger.annotations.ApiModelProperty;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TrackingApplicationTests {
    @Autowired
    IDictionaryItemService dictionaryItem;

    @Autowired
    private ITrackingAgentAccounts iTrackingAgentAccounts;
    @Test
    public void test(){
        TrackingAgencyAccountsDto trackingAgencyAccountsDto = new TrackingAgencyAccountsDto();
        trackingAgencyAccountsDto.setMonyType("0");
        IPage<Map<String, Object>> all = iTrackingAgentAccounts.getAll(trackingAgencyAccountsDto);
        List<Map<String, Object>> records = all.getRecords();
        for (Map<String, Object> record : records) {
            Set<String> strings = record.keySet();
            for (String string : strings) {
                System.out.println(string+" ----------  "+record.get(string));
            }
            System.out.println("=================华丽的分隔符===================");
        }
        System.out.println(all.getRecords());
    }
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
    @Autowired
    private TrackingWaterMapper trackingWaterMapper;
    @Test
    public void test3(){
        List<TrackingWater> realTimeLimit = trackingWaterMapper.findRealTimeLimit();
        System.out.println(realTimeLimit);
    }

}
