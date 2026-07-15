package duoc.amaru.envios.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import duoc.amaru.envios.client.SesionClient;
import duoc.amaru.envios.dto.AddDespachoDTO;
import duoc.amaru.envios.enums.Comuna;
import duoc.amaru.envios.enums.Region;
import duoc.amaru.envios.model.Despacho;
import duoc.amaru.envios.repository.DespachoRepo;

@Service
public class DespachoServicio {
    @Autowired
    private DespachoRepo despachoRepo;

    @Autowired
    private SesionClient sesionClient;

    // AGREGAR DIRECCION
    public Despacho addDireccion(Long userId, AddDespachoDTO dir) {
        // Validar empleado
        sesionClient.validarEmpleado(userId, 3);

        Despacho nueva = new Despacho();
        nueva.setNombreTienda(dir.getNombreTienda());
        nueva.setRegion(dir.getRegion());
        nueva.setComuna(dir.getComuna());
        nueva.setCalle(dir.getCalle());
        nueva.setNCalle(dir.getNCalle());
        nueva.setLatitud(dir.getLatitud());
        nueva.setLongitud(dir.getLongitud());

        Despacho guardada = despachoRepo.save(nueva);

        if (guardada.getNombreTienda().isBlank() || guardada.getNombreTienda() == null) {
            guardada.setNombreTienda("Despacho #"+ guardada.getIdDireccion());
            return despachoRepo.save(guardada);
        }

        return guardada;
    }

    // OBTENER TODAS LAS DIRECCIONES
    public List<Despacho> getAllDirecciones(Long userId) {
        // Validar empleado
        sesionClient.validarEmpleado(userId, 3);

        return despachoRepo.findAll();
    }

    // OBTENER LA DIRECCION MÁS CERCANA
    public Despacho getMasCercana(double latitud, double longitud) {
        List<Despacho> direcciones = despachoRepo.findAll();

        // Para cada direccion, su latitud y longitud [[lat, long], [lat, long]...]
        Set<List<Double>> coords = new HashSet<>();
        for (Despacho dir : direcciones) {
            // [latitud, longitud]
            List<Double> latLong = List.of(dir.getLatitud(), dir.getLongitud());
            coords.add(latLong);
        }

        // TODO: fórmula de Haversine
        return null;
    }

    // OBTENER LAS DIRECCIONES MAS CERCANAS DENTRO DE UN RANGO
    public List<Despacho> getCercanas(double latitud, double longitud, double radio) {
        // TODO: fórmula de Haversine
        return null;
    }

    // OBTENER DIRECCION POR ID
    public Despacho getDireccionById(Long userId, Long dirId) {
        // Validar empleado
        if (userId > 0)
            sesionClient.validarEmpleado(dirId, 3);

        Despacho reply = despachoRepo.findById(dirId).orElse(null);
        if (reply == null)
            return null;

        return reply;
    }

    // OBTENER DIRECCION POR COMUNA
    public List<Despacho> getDirByComuna(Long userId, Comuna comuna) {
        // Validar empleado
        if (userId > 0)
            sesionClient.validarEmpleado(userId, 3);

        return despachoRepo.findByComuna(comuna);
    }

    // OBTENER DIRECCION POR REGION
    public List<Despacho> getDirByRegion(Long userId, Region region) {
        // Validar empleado
        if (userId > 0)
            sesionClient.validarEmpleado(userId, 3);

        return despachoRepo.findByRegion(region);
    }

}
