package duoc.amaru.envios.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import duoc.amaru.envios.dto.DestinoDTO;

@Component
public class DestinoClient {
    @Autowired
    private RestTemplate restTemplate;

    private String serviceUrl = "http://localhost:8086/api/v1/usuarios";

    public DestinoDTO getDireccionEnvio(Long userId, Long dirId) {
        String url = "/user"+ userId +"/direccion/"+ dirId;
        DestinoDTO reply = restTemplate.getForObject(serviceUrl + url, DestinoDTO.class);

        if (reply == null)
            return null;
        
        return reply;
    }
}
