package com.gestionCitas.controls.dao;

import com.gestionCitas.controls.estructures.list.LinkedList;
import com.gestionCitas.models.Medico;

public class MedicoDAO {

    private Medico medico;
    private LinkedList<Medico> listAll;

    public MedicoDAO() {
        this.listAll = new LinkedList<>();
    }

    public Boolean save() throws Exception {
        try {
            int id = listAll.getSize() + 1; // Cambiado size() a getSize()
            medico.setNumeroIdentificacion(String.valueOf(id));
            listAll.add(medico);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean update() throws Exception {
        try {
            for (int i = 0; i < listAll.getSize(); i++) { // Cambiado size() a getSize()
                if (listAll.get(i).getNumeroIdentificacion().equals(medico.getNumeroIdentificacion())) {
                    listAll.update(medico, i); // Cambiado set() a update()
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
            for (int i = 0; i < listAll.getSize(); i++) { // Cambiado size() a getSize()
                if (listAll.get(i).getNumeroIdentificacion().equals(medico.getNumeroIdentificacion())) {
                    listAll.delete(i); // Cambiado remove() a delete()
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
