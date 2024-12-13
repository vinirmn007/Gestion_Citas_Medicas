package com.gestionCitas.rest.APIs;

import java.util.HashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.gestionCitas.controls.dao.services.DiagnosticoServices;
import com.gestionCitas.controls.dao.services.ExamenServices;
import com.google.gson.Gson;

@Path("/examen")
public class ExamenApi {
    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllDiagnositicos () throws Exception {
        HashMap map = new HashMap();
        ExamenServices es = new ExamenServices();
        
        map.put("msg", "OK");
        map.put("data", es.getListAll().toArray());

        if(es.getListAll().isEmpty()) {
            map.put("data", new Object[]{});
            map.put("msg", "No hay datos");
        }

        return Response.ok(map).build();
    }

    @Path("save")
    @POST
    @Consumes (MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveExamen (HashMap map) {
        HashMap res = new HashMap();
        Gson g = new Gson();
        
        try {
            if (map.get("diagnosticoC") != null) {
                DiagnosticoServices cms = new DiagnosticoServices();
                cms.getDiagnostico().setId(Integer.parseInt(map.get("diagnosticoC").toString()));
                if (cms.get(cms.getDiagnostico().getId()) == null) {
                    ExamenServices es = new ExamenServices();
                    es.getExamen().setDescripcion(map.get("descripcion").toString());
                    es.getExamen().setTipo(es.getTipoExamen(map.get("tipoExamen").toString()));
                    es.getExamen().setIdDiagnostico(null);
                    es.getExamen().setDirectorioResultado(map.get("directorioResultado").toString());
                    es.getExamen().setIdDiagnostico(cms.getDiagnostico().getId());
                    es.save();

                    res.put("msg", "OK");
                    res.put("data", "Medicamento creado");
                    
                    return Response.ok(res).build();
                } else {
                    res.put("msg", "Error al guardar");
                    res.put("data", "Cita medica no encontrada");
                    return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
                }
            } else {
                res.put("msg", "Error al guardar");
                res.put("data", "Cita medica no encontrada");
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
            }
        }  catch (Exception e) {
            res.put("msg", "Error al guardar");
            res.put("data", e.toString());
            return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
        } 
        
    }



}

