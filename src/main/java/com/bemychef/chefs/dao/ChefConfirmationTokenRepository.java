package com.bemychef.chefs.dao;

import com.bemychef.chefs.model.ChefConfirmationToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.inject.Named;

@Repository
@Named(value = "ChefConfirmationTokenRepo")
public interface ChefConfirmationTokenRepository extends CrudRepository<ChefConfirmationToken, Long>{

}
