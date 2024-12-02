package com.gestionCitas.models;
/**
 *
 * @author david
 */
import com.gestionCitas.models.enums.Genero;
import com.gestionCitas.models.enums.Identificacion;

public class Persona {
<<<<<<< HEAD
    private String id; // Nuevo identificador único
=======
    private int id; 
>>>>>>> origin/David
    private String nombre;
    private String email;
    private String numeroIdentificacion;
    private Identificacion tipoIdentificacion;
    private Genero genero;
    private String fechaNacimiento; 
    private String celular;         

    public Persona() {}

<<<<<<< HEAD
    public Persona(String id, String nombre, String email, String numeroIdentificacion, 
                   Identificacion tipoIdentificacion, Genero genero) {
=======
    public Persona(int id, String nombre, String email, String numeroIdentificacion, 
                   Identificacion tipoIdentificacion, Genero genero, 
                   String fechaNacimiento, String celular) {
>>>>>>> origin/David
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.numeroIdentificacion = numeroIdentificacion;
        this.tipoIdentificacion = tipoIdentificacion;
        this.genero = genero;
        this.fechaNacimiento = fechaNacimiento;
        this.celular = celular;
    }

<<<<<<< HEAD
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
=======
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
>>>>>>> origin/David

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
<<<<<<< HEAD

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
=======
    
    public String getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(String fechaNacimiento) { 
        this.fechaNacimiento = fechaNacimiento; 
    }
    
    public String getCelular() { return celular; }
    public void setCelular(String celular) { this.celular = celular; }
>>>>>>> origin/David

    @Override
    public String toString() {
        return "Persona{" +
<<<<<<< HEAD
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", numeroIdentificacion='" + numeroIdentificacion + '\'' +
                ", tipoIdentificacion=" + tipoIdentificacion +
                ", genero=" + genero +
                '}';
=======
               "id=" + id +
               ", nombre='" + nombre + '\'' +
               ", email='" + email + '\'' +
               ", numeroIdentificacion='" + numeroIdentificacion + '\'' +
               ", tipoIdentificacion=" + tipoIdentificacion +
               ", genero=" + genero +
               ", fechaNacimiento='" + fechaNacimiento + '\'' +
               ", celular='" + celular + '\'' +
               '}';
>>>>>>> origin/David
    }
}
