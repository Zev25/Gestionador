package modelo;

import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.text.ParseException;

public class GestorDeTarea {
    private List<Tarea> listaTarea;

    public GestorDeTarea() {
        this.listaTarea = new ArrayList<>();
    }

    public void agregarTarea(Tarea tarea) {
        listaTarea.add(tarea);
        guardarTarea();
    }

    public void eliminarTarea(Tarea tarea) {
        listaTarea.remove(tarea);
        guardarTarea();
    }

    public void guardarTarea() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("tareas.txt"))) {
            for (Tarea tarea : listaTarea) {
                writer.println(tarea.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cargarTarea() {
        listaTarea.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader("tareas.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    listaTarea.add(Tarea.fromString(line));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Tarea> getListaTarea() {
        return listaTarea;
    }
}
