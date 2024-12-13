package com.gestionCitas.rest.APIs;

import java.util.HashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.gestionCitas.controls.dao.services.MedicamentoServices;
import com.google.gson.Gson;

@Path("/medicamento")
public class MedicamentoApi {
    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllMedicamentos () throws Exception {
        HashMap map = new HashMap();
        MedicamentoServices ms = new MedicamentoServices();
        
        map.put("msg", "OK");
        map.put("data", ms.getListAll().toArray());

        if(ms.getListAll().isEmpty()) {
            map.put("data", new Object[]{});
            map.put("msg", "No hay datos");
        }

        return Response.ok(map).build();
    }

    @Path("save")
    @POST
    @Consumes (MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveMedicamento (HashMap map) {
        HashMap res = new HashMap();
        Gson g = new Gson();
        
        try {
            MedicamentoServices ms = new MedicamentoServices();
            ms.getMedicamento().setNombre(map.get("nombre").toString());
            ms.getMedicamento().setDosis(map.get("dosis").toString());
            ms.save();

            res.put("msg", "OK");
            res.put("data", "Medicamento creado");
            
            return Response.ok(res).build();
        }  catch (Exception e) {
            res.put("msg", "Error al guardar");
            res.put("data", e.toString());
            return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
        }
        
    }



}
