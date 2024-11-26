package com.gestionCitas.models;

public class Medicamento {
    private int id;
    private String nombre;
    private String dosis;
    

    public Medicamento() {
        this.id = 0;
        this.nombre = "";
        this.dosis = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDosis() {
        return dosis;
    }

    public void setDosis(String dosis) {
        this.dosis = dosis;
    }
}
