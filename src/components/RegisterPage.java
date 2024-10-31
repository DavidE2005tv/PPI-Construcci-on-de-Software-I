package components;

import Controllers.UserController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterPage extends javax.swing.JFrame{
    private JLabel Register;
    private JLabel name;
    private JTextField nameField;
    private JTextField emailField;
    private JPasswordField passwordField1;
    private JButton singUpButton;
    private JButton logInButton;
    private JPanel panel2;
    private JPanel singUp;
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
        singUp.setAlignmentX(Component.CENTER_ALIGNMENT);
        singUp.setFont(new Font("Arial", Font.BOLD, 18));
        singUp.setForeground(new Color(14, 98, 81));

        // INICIAR SESION BOTON
        logInButton.setForeground(new Color(255, 255, 255));
        logInButton.setBackground(new Color(14, 98, 81));
        logInButton.setOpaque(true); // Necesario para que el color de fondo sea visible
        logInButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        logInButton.setFocusPainted(false);
        logInButton.setBorderPainted(false);


        // Abrir la ventana de login despues de dar click en el boton de iniciar sesion
        logInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LogInPage lp = new LogInPage();
                lp.setVisible(true);
                frame2.setVisible(false);
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

                // instancia controlador de usuarios
                UserController uc = new UserController();
                uc.conector();
                uc.singUpUser(nameField.getText(), emailField.getText(),passwordField1.getText());

            }
        });
    }

/*    public void setVisible(boolean b) {
        setVisible(b);
    }*/
}
