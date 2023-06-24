package org.delivery.controlador;

import lombok.*;
import org.delivery.modelo.*;
import org.delivery.modelo.dao.PedidoDAO;
import org.delivery.modelo.dao.ProductoDAO;
import org.delivery.vista.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class ControladorPedido implements ActionListener {

    private Pedido pedido;
    private ColaPedido colaPedido;
    private Cliente cliente;
    private ColaCliente colaCliente;
    private Producto producto;
    private ColaProducto colaProducto;
    private Repartidor repartidor;
    private ColaRepartidor colaRepartidor;
    private Restaurante restaurante;
    private VistaPrincipal vistaPrincipal;
    private VistaTablaCliente vistaTablaCliente;
    private VistaTablaProducto vistaTablaProducto;
    private VistaTablaRepartidor vistaTablaRepartidor;
    private VistaTablaPedido vistaTablaPedido;
    private VistaTablaRestaurante vistaTablaRestaurante;
    private ControladorInicioSesion controladorInicioSesion;
    private PedidoDAO pedidoDAO;

    public ControladorPedido(Pedido pedido, ColaPedido colaPedido, Cliente cliente, ColaCliente colaCliente, Producto producto,
                             ColaProducto colaProducto, Repartidor repartidor, ColaRepartidor colaRepartidor, Restaurante restaurante,
                             VistaPrincipal vistaPrincipal, VistaTablaCliente vistaTablaCliente, VistaTablaProducto vistaTablaProducto,
                             VistaTablaRepartidor vistaTablaRepartidor, VistaTablaPedido vistaTablaPedido,
                             VistaTablaRestaurante vistaTablaRestaurante, ControladorInicioSesion controladorInicioSesion,
                             PedidoDAO pedidoDAO) {
        this.pedido = pedido;
        this.colaPedido = colaPedido;
        this.cliente = cliente;
        this.colaCliente = colaCliente;
        this.producto = producto;
        this.colaProducto = colaProducto;
        this.repartidor = repartidor;
        this.colaRepartidor = colaRepartidor;
        this.restaurante = restaurante;
        this.vistaPrincipal = vistaPrincipal;
        this.vistaTablaCliente = vistaTablaCliente;
        this.vistaTablaProducto = vistaTablaProducto;
        this.vistaTablaRepartidor = vistaTablaRepartidor;
        this.vistaTablaPedido = vistaTablaPedido;
        this.vistaTablaRestaurante = vistaTablaRestaurante;
        this.controladorInicioSesion = controladorInicioSesion;
        this.pedidoDAO = pedidoDAO;
        this.vistaTablaProducto.getJbComprarProducto().addActionListener(this);
        this.pedidoDAO.listar(getColaPedido(), getVistaTablaPedido());
    }

    /**
     * Método que obtiene el producto seleccionado en la tabla de productos de acuerdo a la fila seleccionada
     *
     * @param fila fila seleccionada en la tabla de productos
     * @return el producto de la fila seleccionada
     */
    public Producto getProductoSeleccionado(int fila) {
        Producto aux = getColaProducto().getPrimero();
        int i = 0;
        while (aux != null && i < fila) { // recorre la cola hasta llegar a la fila seleccionada en la tabla de productos
            aux = aux.getSiguiente();
            i++;
        }
        return aux;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        PedidoDAO pedidoDAO = new PedidoDAO();
        ProductoDAO productoDAO = new ProductoDAO();

        if (e.getSource() == getVistaTablaProducto().getJbComprarProducto() && getVistaTablaProducto().getJtProducto().getSelectedRow() != -1) {
            if (getControladorInicioSesion().getCliente() != null) { // si el cliente ha iniciado sesión
                // se obtiene el cliente que ha iniciado sesión para crear el pedido del cliente actual y no del primero
                // de la cola de clientes registrados
                setCliente(getControladorInicioSesion().getCliente());
                long numeroPedido = (long) (Math.random() * 1000); // genera un número aleatorio para el número de pedido
                LocalDateTime fecha = LocalDateTime.now();
                DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String fechaPedido = fecha.format(formato);
                String direccion = getCliente().getDireccion();
                int fila = getVistaTablaProducto().getJtProducto().getSelectedRow(); // fila seleccionada en la tabla de productos
                setProducto(getProductoSeleccionado(fila)); // se obtiene el producto seleccionado en la tabla de productos

                // se obtienen los datos del producto seleccionado en la tabla de productos para crear el pedido
                long id = Long.parseLong(getVistaTablaProducto().getJtProducto().getValueAt(fila, 0).toString());
                String nombreProducto = getVistaTablaProducto().getJtProducto().getValueAt(fila, 2).toString();
                String descripcion = getVistaTablaProducto().getJtProducto().getValueAt(fila, 3).toString();
                double precio = Double.parseDouble(getVistaTablaProducto().getJtProducto().getValueAt(fila, 4).toString());
                int cantidad = Integer.parseInt(getVistaTablaProducto().getJtProducto().getValueAt(fila, 5).toString());
                String categoria = getVistaTablaProducto().getJtProducto().getValueAt(fila, 6).toString();
                int cantidadComprar = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la cantidad de productos que desea comprar: "));

                // se verifica que la cantidad de productos cumpla con las condiciones necesarias para realizar la compra
                if (cantidadComprar > cantidad) {
                    JOptionPane.showMessageDialog(null, "No hay suficientes productos en stock");
                    return;
                } else if (cantidadComprar == 0 || cantidadComprar < 0) {
                    JOptionPane.showMessageDialog(null, "Debe ingresar una cantidad mayor a 0");
                    return;
                } else if (cantidadComprar == cantidad) {
                    if (productoDAO.eliminar(getProducto().getId())) {
                        getColaProducto().eliminar(getProducto());
                        DefaultTableModel modelo = (DefaultTableModel) getVistaTablaProducto().getJtProducto().getModel();
                        modelo.removeRow(fila);
                        getColaProducto().agregarProductoTabla(getVistaTablaProducto().getJtProducto());
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al eliminar el producto '"
                                + getProducto().getNombre() + "'", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    getProducto().setCantidad(cantidad - cantidadComprar); // se actualiza la cantidad del producto seleccionado
                    productoDAO.actualizar(getProducto().getCantidad(), getProducto().getId());
                    getColaProducto().agregarProductoTabla(getVistaTablaProducto().getJtProducto());
                }
                double precioUnitario = getProducto().getPrecio(); // precio unitario del producto seleccionado
                double total = precioUnitario * cantidadComprar;
                String estado = "En preparación"; // estado inicial del pedido

                // se crea el pedido con los datos obtenidos del producto seleccionado y el cliente que ha iniciado sesión
                Pedido pedido = new Pedido(
                        numeroPedido,
                        fechaPedido,
                        getCliente(),
                        direccion,
                        new Producto(id, getRestaurante(), nombreProducto, descripcion, precio, cantidad, categoria),
                        cantidadComprar,
                        precioUnitario,
                        total,
                        estado
                );
                if (pedidoDAO.registrar(pedido)) {
                    getColaPedido().agregar(pedido);
                    getColaPedido().agregarPedidoTabla(getVistaTablaPedido().getJtPedido());
                    JOptionPane.showMessageDialog(null, "¡Pedido realizado con éxito!", "Pedido",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo realizar el pedido", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Debe iniciar sesión para poder comprar",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "¡Debe seleccionar el producto que desea comprar!",
                    "Información", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
