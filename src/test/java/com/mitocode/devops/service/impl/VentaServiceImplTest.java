package com.mitocode.devops.service.impl;

import com.mitocode.devops.model.DetalleVenta;
import com.mitocode.devops.model.Persona;
import com.mitocode.devops.model.Producto;
import com.mitocode.devops.model.Venta;
import com.mitocode.devops.repo.VentaRepo;
import com.mitocode.devops.service.VentaService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class VentaServiceImplTest {

    @Mock
    VentaRepo repo;

    VentaService service;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        service = new VentaServiceImpl(repo);
    }

    @Test
    public void registrar() {
        Producto produto1 = Producto.builder()
                .idProducto(1L)
                .nombre("Chocolate Blanco")
                .marca("Hershey's")
                .build();

        Producto producto2 = Producto.builder()
                .idProducto(3L)
                .nombre("Heineken")
                .marca("Heineken")
                .build();

        DetalleVenta detalleVenta1 = DetalleVenta.builder()
                .idDetalleVenta(1L)
                .producto(produto1)
                .cantidad(10)
                .build();

        DetalleVenta detalleVenta2 = DetalleVenta.builder()
                .idDetalleVenta(2L)
                .producto(producto2)
                .cantidad(5)
                .build();

        Set<DetalleVenta> detalles = new HashSet<>();
        detalles.add(detalleVenta1);
        detalles.add(detalleVenta2);

        String fechaHora = "2016-03-04 11:30:00";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime time1 = LocalDateTime.now();


        Persona persona =  Persona.builder()
                .idPersona(1L)
                .nombres("Solange")
                .apellidos("Rodriguez")
                .build();

        Venta venta = Venta.builder()
                .idVenta(1L)
                .persona(persona)
//                .fecha(LocalDateTime.parse(fechaHora,formatter))
                .importe(79.99)
                .detalleVenta(detalles)
                .build();

        when(repo.save(any(Venta.class))).thenReturn(venta);

        Persona personaVer = Persona.builder()
                .nombres("Solange")
                .apellidos("Rodriguez")
                .build();

        assertEquals(personaVer.getNombres(), venta.getPersona().getNombres());
        assertEquals(personaVer.getApellidos(), venta.getPersona().getApellidos());

        assertEquals("Se calcula el importe del a venta.", 79.99, venta.getImporte(), 0);
    }

    @Test
    public void listar() {

        Producto produto1 = Producto.builder()
                .idProducto(1L)
                .nombre("Chocolate Blanco")
                .marca("Hershey's")
                .build();

        Producto produto2 = Producto.builder()
                .idProducto(2L)
                .nombre("Fanta")
                .marca("Coca-Cola")
                .build();

        Producto producto3 = Producto.builder()
                .idProducto(3L)
                .nombre("Heineken")
                .marca("Heineken")
                .build();

        DetalleVenta detalleVenta1 = DetalleVenta.builder()
                .idDetalleVenta(1L)
                .producto(produto1)
                .cantidad(10)
                .build();

        DetalleVenta detalleVenta2 = DetalleVenta.builder()
                .idDetalleVenta(2L)
                .producto(produto2)
                .cantidad(5)
                .build();

        DetalleVenta detalleVenta3 = DetalleVenta.builder()
                .idDetalleVenta(3L)
                .producto(producto3)
                .cantidad(20)
                .build();

        Set<DetalleVenta> detalles1 = new HashSet<>();
        detalles1.add(detalleVenta1);
        detalles1.add(detalleVenta2);

        Set<DetalleVenta> detalles2 = new HashSet<>();
        detalles2.add(detalleVenta3);

        Venta venta1 = Venta.builder()
                .idVenta(1L)
                .persona(
                        Persona.builder()
                                .idPersona(1L)
                                .nombres("Genaro")
                                .apellidos("Eriol")
                                .build()
                )
                .fecha(LocalDateTime.now().minusDays(1))
                .importe(79.99)
                .detalleVenta(detalles1)
                .build();

        Venta venta2 = Venta.builder()
                .idVenta(2L)
                .persona(
                        Persona.builder()
                                .nombres("Abigail")
                                .apellidos("Granadino")
                                .build()
                )
                .fecha(LocalDateTime.now().minusDays(3))
                .importe(149.99)
                .detalleVenta(detalles2)
                .build();

        when(repo.findAll()).thenReturn(Arrays.asList(venta1, venta2));

        List<Venta> ventas = service.listar();

        assertEquals(2, ventas.size());
    }

}