package com.gestionCitas.models;

import com.gestionCitas.models.enums.*;

public class Examen {
    private int id;
    private TipoExamen tipo;
    private String descripcion;


    public Examen() {
        this.id = 0;
        this.tipo = TipoExamen.LABORATORIO;
        this.descripcion = "";
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TipoExamen getTipo() {
        return tipo;
    }

    public void setTipo(TipoExamen tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}

