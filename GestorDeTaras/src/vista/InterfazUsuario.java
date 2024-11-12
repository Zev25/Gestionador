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
import java.util.Date;

public class InterfazUsuario extends JFrame {
    private GestorDeTarea gestor;
    private DefaultListModel<Tarea> modeloLista;
    private JList<Tarea> listView;
    private JTextField nombreField;
    private JTextField fechaInicioField;
    private JTextField fechaFinField;
    private JTextField categoriaField;
    private JTextField enlaceField;
    private JTextField prioridadField;

    public InterfazUsuario() {
        gestor = new GestorDeTarea();
        gestor.cargarTarea();

        setTitle("Gestor de Tareas");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        modeloLista = new DefaultListModel<>();
        listView = new JList<>(modeloLista);
        listView.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listView.setVisibleRowCount(-1);
        JScrollPane listScroller = new JScrollPane(listView);

        for (Tarea tarea : gestor.getListaTarea()) {
            modeloLista.addElement(tarea);
        }

        nombreField = new JTextField();
        fechaInicioField = new JTextField();
        fechaFinField = new JTextField();
        categoriaField = new JTextField();
        enlaceField = new JTextField();
        prioridadField = new JTextField();

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

                Tarea tarea = new Tarea(
                    nombre,
                    new Date(fechaInicio),
                    new Date(fechaFin),
                    categoria,
                    enlace,
                    prioridad
                );
                gestor.agregarTarea(tarea);
                modeloLista.addElement(tarea);
            }
        });

        JButton editarBoton = new JButton("Editar Tarea");
        editarBoton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Tarea tareaSeleccionada = listView.getSelectedValue();
                if (tareaSeleccionada != null) {
                    nombreField.setText(tareaSeleccionada.getNombre());
                    fechaInicioField.setText(tareaSeleccionada.getFechaInicio().toString());
                    fechaFinField.setText(tareaSeleccionada.getFechaFin().toString());
                    categoriaField.setText(tareaSeleccionada.getCategoria());
                    enlaceField.setText(tareaSeleccionada.getEnlaceArchivo());
                    prioridadField.setText(tareaSeleccionada.getPrioridad());

                    agregarBoton.setText("Actualizar Tarea");
                    agregarBoton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            tareaSeleccionada.setNombre(nombreField.getText());
                            tareaSeleccionada.setFechaInicio(new Date(fechaInicioField.getText()));
                            tareaSeleccionada.setFechaFin(new Date(fechaFinField.getText()));
                            tareaSeleccionada.setCategoria(categoriaField.getText());
                            tareaSeleccionada.setEnlaceArchivo(enlaceField.getText());
                            tareaSeleccionada.setPrioridad(prioridadField.getText());

                            gestor.guardarTarea();
                            listView.repaint();

                            agregarBoton.setText("Agregar Tarea");
                            agregarBoton.addActionListener(this);
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
                Tarea tareaSeleccionada = listView.getSelectedValue();
                if (tareaSeleccionada != null) {
                    gestor.eliminarTarea(tareaSeleccionada);
                    modeloLista.removeElement(tareaSeleccionada);
                } else {
                    JOptionPane.showMessageDialog(null, "Por favor selecciona una tarea para eliminar.");
                }
            }
        });

        listView.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    Tarea tareaSeleccionada = listView.getSelectedValue();
                    if (tareaSeleccionada != null) {
                        try {
                            Desktop.getDesktop().browse(new URI(tareaSeleccionada.getEnlaceArchivo()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(8, 2));
        inputPanel.add(new JLabel("Nombre"));
        inputPanel.add(nombreField);
        inputPanel.add(new JLabel("Fecha de Inicio"));
        inputPanel.add(fechaInicioField);
        inputPanel.add(new JLabel("Fecha de Fin"));
        inputPanel.add(fechaFinField);
        inputPanel.add(new JLabel("Categoría"));
        inputPanel.add(categoriaField);
        inputPanel.add(new JLabel("Enlace al Archivo"));
        inputPanel.add(enlaceField);
        inputPanel.add(new JLabel("Prioridad"));
        inputPanel.add(prioridadField);
        inputPanel.add(agregarBoton);
        inputPanel.add(editarBoton);
        inputPanel.add(eliminarBoton);

        add(inputPanel, BorderLayout.NORTH);
        add(listScroller, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new InterfazUsuario().setVisible(true);
            }
        });
    }
}
