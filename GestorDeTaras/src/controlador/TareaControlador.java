package controlador;

import modelo.GestorDeTarea;
import modelo.Tarea;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TareaControlador {
    private GestorDeTarea gestor;
    private SimpleDateFormat dateFormat;

    public TareaControlador() {
        gestor = new GestorDeTarea();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    }

    public List<Tarea> obtenerTareas() {
        gestor.cargarTarea();
        return gestor.getListaTarea();
    }

    public boolean agregarTarea(String nombre, String fechaInicio, String fechaFin, String categoria, String enlace, String prioridad, String comentarios) {
        try {
            Date inicio = dateFormat.parse(fechaInicio);
            Date fin = dateFormat.parse(fechaFin);
            Tarea tarea = new Tarea(nombre, inicio, fin, categoria, enlace, prioridad, comentarios);
            gestor.agregarTarea(tarea);
            return true;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean editarTarea(Tarea tarea, String nombre, String fechaInicio, String fechaFin, String categoria, String enlace, String prioridad, String comentarios) {
        try {
            tarea.setNombre(nombre);
            tarea.setFechaInicio(dateFormat.parse(fechaInicio));
            tarea.setFechaFin(dateFormat.parse(fechaFin));
            tarea.setCategoria(categoria);
            tarea.setEnlaceArchivo(enlace);
            tarea.setPrioridad(prioridad);
            tarea.setComentarios(comentarios);
            gestor.guardarTarea();
            return true;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void eliminarTarea(Tarea tarea) {
        gestor.eliminarTarea(tarea);
    }

    public List<Tarea> obtenerTareasProximasAFinalizar() {
        List<Tarea> todasLasTareas = obtenerTareas();
        List<Tarea> tareasProximas = new ArrayList<>();
        Date hoy = new Date();
        long diezDiasEnMilisegundos = 10L * 24 * 60 * 60 * 1000; // 10 días en milisegundos

        for (Tarea tarea : todasLasTareas) {
            if (tarea.getFechaFin().getTime() - hoy.getTime() <= diezDiasEnMilisegundos) {
                tareasProximas.add(tarea);
            }
        }
        return tareasProximas;
    }
}
