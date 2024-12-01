package com.gestionCitas.models;

public class Diagnostico {
    private int id;
    private String descripcion;

    public Diagnostico(int id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
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
