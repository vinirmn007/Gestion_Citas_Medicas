package com.gestionCitas.models;

import com.gestionCitas.controls.estructures.list.LinkedList;

public class Receta {
    private int id;
    private String prescripcion;
    private LinkedList<Medicamento> medicamentos;


    public Receta (){
        this.id = 0;
        this.prescripcion = "";
        this.medicamentos = new LinkedList<Medicamento>();
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

