package com.gestionCitas.models;

import com.gestionCitas.models.enums.*;;

public class Persona {
    private Integer id;
    private String nombres;
    private String apellidos;
    private String email;
    private String direccion;
    private String telefono;
    private String fechaNacimiento;
    private String numeroIdentificacion;
    private Identificacion tipoIdentificacion;
    private Genero genero;
    private Integer id_cuenta; 
    private Integer historialMedicoId;

    public Persona() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public String getFechaNacimiento() {
        return this.fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
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
        return this.id_cuenta;
    }

    public void setCuentaId(Integer cuentaId) {
        this.id_cuenta = cuentaId;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return "Persona{" +
               "id=" + id +
               ", nombre='" + nombres + '\'' +
               ", email='" + email + '\'' +
               ", numeroIdentificacion='" + numeroIdentificacion + '\'' +
               ", tipoIdentificacion=" + tipoIdentificacion +
               ", genero=" + genero +
               ", fechaNacimiento='" + fechaNacimiento + '\'' +
               ", celular='" + telefono + '\'' +
               '}';
    }
}