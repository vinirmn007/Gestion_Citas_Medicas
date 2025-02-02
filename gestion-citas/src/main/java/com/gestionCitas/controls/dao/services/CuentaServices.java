package com.gestionCitas.controls.dao.services;

import java.util.HashMap;
import com.gestionCitas.controls.dao.CuentaDao;
import com.gestionCitas.controls.estructures.list.LinkedList;
import com.gestionCitas.models.Cuenta;
import com.gestionCitas.models.Rol;

public class CuentaServices {
    private CuentaDao obj;

    public Object[] listShowAll() throws Exception {
        if (!obj.getListAll().isEmpty()) {
            Cuenta[] lista = (Cuenta[]) obj.getListAll().toArray();
            Object[] respuesta = new Object[lista.length];
            for (int i = 0; i < lista.length; i++) {
                Rol r = new RolServices().get(lista[i].getId_rol());
                HashMap mapa = new HashMap<>();
                mapa.put("id", lista[i].getId());
                mapa.put("usuario", lista[i].getUsuario());
                mapa.put("contrasena", lista[i].getContrasena());
                mapa.put("rol", r);
                
                respuesta[i] = mapa;
            }
            return respuesta;
        }
        return new Object[] {};
    }
    public CuentaServices() {
        obj = new CuentaDao();
    }
    
    public Boolean save() throws Exception {
        return obj.save();
    }

    public Boolean update() throws Exception {
        return obj.update();
    }

    public Boolean update(Cuenta cuenta) throws Exception {
        return obj.update();
    }
    
    public LinkedList listAll() {
        return obj.getListAll();
    }

    public Cuenta getCuenta() {
        return obj.getCuenta();
    }

    public void setCuenta(Cuenta cuenta) {
        obj.setCuenta(cuenta);
    }

    public Cuenta get(Integer id) throws Exception {
        return obj.get(id);
    }

    public Cuenta findByUsuario(String usuario) {
        return obj.findByUsuario(usuario);
    }
}

