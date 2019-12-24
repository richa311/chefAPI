package com.bemychef.chefs.service.impl;

import com.bemychef.chefs.binder.ChefBinder;
import com.bemychef.chefs.constants.ErrorConstants;
import com.bemychef.chefs.constants.Status;
import com.bemychef.chefs.dao.ChefDao;
import com.bemychef.chefs.dao.ChefRepository;
import com.bemychef.chefs.dto.ChefDTO;
import com.bemychef.chefs.model.Chef;
import com.bemychef.chefs.security.PasswordEncryption;
import com.bemychef.chefs.service.ChefService;
import com.bemychef.chefs.service.ConfirmChefService;
import com.bemychef.chefs.util.PropertiesUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class ChefServiceImpl implements ChefService {
    @Autowired
    private ChefDao chefDao;
    @Autowired
    private ChefRepository chefRepository;
    @Autowired
    private ChefBinder chefBinder;
    @Autowired
    private ConfirmChefService confirmChefService;
    @Autowired
    private PropertiesUtil propertiesUtil;

    private static Logger logger = LoggerFactory.getLogger(ChefServiceImpl.class);

    @Override
    public Response isChefAlreadyPresent(String emailId) {
        logger.debug("Executing isChefAlreadyPresent..");
        if (Objects.nonNull(checkIfChefWithGivenEmailExists(emailId)) &&
                Objects.nonNull(checkIfChefWithGivenEmailExists(emailId).getStatus()) &&
                checkIfChefWithGivenEmailExists(emailId).getStatus().equals(Status.ACTIVE)) {
            return Response.status(Response.Status.OK).entity(ErrorConstants.EMAIL_ALREADY_EXISTS).build();
        } else {
            return Response.status(Response.Status.OK).entity(ErrorConstants.CHEF_NOT_FOUND).build();
        }
    }

    @Override
    public Response updateDetails(Long chefId, ChefDTO chefDTO) {
        logger.debug("updateDetails starts...");
        Chef chef;
        try {
            chef = chefBinder.bindChefDTOToChef(chefDTO);
            chefDao.updateChefDetails(chef);
        } catch (Exception ex) {
            return returnResponseUponException();
        }
        return Response.status(Response.Status.OK).entity(chefDTO).build();
    }

    @Override
    public Response getChefDetails(String category) {
        logger.debug("Executing getChefDetails...");
        List<Chef> chefs = (List<Chef>) chefRepository.findAll();
        List<ChefDTO> chefDTOList = new ArrayList<>();
        if (Objects.nonNull(category)) {
            for (Chef chef : chefs) {
                if (category.equalsIgnoreCase(chef.getCategory())) {
                    chefDTOList.add(chefBinder.bindChefToChefDTO(chef));
                }
            }
        } else {
            chefs.forEach(chef -> chefDTOList.add(chefBinder.bindChefToChefDTO(chef)));
        }
        return Response.status(Response.Status.OK).entity(chefDTOList).build();
    }

    @Override
    public Response getChefDetailsById(Long chefId) {
        logger.debug("getChefDetailsById starts...");
        Optional<Chef> chefOptional = chefRepository.findById(chefId);
        logger.debug("getChefDetailsById ends...");
        if (chefOptional.isPresent())
            return Response.status(Response.Status.OK).entity(chefBinder.bindChefToChefDTO(chefOptional.get())).build();
        else {
            return Response.status(Response.Status.OK).entity(ErrorConstants.CHEF_NOT_FOUND).build();
        }
    }

    @Override
    public Response getChefStatus(Long chefId) {
        logger.debug("getChefStatus starts...");
        Optional<Chef> optionalChef = chefRepository.findById(chefId);
        logger.debug("getChefStatus ends...");
        if (optionalChef.isPresent()) {
            return Response.status(Response.Status.OK).entity(optionalChef.get().getStatus()).build();
        } else {
            return Response.status(Response.Status.OK).entity(ErrorConstants.CHEF_NOT_FOUND).build();
        }
    }

    @Override
    public Response updateStatusByChefId(Long chefId, String status) {
        logger.debug("updateStatusByChefId starts...");
        Status enumStatus = null;
        if (status.equalsIgnoreCase("Active")) {
            enumStatus = Status.ACTIVE;
        } else if (status.equalsIgnoreCase("Inactive")) {
            enumStatus = Status.INACTIVE;
        } else if (status.equalsIgnoreCase("Deleted")) {
            enumStatus = Status.DELETED;
        }
        try {
            chefDao.updateStatusOfChefByChefId(chefId, enumStatus);
            return Response.status(Response.Status.OK).build();
        } catch (Exception ex) {
            return returnResponseUponException();
        }
    }

    private Chef checkIfChefWithGivenEmailExists(String emailId) {
        logger.debug("Executing checkIfChefWithGivenEmailExists...");
        return chefDao.checkIfEmailAlreadyExists(emailId);
    }

    @Override
    public Response getEmailIdByChefId(long id) {
        try {
            Optional<Chef> optionalChef = chefRepository.findById(id);
            if (optionalChef.isPresent()) {
                return Response.status(Response.Status.OK).entity(optionalChef.get().getEmailId()).build();
            } else {
                return Response.status(Response.Status.OK).entity(ErrorConstants.CHEF_NOT_FOUND).build();
            }
        } catch (Exception ex) {
            logger.error("Got exception while getting email by chef Id :" + ex.toString());
            return returnResponseUponException();
        }
    }

    @Override
    public Response registerChef(ChefDTO chefDTO) throws JsonProcessingException {
        List<Response> responseList = validateChef(chefDTO);
        if (!responseList.isEmpty()) {
            return responseList.get(0);
        } else {
            if (Objects.nonNull(chefDTO.getEmailId())) {
                Chef chef = checkIfChefWithGivenEmailExists(chefDTO.getEmailId());
                if (Objects.nonNull(chef) && Objects.nonNull(chef.getStatus()) && chef.getStatus().equals(Status.ACTIVE)) {
                    return Response.status(Response.Status.OK).entity(ErrorConstants.EMAIL_ALREADY_EXISTS).build();
                } else if (Objects.nonNull(chef) && Objects.nonNull(chef.getStatus()) &&
                        (chef.getStatus().equals(Status.INACTIVE) || chef.getStatus().equals(Status.DELETED))) {
                    if (confirmChefService.confirmChef(chef).getStatus() == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
                        return returnResponseUponException();
                    }
                    ChefDTO chefDto = chefBinder.bindChefToChefDTO(chef);
                    return Response.status(Response.Status.OK).entity(chefDto).build();
                } else {
                    return registerAndConfirmChef(chefDTO);
                }
            } else if (Objects.nonNull(chefDTO.getContactNumber())) {
                // code for mobile verification
            }
        }
        return null;
    }

    private Response registerAndConfirmChef(ChefDTO chefDTO) {
        char[] password = chefDTO.getPassword();
        Chef chef = chefBinder.bindChefDTOToChef(chefDTO);
        chef.setPassword(Arrays.toString(password));
        if (Objects.nonNull(register(chef))) {
            if (confirmChefService.confirmChef(chef).getStatus() == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
                return returnResponseUponException();
            }
        } else {
            return returnResponseUponException();
        }
        return Response.status(Response.Status.OK).entity(chefBinder.bindChefToChefDTO(chef)).build();
    }

    private List<Response> validateChef(ChefDTO chefDTO) throws JsonProcessingException {
        List<Response> responseList = new ArrayList<>();
        if (Objects.isNull(chefDTO.getEmailId()) && Objects.isNull(chefDTO.getContactNumber())) {
            responseList.add(Response.status(Response.Status.OK).entity(ErrorConstants.EITHER_MOBILE_OR_EMAIL).build());
        }
        if (responseList.isEmpty() && Objects.isNull(chefDTO.getFirstName())) {
            responseList.add(Response.status(Response.Status.OK).entity(ErrorConstants.INVALID_FIRST_NAME).build());
        }
        if (responseList.isEmpty() && Objects.isNull(chefDTO.getLastName())) {
            responseList.add(Response.status(Response.Status.OK).entity(ErrorConstants.INVALID_LAST_NAME).build());
        }
        if (responseList.isEmpty() && !validateEmail(chefDTO.getEmailId())) {
            responseList.add(Response.status(Response.Status.OK).entity(ErrorConstants.INVALID_EMAIL).build());
        }
        return responseList;
    }

    private Response returnResponseUponException() {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ErrorConstants.CONTACT_ADMIN).build();
    }

    private Chef register(Chef chef) {
        logger.debug("Register chefs method starts..");
        String encryptedPassword = "";
        Chef returnhef = null;
        try {
            encryptedPassword = PasswordEncryption.encrypt(chef.getPassword());
            chef.setPassword(encryptedPassword);
            chef.setStatus(Status.INACTIVE);
            returnhef = chefRepository.save(chef);
            logger.debug("Register chefs method ends..");
        } catch (Exception ex) {
            logger.error("Caught exception while saving chef details : " + ex.toString());
            return null;
        }
        returnhef.setPassword("");
        return returnhef;
    }

    private boolean validateEmail(String emailId) {
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(emailId).matches();
    }

    @Override
    public Response getCountOfChefs(String status) {
        List<Chef> chefList = (List<Chef>) chefRepository.findAll();
        if (status.equalsIgnoreCase("all")) {
            return Response.status(Response.Status.OK).entity(chefList.size()).build();
        } else {
            return Response.status(Response.Status.OK).entity(chefList.stream().filter(chef -> chef.getStatus().toString().equalsIgnoreCase(status)).collect(Collectors.toList()).size()).build();
        }
    }

    public ChefBinder getChefBinder() {
        return chefBinder;
    }

    public void setChefBinder(ChefBinder chefBinder) {
        this.chefBinder = chefBinder;
    }

    public PropertiesUtil getPropertiesUtil() {
        return propertiesUtil;
    }

    public void setPropertiesUtil(PropertiesUtil propertiesUtil) {
        this.propertiesUtil = propertiesUtil;
    }
}
