package com.indizen.cursoSpring.servicio.model.vgrouppermission;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.indizen.cursoSpring.servicio.model.ExampleBase;
public class VGroupPermissionExample implements ExampleBase {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table v_group_permission
     *
     * @mbggenerated Fri Apr 27 11:35:15 CEST 2012
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table v_group_permission
     *
     * @mbggenerated Fri Apr 27 11:35:15 CEST 2012
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table v_group_permission
     *
     * @mbggenerated Fri Apr 27 11:35:15 CEST 2012
     */
    protected List oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table v_group_permission
     *
     * @mbggenerated Fri Apr 27 11:35:15 CEST 2012
     */
    public VGroupPermissionExample() {
        oredCriteria = new ArrayList();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table v_group_permission
     *
     * @mbggenerated Fri Apr 27 11:35:15 CEST 2012
     */
    protected VGroupPermissionExample(VGroupPermissionExample example) {
        this.orderByClause = example.orderByClause;
        this.oredCriteria = example.oredCriteria;
        this.distinct = example.distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table v_group_permission
     *
     * @mbggenerated Fri Apr 27 11:35:15 CEST 2012
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table v_group_permission
     *
     * @mbggenerated Fri Apr 27 11:35:15 CEST 2012
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table v_group_permission
     *
     * @mbggenerated Fri Apr 27 11:35:15 CEST 2012
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table v_group_permission
     *
     * @mbggenerated Fri Apr 27 11:35:15 CEST 2012
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table v_group_permission
     *
     * @mbggenerated Fri Apr 27 11:35:15 CEST 2012
     */
    public List getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table v_group_permission
     *
     * @mbggenerated Fri Apr 27 11:35:15 CEST 2012
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table v_group_permission
     *
     * @mbggenerated Fri Apr 27 11:35:15 CEST 2012
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table v_group_permission
     *
     * @mbggenerated Fri Apr 27 11:35:15 CEST 2012
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table v_group_permission
     *
     * @mbggenerated Fri Apr 27 11:35:15 CEST 2012
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table v_group_permission
     *
     * @mbggenerated Fri Apr 27 11:35:15 CEST 2012
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table v_group_permission
     *
     * @mbggenerated Fri Apr 27 11:35:15 CEST 2012
     */
    protected abstract static class GeneratedCriteria {
        protected List criteriaWithoutValue;

        protected List criteriaWithSingleValue;

        protected List criteriaWithListValue;

        protected List criteriaWithBetweenValue;

        protected GeneratedCriteria() {
            super();
            criteriaWithoutValue = new ArrayList();
            criteriaWithSingleValue = new ArrayList();
            criteriaWithListValue = new ArrayList();
            criteriaWithBetweenValue = new ArrayList();
        }

        public boolean isValid() {
            return criteriaWithoutValue.size() > 0
                || criteriaWithSingleValue.size() > 0
                || criteriaWithListValue.size() > 0
                || criteriaWithBetweenValue.size() > 0;
        }

        public List getCriteriaWithoutValue() {
            return criteriaWithoutValue;
        }

        public List getCriteriaWithSingleValue() {
            return criteriaWithSingleValue;
        }

        public List getCriteriaWithListValue() {
            return criteriaWithListValue;
        }

        public List getCriteriaWithBetweenValue() {
            return criteriaWithBetweenValue;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteriaWithoutValue.add(condition);
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            Map map = new HashMap();
            map.put("condition", condition);
            map.put("value", value);
            criteriaWithSingleValue.add(map);
        }

        protected void addCriterion(String condition, List values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            Map map = new HashMap();
            map.put("condition", condition);
            map.put("values", values);
            criteriaWithListValue.add(map);
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            List list = new ArrayList();
            list.add(value1);
            list.add(value2);
            Map map = new HashMap();
            map.put("condition", condition);
            map.put("values", list);
            criteriaWithBetweenValue.add(map);
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdGroupIsNull() {
            addCriterion("id_group is null");
            return (Criteria) this;
        }

        public Criteria andIdGroupIsNotNull() {
            addCriterion("id_group is not null");
            return (Criteria) this;
        }

        public Criteria andIdGroupEqualTo(Integer value) {
            addCriterion("id_group =", value, "idGroup");
            return (Criteria) this;
        }

        public Criteria andIdGroupNotEqualTo(Integer value) {
            addCriterion("id_group <>", value, "idGroup");
            return (Criteria) this;
        }

        public Criteria andIdGroupGreaterThan(Integer value) {
            addCriterion("id_group >", value, "idGroup");
            return (Criteria) this;
        }

        public Criteria andIdGroupGreaterThanOrEqualTo(Integer value) {
            addCriterion("id_group >=", value, "idGroup");
            return (Criteria) this;
        }

        public Criteria andIdGroupLessThan(Integer value) {
            addCriterion("id_group <", value, "idGroup");
            return (Criteria) this;
        }

        public Criteria andIdGroupLessThanOrEqualTo(Integer value) {
            addCriterion("id_group <=", value, "idGroup");
            return (Criteria) this;
        }

        public Criteria andIdGroupIn(List values) {
            addCriterion("id_group in", values, "idGroup");
            return (Criteria) this;
        }

        public Criteria andIdGroupNotIn(List values) {
            addCriterion("id_group not in", values, "idGroup");
            return (Criteria) this;
        }

        public Criteria andIdGroupBetween(Integer value1, Integer value2) {
            addCriterion("id_group between", value1, value2, "idGroup");
            return (Criteria) this;
        }

        public Criteria andIdGroupNotBetween(Integer value1, Integer value2) {
            addCriterion("id_group not between", value1, value2, "idGroup");
            return (Criteria) this;
        }

        public Criteria andNameGroupIsNull() {
            addCriterion("name_group is null");
            return (Criteria) this;
        }

        public Criteria andNameGroupIsNotNull() {
            addCriterion("name_group is not null");
            return (Criteria) this;
        }

        public Criteria andNameGroupEqualTo(String value) {
            addCriterion("name_group =", value, "nameGroup");
            return (Criteria) this;
        }

        public Criteria andNameGroupNotEqualTo(String value) {
            addCriterion("name_group <>", value, "nameGroup");
            return (Criteria) this;
        }

        public Criteria andNameGroupGreaterThan(String value) {
            addCriterion("name_group >", value, "nameGroup");
            return (Criteria) this;
        }

        public Criteria andNameGroupGreaterThanOrEqualTo(String value) {
            addCriterion("name_group >=", value, "nameGroup");
            return (Criteria) this;
        }

        public Criteria andNameGroupLessThan(String value) {
            addCriterion("name_group <", value, "nameGroup");
            return (Criteria) this;
        }

        public Criteria andNameGroupLessThanOrEqualTo(String value) {
            addCriterion("name_group <=", value, "nameGroup");
            return (Criteria) this;
        }

        public Criteria andNameGroupLike(String value) {
            addCriterion("name_group like", value, "nameGroup");
            return (Criteria) this;
        }

        public Criteria andNameGroupNotLike(String value) {
            addCriterion("name_group not like", value, "nameGroup");
            return (Criteria) this;
        }

        public Criteria andNameGroupIn(List values) {
            addCriterion("name_group in", values, "nameGroup");
            return (Criteria) this;
        }

        public Criteria andNameGroupNotIn(List values) {
            addCriterion("name_group not in", values, "nameGroup");
            return (Criteria) this;
        }

        public Criteria andNameGroupBetween(String value1, String value2) {
            addCriterion("name_group between", value1, value2, "nameGroup");
            return (Criteria) this;
        }

        public Criteria andNameGroupNotBetween(String value1, String value2) {
            addCriterion("name_group not between", value1, value2, "nameGroup");
            return (Criteria) this;
        }

        public Criteria andIdPermissionIsNull() {
            addCriterion("id_permission is null");
            return (Criteria) this;
        }

        public Criteria andIdPermissionIsNotNull() {
            addCriterion("id_permission is not null");
            return (Criteria) this;
        }

        public Criteria andIdPermissionEqualTo(Integer value) {
            addCriterion("id_permission =", value, "idPermission");
            return (Criteria) this;
        }

        public Criteria andIdPermissionNotEqualTo(Integer value) {
            addCriterion("id_permission <>", value, "idPermission");
            return (Criteria) this;
        }

        public Criteria andIdPermissionGreaterThan(Integer value) {
            addCriterion("id_permission >", value, "idPermission");
            return (Criteria) this;
        }

        public Criteria andIdPermissionGreaterThanOrEqualTo(Integer value) {
            addCriterion("id_permission >=", value, "idPermission");
            return (Criteria) this;
        }

        public Criteria andIdPermissionLessThan(Integer value) {
            addCriterion("id_permission <", value, "idPermission");
            return (Criteria) this;
        }

        public Criteria andIdPermissionLessThanOrEqualTo(Integer value) {
            addCriterion("id_permission <=", value, "idPermission");
            return (Criteria) this;
        }

        public Criteria andIdPermissionIn(List values) {
            addCriterion("id_permission in", values, "idPermission");
            return (Criteria) this;
        }

        public Criteria andIdPermissionNotIn(List values) {
            addCriterion("id_permission not in", values, "idPermission");
            return (Criteria) this;
        }

        public Criteria andIdPermissionBetween(Integer value1, Integer value2) {
            addCriterion("id_permission between", value1, value2, "idPermission");
            return (Criteria) this;
        }

        public Criteria andIdPermissionNotBetween(Integer value1, Integer value2) {
            addCriterion("id_permission not between", value1, value2, "idPermission");
            return (Criteria) this;
        }

        public Criteria andNamePermissionIsNull() {
            addCriterion("name_permission is null");
            return (Criteria) this;
        }

        public Criteria andNamePermissionIsNotNull() {
            addCriterion("name_permission is not null");
            return (Criteria) this;
        }

        public Criteria andNamePermissionEqualTo(String value) {
            addCriterion("name_permission =", value, "namePermission");
            return (Criteria) this;
        }

        public Criteria andNamePermissionNotEqualTo(String value) {
            addCriterion("name_permission <>", value, "namePermission");
            return (Criteria) this;
        }

        public Criteria andNamePermissionGreaterThan(String value) {
            addCriterion("name_permission >", value, "namePermission");
            return (Criteria) this;
        }

        public Criteria andNamePermissionGreaterThanOrEqualTo(String value) {
            addCriterion("name_permission >=", value, "namePermission");
            return (Criteria) this;
        }

        public Criteria andNamePermissionLessThan(String value) {
            addCriterion("name_permission <", value, "namePermission");
            return (Criteria) this;
        }

        public Criteria andNamePermissionLessThanOrEqualTo(String value) {
            addCriterion("name_permission <=", value, "namePermission");
            return (Criteria) this;
        }

        public Criteria andNamePermissionLike(String value) {
            addCriterion("name_permission like", value, "namePermission");
            return (Criteria) this;
        }

        public Criteria andNamePermissionNotLike(String value) {
            addCriterion("name_permission not like", value, "namePermission");
            return (Criteria) this;
        }

        public Criteria andNamePermissionIn(List values) {
            addCriterion("name_permission in", values, "namePermission");
            return (Criteria) this;
        }

        public Criteria andNamePermissionNotIn(List values) {
            addCriterion("name_permission not in", values, "namePermission");
            return (Criteria) this;
        }

        public Criteria andNamePermissionBetween(String value1, String value2) {
            addCriterion("name_permission between", value1, value2, "namePermission");
            return (Criteria) this;
        }

        public Criteria andNamePermissionNotBetween(String value1, String value2) {
            addCriterion("name_permission not between", value1, value2, "namePermission");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table v_group_permission
     *
     * @mbggenerated do_not_delete_during_merge Fri Apr 27 11:35:15 CEST 2012
     */
   
public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
	 	public Criteria andIdLike(String value) {
            addCriterion("id like", value, "id");
            return this;
        }
    	 	public Criteria andIdGroupLike(String value) {
            addCriterion("id_group like", value, "idGroup");
            return this;
        }
    	 	public Criteria andIdPermissionLike(String value) {
            addCriterion("id_permission like", value, "idPermission");
            return this;
        }
    	 	 }
}