package ConexionDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import Contacto.Contacto;

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
            }
        } catch (Exception e) {
            System.out.println("Problemas de Conexión");
        }
    }

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
