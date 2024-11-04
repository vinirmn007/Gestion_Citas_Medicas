/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.gestion.citas.model;

/**
 *
 * @author david
 */
import com.example.gestion.citas.enums.Identificacion;
import com.example.gestion.citas.enums.Genero;

public class Persona {
    private String nombre;
    private String email;
    private String numeroIdentificacion;
    private Identificacion tipoIdentificacion;
    private Genero genero;

    public Persona() {}

    public Persona(String nombre, String email, String numeroIdentificacion, 
                  Identificacion tipoIdentificacion, Genero genero) {
        this.nombre = nombre;
        this.email = email;
        this.numeroIdentificacion = numeroIdentificacion;
        this.tipoIdentificacion = tipoIdentificacion;
        this.genero = genero;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getNumeroIdentificacion() { return numeroIdentificacion; }
    public void setNumeroIdentificacion(String numeroIdentificacion) { 
        this.numeroIdentificacion = numeroIdentificacion; 
    }
    
    public Identificacion getTipoIdentificacion() { return tipoIdentificacion; }
    public void setTipoIdentificacion(Identificacion tipoIdentificacion) { 
        this.tipoIdentificacion = tipoIdentificacion; 
    }
    
    public Genero getGenero() { return genero; }
    public void setGenero(Genero genero) { this.genero = genero; }
}