package vista;

import modelo.GestorDeTarea;
import modelo.Tarea;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;
import java.text.SimpleDateFormat;

/**
 *
 * @author Felipe
 */

public class InterfazUsuario extends JFrame {
    private GestorDeTarea gestor;
    private TaskTableModel tableModel;
    private JTable taskTable;
    private JTextField nombreField;
    private JTextField fechaInicioField;
    private JTextField fechaFinField;
    private JTextField categoriaField;
    private JTextField enlaceField;
    private JTextField prioridadField;
    private SimpleDateFormat dateFormat;

    public InterfazUsuario() {
        gestor = new GestorDeTarea();
        gestor.cargarTarea();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        setTitle("Gestor de Tareas");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        tableModel = new TaskTableModel();
        taskTable = new JTable(tableModel);
        taskTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(taskTable);

        tableModel.addTasks(gestor.getListaTarea());

        nombreField = new JTextField(15);
        fechaInicioField = new JTextField(15);
        fechaFinField = new JTextField(15);
        categoriaField = new JTextField(15);
        enlaceField = new JTextField(15);
        prioridadField = new JTextField(15);

        JButton agregarBoton = new JButton("Agregar Tarea");
        agregarBoton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = nombreField.getText();
                String fechaInicio = fechaInicioField.getText();
                String fechaFin = fechaFinField.getText();
                String categoria = categoriaField.getText();
                String enlace = enlaceField.getText();
                String prioridad = prioridadField.getText();

                if (nombre.isEmpty() || fechaInicio.isEmpty() || fechaFin.isEmpty() || categoria.isEmpty() || enlace.isEmpty() || prioridad.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor completa todos los campos.");
                    return;
                }

                try {
                    Tarea tarea = new Tarea(
                            nombre,
                            dateFormat.parse(fechaInicio),
                            dateFormat.parse(fechaFin),
                            categoria,
                            enlace,
                            prioridad
                    );
                    gestor.agregarTarea(tarea);
                    tableModel.addTask(tarea);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error en el formato de fecha. Use dd/MM/yyyy.");
                }
            }
        });

        JButton editarBoton = new JButton("Editar Tarea");
        editarBoton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = taskTable.getSelectedRow();
                if (selectedRow != -1) {
                    Tarea tareaSeleccionada = tableModel.getTaskAt(selectedRow);

                    nombreField.setText(tareaSeleccionada.getNombre());
                    fechaInicioField.setText(dateFormat.format(tareaSeleccionada.getFechaInicio()));
                    fechaFinField.setText(dateFormat.format(tareaSeleccionada.getFechaFin()));
                    categoriaField.setText(tareaSeleccionada.getCategoria());
                    enlaceField.setText(tareaSeleccionada.getEnlaceArchivo());
                    prioridadField.setText(tareaSeleccionada.getPrioridad());

                    agregarBoton.setText("Actualizar Tarea");
                    agregarBoton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            try {
                                tareaSeleccionada.setNombre(nombreField.getText());
                                tareaSeleccionada.setFechaInicio(dateFormat.parse(fechaInicioField.getText()));
                                tareaSeleccionada.setFechaFin(dateFormat.parse(fechaFinField.getText()));
                                tareaSeleccionada.setCategoria(categoriaField.getText());
                                tareaSeleccionada.setEnlaceArchivo(enlaceField.getText());
                                tareaSeleccionada.setPrioridad(prioridadField.getText());

                                gestor.guardarTarea();
                                tableModel.updateTask(selectedRow, tareaSeleccionada);
                                agregarBoton.setText("Agregar Tarea");
                                agregarBoton.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        agregarTarea();
                                    }
                                });
                            } catch (Exception ex) {
                                ex.printStackTrace();
                                JOptionPane.showMessageDialog(null, "Error en el formato de fecha. Use dd/MM/yyyy.");
                            }
                        }
                    });
                } else {
                    JOptionPane.showMessageDialog(null, "Por favor selecciona una tarea para editar.");
                }
            }
        });

        JButton eliminarBoton = new JButton("Eliminar Tarea");
        eliminarBoton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = taskTable.getSelectedRow();
                if (selectedRow != -1) {
                    gestor.eliminarTarea(tableModel.getTaskAt(selectedRow));
                    tableModel.removeTask(selectedRow);
                } else {
                    JOptionPane.showMessageDialog(null, "Por favor selecciona una tarea para eliminar.");
                }
            }
        });

        taskTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    int selectedRow = taskTable.getSelectedRow();
                    if (selectedRow != -1) {
                        Tarea tareaSeleccionada = tableModel.getTaskAt(selectedRow);
                        try {
                            Desktop.getDesktop().browse(new URI(tareaSeleccionada.getEnlaceArchivo()));
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        inputPanel.add(createInputField("Nombre", nombreField));
        inputPanel.add(createInputField("Fecha de Inicio", fechaInicioField));
        inputPanel.add(createInputField("Fecha de Fin", fechaFinField));
        inputPanel.add(createInputField("Categoría", categoriaField));
        inputPanel.add(createInputField("Enlace al Archivo", enlaceField));
        inputPanel.add(createInputField("Prioridad", prioridadField));

        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 5, 5));
        buttonPanel.add(agregarBoton);
        buttonPanel.add(editarBoton);
        buttonPanel.add(eliminarBoton);

        inputPanel.add(Box.createVerticalStrut(10));
        inputPanel.add(buttonPanel);

        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private JPanel createInputField(String label, JTextField textField) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.add(new JLabel(label));
        panel.add(textField);
        return panel;
    }

    private void agregarTarea() {
        String nombre = nombreField.getText();
        String fechaInicio = fechaInicioField.getText();
        String fechaFin = fechaFinField.getText();
        String categoria = categoriaField.getText();
        String enlace = enlaceField.getText();
        String prioridad = prioridadField.getText();

        if (nombre.isEmpty() || fechaInicio.isEmpty() || fechaFin.isEmpty() || categoria.isEmpty() || enlace.isEmpty() || prioridad.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor completa todos los campos.");
            return;
        }

        try {
            Tarea tarea = new Tarea(
                    nombre,
                    dateFormat.parse(fechaInicio),
                    dateFormat.parse(fechaFin),
                    categoria,
                    enlace,
                    prioridad
            );
            gestor.agregarTarea(tarea);
            tableModel.addTask(tarea);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error en el formato de fecha. Use dd/MM/yyyy.");
        }
    }
}
