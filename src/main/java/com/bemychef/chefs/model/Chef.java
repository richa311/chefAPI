package com.bemychef.chefs.model;

import com.bemychef.chefs.constants.Status;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

/**
 * Registration class for chefs
 */
@Entity
@Table(name = "ChefDetail")
public class Chef {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String firstName;
	private String middleName;
	private String lastName;
	private String category;
	private String password;
	private Long contactNumber;
	private String emailId;
	@Enumerated(EnumType.STRING)
	private Status status;
	private Timestamp createdOn;
	private String createdBy;
	private Timestamp modifiedOn;
	private String modifiedBy;
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "id")
	private Set<ChefDevice> chefDevices;
	@Embedded
	private Address addresses;

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
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

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Timestamp modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Set<ChefDevice> getChefDevices() {
		return chefDevices;
	}

	public void setChefDevices(Set<ChefDevice> chefDevices) {
		this.chefDevices = chefDevices;
	}

	public Address getAddresses() {
		return addresses;
	}

	public void setAddresses(Address addresses) {
		this.addresses = addresses;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
}
