package com.gestionCitas.rest.APIs;

import java.util.HashMap;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import com.gestionCitas.controls.dao.services.SignosVitalesServices;
import com.gestionCitas.controls.dao.services.HistorialMedicoServices;
import com.gestionCitas.controls.dao.services.TurnoServices;
import com.gestionCitas.models.SignosVitales;
import com.gestionCitas.models.enums.Estado;

@Path("signosVitales")
public class SignosVitalesAPI {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllSignos() throws Exception {
        HashMap map = new HashMap<>();
        SignosVitalesServices svs = new SignosVitalesServices();

        map.put("msg", "OK");
        map.put("data", svs.getListAll().toArray());

        if (svs.getListAll().isEmpty()) {
            map.put("data", new Object[]{});
        }

        return Response.ok(map).build();
    }

    @Path("/save")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveSignos(HashMap map) {
        HashMap res = new HashMap<>();

        try {
            SignosVitalesServices svs = new SignosVitalesServices();
            TurnoServices ts = new TurnoServices();

            //VALIDACIONES
            if (ts.get(Integer.parseInt(map.get("turnoId").toString())) == null) {
                res.put("msg", "Error");
                res.put("data", "No existe ese Turno");
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
            }
            if (svs.getListAll().binarySearch("turnoId", map.get("turnoId").toString()) != null) {
                res.put("msg", "Error");
                res.put("data", "Ya existen signos para ese turno");
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
            }
            if (ts.get(Integer.parseInt(map.get("turnoId").toString())).getEstado() != Estado.RESERVADO) {
                res.put("msg", "Error");
                res.put("data", "El turno no esta en estado RESERVADO");
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
                
            }
            if (Float.parseFloat(map.get("altura").toString()) <= 0 || Float.parseFloat(map.get("altura").toString()) > 5) {
                res.put("msg", "Error");
                res.put("data", "La altura debe ser mayor a 0 o no puede ser tan elevada");
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
            }
            if (Float.parseFloat(map.get("peso").toString()) <= 0 || Float.parseFloat(map.get("peso").toString()) > 600) {
                res.put("msg", "Error");
                res.put("data", "El peso debe ser mayor a 0 o no puede ser tan elevado");
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
            }
            if (Float.parseFloat(map.get("temperatura").toString()) <= 0 || Float.parseFloat(map.get("temperatura").toString()) > 50) {
                res.put("msg", "Error");
                res.put("data", "La temperatura debe ser mayor a 0 o no puede ser tan elevada");
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
            }
            if (Float.parseFloat(map.get("presionSistolica").toString()) <= 0 || Float.parseFloat(map.get("presionSistolica").toString()) > 200) {
                res.put("msg", "Error");
                res.put("data", "La presión sistólica debe ser mayor a 0 o no puede ser tan elevada");
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
            }
            if (Float.parseFloat(map.get("presionDiastolica").toString()) <= 0 || Float.parseFloat(map.get("presionDiastolica").toString()) > 200) {
                res.put("msg", "Error");
                res.put("data", "La presión diastólica debe ser mayor a 0 o no puede ser tan elevada");
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
            }

            //GUARDADO DE DATOS
            svs.getSignosVitales().setAltura(Float.parseFloat(map.get("altura").toString()));
            svs.getSignosVitales().setPeso(Float.parseFloat(map.get("peso").toString()));
            svs.getSignosVitales().setTemperatura(Float.parseFloat(map.get("temperatura").toString()));
            svs.getSignosVitales().setPresionSistolica(Float.parseFloat(map.get("presionSistolica").toString()));
            svs.getSignosVitales().setPresionDiastolica(Float.parseFloat(map.get("presionDiastolica").toString()));
            svs.getSignosVitales().setTurnoId(Integer.parseInt(map.get("turnoId").toString()));
            svs.save();

            //AGREGAR SIGNOS VITALES AL TURNO
            ts.setTurno(ts.get(Integer.parseInt(map.get("turnoId").toString())));
            ts.getTurno().setIdSignosVitales(svs.getSignosVitales().getId());
            ts.getTurno().setEstado(Estado.EN_ESPERA);
            ts.update();

            res.put("msg", "OK");
            res.put("data", "Signos registrados correctamente");

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
    public Response getSignos(@PathParam("id") Integer id) throws Exception {
        HashMap map = new HashMap<>();
        SignosVitalesServices svs = new SignosVitalesServices();
        SignosVitales SignosVitales = svs.get(id);

        if (SignosVitales == null || SignosVitales.getId() == null) {
            map.put("msg", "Error");
            map.put("data", "No existen esos signos");
            return Response.status(Response.Status.BAD_REQUEST).entity(map).build();
        }

        map.put("msg", "OK");
        map.put("data", SignosVitales);

        return Response.ok(map).build();
    }
    /* 
    @Path("/update")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateSignos(HashMap map) {
        HashMap res = new HashMap<>();

        try {
            SignosVitalesServices svs = new SignosVitalesServices();

            svs.getSignosVitales().setObservaciones(map.get("observaciones").toString());
            svs.getSignosVitales().setMotivo(map.get("motivo").toString());
            svs.update();

            res.put("msg", "OK");
            res.put("data", "Signos actualizada correctamente");

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
    public Response deleteSignos(HashMap map) {
        HashMap res = new HashMap<>();

        try {
            SignosVitalesServices svs = new SignosVitalesServices();
            
            Integer id = Integer.parseInt(map.get("id").toString());
            SignosVitales SignosVitales = svs.get(id);

            if (SignosVitales == null || SignosVitales.getId() == null) {
                res.put("msg", "Error");
                res.put("data", "No existe esa Signos");
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
            }

            svs.setSignosVitales(SignosVitales);
            svs.delete();

            res.put("msg", "OK");
            res.put("data", "Signos eliminada correctamente");

            return Response.ok(res).build();
        } catch (Exception e) {
            res.put("msg", "Error");
            res.put("data", e.toString());

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }*/
}
