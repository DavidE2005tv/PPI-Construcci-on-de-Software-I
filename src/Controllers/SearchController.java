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

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SearchController extends BDconnection {
    public List<String[]> ubicationFilter(String ubicacion) {

        // Lista para almacenar los resultados

        List<String[]> foundHotels = new ArrayList<>();


        try {
            conector();

            //consulta para filtrar por ubicacion

            String query = "SELECT * FROM posts WHERE ubicacion = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, ubicacion);

            // Ejecutar la consulta
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                // Almacenar cada publicación en un arreglo
                // es como una caja que contiene los valores de cada post que va dentro de otra caja


                // el tamaño del vector es segun los campos de la BD

                String[] post = new String[4];
                // metodos para objetenr esos valores de cada celda o columna de la BD

                post[0] = rs.getString("idPost");
                post[1] = rs.getString("titulo");
                post[2] = rs.getString("descripcion");
                post[3] = rs.getString("precio");

                // agregar los datos obtenidos a el arreglo de hoteles encontrados para poder ser iterados
                foundHotels.add(post);
            }
        } catch (SQLException e) {
            // Mensaje de error por si la consulta sale mal
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error con la consulta: " + e.getMessage());
        } finally {
            // Cerrar la conexión con la BD
            closeConnection();
        }

        // Retornar la lista de hoteles encontrados
        return foundHotels;
    }

    public List<String[]> recentHotels() {
        List<String[]> hoteles = new ArrayList<>();

        // Conexión con la base de datos
        try {
            conector();

            // Consulta para obtener todos los posts
            String query = "SELECT * FROM posts";
            try (PreparedStatement ps = con.prepareStatement(query);
                 ResultSet rs = ps.executeQuery()) {

                // Recorrer los resultados
                while (rs.next()) {
                    // Crear arreglo para cada hotel
                    String[] post2 = new String[5];
                    post2[0] = rs.getString("idPost");
                    post2[1] = rs.getString("titulo");
                    post2[2] = rs.getString("descripcion");
                    post2[3] = rs.getString("precio");
                    post2[4] = rs.getString("idUsuario");

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
