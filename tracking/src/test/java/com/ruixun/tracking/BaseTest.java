package com.ruixun.tracking;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruixun.tracking.dao.TrackingWaterMapper;
import com.ruixun.tracking.entity.TrackingWater;
import com.ruixun.tracking.entity.dto.TrackingAgencyAccountsDto;
import com.ruixun.tracking.service.ITrackingAgentAccounts;
import com.ruixun.tracking.service.ITrackingWaterService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Program: tracking_system
 * <p>
 * Description:
 *
 * @Date: 2020-03-25 11:04
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class BaseTest {

    @Autowired
    private ITrackingWaterService iTrackingWaterService;

    @Test
    public void test2(){

        LocalDateTime now = LocalDateTime.now().plusDays(-1);
        System.out.println(now);

        IPage<Map<String, Object>> h1001
                = iTrackingWaterService.waterAccounts(new TrackingAgencyAccountsDto(now, "H1001", "H1001-1"), 2);
        System.out.println(h1001.getRecords());
        List<Map<String, Object>> records = h1001.getRecords();
        for (Map<String, Object> record : records) {
            Set<String> strings = record.keySet();
            for (String string : strings) {
                System.out.println(string+"------"+record.get(string));
            }
        }
    }
    @Autowired
    private ITrackingAgentAccounts iTrackingAgentAccounts;

    @Test
    public void test(){
        TrackingAgencyAccountsDto trackingAgencyAccountsDto = new TrackingAgencyAccountsDto();
        trackingAgencyAccountsDto.setAll(1);
        IPage<Map<String, Object>> all = iTrackingAgentAccounts.getAll(trackingAgencyAccountsDto);

        List<Map<String, Object>> records = all.getRecords();
        for (Map<String, Object> record : records) {
            Set<String> strings = record.keySet();
            for (String string : strings) {
                System.out.println(string+"------"+record.get(string));
            }
        }

        System.out.println(all.getRecords());
    }

    @Test
    public void test3(){
        BigDecimal bigDecimal = new BigDecimal(0);
        for (int i=0 ; i<10;i++){
            BigDecimal bigDecimal1 = new BigDecimal(i);
            bigDecimal = bigDecimal.add(bigDecimal);
        }
        System.out.println(bigDecimal);
    }

    @Autowired
    private TrackingWaterMapper trackingWaterMapper;
    @Test
    public void test4(){
//        Map<String, Object> tablesInfo = trackingWaterMapper.findTablesInfo();
    /*    Set<String> strings = tablesInfo.keySet();
        for (String string : strings) {
            System.out.println(string);
        }*/
    }

}
