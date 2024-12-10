package com.gestionCitas.models;

public class Rol {
  private Integer id;
  private String nombre;

  public Rol() {
  }

  public Rol(Integer id, String nombre) {
    this.id = id;
    this.nombre = nombre;
  }

  public Integer getId() {
    return this.id;
  }

  public void setId(Integer value) {
    this.id = value;
  }

  public String getNombre() {
    return this.nombre;
  }

  public void setNombre(String value) {
    this.nombre = value;
  }

  @Override
  public String toString() {
    return "Rol { " +
        "id=" + id +
        ", nombre='" + nombre + '\'' +
        " }";
  }

}

