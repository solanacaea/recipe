package com.food.recipe.repositories;

import java.lang.reflect.Field;
import java.util.Date;

import javax.persistence.EntityManager;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.food.recipe.entries.BackupBean;

import lombok.extern.apachecommons.CommonsLog;

@Repository
@CommonsLog
@Transactional
public class BackupDao {

	@Autowired
	private EntityManager em;
	
	public void auditBackup(Object source, BackupBean target) {
		try {
			BeanUtils.copyProperties(source, target, "id");
			target.setDeleteDate(new Date());
			
			Field idField = source.getClass().getDeclaredField("id");
			idField.setAccessible(true);
			target.setSourceId((int) idField.get(source));
			
			em.merge(target);
		} catch (Exception e) {
			log.error("Error to add audit: " + ExceptionUtils.getStackTrace(e));
		}
	}
	
}
