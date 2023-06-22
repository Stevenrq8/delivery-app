package org.delivery.modelo.dao;

import org.delivery.modelo.*;
import org.delivery.vista.VistaTablaRepartidor;

import javax.swing.*;
import java.sql.*;
import java.util.regex.*;

public class RepartidorDAO extends Conexion {

    /**
     * Método para registrar un repartidor en la base de datos
     *
     * @param repartidor repartidor a registrar
     * @return true si se registra correctamente, false si no
     */
    public boolean registrar(Repartidor repartidor) {
        PreparedStatement ps;
        Connection con = getConexion();
        // consulta para insertar los datos del repartidor en la base de datos
        String sql = "INSERT INTO repartidor (cedula_repartidor, nombre, apellido, direccion, telefono, tipo_vehiculo, correo, contrasena) " +
                "VALUES(?,?,?,?,?,?,?,?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, String.valueOf(repartidor.getCedula()));
            ps.setString(2, repartidor.getNombre());
            ps.setString(3, repartidor.getApellido());
            ps.setString(4, repartidor.getDireccion());
            ps.setString(5, repartidor.getTelefono());
            ps.setString(6, repartidor.getTipoVehiculo());
            ps.setString(7, repartidor.getCorreo());
            ps.setString(8, repartidor.getContrasena());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al registrar repartidor: " + e.getMessage());
            return false;
        }
    }

    /**
     * Método para buscar un repartidor en la base de datos
     *
     * @param cedula cedula del repartidor a buscar
     * @return true si encuentra un repartidor con la cédula ingresada, false si no
     */
    public boolean buscar(long cedula) {
        PreparedStatement ps;
        ResultSet rs;
        Connection con = getConexion();
        // consulta para obtener los datos del repartidor donde la cedula sea igual a la ingresada
        String sql = "SELECT * FROM repartidor WHERE cedula_repartidor = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, String.valueOf(cedula));
            rs = ps.executeQuery();
            return rs.next(); // retorna true si encuentra un repartidor con la cedula ingresada
        } catch (SQLException e) {
            System.out.println("Error al buscar repartidor: " + e.getMessage());
            return false;
        }
    }

    /**
     * Método para buscar un repartidor en la base de datos
     *
     * @param cedula     cédula del repartidor a buscar
     * @param telefono   teléfono del repartidor a buscar
     * @param correo     correo del repartidor a buscar
     * @param contrasena contraseña del repartidor a buscar
     * @return true si se encuentra, false si no
     */
    public boolean buscar(long cedula, String telefono, String correo, String contrasena) {
        PreparedStatement ps;
        ResultSet rs;
        Connection con = getConexion();
        // consulta para obtener los datos del repartidor donde los datos sean iguales a los ingresados en los parámetros
        String sql = "SELECT * FROM repartidor WHERE cedula_repartidor = ? OR telefono = ? OR correo = ? OR contrasena = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, String.valueOf(cedula));
            ps.setString(2, telefono);
            ps.setString(3, correo);
            ps.setString(4, contrasena);
            rs = ps.executeQuery();
            return rs.next(); // retorna true si encuentra un repartidor
        } catch (SQLException e) {
            System.out.println("Error al buscar administrador: " + e.getMessage());
            return false;
        }
    }

    /**
     * Método para actualizar los datos de un repartidor en la base de datos
     *
     * @param repartidor repartidor a actualizar
     * @return true si se actualiza correctamente, false si no
     */
    public boolean actualizar(Repartidor repartidor) {
        PreparedStatement ps;
        Connection con = getConexion();
        // consulta para actualizar los datos del repartidor de pedido en la base de datos
        String sql = "UPDATE repartidor SET nombre=?, apellido=?, direccion=?, telefono=?, tipo_vehiculo=?, correo=?, contrasena=? " +
                "WHERE cedula_repartidor=?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, repartidor.getNombre());
            ps.setString(2, repartidor.getApellido());
            ps.setString(3, repartidor.getDireccion());
            ps.setString(4, repartidor.getTelefono());
            ps.setString(5, repartidor.getTipoVehiculo());
            ps.setString(6, repartidor.getCorreo());
            ps.setString(7, repartidor.getContrasena());
            ps.setString(8, String.valueOf(repartidor.getCedula()));
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error al actualizar repartidor: " + e.getMessage());
            return false;
        }
    }

    /**
     * Método para eliminar un repartidor de la base de datos
     *
     * @param cedula cedula del repartidor a eliminar
     * @return true si se elimina correctamente, false si no
     */
    public boolean eliminar(long cedula) {
        PreparedStatement ps;
        Connection con = getConexion();
        // consulta para eliminar los datos del repartidor donde la cedula sea igual a la ingresada
        String sql = "DELETE FROM repartidor WHERE cedula_repartidor = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, String.valueOf(cedula));
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al eliminar repartidor: " + e.getMessage());
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
     * Método para que un administrador inicie sesión en el sistema comparando la contraseña ingresada
     * con la de la base de datos
     *
     * @param repartidor repartidor que inicia sesión
     * @return true si la contraseña ingresada es igual a la de la base de datos, false si no
     */
    public boolean iniciarSesion(Repartidor repartidor) {
        PreparedStatement ps;
        ResultSet rs;
        Connection con = getConexion();
        // consulta para obtener los datos del repartidor donde el correo sea igual al ingresado
        String sql = "SELECT * FROM repartidor WHERE correo = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, repartidor.getCorreo());
            rs = ps.executeQuery();
            if (rs.next()) { // si encuentra un repartidor con el correo ingresado
                // si la contraseña ingresada es igual a la contraseña del repartidor en la base de datos
                if (repartidor.getContrasena().equals(rs.getString(8))) {
                    // se actualizan los datos del repartidor con los de la base de datos
                    repartidor.setCedula(rs.getLong(1));
                    repartidor.setNombre(rs.getString(2));
                    repartidor.setApellido(rs.getString(3));
                    repartidor.setDireccion(rs.getString(4));
                    repartidor.setTelefono(rs.getString(5));
                    repartidor.setTipoVehiculo(rs.getString(6));
                    repartidor.setCorreo(rs.getString(7));
                    repartidor.setContrasena(rs.getString(8));
                    return true;
                } else {
                    JOptionPane.showMessageDialog(null, "Contraseña incorrecta");
                    return false;
                }
            } else {
                JOptionPane.showMessageDialog(null, "El repartidor no existe");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error al iniciar sesión: " + e.getMessage());
            return false;
        }
    }

    /**
     * Método para listar todos los repartidores registrados en la base de datos
     *
     * @param colaRepartidor       cola donde se guardan los repartidores registrados en la base de datos
     * @param vistaTablaRepartidor tabla donde se muestran los repartidores registrados en la base de datos
     */
    public void listar(ColaRepartidor colaRepartidor, VistaTablaRepartidor vistaTablaRepartidor) {
        PreparedStatement ps;
        ResultSet rs;
        Connection con = getConexion();
        // consulta para obtener los datos de todos los administradores registrados en la base de datos
        String sql = "SELECT * FROM repartidor";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) { // mientras existan administradores registrados en la base de datos
                Repartidor repartidor = new Repartidor();
                repartidor.setCedula(rs.getLong(1));
                repartidor.setNombre(rs.getString(2));
                repartidor.setApellido(rs.getString(3));
                repartidor.setDireccion(rs.getString(4));
                repartidor.setTelefono(rs.getString(5));
                repartidor.setTipoVehiculo(rs.getString(6));
                repartidor.setCorreo(rs.getString(7));
                repartidor.setContrasena(rs.getString(8));
                colaRepartidor.agregar(repartidor);
                colaRepartidor.agregarRepartidorTabla(vistaTablaRepartidor.getJtRepartidor());
            }
        } catch (SQLException e) {
            System.out.println("Error al listar administradores: " + e.getMessage());
        }
    }
}
