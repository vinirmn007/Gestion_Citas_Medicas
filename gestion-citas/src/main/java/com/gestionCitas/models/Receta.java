package com.gestionCitas.models;


public class Receta {
    private int id;
    private String prescripcion;
    private Integer[] idMedicamentos;
    private Integer idDiagnostico;


    public Receta (){
        this.id = 0;
        this.prescripcion = "";
        this.idMedicamentos = null;
        this.idDiagnostico = null;
    }

    public int getId() {
        return id;
    }

    public String getPrescripcion() {
        return prescripcion;
    }

    
    public Integer[] getIdMedicamentos() {
        return idMedicamentos;
    }

    public void setIdMedicamentos(Integer[] idMedicamentos) {
        this.idMedicamentos = idMedicamentos;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPrescripcion(String prescripcion) {
        this.prescripcion = prescripcion;
    }

    public Integer getIdDiagnostico() {
        return idDiagnostico;
    }

    public void setIdDiagnostico(Integer idDiagnostico) {
        this.idDiagnostico = idDiagnostico;
    }
}

