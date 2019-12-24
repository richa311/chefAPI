package com.bemychef.chefs.dao;

import javax.inject.Named;

@Named("ChefConfirmationDaoBean")
public interface ChefConfirmationTokenDao {

	Object findByToken(String confirmationToken);

}
