package com.food.recipe.repositories.lkp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.food.recipe.entries.lkp.Catagory;

@RepositoryRestResource(collectionResourceRel = "catagory", path = "catagory")
public interface CatagoryRepo extends JpaRepository<Catagory, Integer> {

}
