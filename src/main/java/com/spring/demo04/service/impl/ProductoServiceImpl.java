package com.spring.demo04.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.demo04.exception.ResourceNotFoundException;
import com.spring.demo04.model.Mercado;
import com.spring.demo04.model.Producto;
import com.spring.demo04.repository.MercadoRepository;
import com.spring.demo04.repository.ProductoRepository;
import com.spring.demo04.service.ProductoService;


@Service
public class ProductoServiceImpl implements ProductoService {

	@Autowired
    private ProductoRepository productoRepository;
	@Autowired
    private MercadoRepository mercadoRepository;

    public ProductoServiceImpl(ProductoRepository productoRepository, MercadoRepository mercadoRepository) {
        this.productoRepository = productoRepository;
        this.mercadoRepository = mercadoRepository;
    }

    @Override
    public List<Producto> listar() {
        return productoRepository.findAll();
    }

    @Override
    public Producto crearEnMercado(Long mercadoId, Producto producto) {
        // 1) Aseguramos que el mercado existe
        Mercado mercado = mercadoRepository.findById(mercadoId)
                .orElseThrow(() -> new ResourceNotFoundException("Mercado no encontrado con id=" + mercadoId));

        // 2) Enlazamos el producto con el mercado
        producto.setMercado(mercado);

        // 3) Guardamos el producto
        return productoRepository.save(producto);
    }

    @Override
    public Producto obtenerPorId(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id=" + id));
    }

    @Override
    public List<Producto> listarPorMercado(Long mercadoId) {
        // De nuevo: si el mercado no existe, devolvemos 404 (mejor UX / API)
        mercadoRepository.findById(mercadoId)
                .orElseThrow(() -> new ResourceNotFoundException("Mercado no encontrado con id=" + mercadoId));

        return productoRepository.findByMercadoId(mercadoId);
    }

    @Override
    public void eliminar(Long id) {
        obtenerPorId(id);
        productoRepository.deleteById(id);
    }
}
