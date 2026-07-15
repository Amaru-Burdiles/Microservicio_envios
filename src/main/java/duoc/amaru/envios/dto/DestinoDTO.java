package duoc.amaru.envios.dto;

import duoc.amaru.envios.enums.Comuna;
import duoc.amaru.envios.enums.Region;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DestinoDTO {
    private Long id;
    private String etiqueta;
    private String calle;
    private int nCalle;
    private int nCasaDpto;
    private String detalle;
    private Comuna comuna;
    private Region region;
}
