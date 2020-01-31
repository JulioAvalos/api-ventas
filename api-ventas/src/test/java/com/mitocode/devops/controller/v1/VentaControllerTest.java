package com.mitocode.devops.controller.v1;

import com.mitocode.devops.controller.RestResponseEntityExceptionHandler;
import com.mitocode.devops.model.DetalleVenta;
import com.mitocode.devops.model.Persona;
import com.mitocode.devops.model.Producto;
import com.mitocode.devops.model.Venta;
import com.mitocode.devops.service.VentaService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.mitocode.devops.controller.v1.AbstractRestControllerTest.asJsonString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VentaControllerTest {

    @Mock
    VentaService service;

    @InjectMocks
    VentaController controller;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();
    }

    @Test
    public void registrar() throws Exception {
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


        Venta venta = Venta.builder()
                .idVenta(1L)
                .persona(
                        Persona.builder()
                                .idPersona(1L)
                                .nombres("Solange")
                                .apellidos("Rodriguez")
                                .build()
                )
//                .fecha(LocalDateTime.parse(fechaHora,formatter))
                .importe(79.99)
                .detalleVenta(detalles)
                .build();

        when(service.registrar(any())).thenReturn(venta);


        mockMvc.perform(post(VentaController.BASE_URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(venta)))
                .andExpect(status().isCreated());
    }

    @Test
    public void listar() throws Exception {

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

        List<Venta> ventas = Arrays.asList(venta1, venta2);

        when(service.listar()).thenReturn(ventas);

        mockMvc.perform(get(VentaController.BASE_URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
}