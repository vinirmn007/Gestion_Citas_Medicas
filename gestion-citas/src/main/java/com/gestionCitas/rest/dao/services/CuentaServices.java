package com.practica1.rest.controller.dao.services;
import com.practica1.rest.controller.dao.ProyectoDao;
import com.practica1.rest.controller.tda.list.LinkedList;
import com.practica1.rest.models.Proyecto;

public class CuentaServices {
    private CuentaDao obj;

    public CuentaServices() {
        obj = new CuentaDao();
    }
    
    public Boolean save() throws Exception {
        return obj.save();
    }

    public LinkedList listAll() {
        return obj.getListAll();
    }

    public Proyecto getCuenta() {
        return obj.getCuenta();
    }

    public void setCuenta(Cuenta cuenta) {
        obj.setProyecto(cuenta);
    }

}

