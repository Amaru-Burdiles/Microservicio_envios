package duoc.amaru.envios.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import duoc.amaru.envios.dto.DestinoDTO;

@Component
public class GeoCodingClient {
    @Autowired
    private RestTemplate restTemplate;

    private String url;

    public List<Double> getCoordenadas(DestinoDTO destino) {
        // TODO: Conectar con Google Geocoding API
        return null;
    }
}
