package com.gestionCitas.models;

public class Medico extends Persona{
    private String especialidad;
    private String matricula;

    public Medico() {
        super();
        this.especialidad = "";
        this.matricula = "";
    }

    public String getEspecialidad() {
        return this.especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getMatricula() {
        return this.matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
}
