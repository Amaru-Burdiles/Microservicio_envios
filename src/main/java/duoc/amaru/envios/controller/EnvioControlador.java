package duoc.amaru.envios.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import duoc.amaru.envios.dto.CrearEnvioDTO;
import duoc.amaru.envios.service.EnvioServicio;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("api/v1/envios")
public class EnvioControlador {
    @Autowired
    private EnvioServicio envioServicio;
    
    @PostMapping
    public ResponseEntity<?> postEnvio(@RequestBody CrearEnvioDTO newEnvio) {
        Long pedido = newEnvio.getPedidoId();
        Long direccion = newEnvio.getDireccionId();
        return envioServicio.crearEnvio(pedido, direccion);
    }
    
}
