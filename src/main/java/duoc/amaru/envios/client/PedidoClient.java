package duoc.amaru.envios.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import duoc.amaru.envios.dto.PedidoExisteDTO;

@Component
public class PedidoClient {
    @Autowired
    private RestTemplate restTemplate;

    public ResponseEntity<?> pedidoExiste(Long idPedido) {
        PedidoExisteDTO existe = new PedidoExisteDTO(idPedido);

        return restTemplate.postForObject("http://localhost:8081/pedidos/buscar/", existe, ResponseEntity.class);
    }
}
