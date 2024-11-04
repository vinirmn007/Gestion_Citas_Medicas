package com.gestionCitas.models;

import java.util.LinkedList;

public class Receta {
    private int id;
    private String prescripcion;
    private LinkedList<Medicamento> medicamentos;
    private Integer idDiagnostico;


    public Receta (){
        this.id = 0;
        this.prescripcion = "";
        this.medicamentos = new LinkedList<Medicamento>();
        this.idDiagnostico = null;
    }

    public int getId() {
        return id;
    }

    public String getPrescripcion() {
        return prescripcion;
    }

    public LinkedList<Medicamento> getMedicamentos() {
        return medicamentos;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPrescripcion(String prescripcion) {
        this.prescripcion = prescripcion;
    }

    public void setMedicamentos(LinkedList<Medicamento> medicamentos) {
        this.medicamentos = medicamentos;
    }

    public Integer getIdDiagnostico() {
        return idDiagnostico;
    }

    public void setIdDiagnostico(Integer idDiagnostico) {
        this.idDiagnostico = idDiagnostico;
    }
}

