package com.spring.demo04.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

/**
 * Producto (N) ---- (1) Mercado
 *
 * Este lado ("Producto") es el lado "hijo" y contiene la FK hacia mercados.
 */
@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre del producto.
     */
    @NotBlank(message = "El nombre del producto es obligatorio")
    @Size(min = 2, max = 80, message = "El nombre debe tener entre 2 y 80 caracteres")
    @Column(nullable = false, length = 80)
    private String nombre;

    /**
     * Precio del producto.
     * - DecimalMin: debe ser >= 0.01
     * - Digits: limita cantidad de dígitos enteros y decimales
     */
    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor que 0")
    @Digits(integer = 10, fraction = 2, message = "El precio debe tener como máximo 2 decimales")
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal precio;

    /**
     * Stock disponible.
     */
    @Min(value = 0, message = "El stock no puede ser negativo")
    @Column(nullable = false)
    private int stock;

    /**
     * Mercado propietario del producto.
     *
     * fetch = LAZY: por defecto no carga el mercado entero al leer el producto (mejor rendimiento).
     */
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mercado_id", nullable = false)
    private Mercado mercado;

    public Producto() {}

    public Producto(String nombre, BigDecimal precio, int stock) {
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }

    public Long getId() { return id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public Mercado getMercado() { return mercado; }
    public void setMercado(Mercado mercado) { this.mercado = mercado; }
}
