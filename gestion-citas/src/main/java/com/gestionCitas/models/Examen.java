package com.gestionCitas.models;

import com.gestionCitas.models.enums.TipoExamen;

public class Examen {
    private int id;
    private TipoExamen tipo;
    private String descripcion;
    private Boolean estado;
    private String directorioResultado;
    private Integer idDiagnostico;


    
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

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public String getDirectorioResultado() {
        return directorioResultado;
    }

    public void setDirectorioResultado(String directorioResultado) {
        this.directorioResultado = directorioResultado;
    }

    public Integer getIdDiagnostico() {
        return idDiagnostico;
    }   

    public void setIdDiagnostico(Integer idDiagnostico) {
        this.idDiagnostico = idDiagnostico;
    }
    
}

