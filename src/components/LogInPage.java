package components;

import Controllers.UserController;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogInPage extends javax.swing.JFrame{
    private JPanel panel1;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton logInButton;
    private JButton singUpButton;
    private JPanel panel2;
    private JPanel panel3;
    private JPanel panel4;
    private JLabel noCuenta;
    private JLabel labelImage;
    private JFrame frame;

    public LogInPage() {
        frame = new JFrame("User LogIn Page");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(300, 250));
        frame.setResizable(false);
        frame.setTitle("Iniciar Sesión");

        // icono
        ImageIcon img = new ImageIcon("src/resources/logo.png");
        frame.setIconImage(img.getImage());

        // Agregar el panel
        frame.add(panel1);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // INICIAR SESION BOTON

        logInButton.setForeground(new Color(255, 255, 255));
        logInButton.setBackground(new Color(14, 98, 81));
        logInButton.setOpaque(true); // Necesario para que el color de fondo sea visible
        logInButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        logInButton.setFocusPainted(false);
        logInButton.setBorderPainted(false);


        // evento o accion que se realizará al dar click en el boton iniciarSesiónBotton
        logInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Creando un nueva instancia de consulta
                UserController us = new UserController();
                us.conector();


                // llamando el metodo login usuario de la instancia de consulta
                us.loginUser(textField1.getText(), passwordField1.getText());

                //cierra la ventana luego de que el usuario se halla logueado
                frame.setVisible(false);
            }
        });


        // REGISTRARSE BOTON
        singUpButton.setForeground(new Color(255, 255, 255));
        singUpButton.setBackground(new Color(14, 98, 81));
        singUpButton.setOpaque(true); // Necesario para que el color de fondo sea visible
        singUpButton.setFocusPainted(false);
        singUpButton.setBorderPainted(false);

        singUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                RegisterPage rp = new RegisterPage();
                frame.setVisible(false);
                rp.setVisible(true);
            }
        });


    }
    public static void main(String[] args) {
    }


}
