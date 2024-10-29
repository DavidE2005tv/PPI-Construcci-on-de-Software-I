package BD;

import componentes.LogInPage;
import componentes.Search;
import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Querys  extends BDconnection{
    public void cerrarConexion() {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cerrar la conexión: " + e.getMessage());
        }
    }


    // Registrar un Usuario

    public void registrarUsuario(String nombre,String email, String contraseña) {
        try {

            conector();

            // Consulta a la BD

            String query = "INSERT INTO users (nombre,email,contraseña) VALUES (?,?,?)";

            // Ingresar los parametros de la Consulta
            PreparedStatement ps = con.prepareStatement(query);

            // Parametros requeridos

            ps.setString(1, nombre);
            ps.setString(2,email);
            ps.setString(3, contraseña);
            ps.executeUpdate();

            //Mensaje  que se muestra si la consulta fue exitosa

            JOptionPane.showMessageDialog(null, "Usuario registrado con éxito");

        } catch (SQLException e) {

            //Mensaje que se muestra si la consulta no fue exitosa

            JOptionPane.showMessageDialog(null, "Error al registrar usuario: " + e.getMessage());
        } finally {
            // Cerrar la conexion con la BD
            cerrarConexion();
        }
    }


    //Loguearse como Usuario ya registrado

    public void loginUsuario(String nombreUsuario, String contraseña) {
        try {

            //Consulta a la BD

            String query = "SELECT contraseña FROM users WHERE nombre = ?";
            //Ingresar los parametros de la consulta requeridos

            PreparedStatement ps = con.prepareStatement(query);
            //Parametros
            ps.setString(1, nombreUsuario);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Obtener la contraseña colocada en el form
                String contra = rs.getString("contraseña");

                //comparar si la contraseña ingresada coincide con la registrada en la BD

                if (contra.equals(contraseña)) {
                    // proceso despues de un login exitoso
                    // paso a la ventana de busquedas
                    Search search = new Search();
                    search.setVisible(true);
                    search.mostrarHoteles();
                    Querys bd = new Querys();




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
            cerrarConexion();
        }
    }


    //Crear una publicacion

    public void crearPost(
            String titulo, String descripcion,String ubicacion,String precio){
        try {
            conector();
            String query = "INSERT INTO posts (titulo,descripcion,ubicacion,precio) VALUES (?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, titulo);
            ps.setString(2, descripcion);
            ps.setString(3, ubicacion);
            ps.setString(4, precio);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Publicacion creada con exito");
        }
        catch (SQLException e) {

            //Mensaje que se muestra si la consulta no fue exitosa

            JOptionPane.showMessageDialog(null, "Error al hacer la publicacion: " + e.getMessage());
        } finally {
            // Cerrar la conexion con la BD
            cerrarConexion();
        }
    }


    // Eliminar publicacion
    public void eliminarPost(int idPost){
        try{
            conector();
            String query  = "DELETE FROM posts WHERE idPost = ?";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, idPost);

            // Ejecutar la consulta
            int resultado = ps.executeUpdate();

            // mensaje que se ve luego de eliminar un post

            JOptionPane.showMessageDialog(null,"Publicacion eliminada correctamente");
        }catch (SQLException e) {

            // Mensaje de error por si la consulta sale ma
            JOptionPane.showMessageDialog(null, "Error al eliminar la publicacion: " + e.getMessage());
        } finally {

            // Cerrar la conexion con la BD
            cerrarConexion();
        }

    }

    public void obtenerTodosLosPost(){

        try{
            conector();
            String query  = "SELECT * FROM posts";
            PreparedStatement ps = con.prepareStatement(query);
            ps.executeUpdate();

        }        catch (SQLException e) {

            // Mensaje de error por si la consulta sale ma
            JOptionPane.showMessageDialog(null, "Error al eliminar la publicacion: " + e.getMessage());
        } finally {

            // Cerrar la conexion con la BD
            cerrarConexion();
        }
    }

    public List<String[]> filtrarUbicacion(String ubicacion) {

        // Lista para almacenar los resultados

        List<String[]> hotelesEncontrados = new ArrayList<>();


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
                hotelesEncontrados.add(post);
            }
        } catch (SQLException e) {
            // Mensaje de error por si la consulta sale mal
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error con la consulta: " + e.getMessage());
        } finally {
            // Cerrar la conexión con la BD
            cerrarConexion();
        }

        // Retornar la lista de hoteles encontrados
        return hotelesEncontrados;
    }

    public List<String[]> hotelesRecientes() {
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
                    String[] post2 = new String[4];
                    post2[0] = rs.getString("idPost");
                    post2[1] = rs.getString("titulo");
                    post2[2] = rs.getString("descripcion");
                    post2[3] = rs.getString("precio");

                    // Añadir al listado
                    hoteles.add(post2);
                }
            }
        } catch (SQLException e) {
            // Mensaje de error por excepción en consulta
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error con la consulta: " + e.getMessage());
        } finally {
            // Asegurar que la conexión se cierra
            cerrarConexion();
        }

        return hoteles;
    }



}
