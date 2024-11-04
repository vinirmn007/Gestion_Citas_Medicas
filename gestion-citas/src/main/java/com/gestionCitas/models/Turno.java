package models;

import java.util.Date;

public class Turno {
    private int id;
    private int idMedico;
    private int idPaciente;
    private Date fecha;
    private Date hora;
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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
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
        this.fecha = new Date();
        this.hora = new Date();
        this.estado = Estado.RESERVADO;
    }
    public Turno(int id, int idMedico, int idPaciente, Date fecha, Date hora, Estado estado) {
        this.id = id;
        this.idMedico = idMedico;
        this.idPaciente = idPaciente;
        this.fecha = fecha;
        this.hora = hora;
        this.estado = estado;
    }
}
