package com.gestionCitas.controls.dao.implement;

import com.gestionCitas.models.Medico;
import com.gestionCitas.controls.estructures.list.LinkedList;

public class MedicoDAO {

    private Medico medico;
    private LinkedList<Medico> listAll;

    public MedicoDAO() {
        this.listAll = new LinkedList<>();
    }

    public Boolean save() throws Exception {
        try {
            int id = listAll.size() + 1;
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
            for (int i = 0; i < listAll.size(); i++) {
                if (listAll.get(i).getNumeroIdentificacion().equals(medico.getNumeroIdentificacion())) {
                    listAll.set(i, medico);
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
            for (int i = 0; i < listAll.size(); i++) {
                if (listAll.get(i).getNumeroIdentificacion().equals(medico.getNumeroIdentificacion())) {
                    listAll.remove(i);
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
