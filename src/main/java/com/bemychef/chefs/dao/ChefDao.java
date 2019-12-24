package com.bemychef.chefs.dao;

import com.bemychef.chefs.constants.Status;
import com.bemychef.chefs.model.Chef;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChefDao {

	Chef checkIfEmailAlreadyExists(String emailId);

	void updateChefDetails(Chef chef);

	void updateStatusOfChefByChefId(Long chefId, Status status);
}
