package vista;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Felipe
 */


public class MenuInicial extends JFrame {
    public MenuInicial() {
        setTitle("Menú Inicial");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        

        JButton BotonGestionarTareas = new JButton("Gestionar Tareas");
        BotonGestionarTareas.setBounds(100, 60, 200, 50);
        BotonGestionarTareas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new InterfazUsuario().setVisible(true);
                dispose(); // Cierra el menú inicial
            }
        });
        
        JButton BotonHistorial = new JButton("Historial Tareas");
        BotonHistorial.setBounds(100, 170, 200, 50);
        BotonHistorial.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MenuHistorial().setVisible(true);
                dispose();
        }
        });

        add(BotonGestionarTareas);
        add(BotonHistorial);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MenuInicial().setVisible(true);
            }
        });
    }
}
