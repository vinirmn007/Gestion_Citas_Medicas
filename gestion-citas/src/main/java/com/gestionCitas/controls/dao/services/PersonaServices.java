package com.gestionCitas.controls.dao.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;

import com.gestionCitas.controls.dao.PersonaDao;
import com.gestionCitas.controls.estructures.list.LinkedList;
import com.gestionCitas.models.Cuenta;
import com.gestionCitas.models.Persona;
import com.gestionCitas.models.enums.Genero;
import com.gestionCitas.models.enums.Identificacion;

public class PersonaServices {
    private PersonaDao obj;

    public PersonaServices() {
        this.obj = new PersonaDao();
    }

    public Persona getPersona() {
        return this.obj.getPersona();
    }

    public void setPersona(Persona Persona) {
        this.obj.setPersona(Persona);
    }

    public LinkedList getListAll() {
        return this.obj.getListAll();
    }

    public Boolean save() throws Exception {
        return this.obj.save();
    }

    // Modify the save method to accept a Persona object as a parameter
    public Boolean save(Persona persona) throws Exception {
        return obj.save(persona);
    }

    public Boolean update() throws Exception {
        return this.obj.update();
    }

    public Boolean delete() throws Exception {
        return this.obj.delete();
    }

    public Persona get(Integer id) throws Exception {
        return this.obj.getById(id);
    }

    public Identificacion getIdentificacion(String tipo) {
        return this.obj.getTipoIdent(tipo);
    }

    public Identificacion[] getAllIdentificaciones() {
        return this.obj.getAllTiposIdent();
    }

    public Genero getGenero(String genero) {
        return this.obj.getGenero(genero);
    }

    public Genero[] getAllGeneros() {
        return this.obj.getAllGeneros();
    }

    public LinkedList order(String attribute, Integer type) throws Exception {
        return this.obj.getListAll().order(attribute, type);
    }

    public LinkedList linealSearch(String attribute, Object value) throws Exception {
        return this.obj.getListAll().linealSearch(attribute, value);
    }

    public Object binarySearch(String attribute, Object value) throws Exception {
        return this.obj.getListAll().binarySearch(attribute, value);
    }

    public Integer getAge(String fechaNacimiento) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate birthDate = LocalDate.parse(fechaNacimiento, formatter);
        LocalDate today = LocalDate.now();
        return (int) ChronoUnit.YEARS.between(birthDate, today);
    }

    public Boolean validateDateFormat(String date) {
        if (date == null || !date.matches("\\d{2}-\\d{2}-\\d{4}")) {
            return false;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        try {
            LocalDate.parse(date, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public Object[] listShowAll() throws Exception {
        if (!obj.getListAll().isEmpty()) {
            Persona[] lista = (Persona[]) obj.getListAll().toArray();
            Object[] respuesta = new Object[lista.length];
            for (int i = 0; i < lista.length; i++) {
                Cuenta c = new CuentaServices().get(lista[i].getCuentaId());
                HashMap mapa = new HashMap<>();
                mapa.put("id", lista[i].getId());
                mapa.put("nombre", lista[i].getNombres());
                mapa.put("email", lista[i].getEmail());
                mapa.put("celular", lista[i].getTelefono());
                mapa.put("cuenta", c);
                mapa.put("tipoIdentificacion", lista[i].getTipoIdentificacion());
                mapa.put("numeroIdentificacion", lista[i].getNumeroIdentificacion());
                mapa.put("genero", lista[i].getGenero());
                mapa.put("fechaNacimiento", lista[i].getFechaNacimiento());
                respuesta[i] = mapa;
            }
            return respuesta;
        }
        return new Object[] {};
    }

    public Identificacion getIdentificacion(String tipo) {
        return this.obj.getTipoIdent(tipo);
    }

    public Identificacion[] getAllIdentificaciones() {
        return this.obj.getAllTiposIdent();
    }

    public Genero getGenero(String genero) {
        return this.obj.getGenero(genero);
    }

    public Genero[] getAllGeneros() {
        return this.obj.getAllGeneros();
    }

    public LinkedList order(String attribute, Integer type) throws Exception {
        return this.obj.getListAll().order(attribute, type);
    }

    public LinkedList linealSearch(String attribute, Object value) throws Exception {
        return this.obj.getListAll().linealSearch(attribute, value);
    }

    public Object binarySearch(String attribute, Object value) throws Exception {
        return this.obj.getListAll().binarySearch(attribute, value);
    }
}
