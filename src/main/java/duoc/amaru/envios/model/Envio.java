package duoc.amaru.envios.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "envio")
public class Envio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEnvio;

    @Column(name = "pedido_id", unique = true)
    @NotNull
    private Long idPedido;

    @Column(name = "direccion_id")
    @NotNull(message = "Se requiere la dirección de envió")
    private Long direccionDestino;

    @Column(name = "fecha_ini")
    private LocalDate fechaCreacion;

    @Column(name = "fecha_fin")
    private LocalDate fechaEntrega;
    
    private String estado;
    
}
