package com.bemychef.chefs.service;

import com.bemychef.chefs.dto.DeviceDTO;
import org.springframework.stereotype.Service;

@Service
public interface DeviceService {

	public boolean addDevice(DeviceDTO deviceDTO);

	DeviceDTO findDeviceById(Long deviceId);
}
