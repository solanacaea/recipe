package com.food.recipe.repositories.lkp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.food.recipe.entries.lkp.Efficacy;

@RepositoryRestResource(collectionResourceRel = "function", path = "function")
public interface EfficacyRepo extends JpaRepository<Efficacy, Integer> {

}
