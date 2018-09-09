package com.food.recipe.repositories.lkp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.food.recipe.entries.lkp.Type;

@RepositoryRestResource(collectionResourceRel = "type", path = "type")
public interface TypeRepo extends JpaRepository<Type, Integer> {

}
