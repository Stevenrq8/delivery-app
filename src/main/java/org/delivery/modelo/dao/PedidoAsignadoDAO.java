package org.delivery.modelo.dao;

import org.delivery.modelo.*;
import org.delivery.vista.VistaPrincipalRepartidor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PedidoAsignadoDAO extends Conexion {

    /**
     * Método para registrar un pedido asignado en la base de datos
     *
     * @param pedidoAsignado pedido asignado a registrar
     * @return true si se registra correctamente, false si no
     */
    public boolean registrar(PedidoAsignado pedidoAsignado) {
        PreparedStatement ps;
        Connection con = getConexion();
        // consulta para insertar los datos del pedido asignado en la base de datos
        String sql = "INSERT INTO pedido_asignado (numero_pedido_asignado, fecha, nombre_repartidor, nombre_cliente, direccion, nombre_producto, cantidad, precio_unitario, total, estado) " +
                "VALUES(?,?,?,?,?,?,?,?,?,?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, String.valueOf(pedidoAsignado.getNumeroPedido()));
            ps.setString(2, pedidoAsignado.getFecha());
            ps.setString(3, pedidoAsignado.getRepartidor().getNombre() + " " + pedidoAsignado.getRepartidor().getApellido());
            ps.setString(4, pedidoAsignado.getCliente().getNombre() + " " + pedidoAsignado.getCliente().getApellido());
            ps.setString(5, pedidoAsignado.getDireccion());
            ps.setString(6, String.valueOf(pedidoAsignado.getProducto().getNombre()));
            ps.setString(7, String.valueOf(pedidoAsignado.getCantidad()));
            ps.setString(8, String.valueOf(pedidoAsignado.getPrecioUnitario()));
            ps.setString(9, String.valueOf(pedidoAsignado.getTotal()));
            ps.setString(10, pedidoAsignado.getEstado());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al registrar pedido asignado: " + e.getMessage());
            return false;
        }
    }

    /**
     * Método para buscar un pedido asignado en la base de datos
     *
     * @param numeroPedidoAsignado número del pedido asignado a buscar
     * @return true si se encuentra, false si no
     */
    public boolean buscar(long numeroPedidoAsignado) {
        PreparedStatement ps;
        ResultSet rs;
        Connection con = getConexion();
        // consulta para obtener los datos del pedido asignado donde el número del pedido sea igual al ingresado
        String sql = "SELECT * FROM pedido_asignado WHERE numero_pedido_asignado = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, String.valueOf(numeroPedidoAsignado));
            rs = ps.executeQuery();
            return rs.next(); // retorna true si encuentra un pedido asignado con el número de pedido ingresado
        } catch (SQLException e) {
            System.out.println("Error al buscar pedido asignado: " + e.getMessage());
            return false;
        }
    }

    /**
     * Método para actualizar un pedido signado en la base de datos
     *
     * @param pedidoAsignado pedido asignado a actualizar
     * @return true si se actualiza correctamente, false si no
     */
    public boolean actualizar(PedidoAsignado pedidoAsignado) {
        PreparedStatement ps;
        Connection con = getConexion();
        // consulta para actualizar los datos del pedido asignado en la base de datos
        String sql = "UPDATE pedido_asignado SET fecha=?, nombre_repartidor=?, nombre_cliente=?, direccion=?, nombre_producto=?, cantidad=?, precio_unitario=?, total=?, estado=? " +
                "WHERE numero_pedido_asignado=?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, pedidoAsignado.getFecha());
            ps.setString(2, pedidoAsignado.getRepartidor().getNombre() + " " + pedidoAsignado.getRepartidor().getApellido());
            ps.setString(3, pedidoAsignado.getCliente().getNombre() + " " + pedidoAsignado.getCliente().getApellido());
            ps.setString(4, pedidoAsignado.getDireccion());
            ps.setString(5, String.valueOf(pedidoAsignado.getProducto().getNombre()));
            ps.setString(6, String.valueOf(pedidoAsignado.getCantidad()));
            ps.setString(7, String.valueOf(pedidoAsignado.getPrecioUnitario()));
            ps.setString(8, String.valueOf(pedidoAsignado.getTotal()));
            ps.setString(9, pedidoAsignado.getEstado());
            ps.setString(10, String.valueOf(pedidoAsignado.getNumeroPedido()));
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error al actualizar pedido asignado: " + e.getMessage());
            return false;
        }
    }

    /**
     * Método para eliminar un pedido asignado de la base de datos
     *
     * @param numeroPedidoAsignado número del pedido asignado a eliminar
     * @return true si se elimina correctamente, false si no
     */
    public boolean eliminar(long numeroPedidoAsignado) {
        PreparedStatement ps;
        Connection con = getConexion();
        // consulta para eliminar los datos del pedido donde el número del pedido sea igual al ingresado
        String sql = "DELETE FROM pedido_asignado WHERE numero_pedido_asignado = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, String.valueOf(numeroPedidoAsignado));
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al eliminar pedido asignado: " + e.getMessage());
            return false;
        }
    }

    /**
     * Método para listar todos los pedidos asignados registrados en la base de datos
     *
     * @param colaPedidoAsignado       cola donde se guardan los pedidos asignados registrados en la base de datos
     * @param vistaPrincipalRepartidor tabla donde se muestran los pedidos asignados registrados en la base de datos
     */
    public void listar(ColaPedidoAsignado colaPedidoAsignado, VistaPrincipalRepartidor vistaPrincipalRepartidor) {
        PreparedStatement ps;
        ResultSet rs;
        Connection con = getConexion();
        // consulta para obtener los datos de todos los pedidos asignados registrados en la base de datos
        String sql = "SELECT * FROM pedido_asignado";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) { // mientras existan pedidos asignados registrados en la base de datos
                PedidoAsignado pedidoAsignado = new PedidoAsignado();
                Cliente cliente = new Cliente();
                Repartidor repartidor = new Repartidor();
                Producto producto = new Producto();

                pedidoAsignado.setNumeroPedido(rs.getLong(1));
                pedidoAsignado.setFecha(rs.getString(2));
                repartidor.setNombre(rs.getString(3));
                pedidoAsignado.setRepartidor(repartidor);
                cliente.setNombre(rs.getString(4));
                pedidoAsignado.setCliente(cliente);
                cliente.setDireccion(rs.getString(5));
                pedidoAsignado.setDireccion(cliente.getDireccion());
                producto.setNombre(rs.getString(6));
                pedidoAsignado.setProducto(producto);
                pedidoAsignado.setCantidad(rs.getInt(7));
                pedidoAsignado.setPrecioUnitario(rs.getDouble(8));
                pedidoAsignado.setTotal(rs.getDouble(9));
                pedidoAsignado.setEstado(rs.getString(10));
                colaPedidoAsignado.agregar(pedidoAsignado);
                colaPedidoAsignado.agregarPedidoAsignadoTabla(vistaPrincipalRepartidor.getJtPedidoAsignado());
            }
        } catch (SQLException e) {
            System.out.println("Error al listar pedidos asignados: " + e.getMessage());
        }
    }
}
