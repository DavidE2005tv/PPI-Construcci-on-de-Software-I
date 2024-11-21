package Controllers;

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

import BD.BDconnection;
import components.Search;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserController extends BDconnection {

    public void singUpUser(String name, String email, String password) {
        try {

            conector();

            // Consulta a la BD

            String query = "INSERT INTO users (nombre,email,contraseña) VALUES (?,?,?)";

            // Ingresar los parametros de la Consulta
            PreparedStatement ps = con.prepareStatement(query);

            // Parametros requeridos

            ps.setString(1, name);
            ps.setString(2,email);
            ps.setString(3, password);
            ps.executeUpdate();

            //Mensaje  que se muestra si la consulta fue exitosa

            JOptionPane.showMessageDialog(null, "Usuario registrado con éxito");

        } catch (SQLException e) {

            //Mensaje que se muestra si la consulta no fue exitosa

            JOptionPane.showMessageDialog(null, "Error al registrar usuario: " + e.getMessage());
        } finally {
            // Cerrar la conexion con la BD
            closeConnection();
        }
    }


    //Loguearse como Usuario ya registrado

    public void loginUser(String userName, String password) {

        try {

            //Consulta a la BD

            String query = "SELECT email,idUsuario,contraseña FROM users WHERE nombre = ?";
            //Ingresar los parametros de la consulta requeridos

            PreparedStatement ps = con.prepareStatement(query);
            //Parametros
            ps.setString(1, userName);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Obtener la contraseña colocada en el form
                String contra = rs.getString("contraseña");


                // establecer el id del usuario actual

                int idUser = rs.getInt("idUsuario");
                ActualUser.idUser = idUser;

                // establecer el nombre del usuario actual
                ActualUser.userName = userName;

                // establecer el email del usuario actual
                String email = rs.getString("email");
                ActualUser.email = email;

                //comparar si la contraseña ingresada coincide con la registrada en la BD

                if (contra.equals(password)) {

                    // proceso despues de un login exitoso

                    //crear una instancia de la vista search para luego hacerla visible

                    Search sc = new Search();
                    sc.setVisible(true);



                    // metodo para mostrar todos los hoteles sin importar el user
                    //que los publicó

                    sc.getAllHotels();


                } else {

                    // Mensaje que se muestra si las contraseñas no coinciden
                    JOptionPane.showMessageDialog(null, "Contraseña incorrecta");
                }
            } else {
                // Si los usuarios no coinciden sale el siguientes mensaje

                JOptionPane.showMessageDialog(null, "Usuario no encontrado");
            }
        } catch (SQLException e) {
            // Mensaje que sale si sale algo mal con la consulta
            JOptionPane.showMessageDialog(null, "Error en el login: " + e.getMessage());

        } finally {
            //cerrar conexion
            closeConnection();
        }
    }

}
