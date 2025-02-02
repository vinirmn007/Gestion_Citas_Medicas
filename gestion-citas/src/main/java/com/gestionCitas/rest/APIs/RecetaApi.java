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
import com.gestionCitas.controls.dao.services.RecetaServices;
import com.gestionCitas.models.Receta;


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

    @Path("/save")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveReceta(HashMap<String, Object> map) {
        HashMap<String, Object> res = new HashMap<>();
        DiagnosticoServices diagnosticoService = new DiagnosticoServices();
        RecetaServices recetaService = new RecetaServices();
    
        try {
            // Validar que el diagnóstico esté presente y exista
            if (map.containsKey("idDiagnostico")) {
                int idDiagnostico = Integer.parseInt(map.get("idDiagnostico").toString());
                if (diagnosticoService.get(idDiagnostico) == null) {
                    res.put("msg", "Error al guardar");
                    res.put("data", "Diagnóstico no encontrado");
                    return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
                }
    
                // Validar que la prescripción esté presente
                if (!map.containsKey("prescripcion")) {
                    res.put("msg", "Error al guardar");
                    res.put("data", "Prescripción no proporcionada");
                    return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
                }

                if (!map.containsKey("medicamentos")) {
                    res.put("msg", "Error al guardar");
                    res.put("data", "Medicamentos no proporcionados");
                    return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
                }
    
                String prescripcion = map.get("prescripcion").toString();
                String medicamentos = map.get("medicamentos").toString();
    
    
                // Configurar la receta y guardarla
                recetaService.getReceta().setIdDiagnostico(idDiagnostico);
                recetaService.getReceta().setPrescripcion(prescripcion);
                recetaService.getReceta().setMedicamentos(medicamentos);
                recetaService.save();
    
                res.put("msg", "OK");
                res.put("data", "Receta creada exitosamente");
                return Response.ok(res).build();
    
            } else {
                res.put("msg", "Error al guardar");
                res.put("data", "Diagnóstico no proporcionado");
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
            }
        } catch (Exception e) {
            res.put("msg", "Error al guardar total");
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
    public Response update(HashMap<String, Object> map) throws Exception {
        HashMap<String, Object> res = new HashMap<>();
        try {
            // Verificar que el ID de la receta esté presente
            if (map.get("id") == null) {
                res.put("msg", "Error");
                res.put("data", "El ID de la receta es requerido");
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
            }
    
            RecetaServices rs = new RecetaServices();
            rs.getReceta().setId(Integer.parseInt(map.get("id").toString()));
    
            // Verificar que la receta exista
            if (rs.getReceta() == null || rs.get(rs.getReceta().getId()) == null) {
                res.put("msg", "Error");
                res.put("data", "No existe la receta con el ID proporcionado");
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
            }
    
            // Verificar que el diagnóstico esté presente y válido
            if (map.get("diagnosticoC") != null) {
                DiagnosticoServices ds = new DiagnosticoServices();
                ds.setDiagnostico(ds.get(Integer.parseInt(map.get("diagnosticoC").toString())));
                
                if (ds.getDiagnostico() == null) {
                    res.put("msg", "Error");
                    res.put("data", "Diagnóstico no encontrado");
                    return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
                }
    
                // Validar que la prescripción y los medicamentos estén presentes
                if (!map.containsKey("prescripcion")) {
                    res.put("msg", "Error");
                    res.put("data", "Prescripción no proporcionada");
                    return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
                }
                if (!map.containsKey("medicamentos")) {
                    res.put("msg", "Error");
                    res.put("data", "Medicamentos no proporcionados");
                    return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
                }
    
                // Actualizar los datos de la receta
                rs.getReceta().setIdDiagnostico(ds.getDiagnostico().getId());
                rs.getReceta().setPrescripcion(map.get("prescripcion").toString());
                rs.getReceta().setMedicamentos(map.get("medicamentos").toString());
                rs.update();
    
                res.put("msg", "OK");
                res.put("data", rs.getReceta());
                return Response.ok(res).build();
            }
    
            // Si no se especifica el diagnóstico
            res.put("msg", "Error");
            res.put("data", "Diagnóstico no especificado");
            return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
    
        } catch (Exception e) {
            res.put("msg", "Error al actualizar");
            res.put("data", e.toString());
            return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
        }
    }

    @Path("/delete")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(HashMap<String, Object> map) {
        HashMap<String, Object> res = new HashMap<>();
        try {
            // Verificar que el ID de la receta esté presente
            if (map.get("id") == null) {
                res.put("msg", "Error");
                res.put("data", "El ID de la receta es requerido");
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
            }
    
            RecetaServices rs = new RecetaServices();
            rs.getReceta().setId(Integer.parseInt(map.get("id").toString()));
    
            // Verificar que la receta exista
            if (rs.getReceta() == null || rs.get(rs.getReceta().getId()) == null) {
                res.put("msg", "Error");
                res.put("data", "No existe la receta con el ID proporcionado");
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
            }
    
            // Verificar que el diagnóstico asociado a la receta esté presente
            DiagnosticoServices ds = new DiagnosticoServices();
            ds.getDiagnostico().setId(rs.getReceta().getIdDiagnostico());
            if (ds.get(ds.getDiagnostico().getId()) == null) {
                res.put("msg", "Error");
                res.put("data", "No existe el diagnóstico asociado a la receta");
                return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
            }
    
            // Eliminar la receta
            rs.delete();
    
    
            res.put("msg", "OK");
            res.put("data", "Receta eliminada y diagnóstico actualizado");
            return Response.ok(res).build();
    
        } catch (Exception e) {
            e.printStackTrace();
            res.put("msg", "Error al eliminar");
            res.put("data", e.toString());
            return Response.status(Response.Status.BAD_REQUEST).entity(res).build();
        }
    }



}
