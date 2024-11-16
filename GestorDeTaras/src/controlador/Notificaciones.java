package controlador;

import modelo.Tarea;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Felipe
 */
public class Notificaciones {
    private TareaControlador controlador;
    private SimpleDateFormat dateFormat;

    public Notificaciones(TareaControlador controlador) {
        this.controlador = controlador;
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    }

    public void mostrarNotificaciones() {
        List<Tarea> tareasProximasAFinalizar = controlador.obtenerTareasProximasAFinalizar();
        if (!tareasProximasAFinalizar.isEmpty()) {
            StringBuilder mensaje = new StringBuilder("Las siguientes tareas están próximas a finalizar:\n");
            for (Tarea tarea : tareasProximasAFinalizar) {
                mensaje.append("- ").append(tarea.getNombre()).append(" (Finaliza el: ")
                        .append(dateFormat.format(tarea.getFechaFin())).append(")\n");
            }
            JOptionPane.showMessageDialog(null, mensaje.toString(), "Notificaciones de Tareas", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
