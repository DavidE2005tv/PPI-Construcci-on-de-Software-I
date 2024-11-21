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
import java.sql.SQLException;

public class PostController extends BDconnection{

    //Crear una publicacion

    public void createPost(
            String title, String description,String ubication,String price, int idUser){
        try {
            conector();

            // consulta a la BD
            String query = "INSERT INTO posts (titulo,descripcion,ubicacion,precio,idUsuario) VALUES (?,?,?,?,?)";

            // procesar la consulta
            PreparedStatement ps = con.prepareStatement(query);

            // agregar parametros de la consulta
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, ubication);
            ps.setString(4, price);
            ps.setInt(5,idUser);

            // ejecutar la consulta
            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Publicacion creada con exito");
        }
        catch (SQLException e) {

            //Mensaje que se muestra si la consulta no fue exitosa

            JOptionPane.showMessageDialog(null, "Error al hacer la publicacion: " + e.getMessage());
        } finally {
            // Cerrar la conexion con la BD
            closeConnection();
        }
    }


    // Eliminar publicacion
    public void deletePost(int idPost){
        try{
            conector();

            // consulta a la BD
            String query  = "DELETE FROM posts WHERE idPost = ?";
            // preparar consulta
            PreparedStatement ps = con.prepareStatement(query);
            // indicar parametros
            ps.setInt(1, idPost);

            // Ejecutar la consulta
            int queryResult = ps.executeUpdate();

            // mensaje que se ve luego de eliminar un post

            JOptionPane.showMessageDialog(null,"Publicacion eliminada correctamente");
        }catch (SQLException e) {

            // Mensaje de error por si la consulta sale ma
            JOptionPane.showMessageDialog(null, "Error al eliminar la publicacion: " + e.getMessage());
        } finally {

            // Cerrar la conexion con la BD
            closeConnection();
        }

    }

    public void getAllPosts(){

        try{
            conector();

            // consulta  a la BD
            String query  = "SELECT * FROM posts";

            // preparar consulta
            PreparedStatement ps = con.prepareStatement(query);

            // ejecutar consulta
            ps.executeUpdate();

        }        catch (SQLException e) {

            // Mensaje de error por si la consulta sale ma
            JOptionPane.showMessageDialog(null, "Error al eliminar la publicacion: " + e.getMessage());
        } finally {

            // Cerrar la conexion con la BD
            closeConnection();
        }
    }

}
