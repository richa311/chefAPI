package com.bemychef.chefs.binder;

import com.bemychef.chefs.dto.ChefDTO;
import com.bemychef.chefs.model.Chef;

import javax.inject.Named;
import java.sql.Timestamp;
import java.util.Date;

@Named("ChefBinderBean")
public class ChefBinder {

	public Chef bindChefDTOToChef(ChefDTO chefDTO) {
		Chef chef = new Chef();
		chef.setFirstName(chefDTO.getFirstName());
		chef.setMiddleName(chefDTO.getMiddleName());
		chef.setLastName(chefDTO.getLastName());
		chef.setContactNumber(chefDTO.getContactNumber());
		chef.setEmailId(chefDTO.getEmailId());
		chef.setId(chefDTO.getId());
		chef.setStatus(chefDTO.getStatus());
		chef.setCreatedBy(chefDTO.getEmailId());
		chef.setCreatedOn(getTimeStamp());
		chef.setModifiedBy(chefDTO.getEmailId());
		chef.setModifiedOn(getTimeStamp());
		chef.setCategory(chefDTO.getCategory());
		return chef;
	}
	
	private Timestamp getTimeStamp() {
		Date date = new Date();
		Long time = date.getTime();
		return new Timestamp(time);
	}

	public ChefDTO bindChefToChefDTO(Chef chef) {
		ChefDTO chefDTO = new ChefDTO();
		chefDTO.setFirstName(chef.getFirstName());
		chefDTO.setMiddleName(chef.getMiddleName());
		chefDTO.setLastName(chef.getLastName());
		chefDTO.setContactNumber(chef.getContactNumber());
		chefDTO.setEmailId(chef.getEmailId());
		chefDTO.setId(chef.getId());
		chefDTO.setStatus(chef.getStatus());
		chefDTO.setCategory(chef.getCategory());
		
		return chefDTO;
	}
}
