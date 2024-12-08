package com.gestionCitas.models;

public class CitaMedica {
    private Integer id;
    private String observaciones;
    private String motivo;
    private String fecha;
    //RELACIONES
    private Integer signosVitalesId;
    private Integer historialMedicoId;
    private Integer turnoId;
    private Integer diagnosticoId;

    public CitaMedica() {
        this.id = 0;
        this.motivo = "";
        this.observaciones = "";
        this.signosVitalesId = 0;
        this.historialMedicoId = 0;
        this.turnoId = 0;
        this.diagnosticoId = 0;
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

    public String getFecha() {
        return this.fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Integer getSignosVitalesId() {
        return this.signosVitalesId;
    }

    public void setSignosVitalesId(Integer signosVitalesId) {
        this.signosVitalesId = signosVitalesId;
    }

    public Integer getHistorialMedicoId() {
        return this.historialMedicoId;
    }

    public void setHistorialMedicoId(Integer historialMedicoId) {
        this.historialMedicoId = historialMedicoId;
    }

    public Integer getTurnoId() {
        return this.turnoId;
    }

    public void setTurnoId(Integer turno) {
        this.turnoId = turno;
    }

    public Integer getDiagnosticoId() {
        return this.diagnosticoId;
    }

    public void setDiagnosticoId(Integer diagnosticoId) {
        this.diagnosticoId = diagnosticoId;
    }
}