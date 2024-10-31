package Controllers;

import BD.BDconnection;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProfileController extends BDconnection {

    public List<String[]> hotelesRecientes(int idUsuario) {
        List<String[]> hoteles = new ArrayList<>();

        // Conexión con la base de datos
        try {
            conector();

            // Consulta para obtener todos los posts
            String query = "SELECT * FROM posts WHERE idUsuario = ?";
            //Ingresar los parametros de la consulta requeridos

            //Parametros
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, idUsuario);

            try (ResultSet rs = ps.executeQuery()) {

                // Recorrer los resultados
                while (rs.next()) {
                    // Crear arreglo para cada hotel
                    String[] post2 = new String[5];
                    post2[0] = rs.getString("idPost");
                    post2[1] = rs.getString("titulo");
                    post2[2] = rs.getString("descripcion");
                    post2[3] = rs.getString("precio");
                    post2[4] = rs.getString("ubicacion");

                    // Añadir al listado
                    hoteles.add(post2);


                }
            }
        } catch (SQLException e) {
            // Mensaje de error por excepción en consulta
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error con la consulta: " + e.getMessage());
        } finally {
            // Asegurar que la conexión se cierra
            closeConnection();
        }
        return hoteles;
    }

}
