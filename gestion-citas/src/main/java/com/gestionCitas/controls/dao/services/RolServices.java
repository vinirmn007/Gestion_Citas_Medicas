package com.gestionCitas.controls.dao.services;

import com.gestionCitas.controls.dao.RolDao;
import com.gestionCitas.controls.estructures.list.LinkedList;
import com.gestionCitas.models.Rol;

public class RolServices {
    private RolDao obj;

    public RolServices() {
        obj = new RolDao();
    }
    
    public Boolean save() throws Exception {
        return obj.save();
    }
    public Boolean update() throws Exception {
        return obj.update();
    }

    public LinkedList listAll() {
        return obj.getListAll();
    }

    public Rol getRol() {
        return obj.getRol();
    }

    public void setRol(Rol rol) {
        obj.setRol(rol);
    }

    public Rol get(Integer id) throws Exception {
        return obj.get(id);
    }
}
