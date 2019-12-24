package com.bemychef.chefs.service;

import com.bemychef.chefs.dto.ChefDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;

/**
 * Service class for registration
 */
@Service
public interface ChefService {

	/**
	 * Validates if chef is new or is already present by emailID
	 *
	 * @param emailId
	 */
	Response isChefAlreadyPresent(String emailId);

	/**
	 * Updates chef's registration details
	 *
	 * @param chefId
	 * @param chefDTO
	 */
	Response updateDetails(Long chefId, ChefDTO chefDTO);

	/**
	 *
	 * @return List of Chefs
	 */
	Response getChefDetails(String category);

	/**
	 *
	 * @param chefId
	 * @return chef by chefId
	 */
	Response getChefDetailsById(Long chefId);

	/**
	 * gets chef's status by chefId
	 * 
	 * @param chefId
	 * @return Response
	 */
	Response getChefStatus(Long chefId);

	/**
	 * updates chef's status by chefId
	 * 
	 * @param chefId
	 * @param status
	 * @return Response
	 */
	Response updateStatusByChefId(Long chefId, String status);

	/**
	 * gets email id by chef id
	 * 
	 * @param id
	 * @return Response
	 */
	Response getEmailIdByChefId(long id);

	/**
	 *
	 * @param chefDTO
	 * @return Response
	 * @throws JsonProcessingException
	 */
	Response registerChef(ChefDTO chefDTO) throws JsonProcessingException;

	/**
	 * @param status
	 * @return Response containing count of chefs
	 */
	Response getCountOfChefs(String status);
}
