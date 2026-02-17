package com.spring.demo04.service;

import com.spring.demo04.model.Mercado;

import java.util.List;


public interface MercadoService {

    List<Mercado> listar();

    Mercado crear(Mercado mercado);

    Mercado obtenerPorId(Long id);

    void eliminar(Long id);
}
