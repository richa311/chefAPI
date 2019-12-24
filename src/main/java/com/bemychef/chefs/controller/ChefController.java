package com.bemychef.chefs.controller;

import com.bemychef.chefs.dto.ChefDTO;
import com.bemychef.chefs.service.ChefService;
import com.bemychef.chefs.service.ConfirmChefService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

/**
 * Chefs Controller Class
 */

@RestController
@RequestMapping(path = "/chefs")
public class ChefController {
    @Autowired
    private ChefService chefService;

    @Autowired
    private ConfirmChefService confirmChefService;

    @PostMapping(path = "/register", consumes = "application/json", produces = "application/json")
    public Response registerChefs(@RequestBody ChefDTO chefDTO) throws JsonProcessingException {
        return chefService.registerChef(chefDTO);
    }

    /**
     * This API validates chef by email. It checks if the chef is already present or
     * not.
     *
     * @param emailId
     * @return Http Status code
     */
    @PostMapping(path = "/validate", produces = "application/json")
    public Response validateChefByEmail(@RequestBody String emailId) {
        return chefService.isChefAlreadyPresent(emailId);
    }

    /**
     * @return list of all chefs
     */
    @GetMapping(path = "/details", produces = "application/json")
    public Response getChefs(@RequestParam(required = false, value = "category") String category) {
        return chefService.getChefDetails(category);
    }

    /**
     * @param chefId
     * @return chef which belongs to the passed chefId
     */
    @GetMapping(path = "/details/{chefId}", produces = "application/json")
    public Response getChefDetailsById(@PathVariable Long chefId) {
        return chefService.getChefDetailsById(chefId);
    }

    /**
     * @param chefId
     * @return chef status by chef id
     */
    @GetMapping(path = "/status/{chefId}")
    public Response getChefStatus(@PathVariable Long chefId) {
        return chefService.getChefStatus(chefId);
    }

    /**
     * This API updates the chef's status
     *
     * @param chefId
     * @param status
     * @return Http status code
     */
    @PostMapping(path = "updateStatus/{chefId}", produces = "application/json")
    public Response updateChefStatus(@PathVariable Long chefId, @RequestBody String status) {
        return chefService.updateStatusByChefId(chefId, status);
    }

    /**
     * This API updates chef details.
     *
     * @param chefId
     * @param chefDTO
     */
    @PostMapping(path = "updateDetails/{chefId}", consumes = "application/json", produces = "application/json")
    public Response updateChefDetails(@PathVariable Long chefId, @RequestBody ChefDTO chefDTO) {
        return chefService.updateDetails(chefId, chefDTO);
    }

    /**
     * This API confirms the chef by verifying the token
     *
     * @param confirmationToken
     * @return HTTP status code
     */
    @GetMapping(path = "/confirmAccount", produces = "application/json")
    public Response confirmChefAccount(@RequestParam("token") String confirmationToken) {
        return confirmChefService.verifyChefByToken(confirmationToken);
    }

    @GetMapping(path = "/counts", produces = "application/json")
    public Response getCountOfActiveChefs(@QueryParam(value = "status") String status) {
        return chefService.getCountOfChefs(status);
    }
}
