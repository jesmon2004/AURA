package com.spring.demo04.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.spring.demo04.exception.ResourceNotFoundException;
import com.spring.demo04.model.Mercado;
import com.spring.demo04.repository.MercadoRepository;
import com.spring.demo04.service.impl.MercadoServiceImpl;

class MercadoServiceTest {

    @Test
    void crear_mercado_guarda_y_devuelve() {
        MercadoRepository repo = mock(MercadoRepository.class);
        MercadoServiceImpl service = new MercadoServiceImpl(repo);

        Mercado entrada = new Mercado("Mercado Central");
        Mercado guardado = new Mercado("Mercado Central");

        when(repo.save(any(Mercado.class))).thenReturn(guardado);

        Mercado resultado = service.crear(entrada);

        assertNotNull(resultado);
        // CORREGIDO: quitado el "1" que hacía fallar el test
        assertEquals("Mercado Central", resultado.getNombre());
        verify(repo, times(1)).save(any(Mercado.class));
    }

    @Test
    void obtenerPorId_si_no_existe_lanza_404() {
        MercadoRepository repo = mock(MercadoRepository.class);
        MercadoServiceImpl service = new MercadoServiceImpl(repo);

        when(repo.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.obtenerPorId(99L));
    }
}