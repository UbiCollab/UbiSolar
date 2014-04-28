package com.sintef_energy.ubisolar.structs;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yammer.dropwizard.json.JsonSnakeCase;

import java.util.Date;

/**
 * Created by haavard on 2/19/14.
 */
@JsonSnakeCase
public class DeviceUsage {
    @JsonProperty
    private long id;
    @JsonProperty
    private long timestamp;
    @JsonProperty
    private double powerUsage;
    @JsonProperty
    private long deviceId;

    public DeviceUsage() {}

    public DeviceUsage(long id, long deviceId, long timestamp, double powerUsage) {
        this.id = id;
        this.powerUsage = powerUsage;
        this.deviceId = deviceId;
        this.timestamp = timestamp;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public double getPowerUsage() {
        return powerUsage;
    }

    public void setPowerUsage(double powerUsage) {
        this.powerUsage = powerUsage;
    }

    public long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(long deviceId) {
        this.deviceId = deviceId;
    }
}

