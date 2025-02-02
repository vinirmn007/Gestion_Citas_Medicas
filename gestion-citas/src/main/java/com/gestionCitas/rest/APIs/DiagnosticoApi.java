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

import com.gestionCitas.controls.dao.services.CitaMedicaServices;
import com.gestionCitas.controls.dao.services.DiagnosticoServices;
import com.gestionCitas.models.Diagnostico;

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
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveDiagnostico(HashMap<String, Object> map) {
        HashMap<String, Object> res = new HashMap<>();

        try {
            if (map.get("citaM") != null) {
                int idCita = Integer.parseInt(map.get("citaM").toString());

                CitaMedicaServices cms = new CitaMedicaServices();
                cms.get(idCita);
                
                if(cms.getCitaMedica() == null) {
                    res.put("msg", "Error");
                    res.put("data", "Cita médica no encontrada");
                    return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
                }

                if(cms.getCitaMedica().getDiagnosticoId() != 0) {
                    res.put("msg", "Error");
                    res.put("data", "La cita médica ya tiene un diagnóstico asignado");
                    return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
                }

                DiagnosticoServices ds = new DiagnosticoServices();
                ds.getDiagnostico().setDescripcion(map.get("descripcion").toString());
                ds.getDiagnostico().setIdCitaMedica(idCita);
                ds.save();

                int idDiagnostico = ds.getDiagnostico().getId();
                // Actualizar la cita médica con el ID del diagnóstico
                
                cms.setCitaMedica(cms.get(Integer.parseInt(map.get("citaM").toString())));
                cms.getCitaMedica().setDiagnosticoId(idDiagnostico);
                cms.update();
                
                res.put("msg", "OK");
                res.put("data", "Diagnóstico creado y cita médica actualizada con ID del diagnóstico");
                return Response.ok(res).build();
            } else {
                res.put("msg", "Error");
                res.put("data", "Cita médica no especificada");
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
            }
        } catch (Exception e) {
            res.put("msg", "Error al guardar");
            res.put("data", e.toString());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }



    /*
    @Path("save")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveDiagnostico(HashMap map) {
        HashMap res = new HashMap();
        Gson g = new Gson();

        try {
            if (map.get("citaM") != null) {
                CitaMedicaServices cms = new CitaMedicaServices();
                cms.getCitaMedica().setId(Integer.parseInt(map.get("citaM").toString()));
                CitaMedica citaMedica = cms.get(cms.getCitaMedica().getId());

                if (citaMedica != null) {
                    DiagnosticoServices ds = new DiagnosticoServices();
                    ds.getDiagnostico().setDescripcion(map.get("descripcion").toString());
                    ds.getDiagnostico().setIdCitaMedica(citaMedica.getId());
                    ds.save();

                    res.put("msg", "OK");
                    res.put("data", "Diagnostico creado");

                    return Response.ok(res).build();
                } else {
                    res.put("msg", "Error al guardar");
                    res.put("data", "Cita médica no encontrada");
                    return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
                }
            } else {
                res.put("msg", "Error al guardar");
                res.put("data", "Cita médica no especificada");
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
            }
        } catch (Exception e) {
            res.put("msg", "Error al guardar");
            res.put("data", e.toString());
            return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
        }
    }
    /*
    @Path("save")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveDiagnostico(HashMap map) {
        HashMap res = new HashMap();
        Gson g = new Gson();

        try {
            if (map.get("citaM") != null) {
                CitaMedicaServices cms = new CitaMedicaServices();
                cms.getCitaMedica().setId(Integer.parseInt(map.get("citaM").toString()));
                CitaMedica citaMedica = cms.get(cms.getCitaMedica().getId());

                if (citaMedica != null) {
                    if (citaMedica.getDiagnosticoId() == null) {
                        // Calcular el siguiente ID disponible para Diagnóstico
                        DiagnosticoServices ds = new DiagnosticoServices();
                        int nextDiagnosticoId = ds.getAll().stream()
                            .mapToInt(Diagnostico::getId)
                            .max()
                            .orElse(0) + 1;

                        // Crear y asignar el nuevo diagnóstico
                        Diagnostico nuevoDiagnostico = new Diagnostico();
                        nuevoDiagnostico.setId(nextDiagnosticoId);
                        nuevoDiagnostico.setDescripcion(map.get("descripcion").toString());
                        nuevoDiagnostico.setIdCitaMedica(citaMedica.getId());
                        ds.save(nuevoDiagnostico);

                        // Actualizar la cita médica con el nuevo diagnóstico
                        citaMedica.setDiagnosticoId(nextDiagnosticoId);
                        cms.update(citaMedica);

                        res.put("msg", "OK");
                        res.put("data", "Diagnostico creado con ID: " + nextDiagnosticoId);
                        return Response.ok(res).build();
                    } else {
                        res.put("msg", "Error al guardar");
                        res.put("data", "La cita médica ya tiene un diagnóstico asignado");
                        return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
                    }
                } else {
                    res.put("msg", "Error al guardar");
                    res.put("data", "Cita médica no encontrada");
                    return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
                }
            } else {
                res.put("msg", "Error al guardar");
                res.put("data", "Cita médica no especificada");
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
            }
        } catch (Exception e) {
            res.put("msg", "Error al guardar");
            res.put("data", e.toString());
            return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
        }
    }
    */


    @Path("/get/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDiagnostico (@PathParam("id") Integer id) throws Exception {
        HashMap map = new HashMap();
        DiagnosticoServices ds = new DiagnosticoServices();
        Diagnostico d = ds.get(id);

        if (d == null || d.getId() == null) {
            map.put("msg", "Error");
            map.put("data", "Diagnostico no encontrado");
            return Response.status(Response.Status.BAD_REQUEST).entity(map).build();
        }

        map.put("msg", "OK");
        map.put("data", d);
        return Response.ok(map).build();
    }

    @Path("/update")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateDiagnostico(HashMap map) {
        HashMap res = new HashMap();
        try {
            // Validación del ID del diagnóstico
            if (map.get("id") == null) {
                res.put("msg", "ERROR");
                res.put("data", "El ID del diagnóstico es requerido");
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
            }
    
            DiagnosticoServices ds = new DiagnosticoServices();
            ds.getDiagnostico().setId(Integer.parseInt(map.get("id").toString()));
    
            // Verificar si el diagnóstico existe
            if (ds.getDiagnostico() == null || ds.get(ds.getDiagnostico().getId()) == null) {
                res.put("msg", "ERROR");
                res.put("data", "No existe el diagnóstico con el ID proporcionado");
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
            }
    
            // Validación de la cita médica
            if (map.get("citaM") != null) {
                CitaMedicaServices cms = new CitaMedicaServices();
                cms.setCitaMedica(cms.get(Integer.parseInt(map.get("citaM").toString())));
    
                // Verificar si la cita médica existe
                if (cms.getCitaMedica() == null) {
                    res.put("msg", "ERROR");
                    res.put("data", "No existe la cita médica con el ID proporcionado");
                    return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
                }
    
                // Validación de si ya existe un diagnóstico asignado a la cita médica
                if (cms.getCitaMedica().getDiagnosticoId() != 0) {
                    res.put("msg", "ERROR");
                    res.put("data", "La cita médica ya tiene un diagnóstico asignado");
                    return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
                }
    
                // Actualizar el diagnóstico con los nuevos valores
                ds.getDiagnostico().setIdCitaMedica(cms.getCitaMedica().getId());
                if (map.get("descripcion") != null) {
                    ds.getDiagnostico().setDescripcion(map.get("descripcion").toString());
                }
    
                // Guardar los cambios en el diagnóstico
                ds.update();
    
                res.put("msg", "OK");
                res.put("data", "Diagnóstico actualizado correctamente");
                return Response.ok(res).build();
            } else {
                res.put("msg", "ERROR");
                res.put("data", "Falta el parámetro 'citaM'");
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            res.put("msg", "ERROR");
            res.put("data", "Error al actualizar el diagnóstico: " + e.toString());
            return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
        }
    }


    
    @Path("/delete")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(HashMap map) {
        HashMap res = new HashMap();

        try {
            // Validar que se haya proporcionado un ID de diagnóstico
            if (map.get("id") == null) {
                res.put("msg", "ERROR");
                res.put("data", "El ID del diagnóstico es requerido");
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
            }

            DiagnosticoServices ds = new DiagnosticoServices();
            int diagnosticoId = Integer.parseInt(map.get("id").toString());
            
            // Obtener el diagnóstico
            ds.getDiagnostico().setId(diagnosticoId);
            Diagnostico d = ds.get(diagnosticoId);

            // Validar que el diagnóstico exista
            if (d == null) {
                res.put("msg", "ERROR");
                res.put("data", "No existe el diagnóstico con el ID proporcionado");
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
            }

            // Obtener la cita médica asociada al diagnóstico
            CitaMedicaServices cms = new CitaMedicaServices();
            cms.setCitaMedica(cms.get(d.getIdCitaMedica()));

            // Validar que la cita médica exista
            if (cms.getCitaMedica() == null) {
                res.put("msg", "ERROR");
                res.put("data", "No existe la cita médica asociada al diagnóstico");
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
            }

            // Establecer el DiagnosticoId de la cita médica en 0
            cms.getCitaMedica().setDiagnosticoId(0);
            cms.update();  // Actualizar la cita médica

            // Eliminar el diagnóstico
            ds.setDiagnostico(d);
            ds.delete();

            res.put("msg", "OK");
            res.put("data", "Diagnóstico eliminado y Cita Médica actualizada");
            return Response.ok(res).build();

        } catch (Exception e) {
            e.printStackTrace();
            res.put("msg", "ERROR");
            res.put("data", "Error al eliminar el diagnóstico: " + e.toString());
            return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
        }
    }


        



}
