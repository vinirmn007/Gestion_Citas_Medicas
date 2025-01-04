package com.gestionCitas.controls.dao;

import com.gestionCitas.controls.dao.implement.AdapterDao;
import com.gestionCitas.controls.estructures.list.LinkedList;
import com.gestionCitas.models.Cuenta;

public class CuentaDao extends AdapterDao<Cuenta> {
    
    private Cuenta cuenta;
    private LinkedList listAll;
    
    public CuentaDao() {
        super(Cuenta.class);
    }
    public Cuenta getCuenta() {
        if (cuenta == null) {
            cuenta = new Cuenta();
        }
        return this.cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }
    
    public LinkedList getListAll() {
        if(listAll == null){
            this.listAll = (LinkedList<Cuenta>)listAll();
        }
        return listAll;
    }

    public Boolean save() throws Exception {
        Integer id = getListAll().getSize()+1;
        cuenta.setId(id);
        this.persist(this.cuenta);
        return true;
    }

    public Boolean update() throws Exception {
        this.merge(getCuenta(), getCuenta().getId());
        return true;
    }
}