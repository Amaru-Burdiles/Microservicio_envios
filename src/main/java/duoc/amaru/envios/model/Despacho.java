package duoc.amaru.envios.model;

import duoc.amaru.envios.enums.Comuna;
import duoc.amaru.envios.enums.Region;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "direccion_despacho")
public class Despacho {
    private Long idDireccion;
    private String nombreTienda;
    private Region region;
    private Comuna comuna;
    private String calle;
    private int nCalle;
    private double longitud;
    private double latitud;
}