package duoc.amaru.envios.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import duoc.amaru.envios.model.Envio;
import duoc.amaru.envios.repository.EnvioRepo;

public class EnvioServicioTest {

    @Mock
    private EnvioRepo envioRepo;

    @InjectMocks
    private EnvioServicio envioServicio;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void crearEnvio_Exito() {
        // 1. Preparar datos
        Long idPedido = 100L;
        Long idDireccion = 50L;

        // 2. Comportamiento del Mock (Simulamos que el pedido NO tiene un envío asignado aún)
        when(envioRepo.existsByIdPedido(idPedido)).thenReturn(false);

        // 3. Ejecutar el método
        ResponseEntity<?> response = envioServicio.crearEnvio(idPedido, idDireccion);

        // 4. Verificaciones
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Envio creado", response.getBody());
        
        // Verificamos que efectivamente se haya llamado al método save() en la base de datos 1 vez
        verify(envioRepo, times(1)).save(any(Envio.class));
    }

    @Test
    void crearEnvio_PedidoYaTieneEnvio_Retorna400() {
        // 1. Preparar datos
        Long idPedido = 100L;
        Long idDireccion = 50L;

        // 2. Comportamiento del Mock (Simulamos que YA EXISTE un envío para ese pedido)
        when(envioRepo.existsByIdPedido(idPedido)).thenReturn(true);

        // 3. Ejecutar el método
        ResponseEntity<?> response = envioServicio.crearEnvio(idPedido, idDireccion);

        // 4. Verificaciones
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Un envio para este pedido ya existe", response.getBody());
        
        // Verificamos que NUNCA se haya llamado al save() porque el proceso fue abortado
        verify(envioRepo, times(0)).save(any(Envio.class));
    }
}