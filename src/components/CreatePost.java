package components;

/**
 *
 * @authors
 * name: DAVID ESCOBAR RUIZ
 * email: DAVIDESCOBARRUIZ30@GMAIL.COM
 *
 * name: ANDERSON LONDOÑO AVENDAÑO
 * email: AVENDANOANDERSON032@GMAIL.COM
 *
 */

import Controllers.ActualUser;
import Controllers.PostController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.UUID;

public class CreatePost  extends JFrame {

    // Componentes del formulario
    private JTextField txtTitle;
    private JTextArea txtDescription;
    private JTextField txtLocation;
    private JTextField txtPrice;
    private JTextField txtUserId;
    private JTextField txtPostId;
    private JButton btnSubmit;


    public CreatePost(){
        // icono
        try {
            Image icono = new ImageIcon(getClass().getResource("/resources/logo.png")).getImage();
            setIconImage(icono);
        } catch (Exception e) {
            System.out.println("Error" + e);
        }

        // Configuración básica del formulario
        setTitle("Crear Nueva Publicación de Inmueble");
        setSize(500, 500);
        // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Establecer IDs de forma oculta
        txtUserId = new JTextField(ActualUser.idUser);
        // Título de la propiedad
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Título del Inmueble:"), gbc);

        gbc.gridx = 1;
        txtTitle = new JTextField(20);
        add(txtTitle, gbc);

        // Ubicación
        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Ubicación:"), gbc);

        gbc.gridx = 1;
        txtLocation = new JTextField(20);
        add(txtLocation, gbc);

        // Precio
        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Precio:"), gbc);

        gbc.gridx = 1;
        txtPrice = new JTextField(20);
        add(txtPrice, gbc);

        // Descripción
        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Descripción:"), gbc);

        gbc.gridx = 1;
        txtDescription = new JTextArea(4, 20);
        txtDescription.setLineWrap(true);
        add(new JScrollPane(txtDescription), gbc);

        // Botón de Submit
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        btnSubmit = new JButton("Publicar Inmueble");

        btnSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PostController pc = new PostController();
                pc.createPost(txtTitle.getText(),txtDescription.getText(),txtLocation.getText(),txtPrice.getText(),ActualUser.idUser);
            }
        });
        add(btnSubmit, gbc);
    }

    // Método para generar un ID único
    private String generarIdUnico() {
        return UUID.randomUUID().toString();
    }

    private void submitPropertyListing() {
        // Validar campos
        if (validateForm()) {
            // Recoger datos del formulario
            String title = txtTitle.getText();
            String userId = txtUserId.getText();
            String postId = txtPostId.getText();
            String location = txtLocation.getText();
            String price = txtPrice.getText();
            String description = txtDescription.getText();

            // Aquí iría la lógica para guardar la publicación
            // Por ejemplo, enviar a una base de datos o servicio web
            String mensaje = String.format(
                    "Publicación creada:\n" +
                            "Título: %s\n" +
                            "Ubicación: %s\n" +
                            "Precio: %s\n" +
                            "Descripción: %s",
                    title, location, price, description
            );

            JOptionPane.showMessageDialog(this, mensaje, "Publicación Exitosa", JOptionPane.INFORMATION_MESSAGE);

            // Limpiar formulario después de publicar
            limpiarFormulario();
        }
    }

    private boolean validateForm() {
        // Validaciones básicas
        if (txtTitle.getText().trim().isEmpty()) {
            mostrarError("Por favor, ingrese un título");
            return false;
        }

        if (txtLocation.getText().trim().isEmpty()) {
            mostrarError("Por favor, ingrese la ubicación");
            return false;
        }

        try {
            double price = Double.parseDouble(txtPrice.getText());
            if (price <= 0) {
                mostrarError("El precio debe ser mayor a 0");
                return false;
            }
        } catch (NumberFormatException e) {
            mostrarError("Por favor, ingrese un precio válido");
            return false;
        }

        if (txtDescription.getText().trim().isEmpty()) {
            mostrarError("Por favor, ingrese una descripción");
            return false;
        }

        return true;
    }

    private void limpiarFormulario() {
        txtTitle.setText("");
        txtLocation.setText("");
        txtPrice.setText("");
        txtDescription.setText("");
        // Regenerar el ID de post
        txtPostId.setText(generarIdUnico());
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje,
                "Error de Validación", JOptionPane.ERROR_MESSAGE);
    }

}
