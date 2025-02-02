package com.gestionCitas.controls.dao.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

import com.gestionCitas.controls.dao.PersonaDao;
import com.gestionCitas.controls.estructures.list.LinkedList;
import com.gestionCitas.models.Persona;

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
}
