package com.mitocode.tarea.controller.v1;

import com.mitocode.tarea.controller.RestResponseEntityExceptionHandler;
import com.mitocode.tarea.model.Producto;
import com.mitocode.tarea.service.ProductoService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static com.mitocode.tarea.controller.v1.AbstractRestControllerTest.asJsonString;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProductoControllerTest {

    @Mock
    ProductoService service;

    @InjectMocks
    ProductoController controller;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();
    }

    @Test
    public void lista() throws Exception {
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

        List<Producto> productos = Arrays.asList(produto1, produto2);

        when(service.listar()).thenReturn(productos);

        mockMvc.perform(get(ProductoController.BASE_URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    public void listarPorId() throws Exception {

        Producto produto1 = Producto.builder()
                .idProducto(1L)
                .nombre("Chocolate Oscuro")
                .marca("Hershey's")
                .build();

        when(service.leerPorId(anyLong())).thenReturn(produto1);

        mockMvc.perform(get(ProductoController.BASE_URL + "/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre", equalTo("Chocolate Oscuro")))
                .andExpect(jsonPath("$.marca", equalTo("Hershey's")));
    }

    @Test
    public void registrar() throws Exception {
        Producto producto = Producto.builder()
                .idProducto(1L)
                .nombre("Michelob Ultra")
                .marca("Budweiser")
                .build();

        when(service.registrar(any())).thenReturn(producto);

        mockMvc.perform(post(ProductoController.BASE_URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(producto)))
                .andExpect(status().isCreated());
    }

    @Test
    public void modificar() throws Exception {

        Producto producto = Producto.builder()
                .idProducto(1L)
                .nombre("Heineken")
                .marca("Heineken")
                .build();

        when(service.modificar(any(Producto.class))).thenReturn(producto);

        mockMvc.perform(put(ProductoController.BASE_URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(producto)))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.nombre", equalTo("Heineken")))
                .andExpect(jsonPath("$.marca", equalTo("Heineken")));
    }

    @Test
    public void eliminar() throws Exception {
        mockMvc.perform(delete(ProductoController.BASE_URL + "/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}