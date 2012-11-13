package com.indizen.springCurso.persistence.entity;

import java.util.List;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.support.JpaDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.indizen.springCurso.model.product.Product;

@Repository("productDAO")
@Transactional(readOnly = true, isolation = Isolation.DEFAULT)
public class ProductDAOImpl extends JpaDaoSupport implements ProductDAO {

	@Autowired
	public ProductDAOImpl(EntityManagerFactory entityManagerFactory) {
		setEntityManagerFactory(entityManagerFactory);
	}
	
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED)
	public void delete(Product t) {
		getJpaTemplate().remove(getJpaTemplate().merge(t));
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED)
	public List<Product> getAll() {
		return getJpaTemplate().find("from Product");
	}

	@Transactional(readOnly=false, propagation = Propagation.REQUIRED)
	public void insert(Product t) {
		getJpaTemplate().persist(t);
	}

	@Transactional(readOnly=false, propagation = Propagation.REQUIRED)
	public void update(Product t) {
		getJpaTemplate().merge(t);
	}	

}
