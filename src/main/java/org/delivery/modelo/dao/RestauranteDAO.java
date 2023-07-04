package org.delivery.modelo.dao;

import org.delivery.modelo.*;
import org.delivery.vista.VistaTablaRestaurante;

import javax.swing.*;
import java.sql.*;
import java.util.regex.*;

public class RestauranteDAO extends Conexion {

    /**
     * Método para registrar un restaurante en la base de datos
     *
     * @param restaurante restaurante a registrar
     * @return true si se registra correctamente, false si no
     */
    public boolean registrar(Restaurante restaurante) {
        PreparedStatement ps;
        Connection con = getConexion();
        // consulta para insertar los datos del restaurante en la base de datos
        String sql = "INSERT INTO restaurante (nit_restaurante, nombre, direccion, telefono, correo, contrasena) " +
                "VALUES(?,?,?,?,?,?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, String.valueOf(restaurante.getNit()));
            ps.setString(2, restaurante.getNombre());
            ps.setString(3, restaurante.getDireccion());
            ps.setString(4, restaurante.getTelefono());
            ps.setString(5, restaurante.getCorreo());
            ps.setString(6, restaurante.getContrasena());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al registrar restaurante: " + e.getMessage());
            return false;
        }
    }

    /**
     * Método para buscar un restaurante en la base de datos
     *
     * @param nit nit del restaurante a buscar
     * @return true si encuentra un restaurante con el nit ingresado, false si no
     */
    public boolean buscar(long nit) {
        PreparedStatement ps;
        ResultSet rs;
        Connection con = getConexion();
        // consulta para obtener los datos del restaurante donde el nit sea igual al ingresado
        String sql = "SELECT * FROM restaurante WHERE nit_restaurante = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, String.valueOf(nit));
            rs = ps.executeQuery();
            return rs.next(); // retorna true si encuentra un restaurante con el nit ingresado
        } catch (SQLException e) {
            System.out.println("Error al buscar restaurante: " + e.getMessage());
            return false;
        }
    }

    /**
     * Método para buscar un restaurante en la base de datos
     *
     * @param nit        nit del restaurante a buscar
     * @param nombre     nombre del restaurante a buscar
     * @param telefono   teléfono del restaurante a buscar
     * @param correo     correo del restaurante a buscar
     * @param contrasena contraseña del restaurante a buscar
     * @return true si se encuentra, false si no
     */
    public boolean buscar(long nit, String nombre, String telefono, String correo, String contrasena) {
        PreparedStatement ps;
        ResultSet rs;
        Connection con = getConexion();
        // consulta para obtener los datos del administrador donde los datos sean iguales a los ingresados en los parámetros
        String sql = "SELECT * FROM restaurante WHERE nit_restaurante = ? OR nombre = ? OR telefono = ? OR correo = ? OR contrasena = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, String.valueOf(nit));
            ps.setString(2, nombre);
            ps.setString(3, telefono);
            ps.setString(4, correo);
            ps.setString(5, contrasena);
            rs = ps.executeQuery();
            return rs.next(); // retorna true si encuentra un restaurante
        } catch (SQLException e) {
            System.out.println("Error al buscar restaurante: " + e.getMessage());
            return false;
        }
    }

    /**
     * Método para actualizar los datos de un restaurante en la base de datos
     *
     * @param restaurante restaurante a actualizar
     * @return true si se actualiza correctamente, false si no
     */
    public boolean actualizar(Restaurante restaurante) {
        PreparedStatement ps;
        Connection con = getConexion();
        // consulta para actualizar los datos del restaurante en la base de datos
        String sql = "UPDATE restaurante SET nombre=?, direccion=?, telefono=?, correo=?, contrasena=? WHERE nit_restaurante=?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, restaurante.getNombre());
            ps.setString(2, restaurante.getDireccion());
            ps.setString(3, restaurante.getTelefono());
            ps.setString(4, restaurante.getCorreo());
            ps.setString(5, restaurante.getContrasena());
            ps.setString(6, String.valueOf(restaurante.getNit()));
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error al actualizar restaurante: " + e.getMessage());
            return false;
        }
    }

    /**
     * Método para eliminar un restaurante de la base de datos
     *
     * @param nit nit del restaurante a eliminar
     * @return true si se elimina correctamente, false si no
     */
    public boolean eliminar(long nit) {
        PreparedStatement ps;
        Connection con = getConexion();
        // consulta para eliminar los datos del administrador donde el nit sea igual al ingresado
        String sql = "DELETE FROM restaurante WHERE nit_restaurante = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, String.valueOf(nit));
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al eliminar restaurante: " + e.getMessage());
            return false;
        }
    }

    /**
     * Método para validar si el correo cumple con el patrón de un correo
     *
     * @param correo Correo a validar
     * @return true si cumple con el patrón, false si no
     */
    public boolean validarCorreo(String correo) {
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
                "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher mather = pattern.matcher(correo); // compara el correo ingresado con el patrón
        return mather.find();
    }

    /**
     * Método para que un restaurante inicie sesión en el sistema comparando la contraseña ingresada
     * con la de la base de datos
     *
     * @param restaurante restaurante que inicia sesión
     * @return true si la contraseña ingresada es igual a la de la base de datos, false si no
     */
    public boolean iniciarSesion(Restaurante restaurante) {
        PreparedStatement ps;
        ResultSet rs;
        Connection con = getConexion();
        // consulta para obtener los datos del restaurante donde el correo sea igual al ingresado
        String sql = "SELECT * FROM restaurante WHERE correo = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, restaurante.getCorreo());
            rs = ps.executeQuery();
            if (rs.next()) { // si encuentra un restaurante con el correo ingresado
                // si la contraseña ingresada es igual a la contraseña del restaurante en la base de datos
                if (restaurante.getContrasena().equals(rs.getString(6))) {
                    // se actualizan los datos del restaurante con los de la base de datos
                    restaurante.setNit(rs.getLong(1));
                    restaurante.setNombre(rs.getString(2));
                    restaurante.setDireccion(rs.getString(3));
                    restaurante.setTelefono(rs.getString(4));
                    restaurante.setCorreo(rs.getString(5));
                    restaurante.setContrasena(rs.getString(6));
                    return true;
                } else {
                    JOptionPane.showMessageDialog(null, "Contraseña incorrecta");
                    return false;
                }
            } else {
                JOptionPane.showMessageDialog(null, "El restaurante no existe");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error al iniciar sesión: " + e.getMessage());
            return false;
        }
    }

    /**
     * Método para listar todos los restaurantes registrados en la base de datos
     *
     * @param colaRestaurante       cola donde se van a guardar los restaurantes registrados en la base de datos
     * @param vistaTablaRestaurante tabla donde se van a mostrar los restaurantes registrados en la base de datos
     */
    public void listar(ColaRestaurante colaRestaurante, VistaTablaRestaurante vistaTablaRestaurante) {
        PreparedStatement ps;
        ResultSet rs;
        Connection con = getConexion();
        // consulta para obtener los datos de todos los restaurantes registrados en la base de datos
        String sql = "SELECT * FROM restaurante";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) { // mientras existan restaurantes registrados en la base de datos
                Restaurante restaurante = new Restaurante();
                restaurante.setNit(rs.getLong(1));
                restaurante.setNombre(rs.getString(2));
                restaurante.setDireccion(rs.getString(3));
                restaurante.setTelefono(rs.getString(4));
                restaurante.setCorreo(rs.getString(5));
                restaurante.setContrasena(rs.getString(6));
                colaRestaurante.agregar(restaurante);
                colaRestaurante.agregarRestauranteTabla(vistaTablaRestaurante.getJtRestaurante());
            }
        } catch (SQLException e) {
            System.out.println("Error al listar restaurantes: " + e.getMessage());
        }
    }
}
