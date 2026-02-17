package com.spring.demo04.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

/**
 * Mercado (1) ---- (N) Productos
 *
 */
@Entity
@Table(name = "mercados")
public class Mercado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre del mercado.
     */
    @NotBlank(message = "El nombre del mercado es obligatorio")
    @Size(min = 3, max = 80, message = "El nombre debe tener entre 3 y 80 caracteres")
    @Column(nullable = false, length = 80)
    private String nombre;

    /**
     * Lista de productos del mercado.
     * 
     */
    @JsonManagedReference
    @OneToMany(mappedBy = "mercado", cascade = CascadeType.ALL)
    private List<Producto> productos = new ArrayList<>();

    public Mercado() {}

    public Mercado(String nombre) {
        this.nombre = nombre;
    }

   
    public void addProducto(Producto producto) {
        productos.add(producto);
        producto.setMercado(this);
    }

    public void removeProducto(Producto producto) {
        productos.remove(producto);
        producto.setMercado(null);
    }

	public Long getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Producto> getProductos() {
		return productos;
	}

	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}
}
