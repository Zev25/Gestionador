package vista;

import modelo.Tarea;

import javax.swing.*;
import java.awt.*;

public class DetalleTarea extends JFrame {
    public DetalleTarea(Tarea tarea) {
        setTitle("Detalles de la Tarea");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JTextArea tareaDetalles = new JTextArea();
        tareaDetalles.setEditable(false);
        tareaDetalles.setText(generarTextoDetalle(tarea));
        tareaDetalles.setFont(new Font("Arial", Font.PLAIN, 14));
        tareaDetalles.setMargin(new Insets(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(tareaDetalles);
        add(scrollPane, BorderLayout.CENTER);
    }

    private String generarTextoDetalle(Tarea tarea) {
        StringBuilder detalles = new StringBuilder();
        detalles.append("Nombre: ").append(tarea.getNombre()).append("\n");
        detalles.append("Fecha de Inicio: ").append(tarea.getFechaInicio()).append("\n");
        detalles.append("Fecha de Fin: ").append(tarea.getFechaFin()).append("\n");
        detalles.append("Categoría: ").append(tarea.getCategoria()).append("\n");
        detalles.append("Enlace al Archivo: ").append(tarea.getEnlaceArchivo()).append("\n");
        detalles.append("Prioridad: ").append(tarea.getPrioridad()).append("\n");
        detalles.append("Comentarios: ").append(tarea.getComentarios()).append("\n"); // Añadir los comentarios
        return detalles.toString();
    }
}
