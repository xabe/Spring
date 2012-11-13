package com.indizen.springCurso.model.entity;

import java.io.Serializable;
import java.util.Date;

import com.indizen.springCurso.model.EntityBase;
public class Entity implements Serializable,EntityBase {

	private static final long serialVersionUID = 1L;
	
    private Integer id;
    private String nombre;
    private Date fecha;
    private Integer numero;
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public Integer getNumero() {
		return numero;
	}
	public void setNumero(Integer numero) {
		this.numero = numero;
	}
	
	@Override
	public String toString() {
		return "Nombre: "+getNombre()+" Fecha: "+getFecha()+" Numero: "+getNumero();
	}
}
