package com.spring.demo04.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.spring.demo04.model.Mercado;
import com.spring.demo04.model.Producto;
import com.spring.demo04.repository.MercadoRepository;
import com.spring.demo04.repository.ProductoRepository;

/**
 * INTEGRATION TEST (JPA + consulta derivada):
 */
@DataJpaTest
@ActiveProfiles("test")
class ProductoRepositoryTest {

    @Autowired
    private MercadoRepository mercadoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Test
    void guardar_producto_con_mercado_y_buscar_por_mercadoId() {
        Mercado mercado = mercadoRepository.save(new Mercado("Mercado Central"));

        Producto p1 = new Producto("Lechuga", new BigDecimal("1.20"), 20);
        p1.setMercado(mercado);

        Producto p2 = new Producto("Naranjas", new BigDecimal("3.99"), 15);
        p2.setMercado(mercado);

        productoRepository.save(p1);
        productoRepository.save(p2);

        List<Producto> productos = productoRepository.findByMercadoId(mercado.getId());
        assertEquals(2, productos.size());
    }

    @Test
    void findByMercadoId_si_no_hay_productos_devuelve_lista_vacia() {
        Mercado mercado = mercadoRepository.save(new Mercado("Mercado Vacío"));

        List<Producto> productos = productoRepository.findByMercadoId(mercado.getId());
        assertTrue(productos.isEmpty());
    }
}
