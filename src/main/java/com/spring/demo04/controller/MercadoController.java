package com.spring.demo04.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.spring.demo04.model.Mercado;
import com.spring.demo04.model.Producto;
import com.spring.demo04.service.MercadoService;
import com.spring.demo04.service.ProductoService;

import jakarta.validation.Valid;

/**
 * Controller REST para mercados.
 *
 */
@RestController
@RequestMapping("/api/mercados")
public class MercadoController {

	@Autowired
    private MercadoService mercadoService;
	@Autowired
    private ProductoService productoService;

    @GetMapping
    public List<Mercado> listar() {
        return mercadoService.listar();
    }

    @GetMapping("/{id}")
    public Mercado obtener(@PathVariable Long id) {
        return mercadoService.obtenerPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mercado crear(@Valid @RequestBody Mercado mercado) {
    	if(mercado == null) {
    		//HTTP 400 Bad Request
    	}
        return mercadoService.crear(mercado);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        mercadoService.eliminar(id);
    }

    // ---------------------------
    // Sub-recursos: productos del mercado
    // ---------------------------

    /**
     * Crea un producto dentro de un mercado concreto.
     * POST /api/mercados/{mercadoId}/productos
     */
    @PostMapping("/{mercadoId}/productos")
    @ResponseStatus(HttpStatus.CREATED)
    public Producto crearProductoEnMercado(@PathVariable Long mercadoId, @Valid @RequestBody Producto producto) {
        return productoService.crearEnMercado(mercadoId, producto);
    }

    /**
     * Lista productos de un mercado:
     * GET /api/mercados/{mercadoId}/productos
     */
    @GetMapping("/{mercadoId}/productos")
    public List<Producto> listarProductosDeMercado(@PathVariable Long mercadoId) {
        return productoService.listarPorMercado(mercadoId);
    }
}
