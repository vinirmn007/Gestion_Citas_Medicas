package com.gestionCitas.rest.dao.services;

import java.util.LinkedList;

import com.gestionCitas.rest.dao.RolDao;

public class RolServices {
    private RolDao obj;

    public RolServices() {
        obj = new RolDao();
    }
    
    public Boolean save() throws Exception {
        return obj.save();
    }

    public LinkedList listAll() {
        return obj.getListAll();
    }

    public Proyecto getRol() {
        return obj.getRol();
    }
}
