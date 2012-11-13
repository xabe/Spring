package com.indizen.springCurso.service.entity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.indizen.springCurso.model.entity.Entity;
import com.indizen.springCurso.persistence.entity.EntityDAO;


@Service("entityService")
@Transactional(readOnly = true, isolation = Isolation.SERIALIZABLE)
public class EntityServiceImpl implements EntityService {
	@Autowired
	private EntityDAO entityDAO;

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void add(Entity aEntity) {
		entityDAO.insert(aEntity);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void update(Entity aEntity) {
		entityDAO.update(aEntity);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void delete(Entity aEntity) {
		entityDAO.delete(aEntity);
	}

	public List<Entity> getAll() {
		return entityDAO.getAll();
	}
}
