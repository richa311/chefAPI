package com.bemychef.chefs.dto;

import com.bemychef.chefs.constants.Status;
import com.bemychef.chefs.model.Address;
import com.bemychef.chefs.model.ChefDevice;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.Set;

@JsonInclude(Include.NON_NULL)
@NotNull
public class ChefDTO {
    private long id;
    @NotNull
    private String firstName;
    private String middleName;
    private String lastName;
    @NotNull
    private String category;
    @NotNull
    private char[] password;
    private Long contactNumber;
    private String emailId;
    private Status status;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private Set<ChefDevice> chefDevices;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private Set<Address> addresses;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    public Long getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(Long contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Set<ChefDevice> getChefDevices() {
        return chefDevices;
    }

    public void setChefDevices(Set<ChefDevice> chefDevices) {
        this.chefDevices = chefDevices;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
