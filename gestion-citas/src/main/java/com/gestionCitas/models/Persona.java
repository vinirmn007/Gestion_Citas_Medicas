/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gestionCitas.models;

/**
 *
 * @author david
 */
import com.gestionCitas.models.enums.*;;

public class Persona {
    private Integer id;
    private String nombre;
    private String email;
    private String numeroIdentificacion;
    private Identificacion tipoIdentificacion;
    private Genero genero;
    private Integer historialMedicoId;
    private Integer cuentaId;

    public Persona() {
    }

    public Persona(Integer id, String nombre, String email, String numeroIdentificacion,
            Identificacion tipoIdentificacion, Genero genero, Integer historialMedicoId, Integer cuentaId) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.numeroIdentificacion = numeroIdentificacion;
        this.tipoIdentificacion = tipoIdentificacion;
        this.genero = genero;
        this.historialMedicoId = historialMedicoId;
        this.cuentaId = cuentaId;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    public Identificacion getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(Identificacion tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public Integer getHistorialMedicoId() {
        return this.historialMedicoId;
    }

    public void setHistorialMedicoId(Integer historialMedicoId) {
        this.historialMedicoId = historialMedicoId;
    }

    public Integer getCuentaId() {
        return this.cuentaId;
    }

    public void setCuentaId(Integer cuentaId) {
        this.cuentaId = cuentaId;
    }
}