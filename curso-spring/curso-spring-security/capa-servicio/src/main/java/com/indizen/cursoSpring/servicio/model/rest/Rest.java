package com.indizen.cursoSpring.servicio.model.rest;

import java.io.Serializable;
import java.util.Date;

import com.indizen.cursoSpring.servicio.model.EntityBase;
public class Rest implements Serializable,EntityBase {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_rest.id
     *
     * @mbggenerated Fri Apr 20 10:00:45 CEST 2012
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_rest.nombre
     *
     * @mbggenerated Fri Apr 20 10:00:45 CEST 2012
     */
    private String nombre;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_rest.fecha
     *
     * @mbggenerated Fri Apr 20 10:00:45 CEST 2012
     */
    private Date fecha;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_rest.numero
     *
     * @mbggenerated Fri Apr 20 10:00:45 CEST 2012
     */
    private Integer numero;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_rest
     *
     * @mbggenerated Fri Apr 20 10:00:45 CEST 2012
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_rest.id
     *
     * @return the value of t_rest.id
     *
     * @mbggenerated Fri Apr 20 10:00:45 CEST 2012
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_rest.id
     *
     * @param id the value for t_rest.id
     *
     * @mbggenerated Fri Apr 20 10:00:45 CEST 2012
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_rest.nombre
     *
     * @return the value of t_rest.nombre
     *
     * @mbggenerated Fri Apr 20 10:00:45 CEST 2012
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_rest.nombre
     *
     * @param nombre the value for t_rest.nombre
     *
     * @mbggenerated Fri Apr 20 10:00:45 CEST 2012
     */
    public void setNombre(String nombre) {
        this.nombre = nombre == null ? null : nombre.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_rest.fecha
     *
     * @return the value of t_rest.fecha
     *
     * @mbggenerated Fri Apr 20 10:00:45 CEST 2012
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_rest.fecha
     *
     * @param fecha the value for t_rest.fecha
     *
     * @mbggenerated Fri Apr 20 10:00:45 CEST 2012
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_rest.numero
     *
     * @return the value of t_rest.numero
     *
     * @mbggenerated Fri Apr 20 10:00:45 CEST 2012
     */
    public Integer getNumero() {
        return numero;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_rest.numero
     *
     * @param numero the value for t_rest.numero
     *
     * @mbggenerated Fri Apr 20 10:00:45 CEST 2012
     */
    public void setNumero(Integer numero) {
        this.numero = numero;
    }
}
