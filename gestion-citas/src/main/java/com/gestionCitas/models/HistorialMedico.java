package com.gestionCitas.models;

public class HistorialMedico {
    private Integer id;
    private String alergias;
    private String tipoSangre;
    private String antecendentesFamiliares;
    private String medicacionActual;
    private String patologiasPasadas;
    //private Persona paciente;
    //private LinkedList<CitaMedica> citasMedicas;

    public HistorialMedico() {
        this.id = 0;
        this.alergias = "";
        this.tipoSangre = "";
        this.antecendentesFamiliares = "";
        this.medicacionActual = "";
        this.patologiasPasadas = "";
        //this.paciente = null;
        //this.citasMedicas = new LinkedList<>();
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

    /*public LinkedList<CitaMedica> getCitasMedicas() {
        return this.citasMedicas;
    }

    public void setCitasMedicas(LinkedList<CitaMedica> citasMedicas) {
        this.citasMedicas = citasMedicas;
    }
    
    public Persona getPaciente() {
        return this.paciente;
    }

    public void setPaciente(Persona paciente) {
        this.paciente = paciente;
    }*/
}
