package com.gestionCitas.models;

import java.util.LinkedList;

public class Receta {
    private int id;
    private String prescripcion;
    private LinkedList<Medicamento> medicamentos;


    public Receta(int id, String prescripcion, LinkedList<Medicamento> medicamentos) {
        this.id = id;
        this.prescripcion = prescripcion;
        this.medicamentos = medicamentos;
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
}

