package com.gestionCitas.controls.dao;

import com.gestionCitas.controls.dao.implement.AdapterDao;
import com.gestionCitas.controls.estructures.list.LinkedList;
import com.gestionCitas.models.Rol;

public class RolDao extends AdapterDao<Rol> {
    
    private Rol rol;
    private LinkedList<Rol> listAll;
    public RolDao() {
        super(Rol.class);
    }
    public Rol getRol() {
        if (rol == null) {
            rol = new Rol();
        }
        return this.rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
    
    public LinkedList<Rol> getListAll() {
        if(listAll == null){
            this.listAll = (LinkedList<Rol>)listAll();
        }
        return listAll;
    }

    public Boolean save() throws Exception {
        Integer id = getListAll().getSize()+1;
        rol.setId(id);
        this.persist(this.rol);
        return true;
    }

    public Boolean update() throws Exception {
        this.merge(getRol(), getRol().getId());
        return true;
    }



}
