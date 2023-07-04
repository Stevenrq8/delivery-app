package org.delivery.modelo;

import lombok.*;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;

@Getter
@Setter
@NoArgsConstructor
public class ColaPedidoAsignado {

    private PedidoAsignado primero;
    private PedidoAsignado ultimo;
    private int totalPedidosAsignados;

    public boolean colaVacia() {
        return getTotalPedidosAsignados() == 0;
    }

    public void agregar(PedidoAsignado pedido) {
        if (colaVacia()) {
            setPrimero(pedido);
            setUltimo(pedido);
        } else {
            getUltimo().setSiguiente(pedido);
            setUltimo(pedido);
        }
        totalPedidosAsignados++;
    }

    /**
     * Método que agrega los pedidos a la tabla de pedidos asignados
     *
     * @param tabla tabla a la que se agregan los pedidos asignados
     */
    public void agregarPedidoAsignadoTabla(JTable tabla) {
        PedidoAsignado aux = getPrimero();
        DefaultTableModel modelo = new DefaultTableModel();
        int fila = 0;

        modelo.addColumn("Número de Pedido");
        modelo.addColumn("Fecha");
        modelo.addColumn("Repartidor");
        modelo.addColumn("Cliente");
        modelo.addColumn("Dirección");
        modelo.addColumn("Producto");
        modelo.addColumn("Cantidad");
        modelo.addColumn("Precio Unitario");
        modelo.addColumn("Total");
        modelo.addColumn("Estado");
        if (!colaVacia()) {
            while (aux != null) {
                modelo.addRow(new Object[]{});
                modelo.setValueAt(aux.getNumeroPedido(), fila, 0);
                modelo.setValueAt(aux.getFecha(), fila, 1);
                modelo.setValueAt(aux.getRepartidor(), fila, 2);
                modelo.setValueAt(aux.getCliente(), fila, 3);
                modelo.setValueAt(aux.getDireccion(), fila, 4);
                modelo.setValueAt(aux.getProducto(), fila, 5);
                modelo.setValueAt(aux.getCantidad(), fila, 6);
                modelo.setValueAt(aux.getPrecioUnitario(), fila, 7);
                modelo.setValueAt(aux.getTotal(), fila, 8);
                modelo.setValueAt(aux.getEstado(), fila, 9);
                fila++;
                aux = aux.getSiguiente();
            }
            tabla.setModel(modelo);
        }
    }

    public boolean buscar(long numeroPedido) {
        if (!colaVacia()) {
            PedidoAsignado aux = getPrimero();
            while (aux != null) {
                if (aux.getNumeroPedido() == numeroPedido) {
                    return true;
                }
                aux = aux.getSiguiente();
            }
        }
        return false;
    }

    public void eliminar() {
        if (!colaVacia()) {
            setPrimero(getPrimero().getSiguiente());
            totalPedidosAsignados--;
        }
    }

    public void limpiar() {
        setPrimero(null);
        setUltimo(null);
        totalPedidosAsignados = 0;
    }
}

