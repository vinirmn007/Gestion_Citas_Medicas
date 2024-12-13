package com.gestionCitas.controls.dao;

import com.gestionCitas.models.Persona;
import com.gestionCitas.controls.estructures.list.LinkedList;

public class PersonaDAO {

    private Persona persona;
    private LinkedList<Persona> listAll;

    public PersonaDAO() {
        this.listAll = new LinkedList<>();
    }

    public Boolean save() throws Exception {
        try {
            int id = listAll.getSize() + 1;
            persona.setNumeroIdentificacion(String.valueOf(id));
            listAll.add(persona);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean update() throws Exception {
        try {
            for (int i = 0; i < listAll.getSize(); i++) {
                if (listAll.get(i).getNumeroIdentificacion().equals(persona.getNumeroIdentificacion())) {
                    listAll.update(persona, i);
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean delete() throws Exception {
        try {
            for (int i = 0; i < listAll.getSize(); i++) {
                if (listAll.get(i).getNumeroIdentificacion().equals(persona.getNumeroIdentificacion())) {
                    listAll.delete(i); // Cambiado de remove a delete
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
