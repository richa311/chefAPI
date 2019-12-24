package com.bemychef.chefs.service;

import com.bemychef.chefs.model.Chef;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;

@Service
public interface ConfirmChefService {

	Response confirmChef(Chef chef);

	Response verifyChefByToken(String confirmationToken);

}
