package com.gestionCitas.models;

import com.gestionCitas.controls.estructures.stack.Stack;

public class HistorialMedico {
    private Integer id;
    private String alergias;
    private String tipoSangre;
    private String antecendentesFamiliares;
    private String medicacionActual;
    private String patologiasPasadas;
    private Integer pacienteId;
    private Stack<Integer> citasMedicas;

    public HistorialMedico() {
        this.id = 0;
        this.alergias = "";
        this.tipoSangre = "";
        this.antecendentesFamiliares = "";
        this.medicacionActual = "";
        this.patologiasPasadas = "";
        this.pacienteId = 0;
        this.citasMedicas = new Stack<>(100);
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

    public String getTipoSangre() {
        return this.tipoSangre;
    }

    public void setTipoSangre(String tipoSangre) {
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

    public Stack<Integer> getCitasMedicas() {
        return this.citasMedicas;
    }

    public void setCitasMedicas(Stack<Integer> citasMedicas) {
        this.citasMedicas = citasMedicas;
    }
    
    public Integer getPacienteId() {
        return this.pacienteId;
    }

    public void setPacienteId(Integer pacienteId) {
        this.pacienteId = pacienteId;
    }
}
