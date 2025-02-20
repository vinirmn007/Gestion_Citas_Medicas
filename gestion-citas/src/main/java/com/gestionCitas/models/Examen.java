package com.gestionCitas.models;

import com.gestionCitas.models.enums.TipoExamen;

public class Examen {
    private Integer id;
    private TipoExamen tipo;
    private String descripcion;
    private String nombreArchivo;
    private Integer idDiagnostico;


    
    public Integer getId() {
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

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public Integer getIdDiagnostico() {
        return idDiagnostico;
    }   

    public void setIdDiagnostico(Integer idDiagnostico) {
        this.idDiagnostico = idDiagnostico;
    }
    
}

