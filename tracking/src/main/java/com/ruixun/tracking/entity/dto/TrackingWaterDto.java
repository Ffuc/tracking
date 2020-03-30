package com.ruixun.tracking.entity.dto;

import com.ruixun.tracking.entity.TrackingWater;
import com.ruixun.tracking.entity.TrackingWaterDetails;

public class TrackingWaterDto extends TrackingWater {
    private TrackingWaterDetails trackingWaterDetails;

    public TrackingWaterDto(TrackingWaterDetails trackingWaterDetails) {
        this.trackingWaterDetails = trackingWaterDetails;
    }

    public TrackingWaterDto() {
    }

    public TrackingWaterDetails getTrackingWaterDetails() {
        return trackingWaterDetails;
    }

    public void setTrackingWaterDetails(TrackingWaterDetails trackingWaterDetails) {
        this.trackingWaterDetails = trackingWaterDetails;
    }
}
