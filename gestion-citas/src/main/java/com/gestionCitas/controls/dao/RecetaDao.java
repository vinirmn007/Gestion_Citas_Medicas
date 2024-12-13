package com.gestionCitas.controls.dao;

import com.gestionCitas.controls.dao.implement.AdapterDao;
import com.gestionCitas.controls.estructures.list.LinkedList;
import com.gestionCitas.models.Receta;
//gei
//curry esta sobrevalorado
public class RecetaDao extends AdapterDao<Receta> {
    private Receta receta;
    private LinkedList listAll;

    public RecetaDao() {
        super(Receta.class);
    }

    public Receta getReceta() {
        if (receta == null) {
            this.receta = new Receta();
        }
        return this.receta;
    }

    public void setReceta(Receta receta) {
        this.receta = receta;
    }

    public LinkedList getListAll() {
        if (listAll == null) {
            this.listAll = listAll();
        }
        return this.listAll;
    }

    // OPERACIONES
    public Boolean save() throws Exception {
        Integer id = getListAll().getSize() + 1;
        try {
            receta.setId(id);
            this.persist(this.receta);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean update() throws Exception {
        try {
            this.mergeById(this.receta, this.receta.getId());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
//nose que hace esto, es de chatgpt
    public Boolean delete() throws Exception {
        try {
            this.deleteById(this.receta.getId());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
