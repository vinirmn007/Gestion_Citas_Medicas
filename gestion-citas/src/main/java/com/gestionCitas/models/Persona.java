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
    private String direccion;
    private String telefono;
    private Date fechaNacimiento;
    private String numeroIdentificacion;
    private Identificacion tipoIdentificacion;
    private Genero genero;
    private Integer historialMedicoId;
    private Integer cuentaId;

    public Persona() {
    }

    public Persona(Integer id, String nombre, String email, String direccion, String telefono, Date fechaNacimiento,
            String numeroIdentificacion, Identificacion tipoIdentificacion, Genero genero, Integer historialMedicoId,
            Integer cuentaId) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.direccion = direccion;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
        this.numeroIdentificacion = numeroIdentificacion;
        this.tipoIdentificacion = tipoIdentificacion;
        this.genero = genero;
        this.historialMedicoId = historialMedicoId;
        this.cuentaId = cuentaId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer getHistorialMedicoId() {
        return this.historialMedicoId;
    }

    public void setHistorialMedicoId(Integer historialMedicoId) {
        this.historialMedicoId = historialMedicoId;
    }

    public Integer getCuentaId() {
        return this.cuentaId;
    }

    public void setCuentaId(Integer cuentaId) {
        this.cuentaId = cuentaId;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Date getFechaNacimiento() {
        return this.fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
}