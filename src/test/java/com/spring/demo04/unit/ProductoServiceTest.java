package com.spring.demo04.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.spring.demo04.exception.ResourceNotFoundException;
import com.spring.demo04.model.Mercado;
import com.spring.demo04.model.Producto;
import com.spring.demo04.repository.MercadoRepository;
import com.spring.demo04.repository.ProductoRepository;
import com.spring.demo04.service.impl.ProductoServiceImpl;

/**
 * UNIT TEST (ProductoService): comprobamos flujo "crearEnMercado"
 */
class ProductoServiceTest {

    @Test
    void crearEnMercado_si_mercado_existe_guarda_producto() {
        ProductoRepository prodRepo = mock(ProductoRepository.class);
        MercadoRepository mercRepo = mock(MercadoRepository.class);

        ProductoServiceImpl service = new ProductoServiceImpl(prodRepo, mercRepo);

        Mercado mercado = new Mercado("Mercado Norte");
        when(mercRepo.findById(1L)).thenReturn(Optional.of(mercado));

        Producto p = new Producto("Tomates", new BigDecimal("2.50"), 10);
        when(prodRepo.save(any(Producto.class))).thenAnswer(inv -> inv.getArgument(0));

        Producto guardado = service.crearEnMercado(1L, p);

        assertEquals("Tomates", guardado.getNombre());
        assertNotNull(guardado.getMercado());
        assertEquals("Mercado Norte", guardado.getMercado().getNombre());
        verify(prodRepo, times(1)).save(any(Producto.class));
    }

    @Test
    void crearEnMercado_si_mercado_no_existe_lanza_404() {
        ProductoRepository prodRepo = mock(ProductoRepository.class);
        MercadoRepository mercRepo = mock(MercadoRepository.class);

        ProductoServiceImpl service = new ProductoServiceImpl(prodRepo, mercRepo);

        when(mercRepo.findById(404L)).thenReturn(Optional.empty());

        Producto p = new Producto("Pan", new BigDecimal("1.10"), 5);

        assertThrows(ResourceNotFoundException.class, () -> service.crearEnMercado(404L, p));
    }
}
