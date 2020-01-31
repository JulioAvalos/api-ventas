package com.mitocode.devops.service.impl;

import com.mitocode.devops.model.Producto;
import com.mitocode.devops.repo.ProductoRepo;
import com.mitocode.devops.service.ProductoService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ProductoServiceImplTest {

    @Mock
    ProductoRepo repo;

    ProductoService service;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        service = new ProductoServiceImpl(repo);
    }

    @Test
    public void registrar() {
        Producto produto1 = Producto.builder()
                .idProducto(1L)
                .nombre("Chocolate Blanco")
                .marca("Hershey's")
                .build();

        when(repo.save(any(Producto.class))).thenReturn(produto1);

        Producto productoRegistrado = service.registrar(produto1);
        assertEquals("Chocolate Blanco", productoRegistrado.getNombre());
    }

    @Test
    public void modificar() {

        Producto produto1 = Producto.builder()
                .idProducto(1L)
                .nombre("Chocolate con mani")
                .marca("Hershey's")
                .build();

        when(repo.save(any(Producto.class))).thenReturn(produto1);

        Producto productoRegistrado = service.registrar(produto1);
        productoRegistrado.setMarca("Nestle");

        Producto modificado = service.modificar(productoRegistrado);

        assertEquals(new Long(1L), modificado.getIdProducto());
        assertEquals("Chocolate con mani", modificado.getNombre());
        assertEquals("Nestle", modificado.getMarca());
    }

    @Test
    public void listar() {
        Producto producto1 = Producto.builder()
                .idProducto(1L)
                .nombre("Cianurito Feliz XD")
                .marca("Laboratorios Siguenza")
                .build();

        Producto producto2 = Producto.builder()
                .idProducto(1L)
                .nombre("Paracetamol")
                .marca("Laboratorios Siguenza")
                .build();
        when(repo.findAll()).thenReturn(Arrays.asList(producto1, producto2));

        List<Producto> productos = service.listar();

        assertEquals(2, productos.size());
    }

    @Test
    public void leerPorId() {
        Producto producto1 = Producto.builder()
                .idProducto(1L)
                .nombre("Cubo Rubik")
                .marca("Hasbro")
                .build();

        when(repo.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(producto1));

        Producto producto = service.leerPorId(1L);

        assertEquals("Cubo Rubik", producto1.getNombre());
    }

    @Test
    public void eliminar() {

        Long id = 1L;

        repo.deleteById(id);

        verify(repo, times(1)).deleteById(anyLong());
    }
}