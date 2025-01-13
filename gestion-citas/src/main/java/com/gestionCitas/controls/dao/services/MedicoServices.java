package com.gestionCitas.controls.dao.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import com.gestionCitas.controls.dao.MedicoDao;
import com.gestionCitas.controls.estructures.list.LinkedList;
import com.gestionCitas.models.Medico;
import com.gestionCitas.models.enums.Genero;
import com.gestionCitas.models.enums.Identificacion;

public class MedicoServices {
    private MedicoDao obj;

    public MedicoServices() {
        this.obj = new MedicoDao();
    }

    public Medico getMedico() {
        return this.obj.getMedico();
    }

    public void setMedico(Medico Medico) {
        this.obj.setMedico(Medico);
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

    public Medico get(Integer id) throws Exception {
        return this.obj.getById(id);
    }

    public Identificacion getIdentificacion(String tipo) {
        return this.obj.getTipoIdent(tipo);
    }

    public Identificacion[] getAllIdentificaciones() {
        return this.obj.getAllTiposIdent();
    }

    public Genero[] getAllGeneros() {
        return this.obj.getAllGeneros();
    }

    public Genero getGenero(String genero) {
        return this.obj.getGenero(genero);
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

    public Identificacion getIdentificacion(String tipo) {
        return this.obj.getTipoIdent(tipo);
    }

    public Identificacion[] getAllIdentificaciones() {
        return this.obj.getAllTiposIdent();
    }

    public Genero[] getAllGeneros() {
        return this.obj.getAllGeneros();
    }

    public Genero getGenero(String genero) {
        return this.obj.getGenero(genero);
    }
}
