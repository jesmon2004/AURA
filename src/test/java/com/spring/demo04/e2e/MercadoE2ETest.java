package com.spring.demo04.e2e;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.spring.demo04.model.Mercado;

import tools.jackson.databind.ObjectMapper;

/**
 * E2E TEST (API completa con MockMvc):
 * - levantamos todo Spring Boot
 * - hacemos peticiones HTTP simuladas a los endpoints
 * - usamos perfil test (H2)
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class MercadoE2ETest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void crear_y_obtener_mercado() throws Exception {
        Mercado m = new Mercado("Mercado E2E");

        String json = objectMapper.writeValueAsString(m);

        // 1) Creamos la request
        String response = mockMvc.perform(post("/api/mercados")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Mercado E2E"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        // 2) Sacamos el ID del JSON devuelto
        Long id = objectMapper.readTree(response).get("id").asLong();

        // 3) Consultamos para verificar que se guardó correctamente
        mockMvc.perform(get("/api/mercados/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));
    }

    @Test
    void validacion_si_nombre_vacio_devuelve_400() throws Exception {
        Mercado m = new Mercado("");
        String json = objectMapper.writeValueAsString(m);

        mockMvc.perform(post("/api/mercados")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.nombre").exists());
    }
}
