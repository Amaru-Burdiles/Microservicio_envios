package duoc.amaru.envios.dto;

import duoc.amaru.envios.enums.Comuna;
import duoc.amaru.envios.enums.Region;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddDespachoDTO {
    private String nombreTienda;
    
    @NotNull
    private Region region;

    @NotNull
    private Comuna comuna;

    @NotBlank
    private String calle;

    @Positive
    private int nCalle;

    private double latitud;
    private double longitud;
}
