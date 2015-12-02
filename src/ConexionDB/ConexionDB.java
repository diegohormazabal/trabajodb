package ConexionDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class ConexionDB {

    private Connection connection = null;
    private ResultSet rs = null;
    private Statement s = null;

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
            }
        } catch (Exception e) {
            System.out.println("Problemas de Conexión");
        }
    }

    public int Insertar(String nom,int num) {
        int ret=2;
        try {
            String nombre = nom;
            int numero = num;
            Conexion();
            s = connection.createStatement();
            int z = s.executeUpdate("INSERT INTO contacto (nombre,numero) VALUES ('" + nombre + "','" + numero + "')");
            if (z == 1) {
                ret=1;
            } else {
                ret=0;

            }
        } catch (Exception e) {
            System.out.println("Error de conexion");
            e.printStackTrace();
        }
        return ret;
    }

    public void consultar(String n) {

        try {
            String name = n;
            Conexion();
            s = connection.createStatement();
            rs = s.executeQuery("SELECT nombre FROM contacto WHERE nombre='" + name + "'");
        } catch (Exception e) {
            System.out.println("Problema Buscando La Base de Datos");
        }

        // IMPRIMIR RESULTADO
        String string = "";

        try {
            while (rs.next()) {

                string += rs.getString(1) + "\n";
                JOptionPane.showMessageDialog(null, string);
                string = "";
                
            }
        } catch (Exception e) {
            System.out.println("Problema al imprimir la información.");
        }

    }

}

