package com.practica1.rest.controller.dao.services;
import com.practica1.rest.controller.dao.ProyectoDao;
import com.practica1.rest.controller.tda.list.LinkedList;
import com.practica1.rest.models.Proyecto;

public class ProyectoServices {
    private ProyectoDao obj;

    public ProyectoServices() {
        obj = new ProyectoDao();
    }
    
    public Boolean save() throws Exception {
        return obj.save();
    }

    public LinkedList listAll() {
        return obj.getListAll();
    }

    public Proyecto getProyecto() {
        return obj.getProyecto();
    }

    public void setProyecto(Proyecto proyecto) {
        obj.setProyecto(proyecto);
    }

}

