package com.gestionCitas.models;

/**
 *
 * @author david
 */
import com.gestionCitas.models.enums.Genero;
import com.gestionCitas.models.enums.Identificacion;

public class Medico {

    private String id;
    private String nombre;
    private String email;
    private String numeroIdentificacion;
    private Identificacion tipoIdentificacion;
    private Genero genero;
    private String especialidad;
    private String numeroLicencia;

    public Medico() {
    }

    public Medico(String id, String nombre, String email, String numeroIdentificacion,
            Identificacion tipoIdentificacion, Genero genero,
            String especialidad, String numeroLicencia) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.numeroIdentificacion = numeroIdentificacion;
        this.tipoIdentificacion = tipoIdentificacion;
        this.genero = genero;
        this.especialidad = especialidad;
        this.numeroLicencia = numeroLicencia;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    public Identificacion getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(Identificacion tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getNumeroLicencia() {
        return numeroLicencia;
    }

    public void setNumeroLicencia(String numeroLicencia) {
        this.numeroLicencia = numeroLicencia;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Medico medico = (Medico) obj;
        return id != null && id.equals(medico.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Medico{"
                + "id='" + id + '\''
                + ", nombre='" + nombre + '\''
                + ", email='" + email + '\''
                + ", numeroIdentificacion='" + numeroIdentificacion + '\''
                + ", tipoIdentificacion=" + tipoIdentificacion
                + ", genero=" + genero
                + ", especialidad='" + especialidad + '\''
                + ", numeroLicencia='" + numeroLicencia + '\''
                + '}';
    }
}
