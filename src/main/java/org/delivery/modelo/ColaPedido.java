package org.delivery.modelo;

import lombok.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

@Getter
@Setter
@NoArgsConstructor
public class ColaPedido {

    private Pedido primero;
    private Pedido ultimo;
    private int totalPedidos;

    public boolean colaVacia() {
        return getTotalPedidos() == 0;
    }

    public void agregar(Pedido pedido) {
        if (colaVacia()) {
            setPrimero(pedido);
            setUltimo(pedido);
        } else {
            getUltimo().setSiguiente(pedido);
            setUltimo(pedido);
        }
        totalPedidos++;
    }

    /**
     * Método que agrega los pedidos a una tabla
     *
     * @param tabla tabla a la que se agregan los pedidos
     */
    public void agregarPedidoTabla(JTable tabla) {
        Pedido aux = getPrimero();
        DefaultTableModel modelo = new DefaultTableModel();
        int fila = 0;

        modelo.addColumn("Número de Pedido");
        modelo.addColumn("Fecha");
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
                modelo.setValueAt(aux.getCliente(), fila, 2);
                modelo.setValueAt(aux.getDireccion(), fila, 3);
                modelo.setValueAt(aux.getProducto(), fila, 4);
                modelo.setValueAt(aux.getCantidad(), fila, 5);
                modelo.setValueAt(aux.getPrecioUnitario(), fila, 6);
                modelo.setValueAt(aux.getTotal(), fila, 7);
                modelo.setValueAt(aux.getEstado(), fila, 8);
                fila++;
                aux = aux.getSiguiente();
            }
            tabla.setModel(modelo);
        }
    }

    public boolean buscar(long numeroPedido) {
        if (!colaVacia()) {
            Pedido aux = getPrimero();
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
            totalPedidos--;
        }
    }

    public void limpiar() {
        setPrimero(null);
        setUltimo(null);
        totalPedidos = 0;
    }
}
