package duoc.amaru.envios.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import duoc.amaru.envios.model.Envio;
import duoc.amaru.envios.repository.EnvioRepo;

@Service
public class EnvioServicio {
    @Autowired
    private EnvioRepo envioRepo;
    
    // Testeo
    private AtomicInteger fases = new AtomicInteger(1);
    private HashMap<Integer, String> estados = new HashMap<>();
    {
        estados.put(1, "En bodega");
        estados.put(2, "Enviado");
        estados.put(3, "En transito");
        estados.put(4, "En reparto");
        estados.put(1, "En bodega");
    }
    
    // CREAR PEDIDO
    public ResponseEntity<?> crearEnvio(Long pedido, Long direccion) {
        if (envioRepo.existsByIdPedido(pedido))
            return ResponseEntity.status(400).body("Un envio para este pedido ya existe");

        

        Envio e = new Envio();
        e.setIdPedido(pedido);
        e.setDireccionDestino(direccion);
        e.setFechaCreacion(LocalDate.now());
        e.setEstado(estados.get(1));
        return ResponseEntity.ok("Envio creado");
    }


}
