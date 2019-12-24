package com.bemychef.chefs.dao.impl;

import com.bemychef.chefs.constants.Status;
import com.bemychef.chefs.dao.ChefDao;
import com.bemychef.chefs.model.Chef;
import com.bemychef.chefs.security.PasswordEncryption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Named("chefDaoBean")
public class ChefDaoImpl implements ChefDao {

    @PersistenceContext
    private EntityManager em;

    private static Logger logger = LoggerFactory.getLogger(PasswordEncryption.class);

    @Override
    public Chef checkIfEmailAlreadyExists(String emailId) {
        logger.debug("checkIfEmailAlreadyExists starts..");
        Query query = em.createQuery("SELECT u FROM Chef u WHERE u.emailId = :emailId");
        query.setParameter("emailId", emailId);
        logger.debug("checkIfEmailAlreadyExists ends..");
        if (!query.getResultList().isEmpty()) {
            return (Chef) query.getResultList().get(0);
        } else {
            return null;
        }
    }

    @Override
    public void updateChefDetails(Chef chef) {
        logger.debug("updateChefDetails starts..");
        Query query = em.createQuery(
                "UPDATE Chef u SET u.firstName =:firstName, u.lastName =:lastName, u.middleName =:middleName, u.category =:category"
                        + "u.contactNumber =:contactNumber, u.emailId =:emailId, u.status =:status, u.createdBy =:createdBy, u.createdOn =:createdOn,"
                        + "u.  modifiedOn =:modifiedOn, u.modifiedBy =:modifiedBy");
        query.setParameter("firstName", chef.getFirstName());
        query.setParameter("lastName", chef.getLastName());
        query.setParameter("middleName", chef.getMiddleName());
        query.setParameter("category", chef.getCategory());
        query.setParameter("contactNumber", chef.getContactNumber());
        query.setParameter("emailId", chef.getEmailId());
        query.setParameter("status", chef.getStatus());
        query.setParameter("createdBy", chef.getCreatedBy());
        query.setParameter("createdOn", chef.getCreatedOn());
        query.setParameter("modifiedOn", chef.getModifiedOn());
        query.setParameter("modifiedBy", chef.getModifiedBy());

        logger.debug("updateChefDetails ends..");
        query.executeUpdate();
    }

    @Override
    @Transactional
    public void updateStatusOfChefByChefId(Long chefId, Status status) {
        logger.debug("updateStatusOfChefByChefId starts..");
        Query query = em.createQuery("UPDATE Chef u SET u.status = :status WHERE u.id = :chefId");
        query.setParameter("status", status);
        query.setParameter("chefId", chefId);

        em.joinTransaction();
        query.executeUpdate();
        logger.debug("updateStatusOfChefByChefId starts..");
    }
}
