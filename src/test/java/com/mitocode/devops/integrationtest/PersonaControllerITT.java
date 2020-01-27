package com.mitocode.devops.integrationtest;

import com.mitocode.devops.model.Persona;
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

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("dev")
public class PersonaControllerITT {

    @Autowired
    private TestRestTemplate restTemplate;

    private static final String URL = "/api/v1/personas/";

    @Test
    public void listar() throws Exception {

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<String> requestEntity = new HttpEntity<String>(null, headers);

        ResponseEntity<List<Persona>> responseEntity = restTemplate.exchange(URL, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<Persona>>() {});

        int status = responseEntity.getStatusCodeValue();
        List<Persona> resultPersona = responseEntity.getBody();

        assertEquals("Response status incorrecto!", HttpStatus.OK.value(), status);
        assertNotNull(resultPersona);
    }

    @Test
    public void listarPorId() throws Exception {

        ResponseEntity<Persona> responseEntity = restTemplate.getForEntity(URL + "{id}", Persona.class, 1L);

        int status = responseEntity.getStatusCodeValue();
        Persona resultPersona = responseEntity.getBody();

        assertEquals("Response status incorrecto!", HttpStatus.OK.value(), status);
        assertNotNull(resultPersona);
        assertEquals(1L, resultPersona.getIdPersona().longValue());
    }

    @Test
    public void insertar() throws Exception {

        Persona persona = Persona.builder()
                .nombres("Santiago Fernando")
                .apellidos("Lainez Gordan")
                .build();

        //ejecuta
        ResponseEntity<Persona> responseEntity = restTemplate.postForEntity(URL, persona, Persona.class);

        int status = responseEntity.getStatusCodeValue();

        // verifica respuesta
        assertEquals("Response status incorrecto!", HttpStatus.CREATED.value(), status);
    }

    @Test
    public void modificar() throws Exception {
        // Se prepara el objeto a enviar para modificar
        Persona persona = Persona.builder()
                .idPersona(1L)
                .nombres("Mario Alberto")
                .apellidos("Gomez Beltran")
                .build();

        HttpEntity<Persona> requestEntity = new HttpEntity<>(persona);

        //se ejecuta
        ResponseEntity<Void> responseEntity = restTemplate.exchange(URL, HttpMethod.PUT, requestEntity, Void.class);

        //se verifica
        int status = responseEntity.getStatusCodeValue();
        assertEquals("Response status incorrecto", HttpStatus.NO_CONTENT.value(), status);

    }

}
