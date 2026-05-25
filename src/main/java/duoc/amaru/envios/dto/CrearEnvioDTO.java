package duoc.amaru.envios.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CrearEnvioDTO {
    private Long pedidoId;
    private Long direccionId;
}
