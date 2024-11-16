package vista;

import controlador.Notificaciones;
import modelo.Tarea;
import controlador.TareaControlador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;
import java.text.SimpleDateFormat;

public class InterfazUsuario extends JFrame {
    private TareaControlador controlador;
    private Notificaciones notificaciones;
    private TaskTableModel tableModel;
    private JTable taskTable;
    private JTextField nombreField;
    private JTextField fechaInicioField;
    private JTextField fechaFinField;
    private JTextField categoriaField;
    private JTextField enlaceField;
    private JTextField prioridadField;
    private JTextArea comentariosArea;
    private SimpleDateFormat dateFormat;

    public InterfazUsuario() {
        controlador = new TareaControlador();
        notificaciones = new Notificaciones(controlador);
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        setTitle("Gestor de Tareas");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        tableModel = new TaskTableModel();
        taskTable = new JTable(tableModel);
        taskTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(taskTable);

        tableModel.addTasks(controlador.obtenerTareas());

        nombreField = new JTextField(15);
        fechaInicioField = new JTextField(15);
        fechaFinField = new JTextField(15);
        categoriaField = new JTextField(15);
        enlaceField = new JTextField(15);
        prioridadField = new JTextField(15);
        comentariosArea = new JTextArea(3, 15);
        comentariosArea.setLineWrap(true);
        comentariosArea.setWrapStyleWord(true);
        comentariosArea.setPreferredSize(new Dimension(400, 100));
        JScrollPane comentariosScrollPane = new JScrollPane(comentariosArea);
        comentariosScrollPane.setPreferredSize(new Dimension(400, 100));

        JButton agregarBoton = new JButton("Agregar Tarea");
        agregarBoton.setBackground(new Color(34, 139, 34));
        agregarBoton.setForeground(Color.WHITE);
        agregarBoton.setFont(new Font("Arial", Font.BOLD, 12));
        agregarBoton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarTarea();
            }
        });

        JButton editarBoton = new JButton("Editar Tarea");
        editarBoton.setBackground(new Color(255, 165, 0));
        editarBoton.setForeground(Color.WHITE);
        editarBoton.setFont(new Font("Arial", Font.BOLD, 12));
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
                    comentariosArea.setText(tareaSeleccionada.getComentarios());

                    agregarBoton.setText("Actualizar Tarea");
                    agregarBoton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            try {
                                controlador.editarTarea(tareaSeleccionada,
                                    nombreField.getText(),
                                    fechaInicioField.getText(),
                                    fechaFinField.getText(),
                                    categoriaField.getText(),
                                    enlaceField.getText(),
                                    prioridadField.getText(),
                                    comentariosArea.getText());

                                tableModel.updateTask(selectedRow, tareaSeleccionada);
                                agregarBoton.setText("Agregar Tarea");
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
        eliminarBoton.setBackground(new Color(178, 34, 34));
        eliminarBoton.setForeground(Color.WHITE);
        eliminarBoton.setFont(new Font("Arial", Font.BOLD, 12));
        eliminarBoton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = taskTable.getSelectedRow();
                if (selectedRow != -1) {
                    controlador.eliminarTarea(tableModel.getTaskAt(selectedRow));
                    tableModel.removeTask(selectedRow);
                } else {
                    JOptionPane.showMessageDialog(null, "Por favor selecciona una tarea para eliminar.");
                }
            }
        });

        JButton volverBoton = new JButton("Volver al Menú Inicial");
        volverBoton.setBackground(new Color(70, 130, 180));
        volverBoton.setForeground(Color.WHITE);
        volverBoton.setFont(new Font("Arial", Font.BOLD, 12));
        volverBoton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MenuInicial().setVisible(true);
                dispose();
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
        inputPanel.add(createInputField("Comentarios", comentariosScrollPane));

        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 5, 5));
        buttonPanel.add(agregarBoton);
        buttonPanel.add(editarBoton);
        buttonPanel.add(eliminarBoton);

        JPanel volverPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        volverPanel.add(volverBoton);

        inputPanel.add(Box.createVerticalStrut(10));
        inputPanel.add(buttonPanel);

        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(volverPanel, BorderLayout.SOUTH);

        // Llamar a método para mostrar notificaciones al iniciar
        notificaciones.mostrarNotificaciones();
    }

    private JPanel createInputField(String label, Component component) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.add(new JLabel(label));
        panel.add(component);
        return panel;
    }

    private void agregarTarea() {
        String nombre = nombreField.getText();
        String fechaInicio = fechaInicioField.getText();
        String fechaFin = fechaFinField.getText();
        String categoria = categoriaField.getText();
        String enlace = enlaceField.getText();
        String prioridad = prioridadField.getText();
        String comentarios = comentariosArea.getText();

        if (nombre.isEmpty() || fechaInicio.isEmpty() || fechaFin.isEmpty() || categoria.isEmpty() || enlace.isEmpty() || prioridad.isEmpty() || comentarios.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor completa todos los campos.");
            return;
        }

        try {
            controlador.agregarTarea(nombre, fechaInicio, fechaFin, categoria, enlace, prioridad, comentarios);
            tableModel.addTask(new Tarea(nombre, dateFormat.parse(fechaInicio), dateFormat.parse(fechaFin), categoria, enlace, prioridad, comentarios));
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error en el formato de fecha. Use dd/MM/yyyy.");
        }
    }
}
