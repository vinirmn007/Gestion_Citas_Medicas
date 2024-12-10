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

import com.gestionCitas.controls.dao.services.HistorialMedicoServices;
import com.gestionCitas.models.HistorialMedico;

@Path("historialMedico")
public class HistorialMedicoAPI {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllHistoriales() throws Exception {
        HashMap map = new HashMap<>();
        HistorialMedicoServices hss = new HistorialMedicoServices();

        map.put("msg", "OK");
        map.put("data", hss.getListAll().toArray());

        if (hss.getListAll().isEmpty()) {
            map.put("data", new Object[]{});
        }

        return Response.ok(map).build();
    }

    @Path("/saver")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response saver() throws Exception {
        HashMap map = new HashMap<>();
        HistorialMedicoServices hss = new HistorialMedicoServices();

        hss.getHistorialMedico().setAlergias("Ninguna");
        hss.getHistorialMedico().setAntecendentesFamiliares("Ninguna");
        hss.getHistorialMedico().setDiscapacidad("Ninguna");
        hss.getHistorialMedico().setMedicacionActual("Ninguna");
        hss.getHistorialMedico().setPatologiasPasadas("Ninguna");
        //hss.getHistorialMedico().setTipoSangre(map.get("tipo_sangre").toString());
        hss.getHistorialMedico().setPacienteId(1);
        hss.save();

        map.put("msg", "OK");
        map.put("data", hss.getListAll().toArray());

        if (hss.getListAll().isEmpty()) {
            map.put("data", new Object[]{});
        }

        return Response.ok(map).build();
    }

    @Path("/save")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveHistoriales(HashMap map) {
        HashMap res = new HashMap<>();

        try {
            HistorialMedicoServices hss = new HistorialMedicoServices();

            hss.getHistorialMedico().setAlergias(map.get("alergias").toString());
            hss.getHistorialMedico().setAntecendentesFamiliares(map.get("antecedentes").toString());
            hss.getHistorialMedico().setDiscapacidad(map.get("discapacidad").toString());
            hss.getHistorialMedico().setMedicacionActual(map.get("medicacion").toString());
            hss.getHistorialMedico().setPatologiasPasadas(map.get("patologias").toString());
            //hss.getHistorialMedico().setTipoSangre(map.get("tipo_sangre").toString());
            hss.getHistorialMedico().setPacienteId(Integer.parseInt(map.get("pacienteId").toString()));
            hss.save();

            res.put("msg", "OK");
            res.put("data", "Historial registrada correctamente");

            return Response.ok(res).build();
        } catch (Exception e) {
            res.put("msg", "Error");
            res.put("data", e.toString());

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

    @Path("/get/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getHistorial(@PathParam("id") Integer id) throws Exception {
        HashMap map = new HashMap<>();
        HistorialMedicoServices hss = new HistorialMedicoServices();
        HistorialMedico HistorialMedico = hss.get(id);

        if (HistorialMedico == null || HistorialMedico.getId() == null) {
            map.put("msg", "Error");
            map.put("data", "No existe ese Historial");
            return Response.status(Response.Status.BAD_REQUEST).entity(map).build();
        }

        map.put("msg", "OK");
        map.put("data", HistorialMedico);

        return Response.ok(map).build();
    }
    
    @Path("/update")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateHistoriales(HashMap map) {
        HashMap res = new HashMap<>();

        try {
            HistorialMedicoServices hss = new HistorialMedicoServices();

            hss.getHistorialMedico().setAlergias(map.get("alergias").toString());
            hss.getHistorialMedico().setAntecendentesFamiliares(map.get("antecedentes").toString());
            hss.getHistorialMedico().setMedicacionActual(map.get("medicacion").toString());
            hss.update();

            res.put("msg", "OK");
            res.put("data", "Historial actualizada correctamente");

            return Response.ok(res).build();
        } catch (Exception e) {
            res.put("msg", "Error");
            res.put("data", e.toString());

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }
    /* 
    @Path("/delete")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteHistoriales(HashMap map) {
        HashMap res = new HashMap<>();

        try {
            HistorialMedicoServices hss = new HistorialMedicoServices();
            
            Integer id = Integer.parseInt(map.get("id").toString());
            HistorialMedico HistorialMedico = hss.get(id);

            if (HistorialMedico == null || HistorialMedico.getId() == null) {
                res.put("msg", "Error");
                res.put("data", "No existe ese Historial");
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
            }

            hss.setHistorialMedico(HistorialMedico);
            hss.delete();

            res.put("msg", "OK");
            res.put("data", "Historial eliminada correctamente");

            return Response.ok(res).build();
        } catch (Exception e) {
            res.put("msg", "Error");
            res.put("data", e.toString());

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }*/
}
