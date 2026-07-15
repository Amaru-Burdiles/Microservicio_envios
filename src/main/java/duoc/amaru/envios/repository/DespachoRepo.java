package duoc.amaru.envios.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import duoc.amaru.envios.model.Despacho;
import java.util.List;
import duoc.amaru.envios.enums.Comuna;
import duoc.amaru.envios.enums.Region;



public interface DespachoRepo extends JpaRepository<Despacho, Long> {
    List<Despacho> findByComuna(Comuna comuna);
    List<Despacho> findByRegion(Region region);
}
