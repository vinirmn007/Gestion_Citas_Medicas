package com.gestionCitas.rest.APIs;

import java.util.HashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.gestionCitas.controls.dao.services.DiagnosticoServices;
import com.gestionCitas.controls.dao.services.MedicamentoServices;
import com.gestionCitas.controls.dao.services.RecetaServices;
import com.gestionCitas.models.Receta;
import com.google.gson.Gson;


@Path("/receta")
public class RecetaApi {
    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllDiagnositicos () throws Exception {
        HashMap map = new HashMap();
        RecetaServices ds = new RecetaServices();
        
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
    public Response savereceta (HashMap map) {
        HashMap res = new HashMap();
        Gson g = new Gson();
        MedicamentoServices medicamentos = new MedicamentoServices();
        medicamentos.getListAll().toArray();
        
        try {
            if (map.get("diagnosticoC") != null) {
                DiagnosticoServices cms = new DiagnosticoServices();
                cms.getDiagnostico().setId(Integer.parseInt(map.get("diagnosticoC").toString()));
                if (cms.get(cms.getDiagnostico().getId()) == null) {
                    Integer[] medicamentoRecetados = new Integer[map.size() - 2];   
                    RecetaServices ds = new RecetaServices();
                    ds.getReceta().setPrescripcion(map.get("prescripcion").toString());
                    ds.getReceta().setIdMedicamentos(medicamentoRecetados);
                    MedicamentoServices ms = new MedicamentoServices();
                    //validar que los medicamentos recetados existan
                    for (int i = 0; i < medicamentoRecetados.length; i++) {
                        ms.getMedicamento().setId(medicamentoRecetados[i]);
                        if (ms.get(ms.getMedicamento().getId()) == null) {
                            res.put("msg", "Error al guardar");
                            res.put("data", "Medicamento no encontrado");
                            return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
                        }
                    }
                    ds.getReceta().setIdDiagnostico(cms.getDiagnostico().getId());
                    ds.save();

                    res.put("msg", "OK");
                    res.put("data", "receta creado");
                    
                    return Response.ok(res).build();
                } else {
                    res.put("msg", "Error al guardar");
                    res.put("data", "Diagnostico o Receta no encontrada");
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

    @Path("/get/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") int id) throws Exception {
        HashMap map = new HashMap();
        RecetaServices rs = new RecetaServices();
        Receta r = rs.get(id);

        if (r == null || r.getId() == null) {
            map.put("msg", "Error");
            map.put("msg", "Error al obtener");
            return Response.status(Response.Status.BAD_REQUEST).entity(map).build();
        }

        map.put("msg", "OK");
        map.put("data", r);
        return Response.ok(map).build();
    }

    @Path("/update")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(HashMap map) throws Exception {
        HashMap res = new HashMap();
        try {
            if (map.get("id") == null) {
                res.put("msg", "Error");
                res.put("data", "El ID de la receta es requerido");
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
            }

            RecetaServices rs = new RecetaServices();
            rs.getReceta().setId(Integer.parseInt(map.get("id").toString()));

            if (rs.getReceta() == null || rs.get(rs.getReceta().getId()) == null) {
                res.put("msg", "Error");
                res.put("data", "No existe la receta con el ID proporcionado");
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
            }

            if (map.get("diagnosticoC") != null) {
                DiagnosticoServices ds = new DiagnosticoServices();
                ds.setDiagnostico(ds.get(Integer.parseInt(map.get("diagnosticoC").toString())));
                
                if (ds.getDiagnostico() == null) {
                    res.put("msg", "Error");
                    res.put("data", "Diagnostico no encontrado");
                    return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
                }

                rs.getReceta().setIdDiagnostico(ds.getDiagnostico().getId());
                rs.getReceta().setPrescripcion(map.get("prescripcion").toString());
                rs.getReceta().setIdMedicamentos((Integer[]) map.get("idMedicamentos"));
                rs.update();

                res.put("msg", "OK");
                res.put("data", rs.getReceta());
                return Response.ok(res).build();
            }

            res.put("msg", "Error");
            res.put("data", "Diagnostico no especificado");
            return Response.status(Response.Status.BAD_REQUEST).entity(res).build();


        } catch(Exception e) {
            res.put("msg", "Error al actualizar");
            res.put("data", e.toString());
            return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
        }
    }



}
