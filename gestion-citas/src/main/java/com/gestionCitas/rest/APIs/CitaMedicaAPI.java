package com.gestionCitas.rest.APIs;

import java.util.HashMap;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import com.gestionCitas.controls.dao.services.CitaMedicaServices;
import com.gestionCitas.controls.dao.services.HistorialMedicoServices;
import com.gestionCitas.controls.dao.services.TurnoServices;
import com.gestionCitas.models.CitaMedica;
import com.gestionCitas.models.enums.Estado;

@Path("citasMedicas")
public class CitaMedicaAPI {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCitas() throws Exception {
        HashMap map = new HashMap<>();
        CitaMedicaServices cms = new CitaMedicaServices();

        map.put("msg", "OK");
        map.put("data", cms.getListAll().toArray());

        if (cms.getListAll().isEmpty()) {
            map.put("data", new Object[]{});
        }

        return Response.ok(map).build();
    }

    @Path("/save")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveCitas(HashMap map) {
        HashMap res = new HashMap<>();

        try {
            CitaMedicaServices cms = new CitaMedicaServices();
            TurnoServices ts = new TurnoServices();
            HistorialMedicoServices hms = new HistorialMedicoServices();

            //VALIDACIONES
            if (ts.get(Integer.parseInt(map.get("turnoId").toString())) == null) {
                res.put("msg", "Error");
                res.put("data", "No existe ese Turno");
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
            }

            if (hms.binarySearch("pacienteId", ts.get(Integer.parseInt(map.get("turnoId").toString())).getIdPaciente()) == null) {
                res.put("msg", "Error");
                res.put("data", "No existe ese Historial Medico o no esta asociado a la persona correcta");
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
            }

            //GUARDADO DE DATOS
            cms.getCitaMedica().setObservaciones(map.get("observaciones").toString());
            cms.getCitaMedica().setMotivo(map.get("motivo").toString());
            cms.getCitaMedica().setTurnoId(Integer.parseInt(map.get("turnoId").toString()));
            //cms.getCitaMedica().setSignosVitalesId(Integer.parseInt(map.get("signosVitalesId").toString()));
            cms.getCitaMedica().setHistorialMedicoId(Integer.parseInt(map.get("historialMedicoId").toString()));
            cms.save();

            //CAMBIAR ESTADO DEL TURNO
            ts.setTurno(ts.get(Integer.parseInt(map.get("turnoId").toString())));
            ts.getTurno().setEstado(Estado.FINALIZADO);
            ts.update();

            res.put("msg", "OK");
            res.put("data", "Cita registrada correctamente");

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
    public Response getCita(@PathParam("id") Integer id) throws Exception {
        HashMap map = new HashMap<>();
        CitaMedicaServices cms = new CitaMedicaServices();
        CitaMedica CitaMedica = cms.get(id);

        if (CitaMedica == null || CitaMedica.getId() == null) {
            map.put("msg", "Error");
            map.put("data", "No existe esa Cita");
            return Response.status(Response.Status.BAD_REQUEST).entity(map).build();
        }

        map.put("msg", "OK");
        map.put("data", CitaMedica);

        return Response.ok(map).build();
    }
    /* 
    @Path("/update")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCitas(HashMap map) {
        HashMap res = new HashMap<>();

        try {
            CitaMedicaServices cms = new CitaMedicaServices();

            cms.getCitaMedica().setObservaciones(map.get("observaciones").toString());
            cms.getCitaMedica().setMotivo(map.get("motivo").toString());
            cms.update();

            res.put("msg", "OK");
            res.put("data", "Cita actualizada correctamente");

            return Response.ok(res).build();
        } catch (Exception e) {
            res.put("msg", "Error");
            res.put("data", e.toString());

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

    @Path("/delete")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCitas(HashMap map) {
        HashMap res = new HashMap<>();

        try {
            CitaMedicaServices cms = new CitaMedicaServices();
            
            Integer id = Integer.parseInt(map.get("id").toString());
            CitaMedica CitaMedica = cms.get(id);

            if (CitaMedica == null || CitaMedica.getId() == null) {
                res.put("msg", "Error");
                res.put("data", "No existe esa Cita");
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
            }

            cms.setCitaMedica(CitaMedica);
            cms.delete();

            res.put("msg", "OK");
            res.put("data", "Cita eliminada correctamente");

            return Response.ok(res).build();
        } catch (Exception e) {
            res.put("msg", "Error");
            res.put("data", e.toString());

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }*/
}
