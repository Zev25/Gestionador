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

        JButton gestionarTareasButton = new JButton("Gestionar Tareas");
        gestionarTareasButton.setBounds(100, 100, 200, 50);
        gestionarTareasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new InterfazUsuario().setVisible(true);
                dispose(); // Cierra el menú inicial
            }
        });

        add(gestionarTareasButton);
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
