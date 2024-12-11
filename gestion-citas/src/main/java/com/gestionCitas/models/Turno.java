package com.gestionCitas.models;

import java.util.Date;

import com.gestionCitas.models.enums.Estado;

public class Turno {
    private int id;
    private int idMedico;
    private int idPaciente;
    private String fecha;
    private String hora;
    private Estado estado;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(int idMedico) {
        this.idMedico = idMedico;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Turno() {
        this.id = 0;
        this.idMedico = 0;
        this.idPaciente = 0;
        this.fecha = "";
        this.hora = "";	
        this.estado = Estado.RESERVADO;
    }
    public Turno(int id, int idMedico, int idPaciente, String fecha, String hora, Estado estado) {
        this.id = id;
        this.idMedico = idMedico;
        this.idPaciente = idPaciente;
        this.fecha = fecha;
        this.hora = hora;
        this.estado = estado;
    }
}
