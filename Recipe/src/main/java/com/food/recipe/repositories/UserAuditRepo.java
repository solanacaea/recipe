package com.food.recipe.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.food.recipe.entries.UserAudit;

@RepositoryRestResource(collectionResourceRel = "useraudit", path = "useraudit")
public interface UserAuditRepo extends JpaRepository<UserAudit, Integer> {

}
