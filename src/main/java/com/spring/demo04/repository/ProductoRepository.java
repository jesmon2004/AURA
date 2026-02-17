package com.spring.demo04.repository;

import com.spring.demo04.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ProductoRepository extends JpaRepository<Producto, Long> {

    List<Producto> findByMercadoId(Long mercadoId);
}
