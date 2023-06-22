package org.delivery.modelo;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class Pedido {

    private long numeroPedido;
    private String fecha;
    private Cliente cliente;
    private ColaCliente colaCliente;
    private String direccion;
    private Producto producto;
    private ColaProducto colaProducto;
    private int cantidad;
    private double precioUnitario;
    private double total;
    private String estado;
    private Pedido siguiente; // Apuntador al siguiente nodo de la cola

    public Pedido(long numeroPedido, String fecha, Cliente cliente, String direccion, Producto producto, int cantidad,
                  double precioUnitario, double total, String estado) {
        this.numeroPedido = numeroPedido;
        this.fecha = fecha;
        this.cliente = cliente;
        this.direccion = direccion;
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.total = total;
        this.estado = estado;
        this.siguiente = null;
    }
}