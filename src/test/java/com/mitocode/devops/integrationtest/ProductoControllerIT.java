package com.mitocode.devops.integrationtest;

import com.mitocode.devops.model.Producto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("dev")
public class ProductoControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    private static final String URL = "/api/v1/productos/";

    @Test
    public void listar() throws Exception {

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<String> requestEntity = new HttpEntity<String>(null, headers);

        ResponseEntity<List<Producto>> responseEntity = restTemplate.exchange(URL, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<Producto>>() {});

        int status = responseEntity.getStatusCodeValue();
        List<Producto> resultProducto = responseEntity.getBody();

        assertEquals("Response status incorrecto!", HttpStatus.OK.value(), status);
        assertNotNull(resultProducto);
    }

    @Test
    public void listarPorId() throws Exception {

        ResponseEntity<Producto> responseEntity = restTemplate.getForEntity(URL + "{id}", Producto.class, 1L);

        int status = responseEntity.getStatusCodeValue();
        Producto resultProducto = responseEntity.getBody();

        assertEquals("Response status incorrecto!", HttpStatus.OK.value(), status);
        assertNotNull(resultProducto);
        assertEquals(1L, resultProducto.getIdProducto().longValue());
    }

    public void insertar() {
        Producto producto = Producto.builder()
                .idProducto(1L)
                .nombre("Heineken")
                .marca("Heineken")
                .build();

        //ejecuta
        ResponseEntity<Producto> responseEntity = restTemplate.postForEntity(URL, producto, Producto.class);

        int status = responseEntity.getStatusCodeValue();

        // verifica respuesta
        assertEquals("Response status incorrecto!", HttpStatus.CREATED.value(), status);
    }

    @Test
    public void modificar() throws Exception {
        // Se prepara el objeto a enviar para modificar
        Producto producto = Producto.builder()
                .idProducto(1L)
                .nombre("Michelob Ultra")
                .marca("Budweiser")
                .build();

        HttpEntity<Producto> requestEntity = new HttpEntity<>(producto);

        //se ejecuta
        ResponseEntity<Void> responseEntity = restTemplate.exchange(URL, HttpMethod.PUT, requestEntity, Void.class);

        //se verifica
        int status = responseEntity.getStatusCodeValue();
        assertEquals("Response status incorrecto", HttpStatus.NO_CONTENT.value(), status);

    }
}
