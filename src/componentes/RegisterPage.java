package componentes;

import BD.Querys;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterPage extends javax.swing.JFrame{
    private JLabel Register;
    private JLabel Nombre;
    private JTextField nombre;
    private JTextField correo;
    private JPasswordField passwordField1;
    private JButton registrarseButton;
    private JButton iniciarSesiónButton;
    private JPanel panel2;
    private JPanel Registrarse;
    private JFrame frame2;


    public RegisterPage(){
        frame2 = new JFrame("Registro de Usuario");
        frame2.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame2.setPreferredSize(new Dimension(300, 300));
        frame2.setResizable(false);

        // icono
        ImageIcon img = new ImageIcon("src/resources/logo.png");
        frame2.setIconImage(img.getImage());

        // Agregar el panel
        frame2.add(panel2);
        frame2.pack();
        frame2.setLocationRelativeTo(null);
        frame2.setVisible(true);

        // INICIAR SESION TITULO
        Registrarse.setAlignmentX(Component.CENTER_ALIGNMENT);
        Registrarse.setFont(new Font("Arial", Font.BOLD, 18));
        Registrarse.setForeground(new Color(14, 98, 81));

        // INICIAR SESION BOTON
        iniciarSesiónButton.setForeground(new Color(255, 255, 255));
        iniciarSesiónButton.setBackground(new Color(14, 98, 81));
        iniciarSesiónButton.setOpaque(true); // Necesario para que el color de fondo sea visible
        iniciarSesiónButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        iniciarSesiónButton.setFocusPainted(false);
        iniciarSesiónButton.setBorderPainted(false);

        iniciarSesiónButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LogInPage lp = new LogInPage();
                lp.setVisible(true);
                frame2.setVisible(false);
            }
        });


        // REGISTRARSE BOTON
        registrarseButton.setForeground(new Color(255, 255, 255));
        registrarseButton.setBackground(new Color(14, 98, 81));
        registrarseButton.setOpaque(true); // Necesario para que el color de fondo sea visible
        registrarseButton.setFocusPainted(false);
        registrarseButton.setBorderPainted(false);

        registrarseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Querys bd = new Querys();
                bd.conector();
                bd.registrarUsuario(nombre.getText(),correo.getText(),passwordField1.getText());

            }
        });
    }

/*    public void setVisible(boolean b) {
        setVisible(b);
    }*/
}
