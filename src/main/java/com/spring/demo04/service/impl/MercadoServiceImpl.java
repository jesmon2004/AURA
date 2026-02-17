package com.spring.demo04.service.impl;

import com.spring.demo04.exception.ResourceNotFoundException;
import com.spring.demo04.model.Mercado;
import com.spring.demo04.repository.MercadoRepository;
import com.spring.demo04.service.MercadoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MercadoServiceImpl implements MercadoService {

	@Autowired
    private MercadoRepository mercadoRepository;

	public MercadoServiceImpl(MercadoRepository mercadoRepository) {
        this.mercadoRepository = mercadoRepository;
    }
	
    @Override
    public List<Mercado> listar() {
        return mercadoRepository.findAll();
    }

    @Override
    public Mercado crear(Mercado mercado) {
        // En este ejemplo, solo guardamos. La validación (@Valid) vive en el Controller.
        return mercadoRepository.save(mercado);
    }

    @Override
    public Mercado obtenerPorId(Long id) {
        return mercadoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mercado no encontrado con id=" + id));
    }

    @Override
    public void eliminar(Long id) {
        // Comprobamos antes para devolver 404 si no existe.
        obtenerPorId(id);
        mercadoRepository.deleteById(id);
    }
}
