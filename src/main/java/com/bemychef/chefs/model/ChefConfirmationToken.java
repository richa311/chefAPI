package com.bemychef.chefs.model;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "ChefConfirmationToken")
public class ChefConfirmationToken {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long tokenId;
	private String token;
	@OneToOne(targetEntity = Chef.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "id")
	private Chef chef;
	private Date createdDate;
	
	public ChefConfirmationToken() {
		//default Constructor
	}
	
	public ChefConfirmationToken(Chef chef) {
		this.chef = this.chef;
		this.createdDate = new Date();
		this.token = UUID.randomUUID().toString();
	}
	
	public Long getTokenId() {
		return tokenId;
	}
	public void setTokenId(Long tokenId) {
		this.tokenId = tokenId;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Chef getChef() {
		return chef;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}
}
