package duoc.amaru.envios.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import duoc.amaru.envios.model.Envio;
import java.util.List;


public interface EnvioRepo extends JpaRepository<Envio, Long> {
    boolean existsByIdPedido(Long id);

    boolean existsByDireccionDestino(Long id);

    List<Envio> findByEstado(String estado);
}
