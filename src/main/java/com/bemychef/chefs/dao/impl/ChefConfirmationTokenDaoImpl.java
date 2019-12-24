package com.bemychef.chefs.dao.impl;

import com.bemychef.chefs.dao.ChefConfirmationTokenDao;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Named("ChefConfirmationDaoImplBean")
public class ChefConfirmationTokenDaoImpl implements ChefConfirmationTokenDao {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Object findByToken(String confirmationToken) {
		Query query = em.createQuery("SELECT ct FROM ConfirmationToken ct WHERE ct.token =: token"); 
		query.setParameter("token", confirmationToken);
		
		return query.getSingleResult();
	}

	
}
