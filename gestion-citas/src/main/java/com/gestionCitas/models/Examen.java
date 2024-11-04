package com.gestionCitas.models;

import com.gestionCitas.models.enums.*;

public class Examen {
    private int id;
    private TipoExamen tipo;
    private String descripcion;

    public Examen(int id, Estado tipo, String descripcion) {
        this.id = id;
        this.tipo = tipo;
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Estado getTipo() {
        return tipo;
    }

    public void setTipo(Estado tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}

