package com.gestionCitas.models;

public class Cuenta {
  private Integer id;
  private String usuario;
  private String contrasena;
  private Integer id_rol;
  private Integer personaId;

  public Cuenta() {
  }

  public Cuenta(Integer id, String usuario, String contrasena, Integer id_rol, Integer personaId) {
    this.id = id;
    this.usuario = usuario;
    this.contrasena = contrasena;
    this.id_rol = id_rol;
    this.personaId = personaId;
  }


  public Integer getId() {
    return this.id;
  }


  public void setId(Integer value) {
    this.id = value;
  }

  public String getUsuario() {
    return this.usuario;
  }


  public void setUsuario(String value) {
    this.usuario = value;
  }

  public String getContrasena() {
    return this.contrasena;
  }


  public void setContrasena(String value) {
    this.contrasena = value;
  }
    public Integer getId_rol() {
      return this.id_rol;
    }
    public void setId_rol(Integer value) {
      this.id_rol = value;
    }

    public Integer getPersonaId() {
      return this.personaId;
    }
  
    public void setPersonaId(Integer personaId) {
      this.personaId = personaId;
    }
}