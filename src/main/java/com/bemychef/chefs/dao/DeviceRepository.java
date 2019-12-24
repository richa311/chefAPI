package com.bemychef.chefs.dao;

import com.bemychef.chefs.model.ChefDevice;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends CrudRepository<ChefDevice, Long> {

}
