package org.delivery.modelo;

import lombok.*;

@Getter
@Setter
public class Repartidor extends Usuario {

    private String tipoVehiculo;
    private Repartidor siguiente; // Apuntador al siguiente nodo de la cola

    public Repartidor() {
        super(0, "", "", "", "", "", "");
        this.tipoVehiculo = "";
        this.siguiente = null;
    }

    public Repartidor(long cedula, String nombre, String apellido, String direccion, String telefono, String tipoVehiculo, String correo, String contrasena) {
        super(cedula, nombre, apellido, direccion, telefono, correo, contrasena);
        this.tipoVehiculo = tipoVehiculo;
        this.siguiente = null;
    }
}

