package org.delivery.modelo;

import lombok.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

@Getter
@Setter
@NoArgsConstructor
public class ColaProducto {

    private Producto primero;
    private Producto ultimo;
    private int totalProductos;

    public boolean colaVacia() {
        return getTotalProductos() == 0;
    }

    public void agregar(Producto producto) {
        if (colaVacia()) {
            setPrimero(producto);
            setUltimo(producto);
        } else {
            getUltimo().setSiguiente(producto);
            setUltimo(producto);
        }
        totalProductos++;
    }

    /**
     * Método que permite agregar los productos a la tabla.
     *
     * @param tabla tabla donde se van a agregar los productos.
     */
    public void agregarProductoTabla(JTable tabla) {
        Producto aux = getPrimero();
        DefaultTableModel modelo = new DefaultTableModel();
        int fila = 0;

        modelo.addColumn("ID");
        modelo.addColumn("Restaurante");
        modelo.addColumn("Nombre del Producto");
        modelo.addColumn("Descripción");
        modelo.addColumn("Precio");
        modelo.addColumn("Cantidad");
        modelo.addColumn("Categoría");
        if (!colaVacia()) {
            while (aux != null) {
                modelo.addRow(new Object[]{});
                modelo.setValueAt(aux.getId(), fila, 0);
                modelo.setValueAt(aux.getRestaurante(), fila, 1);
                modelo.setValueAt(aux.getNombre(), fila, 2);
                modelo.setValueAt(aux.getDescripcion(), fila, 3);
                modelo.setValueAt(aux.getPrecio(), fila, 4);
                modelo.setValueAt(aux.getCantidad(), fila, 5);
                modelo.setValueAt(aux.getCategoria(), fila, 6);
                fila++;
                aux = aux.getSiguiente();
            }
            tabla.setModel(modelo);
        }
    }

    public boolean buscar(String nombre, String descripcion, double precio, String categoria) {
        if (!colaVacia()) {
            Producto aux = getPrimero();
            while (aux != null) {
                if (aux.getNombre().equals(nombre) && aux.getDescripcion().equals(descripcion)
                        && aux.getPrecio() == precio && aux.getCategoria().equals(categoria)) {
                    return true;
                }
                aux = aux.getSiguiente();
            }
        }
        return false;
    }

    public boolean buscar(String nombre, double precio) {
        if (!colaVacia()) {
            Producto aux = getPrimero();
            while (aux != null) {
                if (aux.getNombre().equals(nombre) && aux.getPrecio() == precio) {
                    return true;
                }
                aux = aux.getSiguiente();
            }
        }
        return false;
    }

    /**
     * Método que permite actualizar la cantidad de un producto.
     *
     * @param nombre  nombre del producto que se va a actualizar.
     * @param cantidad cantidad que se va a actualizar.
     */
    public void actualizarCantidad(String nombre, int cantidad) {
        if (!colaVacia()) {
            Producto aux = getPrimero();
            while (aux != null) {
                if (aux.getNombre().equals(nombre)) {
                    aux.setCantidad(cantidad);
                    return;
                }
                aux = aux.getSiguiente();
            }
        }
    }

    public void eliminar() {
        if (!colaVacia()) {
            setPrimero(getPrimero().getSiguiente());
            totalProductos--;
        }
    }

    /**
     * Método que permite eliminar un producto de la cola.
     *
     * @param producto producto que se va a eliminar.
     */
    public void eliminar(Producto producto) {
        if (!colaVacia()) {
            Producto aux = getPrimero();
            Producto anterior = null;
            while (aux != null) {
                if (aux.getNombre().equals(producto.getNombre())) {
                    if (anterior == null) {
                        eliminar();
                    } else {
                        anterior.setSiguiente(aux.getSiguiente());
                        totalProductos--;
                    }
                    JOptionPane.showMessageDialog(null, "Producto eliminado: " + aux);
                    return;
                }
                anterior = aux;
                aux = aux.getSiguiente();
            }
            JOptionPane.showMessageDialog(null, "Producto no encontrado");
        }
    }

    public void limpiar() {
        setPrimero(null);
        setUltimo(null);
        totalProductos = 0;
    }
}
