package com.gestionCitas.models;

public class Medico extends Persona{
    private String especialidad;
    private String codigoLicencia;

    public Medico() {
        super();
        this.especialidad = "";
        this.codigoLicencia = "";
    }

    public String getEspecialidad() {
        return this.especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getCodigoLicencia() {
        return this.codigoLicencia;
    }

    public void setCodigoLicencia(String codigoLicencia) {
        this.codigoLicencia = codigoLicencia;
    }
}
