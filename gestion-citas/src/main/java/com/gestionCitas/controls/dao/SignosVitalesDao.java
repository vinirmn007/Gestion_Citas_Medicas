package com.gestionCitas.controls.dao;

import com.gestionCitas.controls.dao.implement.AdapterDao;
import com.gestionCitas.controls.estructures.list.LinkedList;
import com.gestionCitas.models.SignosVitales;

public class SignosVitalesDao extends AdapterDao<SignosVitales> {
    private SignosVitales signosVitales;
    private LinkedList listAll;

    public SignosVitalesDao() {
        super(SignosVitales.class);
    }
    
    public SignosVitales getSignosVitales() {
        if (signosVitales == null) {
            this.signosVitales = new SignosVitales(); 
        }
        return this.signosVitales;
    }

    public void setSignosVitales(SignosVitales signosVitales) {
        this.signosVitales = signosVitales;
    }

    public LinkedList getListAll() {
        if (listAll == null) {
            this.listAll = listAll();
        }
        return this.listAll;
    }

    //OPERACIONES
    public Boolean save() throws Exception{
        Integer id = getListAll().getSize() +1;
        try {
            signosVitales.setId(id);
            this.persist(this.signosVitales);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean update() throws Exception {
        try {
            this.mergeById(this.signosVitales, this.signosVitales.getId());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean delete() throws Exception {
        try {
            this.deleteById(this.signosVitales.getId());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
