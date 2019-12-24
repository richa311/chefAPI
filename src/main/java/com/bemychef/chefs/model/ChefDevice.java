package com.bemychef.chefs.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "ChefDevice")
public class ChefDevice {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
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
