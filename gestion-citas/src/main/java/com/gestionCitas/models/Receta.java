package com.gestionCitas.models;


public class Receta {
    private int id;
    private String medicamentos;
    private String prescripcion;
    private Integer idDiagnostico;
    


    public Receta (){
        this.id = 0;
        this.prescripcion = "";
        this.medicamentos = "";
        this.idDiagnostico = null;
    }

    public Integer getId() {
        return id;
    }

    public String getPrescripcion() {
        return prescripcion;
    }

    
    public String getMedicamentos() {
        return medicamentos;
    }

    public void setMedicamentos(String medicamentos) {
        this.medicamentos = medicamentos;
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

