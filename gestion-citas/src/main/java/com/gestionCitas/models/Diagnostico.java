package com.gestionCitas.models;

public class Diagnostico {
    private int id;
    private String descripcion;
    private Integer idCitaMedica;


    public Integer getIdCitaMedica() {
        return idCitaMedica;
    }

    public void setIdCitaMedica(Integer idCitaMedica) {
        this.idCitaMedica = idCitaMedica;
    }
    
    public Diagnostico() {
        this.id = 0;
        this.descripcion = "";
        this.idCitaMedica = null;
    }

    public Diagnostico(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }    
}
