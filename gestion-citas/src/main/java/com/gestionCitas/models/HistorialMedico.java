package com.gestionCitas.models;

import com.gestionCitas.models.enums.TipoSangre;

public class HistorialMedico {
    private Integer id;
    private String alergias;
    private TipoSangre tipoSangre;
    private String antecendentesFamiliares;
    private String medicacionActual;
    private String patologiasPasadas;
    private String discapacidad;
    private Integer pacienteId;

    public HistorialMedico() {
        this.id = 0;
        this.alergias = "";
        this.tipoSangre = TipoSangre.DESCONOCIDO;
        this.antecendentesFamiliares = "";
        this.medicacionActual = "";
        this.patologiasPasadas = "";
        this.discapacidad = "Ninguna";
        this.pacienteId = 0;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAlergias() {
        return this.alergias;
    }

    public void setAlergias(String alergias) {
        this.alergias = alergias;
    }

    public TipoSangre getTipoSangre() {
        return this.tipoSangre;
    }

    public void setTipoSangre(TipoSangre tipoSangre) {
        this.tipoSangre = tipoSangre;
    }

    public String getAntecendentesFamiliares() {
        return this.antecendentesFamiliares;
    }

    public void setAntecendentesFamiliares(String antecendentesFamiliares) {
        this.antecendentesFamiliares = antecendentesFamiliares;
    }

    public String getMedicacionActual() {
        return this.medicacionActual;
    }

    public void setMedicacionActual(String medicacionActual) {
        this.medicacionActual = medicacionActual;
    }

    public String getPatologiasPasadas() {
        return this.patologiasPasadas;
    }

    public void setPatologiasPasadas(String patologiasPasadas) {
        this.patologiasPasadas = patologiasPasadas;
    }

    public String getDiscapacidad() {
        return this.discapacidad;
    }

    public void setDiscapacidad(String discapacidad) {
        this.discapacidad = discapacidad;
    }
    
    public Integer getPacienteId() {
        return this.pacienteId;
    }

    public void setPacienteId(Integer pacienteId) {
        this.pacienteId = pacienteId;
    }
}
