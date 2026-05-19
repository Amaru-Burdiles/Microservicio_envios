package duoc.amaru.envios.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import duoc.amaru.envios.model.Envio;

public interface EnvioRepo extends JpaRepository<Envio, Long> {
    
}
