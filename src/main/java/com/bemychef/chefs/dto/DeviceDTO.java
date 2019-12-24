package com.bemychef.chefs.dto;

import com.bemychef.chefs.model.Chef;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeviceDTO {

    @NotNull
    private String deviceType;
    private Long DeviceToken;
    @NotNull
    @ManyToOne
    private Chef chef;

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public Long getDeviceToken() {
        return DeviceToken;
    }

    public void setDeviceToken(Long deviceToken) {
        DeviceToken = deviceToken;
    }

    public Chef getChef() {
        return chef;
    }

    public void setChef(Chef chef) {
        this.chef = chef;
    }
}
