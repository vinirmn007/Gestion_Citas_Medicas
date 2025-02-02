package com.gestionCitas.controls.dao;

import com.gestionCitas.controls.dao.implement.AdapterDao;
import com.gestionCitas.controls.estructures.exception.ListEmptyException;
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

    public Boolean update(Cuenta cuenta) throws Exception {
        this.mergeT(cuenta);
        return true;
    }

    
    

    public Cuenta findByUsuario(String usuario) {
    try {
        LinkedList<Cuenta> cuentas = getListAll();
        // Verificar si la lista está vacía antes de continuar
        if (cuentas.isEmpty()) {
            throw new ListEmptyException("La lista de cuentas está vacía.");
        }
        for (int i = 0; i < cuentas.getSize(); i++) {
            if (cuentas.get(i).getUsuario().equals(usuario)) {
                return cuentas.get(i); // Devuelve la cuenta encontrada
            }
        }
    } catch (ListEmptyException e) {
        // Maneja la excepción, por ejemplo, imprimiendo un mensaje
        System.out.println(e.getMessage());
    }
    return null; // Si no se encuentra la cuenta o hay un error, retorna null
}

}