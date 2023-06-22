package org.delivery.modelo.dao;

import org.delivery.modelo.AdministradorPedido;
import org.delivery.modelo.ColaAdministradorPedido;
import org.delivery.modelo.Conexion;
import org.delivery.vista.VistaTablaAdministradorPedido;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdministradorPedidoDAO extends Conexion {

    /**
     * Método para registrar un administrador de pedido en la base de datos
     *
     * @param administradorPedido administrador de pedido a registrar
     * @return true si se registra correctamente, false si no
     */
    public boolean registrar(AdministradorPedido administradorPedido) {
        PreparedStatement ps;
        Connection con = getConexion();
        // consulta para insertar los datos del administrador de pedido en la base de datos
        String sql = "INSERT INTO administrador_pedido (cedula_administrador_pedido, nombre, apellido, direccion, telefono, correo, contrasena) " +
                "VALUES(?,?,?,?,?,?,?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, String.valueOf(administradorPedido.getCedula()));
            ps.setString(2, administradorPedido.getNombre());
            ps.setString(3, administradorPedido.getApellido());
            ps.setString(4, administradorPedido.getDireccion());
            ps.setString(5, administradorPedido.getTelefono());
            ps.setString(6, administradorPedido.getCorreo());
            ps.setString(7, administradorPedido.getContrasena());
            ps.execute();
            return true;
        } catch (Exception e) {
            System.out.println("Error al registrar administrador de pedido: " + e.getMessage());
            return false;
        }
    }

    /**
     * Método para buscar un administrador de pedido en la base de datos
     *
     * @param cedula cédula del administrador de pedido a buscar
     * @return true si se encuentra, false si no
     */
    public boolean buscar(long cedula) {
        PreparedStatement ps;
        ResultSet rs;
        Connection con = getConexion();
        // consulta para obtener los datos del administrador de pedido donde la cédula sea igual a la ingresada
        String sql = "SELECT * FROM administrador_pedido WHERE cedula_administrador_pedido = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, String.valueOf(cedula));
            rs = ps.executeQuery();
            return rs.next(); // retorna true si encuentra un administrador de pedido con la cédula ingresada
        } catch (Exception e) {
            System.out.println("Error al buscar administrador de pedido: " + e.getMessage());
            return false;
        }
    }

    /**
     * Método para buscar un administrador de pedido en la base de datos
     *
     * @param cedula     cédula del administrador de pedido a buscar
     * @param telefono   teléfono del administrador de pedido a buscar
     * @param correo     correo del administrador de pedido a buscar
     * @param contrasena contraseña del administrador de pedido a buscar
     * @return true si se encuentra, false si no
     */
    public boolean buscar(long cedula, String telefono, String correo, String contrasena) {
        PreparedStatement ps;
        ResultSet rs;
        Connection con = getConexion();
        // consulta para obtener los datos del administrador de pedido donde los datos sean iguales a los ingresados en los parámetros
        String sql = "SELECT * FROM administrador_pedido WHERE cedula_administrador_pedido = ? OR telefono = ? OR correo = ? OR contrasena = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, String.valueOf(cedula));
            ps.setString(2, telefono);
            ps.setString(3, correo);
            ps.setString(4, contrasena);
            rs = ps.executeQuery();
            return rs.next(); // retorna true si encuentra un administrador de pedido
        } catch (SQLException e) {
            System.out.println("Error al buscar administrador de pedido: " + e.getMessage());
            return false;
        }
    }

    /**
     * Método para actualizar los datos de un administrador de pedido en la base de datos
     *
     * @param administradorPedido administrador de pedido con los datos actualizados
     * @return true si se actualiza correctamente, false si no
     */
    public boolean actualizar(AdministradorPedido administradorPedido) {
        PreparedStatement ps;
        Connection con = getConexion();
        // consulta para actualizar los datos del administrador de pedido en la base de datos
        String sql = "UPDATE administrador_pedido SET nombre=?, apellido=?, direccion=?, telefono=?, correo=?, contrasena=? " +
                "WHERE cedula_administrador_pedido=?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, administradorPedido.getNombre());
            ps.setString(2, administradorPedido.getApellido());
            ps.setString(3, administradorPedido.getDireccion());
            ps.setString(4, administradorPedido.getTelefono());
            ps.setString(5, administradorPedido.getCorreo());
            ps.setString(6, administradorPedido.getContrasena());
            ps.setString(7, String.valueOf(administradorPedido.getCedula()));
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error al actualizar administrador de pedido: " + e.getMessage());
            return false;
        }
    }

    /**
     * Método para eliminar un administrador de pedido de la base de datos
     *
     * @param cedula cédula del administrador de pedido a eliminar
     * @return true si se elimina correctamente, false si no
     */
    public boolean eliminar(long cedula) {
        PreparedStatement ps;
        Connection con = getConexion();
        // consulta para eliminar los datos del administrador de pedido donde la cédula sea igual a la ingresada
        String sql = "DELETE FROM administrador_pedido WHERE cedula_administrador_pedido = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, String.valueOf(cedula));
            ps.execute();
            return true;
        } catch (Exception e) {
            System.out.println("Error al eliminar administrador de pedido: " + e.getMessage());
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
     * Método para que un administrador de pedido inicie sesión
     *
     * @param administradorPedido administrador de pedido que inicia sesión
     * @return true si inicia sesión correctamente, false si no
     */
    public boolean iniciarSesion(AdministradorPedido administradorPedido) {
        PreparedStatement ps;
        ResultSet rs;
        Connection con = getConexion();
        // consulta para obtener los datos del administrador de pedido donde el correo sea igual al ingresado
        String sql = "SELECT * FROM administrador_pedido WHERE correo = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, administradorPedido.getCorreo());
            rs = ps.executeQuery();
            if (rs.next()) { // si encuentra un administrador de pedido con el correo ingresado
                // si la contraseña ingresada es igual a la contraseña del administrador en la base de datos
                if (administradorPedido.getContrasena().equals(rs.getString(7))) {
                    // se actualizan los datos del administrador de pedido con los de la base de datos
                    administradorPedido.setCedula(rs.getLong(1));
                    administradorPedido.setNombre(rs.getString(2));
                    administradorPedido.setApellido(rs.getString(3));
                    administradorPedido.setDireccion(rs.getString(4));
                    administradorPedido.setTelefono(rs.getString(5));
                    administradorPedido.setCorreo(rs.getString(6));
                    administradorPedido.setContrasena(rs.getString(7));
                    return true;
                } else {
                    JOptionPane.showMessageDialog(null, "Contraseña incorrecta");
                    return false;
                }
            } else {
                JOptionPane.showMessageDialog(null, "El administrador de pedido no existe");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error al iniciar sesión: " + e.getMessage());
            return false;
        }
    }

    /**
     * método para listar todos los administradores de pedido registrados en la base de datos
     *
     * @param colaAdministradorPedido       cola donde se van a guardar los administradores de pedido registrados en la base de datos
     * @param vistaTablaAdministradorPedido tabla donde se van a mostrar los administradores de pedido registrados en la base de datos
     */
    public void listar(ColaAdministradorPedido colaAdministradorPedido, VistaTablaAdministradorPedido vistaTablaAdministradorPedido) {
        PreparedStatement ps;
        ResultSet rs;
        Connection con = getConexion();
        // consulta para obtener los datos de todos los administradores de pedido registrados en la base de datos
        String sql = "SELECT * FROM administrador_pedido";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) { // mientras existan administradores de pedido registrados en la base de datos
                AdministradorPedido administradorPedido = new AdministradorPedido();
                administradorPedido.setCedula(rs.getLong(1));
                administradorPedido.setNombre(rs.getString(2));
                administradorPedido.setApellido(rs.getString(3));
                administradorPedido.setDireccion(rs.getString(4));
                administradorPedido.setTelefono(rs.getString(5));
                administradorPedido.setCorreo(rs.getString(6));
                administradorPedido.setContrasena(rs.getString(7));
                colaAdministradorPedido.agregar(administradorPedido);
                colaAdministradorPedido.agregarAdministradorPedidoTabla(vistaTablaAdministradorPedido.getJtAdministradorPedido());
            }
        } catch (SQLException e) {
            System.out.println("Error al listar administradores de pedido: " + e.getMessage());
        }
    }
}
