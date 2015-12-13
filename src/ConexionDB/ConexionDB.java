package ConexionDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import Contacto.Contacto;
import java.sql.SQLException;

public class ConexionDB {

    private Connection connection = null;
    private ResultSet rs = null;
    private Statement s = null;
    private Contacto cont;

    public void Conexion() {

        if (connection != null) {
            return;
        }

        String url = "jdbc:postgresql://localhost:5432/AgendaInacap";
        String password = "vassilis18";
        try {
            Class.forName("org.postgresql.Driver");

            connection = DriverManager.getConnection(url, "postgres", password);

            if (connection != null) {
                System.out.println("Conectando a Base de Datos...");
            } else {
                creardb();
                Conexion();
            }
        } catch (Exception e) {
            System.out.println("Problemas de Conexión");
        }
    }

    public void creardb() {
        // JDBC driver name and database URL
    String JDBC_DRIVER = "org.postgresql.Driver";
    String DB_URL = "jdbc:postgresql://localhost:5432/";

    //  Database credentials
    
    String PASS = "vassilis18";

    
        Connection conn = null;
        Statement stmt = null;
        try {
            //STEP 2: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, "postgres", PASS);

            //STEP 4: Execute a query
            System.out.println("Creating database...");
            stmt = conn.createStatement();

            String sql = "CREATE DATABASE AgendaInacap";
            stmt.executeUpdate(sql);
            System.out.println("Database created successfully...");
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
    
}//end JDBCExample



    public int Insertar(String nom, int num) {
        int ret = 2;
        try {
            String nombre = nom;
            int numero = num;
            Conexion();
            s = connection.createStatement();
            int z = s.executeUpdate("INSERT INTO contacto (nombre,numero) VALUES ('" + nombre + "','" + numero + "')");
            if (z == 1) {
                ret = 1;
            } else {
                ret = 0;

            }
        } catch (Exception e) {
            System.out.println("Error de conexion");
            e.printStackTrace();
        }
        return ret;
    }

    public void Eliminar(String nom) {

        try {
            String nombre = nom;
            Conexion();
            s = connection.createStatement();
            int z = s.executeUpdate("DELETE FROM contacto WHERE nombre='" + nombre + "'");

            if (z == 1) {
                JOptionPane.showMessageDialog(null, "Contacto Eliminado");

            } else {
                JOptionPane.showMessageDialog(null, "Contacto inexistente");
            }

        } catch (Exception e) {
            System.out.println("Error de conexion");
            e.printStackTrace();
        }

    }

    public Contacto consultar(String n) {

        try {
            String name = n;
            Conexion();
            s = connection.createStatement();
            rs = s.executeQuery("SELECT nombre,numero FROM contacto WHERE nombre='" + name + "'");
        } catch (Exception e) {
            System.out.println("Problema Buscando La Base de Datos");
        }

        try {
            if (rs.next() == true) {
                cont = new Contacto(rs.getString(1), rs.getString(2));

            } else {
                JOptionPane.showMessageDialog(null, "Contacto inexistente");
            }

        } catch (Exception e) {
            System.out.println("Problema al imprimir la información.");
        }

        return cont;

    }

    public void Modificar(Contacto conta, String nom, int num) {

        try {
            cont = conta;
            String nombre = nom,asd="";
            int numero = num;

            if (nombre.compareTo(asd)==0) {
                nombre = cont.getNombre();
            }
            if (numero == 0) {
                numero = Integer.parseInt(cont.getNumero());
            }

            Conexion();
            s = connection.createStatement();
            int z = s.executeUpdate("UPDATE contacto set nombre = '" + nombre + "', numero = '" + numero + "' WHERE nombre='" + conta.getNombre() + "'");

            if (z == 1) {
                JOptionPane.showMessageDialog(null, "Contacto Modificado");

            } else {
                JOptionPane.showMessageDialog(null, "Contacto inexistente");
            }

        } catch (Exception e) {
            System.out.println("Error de conexion");
            e.printStackTrace();
        }

    }

}
