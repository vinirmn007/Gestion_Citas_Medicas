package com.gestionCitas.controls.dao.services;

import com.gestionCitas.controls.estructures.list.LinkedList;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.gestionCitas.controls.dao.TurnoDao;
import com.gestionCitas.models.enums.Estado;
import com.gestionCitas.models.Turno;

public class TurnoServices {
    private TurnoDao obj;

    public TurnoServices() {
        this.obj = new TurnoDao();
    }

    public Turno getTurno() {
        return this.obj.getTurno();
    }

    public void setTurno(Turno turno) {
        this.obj.setTurno(turno);
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

    public Turno get(Integer id) throws Exception {
        return this.obj.get(id);
    }

    /**
     * Obtiene una lista de turnos filtrados por estado.
     * @param estado El estado por el cual se quiere filtrar.
     * @return Una lista de turnos con el estado especificado.
     
    public LinkedList<Turno> findByEstado(Estado estado) {
        return this.obj.findByEstado(estado);
    }*/

    public LinkedList orderByFecha(Integer type) throws Exception {
        return this.obj.orderDate(type);
    }

    public LinkedList orderByFecha(LinkedList<Turno> list, Integer type) throws Exception {
        return this.obj.orderDate(list, type);
    }

    public Boolean validateDateFormat(String date) {
        if (date == null || !date.matches("\\d{2}-\\d{2}-\\d{4}")) {
            return false;
        }

        String[] parts = date.split("-");
        int day = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int year = Integer.parseInt(parts[2]);
        //System.out.println(parts.length);
        //System.out.println("Dia: "+parts[0]);
        //System.out.println("Mes: "+parts[1]);
        //System.out.println("Año: "+parts[2]);

        if (parts.length != 3) {
            return false;
        }

        try {
            LocalDate parsedDate = LocalDate.of(year, month, day);
            LocalDate today = LocalDate.now();

            return !parsedDate.isBefore(today);
        } catch (Exception e) {
            return false;
        }
    }

