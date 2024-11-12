package vista;

import modelo.Tarea;
import javax.swing.table.AbstractTableModel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Felipe
 */


public class TaskTableModel extends AbstractTableModel {
    private final List<Tarea> tareas;
    private final String[] columnNames = {"Nombre", "Fecha de Inicio", "Fecha de Fin", "Categoría", "Enlace al Archivo", "Prioridad"};
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public TaskTableModel() {
        this.tareas = new ArrayList<>();
    }

    @Override
    public int getRowCount() {
        return tareas.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Tarea tarea = tareas.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return tarea.getNombre();
            case 1:
                return dateFormat.format(tarea.getFechaInicio());
            case 2:
                return dateFormat.format(tarea.getFechaFin());
            case 3:
                return tarea.getCategoria();
            case 4:
                return tarea.getEnlaceArchivo();
            case 5:
                return tarea.getPrioridad();
            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public void addTask(Tarea tarea) {
        tareas.add(tarea);
        fireTableRowsInserted(tareas.size() - 1, tareas.size() - 1);
    }

    public void addTasks(List<Tarea> tareas) {
        int startIndex = this.tareas.size();
        this.tareas.addAll(tareas);
        fireTableRowsInserted(startIndex, this.tareas.size() - 1);
    }

    public Tarea getTaskAt(int rowIndex) {
        return tareas.get(rowIndex);
    }

    public void updateTask(int rowIndex, Tarea tarea) {
        tareas.set(rowIndex, tarea);
        fireTableRowsUpdated(rowIndex, rowIndex);
    }

    public void removeTask(int rowIndex) {
        tareas.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }
}