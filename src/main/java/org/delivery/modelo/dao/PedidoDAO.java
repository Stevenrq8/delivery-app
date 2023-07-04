package org.delivery.modelo.dao;

import org.delivery.modelo.*;
import org.delivery.vista.VistaTablaPedido;

import java.sql.*;

public class PedidoDAO extends Conexion {

    /**
     * Método para registrar un pedido en la base de datos
     *
     * @param pedido pedido a registrar
     * @return true si se registra correctamente, false si no
     */
    public boolean registrar(Pedido pedido) {
        PreparedStatement ps;
        Connection con = getConexion();
        // consulta para insertar los datos del pedido en la base de datos
        String sql = "INSERT INTO pedido (numero_pedido, fecha, nombre_cliente, direccion, nombre_producto, cantidad, precio_unitario, total, estado) " +
                "VALUES(?,?,?,?,?,?,?,?,?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, String.valueOf(pedido.getNumeroPedido()));
            ps.setString(2, pedido.getFecha());
            ps.setString(3, pedido.getCliente().getNombre() + " " + pedido.getCliente().getApellido());
            ps.setString(4, pedido.getDireccion());
            ps.setString(5, String.valueOf(pedido.getProducto().getNombre()));
            ps.setString(6, String.valueOf(pedido.getCantidad()));
            ps.setString(7, String.valueOf(pedido.getPrecioUnitario()));
            ps.setString(8, String.valueOf(pedido.getTotal()));
            ps.setString(9, pedido.getEstado());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al registrar pedido: " + e.getMessage());
            return false;
        }
    }

    /**
     * Método para buscar un pedido en la base de datos
     *
     * @param numeroPedido número del pedido a buscar
     * @return true si se encuentra, false si no
     */
    public boolean buscar(long numeroPedido) {
        PreparedStatement ps;
        ResultSet rs;
        Connection con = getConexion();
        // consulta para obtener los datos del pedido donde el número del pedido sea igual al ingresado
        String sql = "SELECT * FROM pedido WHERE numero_pedido = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, String.valueOf(numeroPedido));
            rs = ps.executeQuery();
            return rs.next(); // retorna true si encuentra un pedido con el número de pedido ingresado
        } catch (SQLException e) {
            System.out.println("Error al buscar pedido: " + e.getMessage());
            return false;
        }
    }

    /**
     * Método para actualizar un pedido en la base de datos
     *
     * @param pedido pedido a actualizar
     * @return true si se actualiza correctamente, false si no
     */
    public boolean actualizar(Pedido pedido) {
        PreparedStatement ps;
        Connection con = getConexion();
        // consulta para actualizar los datos del pedido en la base de datos
        String sql = "UPDATE pedido SET fecha=?, nombre_cliente=?, direccion=?, nombre_producto=?, cantidad=?, precio_unitario=?, total=?, estado=? " +
                "WHERE numero_pedido=?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, pedido.getFecha());
            ps.setString(2, pedido.getCliente().getNombre() + " " + pedido.getCliente().getApellido());
            ps.setString(3, pedido.getDireccion());
            ps.setString(4, String.valueOf(pedido.getProducto().getNombre()));
            ps.setString(5, String.valueOf(pedido.getCantidad()));
            ps.setString(6, String.valueOf(pedido.getPrecioUnitario()));
            ps.setString(7, String.valueOf(pedido.getTotal()));
            ps.setString(8, pedido.getEstado());
            ps.setString(9, String.valueOf(pedido.getNumeroPedido()));
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error al actualizar pedido: " + e.getMessage());
            return false;
        }
    }

    /**
     * Método para eliminar un pedido de la base de datos
     *
     * @param numeroPedido número del pedido a eliminar
     * @return true si se elimina correctamente, false si no
     */
    public boolean eliminar(long numeroPedido) {
        PreparedStatement ps;
        Connection con = getConexion();
        // consulta para eliminar los datos del pedido donde el número del pedido sea igual al ingresado
        String sql = "DELETE FROM pedido WHERE numero_pedido = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, String.valueOf(numeroPedido));
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al eliminar pedido: " + e.getMessage());
            return false;
        }
    }

    /**
     * Método para listar todos los pedidos registrados en la base de datos
     *
     * @param colaPedido       cola donde se guardan los pedidos registrados en la base de datos
     * @param vistaTablaPedido tabla donde se muestran los pedidos registrados en la base de datos
     */
    public void listar(ColaPedido colaPedido, VistaTablaPedido vistaTablaPedido) {
        PreparedStatement ps;
        ResultSet rs;
        Connection con = getConexion();
        // consulta para obtener los datos de todos los pedidos registrados en la base de datos
        String sql = "SELECT * FROM pedido";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) { // mientras existan pedidos registrados en la base de datos
                Pedido pedido = new Pedido();
                Cliente cliente = new Cliente();
                Producto producto = new Producto();

                pedido.setNumeroPedido(rs.getLong(1));
                pedido.setFecha(rs.getString(2));
                cliente.setNombre(rs.getString(3)); // se obtiene el nombre del cliente
                pedido.setCliente(cliente); // se guarda el nombre del cliente en el pedido
                cliente.setDireccion(rs.getString(4));
                pedido.setDireccion(cliente.getDireccion());
                producto.setNombre(rs.getString(5));
                pedido.setProducto(producto);
                pedido.setCantidad(rs.getInt(6));
                pedido.setPrecioUnitario(rs.getDouble(7));
                pedido.setTotal(rs.getDouble(8));
                pedido.setEstado(rs.getString(9));
                colaPedido.agregar(pedido);
                colaPedido.agregarPedidoTabla(vistaTablaPedido.getJtPedido());
            }
        } catch (SQLException e) {
            System.out.println("Error al listar pedidos: " + e.getMessage());
        }
    }
}
