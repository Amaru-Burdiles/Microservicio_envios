package duoc.amaru.envios.service;

import duoc.amaru.envios.client.DestinoClient;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import duoc.amaru.envios.client.GeoCodingClient;
import duoc.amaru.envios.client.PedidoClient;
import duoc.amaru.envios.client.SesionClient;
import duoc.amaru.envios.dto.CrearEnvioDTO;
import duoc.amaru.envios.dto.DestinoDTO;
import duoc.amaru.envios.dto.PedidoDTO;
import duoc.amaru.envios.model.Despacho;
import duoc.amaru.envios.model.Envio;
import duoc.amaru.envios.repository.EnvioRepo;

@Service
public class EnvioServicio {
    @Autowired
    private EnvioRepo envioRepo;
    
    @Autowired
    private SesionClient sesionClient;

    @Autowired
    private PedidoClient pedidoClient;

    @Autowired
    private DestinoClient destinoClient;

    @Autowired
    private DespachoServicio despachoServicio;

    @Autowired
    private GeoCodingClient geoCodingClient;

    // CREAR ENVIO
    public Envio crearEnvio(Long userId, CrearEnvioDTO envioDTO) {
        // Validar usuario
        sesionClient.validarUsuario(userId);

        // Validar Pedido existe
        PedidoDTO pedido = pedidoClient.pedidoExiste(envioDTO.getPedidoId());
        if (pedido == null)
            return null;

        // Validar Direccion de envio existe para el Cliente
        DestinoDTO destino = destinoClient.getDireccionEnvio(pedido.getIdCliente(), envioDTO.getDireccionId());

        Envio nuevo = new Envio();
        nuevo.setIdPedido(envioDTO.getPedidoId());
        nuevo.setDireccionDestino(envioDTO.getDireccionId());

        // Convierte la dirección de destino a latitud y longitud
        List<Double> coords = geoCodingClient.getCoordenadas(destino);

        // Elige la tienda de despacho más cercana al las coords de destino
        Despacho despacho = despachoServicio.getMasCercana(coords.getFirst(), coords.getLast());
        nuevo.setDireccionDespacho(despacho);

        if (despacho == null) // Pendiente significa que no se ha encontrado un despacho
            nuevo.setEstado("Pendiente");
        else
            nuevo.setEstado("En despacho");

        nuevo.setFechaCreacion(LocalDate.now());
        
        return envioRepo.save(nuevo);
    }

    // OBTENER TODOS LOS ENVIOS
    public List<Envio> getEnvios(Long userId) {
        // Validar empleado
        sesionClient.validarEmpleado(userId, 2);

        return envioRepo.findAll();
    }

    // OBTENER ENVIOS POR CLIENTE
    public List<Envio> getEnviosByCliente(Long exeId, Long userId) {
        // Validar cliente dueño de los envios o empleado
        if (exeId == userId)
            sesionClient.validarCliente(exeId);
        else
            sesionClient.validarEmpleado(exeId, 2);

        // Lista con todos los envios
        List<Envio> todos = envioRepo.findAll();

        // Solo los envios del cliente
        List<Envio> filtrado = new ArrayList<>();

        // Filtro
        for (Envio e : todos) {
            PedidoDTO pedido = pedidoClient.pedidoExiste(e.getIdPedido());
            if (pedido.getIdCliente() == userId)
                filtrado.add(e);
        }

        return filtrado;
    }

    // OBTENER ENVIOS POR ESTADO [POR CLIENTE]
    public List<Envio> getEnviosByEstado(Long exeId, Long userId, String estado) {
        // Validar cliente dueño de los envios o empleado
        if (exeId == userId)
            sesionClient.validarCliente(exeId);
        else
            sesionClient.validarEmpleado(exeId, 2);

        // Lista con todos los envios
        List<Envio> todos = envioRepo.findAll();

        // Solo los envios del cliente
        List<Envio> filtrado = new ArrayList<>();

        // Filtro
        for (Envio e : todos) {
            if (e.getEstado().equalsIgnoreCase(estado)) {
                
                // Consulta el id del pedido al microservicio Pedidos
                PedidoDTO pedido = pedidoClient.pedidoExiste(e.getIdPedido());
                
                // Compara que el usuario sea el buscado
                if (pedido.getIdCliente() == userId)
                    filtrado.add(e);
            }
        }

        return filtrado;
    }

    // OBTENER ENVIOS POR ESTADO
    public List<Envio> getEnviosByEstado(Long exeId, String estado) {
        // Validar empleado
        sesionClient.validarEmpleado(exeId, 2);

        // TODO: Definir Set de estados validos

        return envioRepo.findByEstado(estado);
    }

    // OBTENER ENVIO POR ID
    public Envio getEnvioById(Long userId, Long envioId) {
        // Validar usuario
        sesionClient.validarUsuario(userId);

        // Buscar envio por id
        Envio e = envioRepo.findById(envioId).orElse(null);
        if (e == null)
            return null;

        // Verificar si es el cliente quien intenta ver su envio
        PedidoDTO pedido = pedidoClient.pedidoExiste(e.getIdPedido());
        if (pedido.getIdCliente() == userId)
            return e;
        
        // Si no, validar que se trata de un empleado autorizado
        sesionClient.validarEmpleado(envioId, 2);
        return e;
    }

    // CANCELAR ENVIO
    public boolean cancelarEnvio(Long userId, Long envioId) {
        // Validar usuario
        sesionClient.validarUsuario(userId);

        // Buscar envio por id
        Envio e = envioRepo.findById(envioId).orElse(null);
        if (e == null)
            return false;

        // Verificar si es el cliente quien intenta ver su envio
        PedidoDTO pedido = pedidoClient.pedidoExiste(e.getIdPedido());
        if (pedido.getIdCliente() == userId) {
            e.setEstado("Cancelado");
            return true;
        }
        
        // Si no, validar que se trata de un empleado autorizado
        sesionClient.validarEmpleado(envioId, 2);
        e.setEstado("Cancelado");
        return true;
    }

    // MARCAR COMO FINALIZADO
    public boolean finalizarEnvio(Long userId, Long envioId) {
        // Validar usuario
        sesionClient.validarUsuario(userId);

        // Buscar envio por id
        Envio e = envioRepo.findById(envioId).orElse(null);
        if (e == null)
            return false;

        // Verificar si es el cliente quien intenta ver su envio
        PedidoDTO pedido = pedidoClient.pedidoExiste(e.getIdPedido());
        if (pedido.getIdCliente() == userId) {
            e.setEstado("Finalizado");
            return true;
        }
        
        // Si no, validar que se trata de un empleado autorizado
        sesionClient.validarEmpleado(envioId, 2);
        e.setEstado("Finalizado");
        return true;
    }

    // CALCULAR RUTA
    // TODO:
}
