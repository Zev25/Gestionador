package vista;

import modelo.GestorDeTarea;
import modelo.Tarea;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class MenuHistorial extends JFrame {
    private GestorDeTarea gestor;
    private JTable taskTable;
    private TaskTableModel tableModel;

    public MenuHistorial() {
        gestor = new GestorDeTarea();
        gestor.cargarTarea();

        setTitle("Historial de Tareas");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        tableModel = new TaskTableModel();
        taskTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(taskTable);

        List<Tarea> tareas = gestor.getListaTarea();
        tableModel.addTasks(tareas);

        taskTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    int selectedRow = taskTable.getSelectedRow();
                    if (selectedRow != -1) {
                        Tarea tareaSeleccionada = tableModel.getTaskAt(selectedRow);
                        new DetalleTarea(tareaSeleccionada).setVisible(true);
                    }
                }
            }
        });

        add(scrollPane, BorderLayout.CENTER);

        // Botón de volver
        JButton BotonVolver = new JButton("Volver al Menú Inicial");
        BotonVolver.setBackground(new Color(70, 130, 180));
        BotonVolver.setForeground(Color.WHITE);
        BotonVolver.setFont(new Font("Arial", Font.BOLD, 12));
        BotonVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MenuInicial().setVisible(true);
                dispose();
            }
        });

        JPanel BotonPanel = new JPanel();
        BotonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        BotonPanel.add(BotonVolver);
        add(BotonPanel, BorderLayout.SOUTH);
    }
}
