/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gestionCitas.models;
/**
 *
 * @author david
 */
import com.gestionCitas.models.enums.Genero;
import com.gestionCitas.models.enums.Identificacion;

public class Persona {
    private int id; 
    private String nombre;
    private String email;
    private String numeroIdentificacion;
    private Identificacion tipoIdentificacion;
    private Genero genero;
    private String fechaNacimiento; 
    private String celular;         

    public Persona() {}

    public Persona(int id, String nombre, String email, String numeroIdentificacion, 
                   Identificacion tipoIdentificacion, Genero genero, 
                   String fechaNacimiento, String celular) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.numeroIdentificacion = numeroIdentificacion;
        this.tipoIdentificacion = tipoIdentificacion;
        this.genero = genero;
        this.fechaNacimiento = fechaNacimiento;
        this.celular = celular;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

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
    
    public String getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(String fechaNacimiento) { 
        this.fechaNacimiento = fechaNacimiento; 
    }
    
    public String getCelular() { return celular; }
    public void setCelular(String celular) { this.celular = celular; }

    @Override
    public String toString() {
        return "Persona{" +
               "id=" + id +
               ", nombre='" + nombre + '\'' +
               ", email='" + email + '\'' +
               ", numeroIdentificacion='" + numeroIdentificacion + '\'' +
               ", tipoIdentificacion=" + tipoIdentificacion +
               ", genero=" + genero +
               ", fechaNacimiento='" + fechaNacimiento + '\'' +
               ", celular='" + celular + '\'' +
               '}';
    }
}
