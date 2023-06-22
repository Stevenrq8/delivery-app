package org.delivery.modelo.dao;

import org.delivery.modelo.*;
import org.delivery.vista.VistaTablaCliente;

import javax.swing.*;
import java.sql.*;
import java.util.regex.*;

public class ClienteDAO extends Conexion {

    /**
     * Método para registrar un cliente en la base de datos
     *
     * @param cliente cliente a registrar
     * @return true si se registra correctamente, false si no
     */
    public boolean registrar(Cliente cliente) {
        PreparedStatement ps;
        Connection con = getConexion();
        // consulta para insertar los datos del cliente en la base de datos
        String sql = "INSERT INTO cliente (cedula_cliente, nombre, apellido, direccion, telefono, correo, contrasena) " +
                "VALUES(?,?,?,?,?,?,?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, String.valueOf(cliente.getCedula()));
            ps.setString(2, cliente.getNombre());
            ps.setString(3, cliente.getApellido());
            ps.setString(4, cliente.getDireccion());
            ps.setString(5, cliente.getTelefono());
            ps.setString(6, cliente.getCorreo());
            ps.setString(7, cliente.getContrasena());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al registrar cliente: " + e.getMessage());
            return false;
        }
    }

    /**
     * Método para buscar un cliente en la base de datos
     *
     * @param cedula cédula del cliente a buscar
     * @return true si encuentra un cliente con la cédula ingresada, false si no
     */
    public boolean buscar(long cedula) {
        PreparedStatement ps;
        ResultSet rs;
        Connection con = getConexion();
        // consulta para obtener los datos del cliente donde la cedula sea igual a la ingresada
        String sql = "SELECT * FROM cliente WHERE cedula_cliente = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, String.valueOf(cedula));
            rs = ps.executeQuery();
            return rs.next(); // retorna true si encuentra un cliente con la cedula ingresada
        } catch (SQLException e) {
            System.out.println("Error al buscar cliente: " + e.getMessage());
            return false;
        }
    }

    /**
     * Método para buscar un cliente en la base de datos
     *
     * @param cedula     cédula del cliente a buscar
     * @param telefono   teléfono del cliente a buscar
     * @param correo     correo del cliente a buscar
     * @param contrasena contraseña del cliente a buscar
     * @return true si se encuentra, false si no
     */
    public boolean buscar(long cedula, String telefono, String correo, String contrasena) {
        PreparedStatement ps;
        ResultSet rs;
        Connection con = getConexion();
        // consulta para obtener los datos del cliente donde los datos sean iguales a los ingresados en los parámetros
        String sql = "SELECT * FROM cliente WHERE cedula_cliente = ? OR telefono = ? OR correo = ? OR contrasena = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, String.valueOf(cedula));
            ps.setString(2, telefono);
            ps.setString(3, correo);
            ps.setString(4, contrasena);
            rs = ps.executeQuery();
            return rs.next(); // retorna true si encuentra un cliente
        } catch (SQLException e) {
            System.out.println("Error al buscar cliente: " + e.getMessage());
            return false;
        }
    }

    /**
     * Método para actualizar los datos de un cliente en la base de datos
     *
     * @param cliente cliente con los datos actualizados
     * @return true si se actualiza correctamente, false si no
     */
    public boolean actualizar(Cliente cliente) {
        PreparedStatement ps;
        Connection con = getConexion();
        // consulta para actualizar los datos del cliente de pedido en la base de datos
        String sql = "UPDATE cliente SET nombre=?, apellido=?, direccion=?, telefono=?, correo=?, contrasena=? " +
                "WHERE cedula_cliente=?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellido());
            ps.setString(3, cliente.getDireccion());
            ps.setString(4, cliente.getTelefono());
            ps.setString(5, cliente.getCorreo());
            ps.setString(6, cliente.getContrasena());
            ps.setString(7, String.valueOf(cliente.getCedula()));
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error al actualizar cliente: " + e.getMessage());
            return false;
        }
    }

    /**
     * Método para eliminar un cliente de la base de datos
     *
     * @param cedula cédula del cliente a eliminar
     * @return true si se elimina correctamente, false si no
     */
    public boolean eliminar(long cedula) {
        PreparedStatement ps;
        Connection con = getConexion();
        // consulta para eliminar los datos del cliente donde la cédula sea igual a la ingresada
        String sql = "DELETE FROM cliente WHERE cedula_cliente = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, String.valueOf(cedula));
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al eliminar cliente: " + e.getMessage());
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
     * @param cliente cliente que inicia sesión
     * @return true si la contraseña ingresada es igual a la de la base de datos, false si no
     */
    public boolean iniciarSesion(Cliente cliente) {
        PreparedStatement ps;
        ResultSet rs;
        Connection con = getConexion();
        // consulta para obtener los datos del cliente donde el correo sea igual al ingresado
        String sql = "SELECT * FROM cliente WHERE correo = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, cliente.getCorreo());
            rs = ps.executeQuery();
            if (rs.next()) { // si encuentra un cliente con el correo ingresado
                // si la contraseña ingresada es igual a la contraseña del cliente en la base de datos
                if (cliente.getContrasena().equals(rs.getString(7))) {
                    // se actualizan los datos del cliente con los de la base de datos
                    cliente.setCedula(rs.getLong(1));
                    cliente.setNombre(rs.getString(2));
                    cliente.setApellido(rs.getString(3));
                    cliente.setDireccion(rs.getString(4));
                    cliente.setTelefono(rs.getString(5));
                    cliente.setCorreo(rs.getString(6));
                    cliente.setContrasena(rs.getString(7));
                    return true;
                } else {
                    JOptionPane.showMessageDialog(null, "Contraseña incorrecta");
                    return false;
                }
            } else {
                JOptionPane.showMessageDialog(null, "El cliente no existe");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error al iniciar sesión: " + e.getMessage());
            return false;
        }
    }

    /**
     * Método para listar todos los clientes registrados en la base de datos
     *
     * @param colaCliente       cola donde guardan los clientes registrados en la base de datos
     * @param vistaTablaCliente tabla donde se van a mostrar los clientes registrados en la base de datos
     */
    public void listar(ColaCliente colaCliente, VistaTablaCliente vistaTablaCliente) {
        PreparedStatement ps;
        ResultSet rs;
        Connection con = getConexion();
        // consulta para obtener los datos de todos los clientes registrados en la base de datos
        String sql = "SELECT * FROM cliente";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) { // mientras existan clientes registrados en la base de datos
                Cliente cliente = new Cliente();
                cliente.setCedula(rs.getLong(1));
                cliente.setNombre(rs.getString(2));
                cliente.setApellido(rs.getString(3));
                cliente.setDireccion(rs.getString(4));
                cliente.setTelefono(rs.getString(5));
                cliente.setCorreo(rs.getString(6));
                cliente.setContrasena(rs.getString(7));
                colaCliente.agregar(cliente);
                colaCliente.agregarClienteTabla(vistaTablaCliente.getJtCliente());
            }
        } catch (SQLException e) {
            System.out.println("Error al listar clientes: " + e.getMessage());
        }
    }
}
