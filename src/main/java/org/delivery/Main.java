package org.delivery;

import org.delivery.controlador.*;
import org.delivery.modelo.*;
import org.delivery.modelo.dao.*;
import org.delivery.vista.*;

public class Main {
    public static void main(String[] args) {

        // modelos
        Administrador administrador = new Administrador();
        AdministradorPedido administradorPedido = new AdministradorPedido();
        Cliente cliente = new Cliente();
        Repartidor repartidor = new Repartidor();
        Restaurante restaurante = new Restaurante();
        Producto producto = new Producto();
        Pedido pedido = new Pedido();
        ColaAdministrador colaAdministrador = new ColaAdministrador();
        ColaAdministradorPedido colaAdministradorPedido = new ColaAdministradorPedido();
        ColaCliente colaCliente = new ColaCliente();
        ColaRepartidor colaRepartidor = new ColaRepartidor();
        ColaRestaurante colaRestaurante = new ColaRestaurante();
        ColaProducto colaProducto = new ColaProducto();
        ColaPedido colaPedido = new ColaPedido();
        ColaPedidoAsignado colaPedidoAsignado = new ColaPedidoAsignado();

        // DAOs
        AdministradorDAO administradorDAO = new AdministradorDAO();
        AdministradorPedidoDAO administradorPedidoDAO = new AdministradorPedidoDAO();
        ClienteDAO clienteDAO = new ClienteDAO();
        PedidoDAO pedidoDAO = new PedidoDAO();
        ProductoDAO productoDAO = new ProductoDAO();
        RepartidorDAO repartidorDAO = new RepartidorDAO();
        RestauranteDAO restauranteDAO = new RestauranteDAO();

        // vistas
        VistaPrincipal vistaPrincipal = new VistaPrincipal();
        VistaRegistroAdministrador vistaRegistroAdministrador = new VistaRegistroAdministrador();
        VistaPrincipalCliente vistaPrincipalCliente = new VistaPrincipalCliente();
        VistaPrincipalAdministrador vistaPrincipalAdministrador = new VistaPrincipalAdministrador();
        VistaPrincipalAdministradorPedido vistaPrincipalAdministradorPedido = new VistaPrincipalAdministradorPedido();
        VistaPrincipalRestaurante vistaPrincipalRestaurante = new VistaPrincipalRestaurante();
        VistaPrincipalRepartidor vistaPrincipalRepartidor = new VistaPrincipalRepartidor();
        VistaTablaAdministrador vistaTablaAdministrador = new VistaTablaAdministrador();
        VistaTablaAdministradorPedido vistaTablaAdministradorPedido = new VistaTablaAdministradorPedido();
        VistaTablaCliente vistaTablaCliente = new VistaTablaCliente();
        VistaTablaRepartidor vistaTablaRepartidor = new VistaTablaRepartidor();
        VistaTablaRestaurante vistaTablaRestaurante = new VistaTablaRestaurante();
        VistaTablaProducto vistaTablaProducto = new VistaTablaProducto();
        VistaTablaPedido vistaTablaPedido = new VistaTablaPedido();
        VistaRegistroAdministradorPedido vistaRegistroAdministradorPedido = new VistaRegistroAdministradorPedido();
        VistaRegistroCliente vistaRegistroCliente = new VistaRegistroCliente();
        VistaRegistroRepartidor vistaRegistroRepartidor = new VistaRegistroRepartidor();
        VistaRegistroRestaurante vistaRegistroRestaurante = new VistaRegistroRestaurante();
        VistaRegistroProducto vistaRegistroProducto = new VistaRegistroProducto();
        VistaInicioSesion vistaInicioSesion = new VistaInicioSesion();

        // controladores (se pasan los modelos y las vistas) para que se comuniquen entre sí
        ControladorInicioSesion controladorInicioSesion = new ControladorInicioSesion(
                administrador, administradorPedido, cliente, repartidor, restaurante, colaAdministrador,
                colaAdministradorPedido, colaCliente, colaRepartidor, colaRestaurante, vistaPrincipal, vistaPrincipalCliente,
                vistaPrincipalAdministrador, vistaPrincipalAdministradorPedido, vistaPrincipalRestaurante,
                vistaPrincipalRepartidor, vistaInicioSesion, vistaTablaProducto
        );
        ControladorAdministrador controladorAdministrador = new ControladorAdministrador(
                colaAdministrador, colaAdministradorPedido, colaCliente, colaRepartidor, colaRestaurante, vistaPrincipal,
                vistaPrincipalAdministrador, vistaRegistroAdministrador, vistaTablaAdministrador, vistaTablaAdministradorPedido,
                vistaTablaCliente, vistaTablaRepartidor, vistaTablaRestaurante, vistaTablaProducto, vistaTablaPedido,
                administradorDAO
        );
        ControladorAdministradorPedido controladorAdministradorPedido = new ControladorAdministradorPedido(
                repartidor, colaAdministrador, colaAdministradorPedido, colaCliente, colaRepartidor, colaRestaurante,
                colaPedido, colaPedidoAsignado, vistaPrincipal, vistaPrincipalAdministradorPedido, vistaPrincipalRepartidor,
                vistaRegistroAdministradorPedido, vistaTablaAdministradorPedido, vistaTablaPedido, controladorInicioSesion,
                administradorPedidoDAO
        );
        ControladorCliente controladorCliente = new ControladorCliente(
                colaAdministrador, colaAdministradorPedido, colaCliente, colaRepartidor, colaRestaurante, vistaPrincipal,
                vistaPrincipalCliente, vistaRegistroCliente, vistaTablaCliente, vistaTablaProducto, clienteDAO
        );
        ControladorRepartidor controladorRepartidor = new ControladorRepartidor(
                colaAdministrador, colaAdministradorPedido, colaCliente, colaRepartidor, colaRestaurante, colaPedidoAsignado,
                vistaPrincipal, vistaPrincipalRepartidor, vistaRegistroRepartidor, vistaTablaRepartidor, repartidorDAO
        );
        ControladorRestaurante controladorRestaurante = new ControladorRestaurante(
                colaAdministrador, colaAdministradorPedido, colaCliente, colaRepartidor, colaRestaurante, vistaPrincipal,
                vistaPrincipalRestaurante, vistaRegistroRestaurante, vistaRegistroProducto, vistaTablaRestaurante,
                restauranteDAO
        );
        ControladorProducto controladorProducto = new ControladorProducto(
                restaurante, producto, colaProducto, vistaPrincipal, vistaRegistroProducto, vistaRegistroRestaurante,
                vistaPrincipalRestaurante, vistaTablaProducto, controladorInicioSesion, productoDAO
        );
        ControladorPedido controladorPedido = new ControladorPedido(
                pedido, colaPedido, cliente, colaCliente, producto,
                colaProducto, repartidor, colaRepartidor, restaurante, vistaPrincipal, vistaTablaCliente, vistaTablaProducto,
                vistaTablaRepartidor, vistaTablaPedido, vistaTablaRestaurante, controladorInicioSesion, pedidoDAO
        );
        vistaPrincipal.setVisible(true);
    }
}