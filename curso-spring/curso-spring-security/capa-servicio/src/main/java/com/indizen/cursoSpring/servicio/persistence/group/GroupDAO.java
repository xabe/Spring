package com.indizen.cursoSpring.servicio.persistence.group;

import java.util.List;


import com.indizen.cursoSpring.servicio.model.group.Group;
import com.indizen.cursoSpring.servicio.model.group.GroupExample;
import com.indizen.cursoSpring.servicio.persistence.PersistenceBase;
public interface GroupDAO extends PersistenceBase<Group, GroupExample> {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_group
     *
     * @mbggenerated Fri Apr 27 11:35:15 CEST 2012
     */
    int countByExample(GroupExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_group
     *
     * @mbggenerated Fri Apr 27 11:35:15 CEST 2012
     */
    int deleteByExample(GroupExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_group
     *
     * @mbggenerated Fri Apr 27 11:35:15 CEST 2012
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_group
     *
     * @mbggenerated Fri Apr 27 11:35:15 CEST 2012
     */
    Integer insert(Group record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_group
     *
     * @mbggenerated Fri Apr 27 11:35:15 CEST 2012
     */
    Integer insertSelective(Group record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_group
     *
     * @mbggenerated Fri Apr 27 11:35:15 CEST 2012
     */
    List selectByExample(GroupExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_group
     *
     * @mbggenerated Fri Apr 27 11:35:15 CEST 2012
     */
    Group selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_group
     *
     * @mbggenerated Fri Apr 27 11:35:15 CEST 2012
     */
    int updateByExampleSelective(Group record, GroupExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_group
     *
     * @mbggenerated Fri Apr 27 11:35:15 CEST 2012
     */
    int updateByExample(Group record, GroupExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_group
     *
     * @mbggenerated Fri Apr 27 11:35:15 CEST 2012
     */
    int updateByPrimaryKeySelective(Group record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_group
     *
     * @mbggenerated Fri Apr 27 11:35:15 CEST 2012
     */
    int updateByPrimaryKey(Group record);
}
