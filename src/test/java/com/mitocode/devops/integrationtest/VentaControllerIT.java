package com.mitocode.devops.integrationtest;

import com.mitocode.devops.model.DetalleVenta;
import com.mitocode.devops.model.Persona;
import com.mitocode.devops.model.Producto;
import com.mitocode.devops.model.Venta;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("dev")
public class VentaControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    private static final String URL = "/api/v1/ventas/";

    @Test
    public void listar() throws Exception {

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<String> requestEntity = new HttpEntity<String>(null, headers);

        ResponseEntity<List<Venta>> responseEntity = restTemplate.exchange(URL, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<Venta>>() {});

        int status = responseEntity.getStatusCodeValue();
        List<Venta> resultProducto = responseEntity.getBody();

        assertEquals("Response status incorrecto!", HttpStatus.OK.value(), status);
        assertNotNull(resultProducto);
    }

    @Test
    public void insertar () {
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

        //ejecuta
        ResponseEntity<Venta> responseEntity = restTemplate.postForEntity(URL, venta, Venta.class);

        int status = responseEntity.getStatusCodeValue();

        // verifica respuesta
        assertEquals("Response status incorrecto!", HttpStatus.CREATED.value(), status);
    }

}
