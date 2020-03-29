package com.ruixun.tracking.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ruixun.tracking.entity.dto.TrackingAgencyAccountsDto;
import com.ruixun.tracking.service.impl.TrackingAgentAccounts;
import java.util.List;
import java.util.Map;

public interface ITrackingAgentAccounts {
    IPage<Map<String, Object>> getAll(TrackingAgencyAccountsDto trackingAgencyAccountsDto);
}
