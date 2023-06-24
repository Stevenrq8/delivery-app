package org.delivery.modelo.dao;

import org.delivery.modelo.*;
import org.delivery.vista.VistaTablaProducto;

import java.sql.*;

public class ProductoDAO extends Conexion {

    /**
     * Método para registrar un producto en la base de datos
     *
     * @param producto producto a registrar
     * @return true si se registra correctamente, false si no
     */
    public boolean registrar(Producto producto) {
        PreparedStatement ps;
        Connection con = getConexion();
        // consulta para insertar los datos del producto en la base de datos
        String sql = "INSERT INTO producto (id_producto, nombre_restaurante, nombre, descripcion, precio, cantidad, categoria) " +
                "VALUES(?,?,?,?,?,?,?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, String.valueOf(producto.getId()));
            ps.setString(2, String.valueOf(producto.getRestaurante().getNombre()));
            ps.setString(3, producto.getNombre());
            ps.setString(4, producto.getDescripcion());
            ps.setString(5, String.valueOf(producto.getPrecio()));
            ps.setString(6, String.valueOf(producto.getCantidad()));
            ps.setString(7, producto.getCategoria());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al registrar producto: " + e.getMessage());
            return false;
        }
    }

    /**
     * Método para buscar un producto en la base de datos
     *
     * @param id id del producto a buscar
     * @return true si encuentra un producto con el id ingresado, false si no
     */
    public boolean buscar(long id) {
        PreparedStatement ps;
        ResultSet rs;
        Connection con = getConexion();
        // consulta para obtener los datos del producto donde el id sea igual al ingresado
        String sql = "SELECT * FROM producto WHERE id_producto = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, String.valueOf(id));
            rs = ps.executeQuery();
            return rs.next(); // retorna true si encuentra un producto con el id ingresado
        } catch (SQLException e) {
            System.out.println("Error al buscar producto: " + e.getMessage());
            return false;
        }
    }

    /**
     * Método para actualizar los datos de un producto en la base de datos
     *
     * @param producto producto a actualizar
     * @return true si se actualiza correctamente, false si no
     */
    public boolean actualizar(Producto producto) {
        PreparedStatement ps;
        Connection con = getConexion();
        // consulta para actualizar los datos del producto en la base de datos
        String sql = "UPDATE producto SET nombre_restaurante=?, nombre=?, descripcion=?, precio=?, cantidad=?, categoria=? " +
                "WHERE id_producto=?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, String.valueOf(producto.getRestaurante().getNombre()));
            ps.setString(2, producto.getNombre());
            ps.setString(3, producto.getDescripcion());
            ps.setString(4, String.valueOf(producto.getPrecio()));
            ps.setString(5, String.valueOf(producto.getCantidad()));
            ps.setString(6, producto.getCategoria());
            ps.setString(7, String.valueOf(producto.getId()));
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error al actualizar producto: " + e.getMessage());
            return false;
        }
    }

    /**
     * Método para actualizar la cantidad de un producto en la base de datos
     *
     * @param cantidad cantidad del producto a actualizar
     * @param id       id del producto a actualizar
     * @return true si se actualiza correctamente, false si no
     */
    public boolean actualizar(int cantidad, long id) {
        PreparedStatement ps;
        Connection con = getConexion();
        // consulta para actualizar los datos del producto en la base de datos
        String sql = "UPDATE producto SET cantidad=? WHERE id_producto=?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, String.valueOf(cantidad));
            ps.setString(2, String.valueOf(id));
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error al actualizar producto: " + e.getMessage());
            return false;
        }
    }

    /**
     * Método para eliminar un producto de la base de datos
     *
     * @param id id del producto a eliminar
     * @return true si se elimina correctamente, false si no
     */
    public boolean eliminar(long id) {
        PreparedStatement ps;
        Connection con = getConexion();
        // consulta para eliminar los datos del producto donde el id sea igual al ingresado
        String sql = "DELETE FROM producto WHERE id_producto = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, String.valueOf(id));
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al eliminar producto: " + e.getMessage());
            return false;
        }
    }

    /**
     * Método para listar los productos registrados en la base de datos
     *
     * @param colaProducto       cola donde se guardan los productos registrados en la base de datos
     * @param vistaTablaProducto tabla donde se muestran los productos registrados en la base de datos
     */
    public void listar(ColaProducto colaProducto, VistaTablaProducto vistaTablaProducto) {
        PreparedStatement ps;
        ResultSet rs;
        Connection con = getConexion();
        // consulta para obtener los datos de todos los productos registrados en la base de datos
        String sql = "SELECT * FROM producto";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) { // mientras existan productos registrados en la base de datos
                Producto producto = new Producto();
                Restaurante restaurante = new Restaurante();

                producto.setId(rs.getLong(1));
                restaurante.setNombre(rs.getString(2)); // se obtiene el nombre del restaurante
                producto.setRestaurante(restaurante); // se guarda el nombre del restaurante en el producto
                producto.setNombre(rs.getString(3));
                producto.setDescripcion(rs.getString(4));
                producto.setPrecio(rs.getDouble(5));
                producto.setCantidad(rs.getInt(6));
                producto.setCategoria(rs.getString(7));
                colaProducto.agregar(producto);
                colaProducto.agregarProductoTabla(vistaTablaProducto.getJtProducto());
            }
        } catch (SQLException e) {
            System.out.println("Error al listar productos: " + e.getMessage());
        }
    }
}
