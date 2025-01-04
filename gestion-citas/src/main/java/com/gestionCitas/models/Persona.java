package com.gestionCitas.models;
/**
 *
 * @author david
 */
import com.gestionCitas.models.enums.Genero;
import com.gestionCitas.models.enums.Identificacion;

public class Persona {
    private Integer id; 
    private String nombre;
    private String email;
    private String numeroIdentificacion;
    private Identificacion tipoIdentificacion;
    private Genero genero;

    public Persona() {}

    public Persona(Integer id, String nombre, String email, String numeroIdentificacion, 
                   Identificacion tipoIdentificacion, Genero genero) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.numeroIdentificacion = numeroIdentificacion;
        this.tipoIdentificacion = tipoIdentificacion;
        this.genero = genero;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getNumeroIdentificacion() { return numeroIdentificacion; }
    public void setNumeroIdentificacion(String numeroIdentificacion) { 
        this.numeroIdentificacion = numeroIdentificacion; 
    }

    public Identificacion getTipoIdentificacion() { return tipoIdentificacion; }
    public void setTipoIdentificacion(Identificacion tipoIdentificacion) { 
        this.tipoIdentificacion = tipoIdentificacion; 
    }

    public Genero getGenero() { return genero; }
    public void setGenero(Genero genero) { this.genero = genero; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Persona persona = (Persona) obj;
        return id != null && id.equals(persona.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public String toString() {
        return "Persona{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", numeroIdentificacion='" + numeroIdentificacion + '\'' +
                ", tipoIdentificacion=" + tipoIdentificacion +
                ", genero=" + genero +
                '}';
    }
}
