package com.bemychef.chefs.service.impl;

import com.bemychef.chefs.constants.ErrorConstants;
import com.bemychef.chefs.dao.ChefConfirmationTokenDao;
import com.bemychef.chefs.dao.ChefConfirmationTokenRepository;
import com.bemychef.chefs.model.Chef;
import com.bemychef.chefs.model.ChefConfirmationToken;
import com.bemychef.chefs.service.ChefService;
import com.bemychef.chefs.service.ConfirmChefService;
import com.bemychef.chefs.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import javax.inject.Named;
import javax.ws.rs.core.Response;

@Service
@Named(value = "confirmationChefServiceImpl")
public class ConfirmChefServiceImpl implements ConfirmChefService {

    @Autowired
    private ChefService chefService;
    @Autowired
    private ChefConfirmationTokenRepository chefConfirmationTokenRepository;
    @Autowired
    private EmailService emailSenderService;
    @Autowired
    private ChefConfirmationTokenDao chefConfirmationTokenDao;

    private static Logger logger = LoggerFactory.getLogger(ConfirmChefServiceImpl.class);

    @Override
    public Response confirmChef(Chef chef) {
        ChefConfirmationToken token = new ChefConfirmationToken(chef);
        try {
            sendChefEmail(chef, token);
            return Response.status(Response.Status.OK).build();
        } catch (Exception ex) {
            logger.error("Exception occurred during saving confirmation token : " + ex.toString());
            return returnResponseUponException();
        }
    }

    private void sendChefEmail(Chef chef, ChefConfirmationToken token) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("saurabhguest8899@gmail.com");
        mailMessage.setTo(chef.getEmailId());
        mailMessage.setSubject("Be My Chef: Registration");
        mailMessage.setText("To confirm your account, please click here : "
                + "http://localhost:8080/v1/api/confirm-account?token=" + token.getToken());
        emailSenderService.sendEmail(mailMessage);
    }

    @Override
    public Response verifyChefByToken(String confirmationToken) {
        ChefConfirmationToken chefConfirmationTokenObj = (ChefConfirmationToken) chefConfirmationTokenDao
                .findByToken(confirmationToken);
        if (null != chefConfirmationTokenObj) {
            try {
                activateChef(chefConfirmationTokenObj);
                return Response.status(Response.Status.OK).build();
            } catch (Exception ex) {
                logger.error("Exception occurred while verifying token for chef : " + ex.toString());
                return returnResponseUponException();
            }
        } else {
            return Response.status(Response.Status.OK).entity(ErrorConstants.CHEF_NOT_FOUND).build();
        }
    }

    private Response returnResponseUponException() {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ErrorConstants.CONTACT_ADMIN).build();
    }

    private Response activateChef(ChefConfirmationToken chefConfirmationTokenObj) {
        return chefService.updateStatusByChefId(chefConfirmationTokenObj.getChef().getId(), "Active");
    }

    public ChefService getChefService() {
        return chefService;
    }

    public void setChefService(ChefService chefService) {
        this.chefService = chefService;
    }

    public ChefConfirmationTokenRepository getChefConfirmationTokenRepository() {
        return chefConfirmationTokenRepository;
    }

    public void setChefConfirmationTokenRepository(ChefConfirmationTokenRepository chefConfirmationTokenRepository) {
        this.chefConfirmationTokenRepository = chefConfirmationTokenRepository;
    }

    public ChefConfirmationTokenDao getChefConfirmationTokenDao() {
        return chefConfirmationTokenDao;
    }

    public void setChefConfirmationTokenDao(ChefConfirmationTokenDao chefConfirmationTokenDao) {
        this.chefConfirmationTokenDao = chefConfirmationTokenDao;
    }
}
