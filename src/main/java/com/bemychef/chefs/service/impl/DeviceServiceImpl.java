package com.bemychef.chefs.service.impl;

import com.bemychef.chefs.binder.DeviceBinder;
import com.bemychef.chefs.dao.DeviceRepository;
import com.bemychef.chefs.dto.DeviceDTO;
import com.bemychef.chefs.model.ChefDevice;
import com.bemychef.chefs.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private DeviceBinder binder;

    @Override
    public boolean addDevice(DeviceDTO deviceDTO) {
        try {
            ChefDevice chefDevice = binder.bindDeviceDTOToDevice(deviceDTO);
            deviceRepository.save(chefDevice);
            return true;
        } catch (Exception e) {
            return false;
            // log exception
        }
    }

    @Override
    public DeviceDTO findDeviceById(Long deviceId) {
        Optional<ChefDevice> optionalDevice = deviceRepository.findById(deviceId);
        if (optionalDevice.isPresent()) {
            return binder.bindDeviceToDeviceDTO(optionalDevice.get());
        } else {
            return null;
        }
    }
}
