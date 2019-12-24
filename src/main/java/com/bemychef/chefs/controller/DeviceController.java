package com.bemychef.chefs.controller;

import com.bemychef.chefs.dto.DeviceDTO;
import com.bemychef.chefs.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Response;

@RestController
@RequestMapping("/chefs")
public class DeviceController {

	@Autowired
	private DeviceService deviceService;

	@PostMapping("/device")
	public int addDevice(@RequestBody DeviceDTO deviceDTO) {
		if (deviceService.addDevice(deviceDTO))
			return Response.Status.CREATED.getStatusCode();
		else
			return Response.Status.INTERNAL_SERVER_ERROR.getStatusCode();
	}

	@GetMapping
	public DeviceDTO getDeviceDetails(@PathVariable Long deviceId) {
		return deviceService.findDeviceById(deviceId);
	}

	public DeviceService getDeviceService() {
		return deviceService;
	}

	public void setDeviceService(DeviceService deviceService) {
		this.deviceService = deviceService;
	}

}
