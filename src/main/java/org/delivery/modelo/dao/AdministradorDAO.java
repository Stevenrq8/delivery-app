package org.delivery.modelo.dao;

import org.delivery.modelo.*;
import org.delivery.vista.VistaTablaAdministrador;

import javax.swing.*;
import java.sql.*;
import java.util.regex.*;

public class AdministradorDAO extends Conexion {

    /**
     * Método para registrar un administrador en la base de datos
     *
     * @param administrador administrador a registrar
     * @return true si se registra correctamente, false si no
     */
    public boolean registrar(Administrador administrador) {
        PreparedStatement ps;
        Connection con = getConexion();
        // consulta para insertar los datos del administrador en la base de datos
        String sql = "INSERT INTO administrador (cedula_administrador, nombre, apellido, direccion, telefono, correo, contrasena) " +
                "VALUES(?,?,?,?,?,?,?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, String.valueOf(administrador.getCedula()));
            ps.setString(2, administrador.getNombre());
            ps.setString(3, administrador.getApellido());
            ps.setString(4, administrador.getDireccion());
            ps.setString(5, administrador.getTelefono());
            ps.setString(6, administrador.getCorreo());
            ps.setString(7, administrador.getContrasena());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al registrar administrador: " + e.getMessage());
            return false;
        }
    }

    /**
     * Método para buscar un administrador en la base de datos
     *
     * @param cedula cédula del administrador a buscar
     * @return true si se encuentra, false si no
     */
    public boolean buscar(long cedula) {
        PreparedStatement ps;
        ResultSet rs;
        Connection con = getConexion();
        // consulta para obtener los datos del administrador donde la cédula sea igual a la ingresada
        String sql = "SELECT * FROM administrador WHERE cedula_administrador = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, String.valueOf(cedula));
            rs = ps.executeQuery();
            return rs.next(); // retorna true si encuentra un administrador con la cédula ingresada
        } catch (SQLException e) {
            System.out.println("Error al buscar administrador: " + e.getMessage());
            return false;
        }
    }

    /**
     * Método para buscar un administrador en la base de datos
     *
     * @param cedula     cédula del administrador a buscar
     * @param telefono   teléfono del administrador a buscar
     * @param correo     correo del administrador a buscar
     * @param contrasena contraseña del administrador a buscar
     * @return true si se encuentra, false si no
     */
    public boolean buscar(long cedula, String telefono, String correo, String contrasena) {
        PreparedStatement ps;
        ResultSet rs;
        Connection con = getConexion();
        // consulta para obtener los datos del administrador donde los datos sean iguales a los ingresados en los parámetros
        String sql = "SELECT * FROM administrador WHERE cedula_administrador = ? OR telefono = ? OR correo = ? OR contrasena = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, String.valueOf(cedula));
            ps.setString(2, telefono);
            ps.setString(3, correo);
            ps.setString(4, contrasena);
            rs = ps.executeQuery();
            return rs.next(); // retorna true si encuentra un administrador
        } catch (SQLException e) {
            System.out.println("Error al buscar administrador: " + e.getMessage());
            return false;
        }
    }

    /**
     * Método para actualizar los datos de un administrador en la base de datos
     *
     * @param administrador administrador con los datos actualizados
     * @return true si se actualiza correctamente, false si no
     */
    public boolean actualizar(Administrador administrador) {
        PreparedStatement ps;
        Connection con = getConexion();
        // consulta para actualizar los datos del administrador en la base de datos
        String sql = "UPDATE administrador SET nombre=?, apellido=?, direccion=?, telefono=?, correo=?, contrasena=? WHERE cedula_administrador=?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, administrador.getNombre());
            ps.setString(2, administrador.getApellido());
            ps.setString(3, administrador.getDireccion());
            ps.setString(4, administrador.getTelefono());
            ps.setString(5, administrador.getCorreo());
            ps.setString(6, administrador.getContrasena());
            ps.setString(7, String.valueOf(administrador.getCedula()));
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error al actualizar administrador: " + e.getMessage());
            return false;
        }
    }

    /**
     * Método para eliminar un administrador de la base de datos
     *
     * @param cedula cédula del administrador a eliminar
     * @return true si se elimina correctamente, false si no
     */
    public boolean eliminar(long cedula) {
        PreparedStatement ps;
        Connection con = getConexion();
        // consulta para eliminar los datos del administrador donde la cédula sea igual a la ingresada
        String sql = "DELETE FROM administrador WHERE cedula_administrador = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, String.valueOf(cedula));
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al eliminar administrador: " + e.getMessage());
            return false;
        }
    }

    /**
     * Método para validar si el correo cumple con el patrón de un correo
     *
     * @param correo correo a validar
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
     * @param administrador administrador a iniciar sesión
     * @return true si inicia sesión correctamente, false si no
     */
    public boolean iniciarSesion(Administrador administrador) {
        PreparedStatement ps;
        ResultSet rs;
        Connection con = getConexion();
        // consulta para obtener los datos del administrador donde el correo sea igual al ingresado
        String sql = "SELECT * FROM administrador WHERE correo = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, administrador.getCorreo());
            rs = ps.executeQuery();
            if (rs.next()) { // si encuentra un administrador con el correo ingresado
                // si la contraseña ingresada es igual a la contraseña del administrador en la base de datos
                if (administrador.getContrasena().equals(rs.getString(7))) {
                    // se actualizan los datos del administrador con los de la base de datos
                    administrador.setCedula(rs.getLong(1));
                    administrador.setNombre(rs.getString(2));
                    administrador.setApellido(rs.getString(3));
                    administrador.setDireccion(rs.getString(4));
                    administrador.setTelefono(rs.getString(5));
                    administrador.setCorreo(rs.getString(6));
                    administrador.setContrasena(rs.getString(7));
                    return true;
                } else {
                    JOptionPane.showMessageDialog(null, "Contraseña incorrecta");
                    return false;
                }
            } else {
                JOptionPane.showMessageDialog(null, "El administrador no existe");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error al iniciar sesión: " + e.getMessage());
            return false;
        }
    }

    /**
     * Método para listar los administradores registrados en la base de datos y guardarlos en una cola
     *
     * @param colaAdministrador       cola donde se guardan los administradores registrados en la base de datos
     * @param vistaTablaAdministrador tabla donde se mostrarán los administradores registrados en la base de datos
     */
    public void listar(ColaAdministrador colaAdministrador, VistaTablaAdministrador vistaTablaAdministrador) {
        PreparedStatement ps;
        ResultSet rs;
        Connection con = getConexion();
        // consulta para obtener los datos de todos los administradores registrados en la base de datos
        String sql = "SELECT * FROM administrador";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) { // mientras existan administradores registrados en la base de datos
                Administrador administrador = new Administrador();
                administrador.setCedula(rs.getLong(1));
                administrador.setNombre(rs.getString(2));
                administrador.setApellido(rs.getString(3));
                administrador.setDireccion(rs.getString(4));
                administrador.setTelefono(rs.getString(5));
                administrador.setCorreo(rs.getString(6));
                administrador.setContrasena(rs.getString(7));
                colaAdministrador.agregar(administrador);
                colaAdministrador.agregarAdministradorTabla(vistaTablaAdministrador.getJtAdministrador());
            }
        } catch (SQLException e) {
            System.out.println("Error al listar administradores: " + e.getMessage());
        }
    }
}
