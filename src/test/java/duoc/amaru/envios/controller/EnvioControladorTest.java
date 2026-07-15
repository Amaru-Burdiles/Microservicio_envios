package duoc.amaru.envios.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import duoc.amaru.envios.dto.CrearEnvioDTO;
import duoc.amaru.envios.service.EnvioServicio;
import tools.jackson.databind.ObjectMapper;

@WebMvcTest(EnvioControlador.class)
public class EnvioControladorTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private EnvioServicio envioServicio;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void postEnvio_Exito() throws Exception {
        CrearEnvioDTO dtoEntrada = new CrearEnvioDTO();
        dtoEntrada.setPedidoId(10L);
        dtoEntrada.setDireccionId(20L);

        // SOLUCIÓN AQUÍ: Agregamos el casteo (ResponseEntity) antes de la respuesta
        when(envioServicio.crearEnvio(10L, 20L))
                .thenReturn((ResponseEntity) ResponseEntity.status(201).body("Envío creado correctamente"));

        mockMvc.perform(post("/api/v1/envios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dtoEntrada)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Envío creado correctamente"));

        verify(envioServicio, times(1)).crearEnvio(10L, 20L);
    }

    @Test
    void postEnvio_FallaPorLogicaDeNegocio() throws Exception {
        CrearEnvioDTO dtoEntrada = new CrearEnvioDTO();
        dtoEntrada.setPedidoId(99L);
        dtoEntrada.setDireccionId(20L);

        // SOLUCIÓN AQUÍ: Agregamos el casteo (ResponseEntity) antes de la respuesta
        when(envioServicio.crearEnvio(99L, 20L))
                .thenReturn((ResponseEntity) ResponseEntity.status(404).body("Pedido no encontrado"));

        mockMvc.perform(post("/api/v1/envios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dtoEntrada)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Pedido no encontrado"));

        verify(envioServicio, times(1)).crearEnvio(99L, 20L);
    }
}