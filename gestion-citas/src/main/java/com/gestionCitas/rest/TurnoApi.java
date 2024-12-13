package com.gestionCitas.rest;

import java.util.HashMap;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.gestionCitas.controls.dao.services.TurnoServices;
import com.gestionCitas.models.enums.Estado;
import com.gestionCitas.models.Turno;
import com.gestionCitas.controls.estructures.list.LinkedList;

@Path("/turno")
public class TurnoApi {

    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listTurnos() {
        HashMap<String, Object> map = new HashMap<>();
        TurnoServices turnoServices = new TurnoServices();
        LinkedList<Turno> turnos = turnoServices.getListAll();
        map.put("msg", "Lista de turnos");
        map.put("data", turnos.isEmpty() ? new Object[]{} : turnos.toArray());
        return Response.ok(map).build();
    }

    @Path("/create")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createTurno(HashMap<String, Object> map) {
        HashMap<String, Object> res = new HashMap<>();
        try {
            TurnoServices turnoServices = new TurnoServices();
            turnoServices.getTurno().setIdMedico(Integer.parseInt(map.get("idMedico").toString()));
            turnoServices.getTurno().setIdPaciente(Integer.parseInt(map.get("idPaciente").toString()));
            turnoServices.getTurno().setFecha(map.get("fecha").toString());
            turnoServices.getTurno().setHora(map.get("hora").toString());
            if (map.containsKey("estado")) {
                turnoServices.getTurno().setEstado(Estado.valueOf(map.get("estado").toString().toUpperCase()));
            }
            turnoServices.save();
            res.put("msg", "Turno creado exitosamente");
            return Response.ok(res).build();
        } catch (Exception e) {
            res.put("msg", "Error al crear el turno");
            res.put("data", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

    @Path("/get/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTurno(@PathParam("id") Integer id) {
        HashMap<String, Object> map = new HashMap<>();
        TurnoServices turnoServices = new TurnoServices();
        try {
            Turno turno = turnoServices.get(id);
            if (turno != null) {
                map.put("msg", "Turno encontrado");
                map.put("data", turno);
                return Response.ok(map).build();
            } else {
                map.put("msg", "Turno no encontrado");
                return Response.status(Response.Status.NOT_FOUND).entity(map).build();
            }
        } catch (Exception e) {
            map.put("msg", "Error al buscar el turno");
            map.put("data", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(map).build();
        }
    }

    @Path("/update")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateTurno(HashMap<String, Object> map) {
        HashMap<String, Object> res = new HashMap<>();
        try {
            TurnoServices turnoServices = new TurnoServices();
            turnoServices.getTurno().setId(Integer.parseInt(map.get("id").toString()));
            turnoServices.getTurno().setIdMedico(Integer.parseInt(map.get("idMedico").toString()));
            turnoServices.getTurno().setIdPaciente(Integer.parseInt(map.get("idPaciente").toString()));
            if (map.containsKey("estado")) {
                turnoServices.getTurno().setEstado(Estado.valueOf(map.get("estado").toString().toUpperCase()));
            }
            turnoServices.update();
            res.put("msg", "Turno actualizado exitosamente");
            return Response.ok(res).build();
        } catch (Exception e) {
            res.put("msg", "Error al actualizar el turno");
            res.put("data", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

    @Path("/delete/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteTurno(@PathParam("id") Integer id) {
        HashMap<String, Object> res = new HashMap<>();
        try {
            TurnoServices turnoServices = new TurnoServices();
            turnoServices.getTurno().setId(id);
            turnoServices.delete();
            res.put("msg", "Turno eliminado exitosamente");
            return Response.ok(res).build();
        } catch (Exception e) {
            res.put("msg", "Error al eliminar el turno");
            res.put("data", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

    /*@Path("/filterByEstado/{estado}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response filterTurnosByEstado(@PathParam("estado") String estado) {
        HashMap<String, Object> map = new HashMap<>();
        TurnoServices turnoServices = new TurnoServices();
        try {
            LinkedList<Turno> turnos = turnoServices.findByEstado(Estado.valueOf(estado.toUpperCase()));
            map.put("msg", "Turnos filtrados por estado: " + estado);
            map.put("data", turnos.isEmpty() ? new Object[]{} : turnos.toArray());
            return Response.ok(map).build();
        } catch (Exception e) {
            map.put("msg", "Error al filtrar turnos");
            map.put("data", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(map).build();
        }
    }*/
}
