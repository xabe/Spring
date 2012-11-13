package com.indizen.springCurso.persistence.entity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.indizen.springCurso.model.entity.Entity;

@Repository("entityDAO")
@Transactional(readOnly = true, isolation=Isolation.SERIALIZABLE)
public class EntityDAOImpl extends SqlMapClientDaoSupport implements EntityDAO {


    @Autowired
    public EntityDAOImpl(SqlMapClient sqlMapClient) {
        setSqlMapClient(sqlMapClient);
    }
    
    @Transactional(readOnly=false, propagation = Propagation.REQUIRED)
    public void insert(Entity t) {
        getSqlMapClientTemplate().insert("t_entity.insert", t);
    }

    @Transactional(readOnly=false, propagation = Propagation.REQUIRES_NEW)
    public void delete(Entity t) {
    	getSqlMapClientTemplate().delete("t_entity.delete", t);
    }
    
    @SuppressWarnings("unchecked")
    public List<Entity> getAll() {
    	return getSqlMapClientTemplate().queryForList("t_entity.select");
    }
    
    @Transactional(readOnly=false, propagation = Propagation.REQUIRES_NEW)
    public void update(Entity t) {
    	getSqlMapClientTemplate().update("t_entity.update", t);
    }   
}
