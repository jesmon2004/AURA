package com.spring.demo04.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.spring.demo04.model.Mercado;
import com.spring.demo04.repository.MercadoRepository;

/**
 * INTEGRATION TEST (JPA):
 * - levantamos solo la capa JPA con @DataJpaTest
 * - usamos H2 en memoria (perfil test)
 */
@DataJpaTest
@ActiveProfiles("test")
class MercadoRepositoryTest {

    @Autowired
    private MercadoRepository mercadoRepository;

    @Test
    void guardar_y_leer_mercado_funciona() {
        Mercado m = new Mercado("Mercado Sur");

        Mercado guardado = mercadoRepository.save(m);

        assertNotNull(guardado.getId());

        Mercado encontrado = mercadoRepository.findById(guardado.getId()).orElseThrow();
        assertEquals("Mercado Sur", encontrado.getNombre());
    }

    @Test
    void borrar_mercado_funciona() {
        Mercado m = mercadoRepository.save(new Mercado("Mercado Este"));
        Long id = m.getId();

        mercadoRepository.deleteById(id);

        assertTrue(mercadoRepository.findById(id).isEmpty());
    }
}
