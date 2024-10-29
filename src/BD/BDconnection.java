package BD;

import java.sql.*;
import javax.swing.JOptionPane;

public class BDconnection{

    public static final String URL = "jdbc:mysql://localhost:3306/ppi";
    protected static Connection con;

    private static final String driver = "com.mysql.jdbc.Driver";
    private static final  String user = "root";
    private static final String pass = "20070131";
    private static final String url = "jdbc:mysql://localhost:3306/ppi";

    public void conector(){
        con = null;
        try{
            Class.forName(driver);
            con = (Connection)DriverManager.getConnection(url, user, pass);
        }
        catch(ClassNotFoundException | SQLException e){
            JOptionPane.showMessageDialog(null, "Error de conexion " + e.getMessage());
        }
    }
}
