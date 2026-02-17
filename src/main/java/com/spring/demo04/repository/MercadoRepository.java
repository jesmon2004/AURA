package com.spring.demo04.repository;

import com.spring.demo04.model.Mercado;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MercadoRepository extends JpaRepository<Mercado, Long> {
}
