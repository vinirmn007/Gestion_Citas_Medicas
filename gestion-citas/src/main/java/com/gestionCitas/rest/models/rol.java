package com.gestionCitas.rest.models;

public class rol {
    private Integer int;
    private String nombre;


public rol(Integer int, String nombre) {
    this.int = int;
    this.nombre = nombre;



    public Integer getInt() {
      return this.int;
    }
    public void setInt(Integer value) {
      this.int = value;
    }

    public String getNombre() {
      return this.nombre;
    }
    public void setNombre(String value) {
      this.nombre = value;
    }
}
