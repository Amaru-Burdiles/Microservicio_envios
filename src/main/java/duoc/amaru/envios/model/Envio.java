package duoc.amaru.envios.model;

import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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

    @OneToOne(mappedBy = "envio", cascade = CascadeType.ALL, orphanRemoval = true)
    private Long idPedido;

    @OneToOne(mappedBy = "envio", cascade = CascadeType.ALL, orphanRemoval = true)
    private Long direccionDestino;

    @Column(name = "fecha_ini")
    private LocalDate fechaCreacion;

    @Column(name = "fecha_fin")
    private LocalDate fechaEntrega;
    private String estado;
    
    public Envio(Long idPedido, Long direccionDestino, LocalDate fechaEntrega) {
        this.idPedido = idPedido;
        this.direccionDestino = direccionDestino;
        this.fechaEntrega = fechaEntrega;
        this.fechaCreacion = LocalDate.now();
        this.estado = "Preparando";
    }
}
