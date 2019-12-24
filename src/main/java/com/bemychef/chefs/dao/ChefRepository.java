package com.bemychef.chefs.dao;

import com.bemychef.chefs.model.Chef;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChefRepository extends CrudRepository<Chef, Long> {
}
