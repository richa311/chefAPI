package com.bemychef.chefs.binder;

import com.bemychef.chefs.dto.DeviceDTO;
import com.bemychef.chefs.model.ChefDevice;

import javax.inject.Named;

@Named("DeviceBinderBean")
public class DeviceBinder {

	public ChefDevice bindDeviceDTOToDevice(DeviceDTO deviceDTO) {
		ChefDevice chefDevice = new ChefDevice();
		chefDevice.setDeviceToken(deviceDTO.getDeviceToken());
		chefDevice.setDeviceType(deviceDTO.getDeviceType());
		chefDevice.setChef(deviceDTO.getChef());
		return chefDevice;
	}

	public DeviceDTO bindDeviceToDeviceDTO(ChefDevice chefDevice) {
		DeviceDTO deviceDTO = new DeviceDTO();
		deviceDTO.setDeviceToken(chefDevice.getDeviceToken());
		deviceDTO.setDeviceType(chefDevice.getDeviceType());
		deviceDTO.setChef(chefDevice.getChef());
		return deviceDTO;
	}
}
