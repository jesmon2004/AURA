package com.spring.demo04.e2e;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.spring.demo04.model.Mercado;
import com.spring.demo04.model.Producto;

import tools.jackson.databind.ObjectMapper;

/**
 * E2E TEST de productos:
 * - Creamos un mercado
 * - Creamos un producto dentro del mercado
 * - Listamos productos del mercado
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ProductoE2ETest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void crear_producto_en_mercado_y_listar() throws Exception {
        // 1) Creamos mercado
        Mercado mercado = new Mercado("Mercado Productos");
        String mercadoJson = objectMapper.writeValueAsString(mercado);

        String mercadoResponse = mockMvc.perform(post("/api/mercados")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mercadoJson))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Long mercadoId = objectMapper.readTree(mercadoResponse).get("id").asLong();

        // 2) Creamos producto dentro del mercado
        Producto p = new Producto("Queso", new BigDecimal("4.50"), 7);
        String productoJson = objectMapper.writeValueAsString(p);

        mockMvc.perform(post("/api/mercados/" + mercadoId + "/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productoJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Queso"));

        // 3) Listamos productos del mercado
        mockMvc.perform(get("/api/mercados/" + mercadoId + "/productos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Queso"));
    }

}
