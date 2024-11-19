package com.gestionCitas.models;

import com.gestionCitas.models.enums.TipoExamen;

public class Examen {
    private int id;
    private Estado tipo;
    private String descripcion;
    private Integer idDiagnostico;

    public Examen() {
        this.id = 0;
        this.tipo = TipoExamen.LABORATORIO;
        this.descripcion = "";
        this.idDiagnostico = null;
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

    public Integer getIdDiagnostico() {
        return idDiagnostico;
    }   

    public void setIdDiagnostico(Integer idDiagnostico) {
        this.idDiagnostico = idDiagnostico;
    }
    
}

