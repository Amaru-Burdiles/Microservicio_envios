package duoc.amaru.envios.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import duoc.amaru.envios.dto.PedidoDTO;
import duoc.amaru.envios.dto.ResponseDTO;

@Component
public class PedidoClient {
    @Autowired
    private RestTemplate restTemplate;

    private String serviceUrl = "http://localhost:8085/api/ecomarket/v1/pedido";

    public PedidoDTO pedidoExiste(Long idPedido) {
        return restTemplate.getForObject(serviceUrl +'/'+ idPedido, ResponseDTO.class).getBody();
    }
}
