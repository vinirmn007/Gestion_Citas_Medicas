package com.gestionCitas.rest.APIs;

import java.util.HashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.gestionCitas.controls.dao.services.CitaMedicaServices;
import com.gestionCitas.controls.dao.services.DiagnosticoServices;
import com.google.gson.Gson;

@Path("/diagnostico")
public class DiagnosticoApi {
    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllDiagnositicos () throws Exception {
        HashMap map = new HashMap();
        DiagnosticoServices ds = new DiagnosticoServices();
        
        map.put("msg", "OK");
        map.put("data", ds.getListAll().toArray());

        if(ds.getListAll().isEmpty()) {
            map.put("data", new Object[]{});
            map.put("msg", "No hay datos");
        }

        return Response.ok(map).build();
    }

    @Path("save")
    @POST
    @Consumes (MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveDiagnostico (HashMap map) {
        HashMap res = new HashMap();
        Gson g = new Gson();
        
        try {
            if (map.get("citaM") != null) {
                CitaMedicaServices cms = new CitaMedicaServices();
                cms.getCitaMedica().setId(Integer.parseInt(map.get("citaM").toString()));
                if (cms.get(cms.getCitaMedica().getId()) == null) {
                    DiagnosticoServices ds = new DiagnosticoServices();
                    ds.getDiagnostico().setDescripcion(map.get("descripcion").toString());
                    ds.getDiagnostico().setIdCitaMedica(cms.getCitaMedica().getId());
                    ds.save();

                    res.put("msg", "OK");
                    res.put("data", "Diagnostico creado");
                    
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
