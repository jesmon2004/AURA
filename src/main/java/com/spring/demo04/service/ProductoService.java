package com.spring.demo04.service;

import com.spring.demo04.model.Producto;

import java.util.List;


public interface ProductoService {

    List<Producto> listar();

    Producto crearEnMercado(Long mercadoId, Producto producto);

    Producto obtenerPorId(Long id);

    List<Producto> listarPorMercado(Long mercadoId);

    void eliminar(Long id);
}
