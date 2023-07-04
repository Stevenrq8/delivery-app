package org.delivery.modelo;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class PedidoAsignado {

    private long numeroPedido;
    private String fecha;
    private Repartidor repartidor;
    private Cliente cliente;
    private String direccion;
    private Producto producto;
    private int cantidad;
    private double precioUnitario;
    private double total;
    private String estado;
    private PedidoAsignado siguiente; // Apuntador al siguiente nodo de la cola

    public PedidoAsignado(long numeroPedido, String fecha, Repartidor repartidor, Cliente cliente, String direccion,
                          Producto producto, int cantidad, double precioUnitario, double total, String estado) {
        this.numeroPedido = numeroPedido;
        this.fecha = fecha;
        this.repartidor = repartidor;
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
