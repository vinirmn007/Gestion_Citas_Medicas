package com.gestionCitas.models;

public class Cuenta {
    private Integer id;
    private String usuario;
    private String contrasena;

    public Cuenta(Integer id, String usuario, String contrasena) {
            this.id = id;
            this.usuario = usuario;
            this.contrasena = contrasena;
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
}
