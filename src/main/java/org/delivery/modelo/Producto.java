package org.delivery.modelo;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class Producto {

    private long id;
    private Restaurante restaurante;
    private String nombre;
    private String descripcion;
    private double precio;
    private int cantidad;
    private String categoria;
    private Producto siguiente; // Apuntador al siguiente nodo de la cola

    public Producto(long id, Restaurante restaurante, String nombre, String descripcion, double precio, int cantidad,
                    String categoria) {
        this.id = id;
        this.restaurante = restaurante;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.cantidad = cantidad;
        this.categoria = categoria;
        this.siguiente = null;
    }

    /**
     * Método que devuelve el nombre del producto
     *
     * @return nombre del producto
     */
    @Override
    public String toString() {
        return nombre;
    }
}
