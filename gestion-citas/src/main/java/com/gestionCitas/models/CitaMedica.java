package com.gestionCitas.models;

public class CitaMedica {
    private Integer id;
    private String observaciones;
    private String motivo;
    private SignosVitales signosVitales;
    private HistorialMedico historialMedico;
    //private Turno turno;

    public CitaMedica() {
        this.id = 0;
        this.motivo = "";
        this.observaciones = "";
        this.signosVitales = null;
        this.historialMedico = null;
        //this.turno = null;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getObservaciones() {
        return this.observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getMotivo() {
        return this.motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public SignosVitales getSignosVitales() {
        return this.signosVitales;
    }

    public void setSignosVitales(SignosVitales signosVitales) {
        this.signosVitales = signosVitales;
    }

    public HistorialMedico getHistorialMedico() {
        return this.historialMedico;
    }

    public void setHistorialMedico(HistorialMedico historialMedico) {
        this.historialMedico = historialMedico;
    }

    /*public Turno getTurno() {
        return this.turno;
    }

    public void setTurno(Turno turno) {
        this.turno = turno;
    }*/
}