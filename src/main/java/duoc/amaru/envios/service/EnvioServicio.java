package duoc.amaru.envios.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import duoc.amaru.envios.model.Envio;
import duoc.amaru.envios.repository.EnvioRepo;

@Service
public class EnvioServicio {
    @Autowired
    private EnvioRepo envioRepo;

    // CREACION ENVIO
    public boolean crearEnvio(Envio nuevoEnvio) {
        envioRepo.save(nuevoEnvio);
        return true;
    }

    // ACTUALIZAR ESTADO ENVIO
    public boolean updateEstado(Long id, String estado) {
        for (Envio e : envioRepo.findAll()) {
            if (e.getIdEnvio() == id) {
                e.setEstado(estado);
                return true;
            }

        }

        return false;
    }
}
