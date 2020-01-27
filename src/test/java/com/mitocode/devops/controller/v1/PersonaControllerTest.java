package com.mitocode.devops.controller.v1;

import com.mitocode.devops.controller.RestResponseEntityExceptionHandler;
import com.mitocode.devops.model.Persona;
import com.mitocode.devops.service.PersonaService;
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

import static com.mitocode.devops.controller.v1.AbstractRestControllerTest.asJsonString;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyLong;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PersonaControllerTest {

    @Mock
    PersonaService service;

    @InjectMocks
    PersonaController controller;

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
        Persona persona1 = Persona.builder()
                .idPersona(1L)
                .nombres("Mariana")
                .apellidos("Abregos")
                .build();

        Persona persona2 = Persona.builder()
                .idPersona(2L)
                .nombres("Sofia")
                .apellidos("Poe")
                .build();

        List<Persona> personas = Arrays.asList(persona1, persona2);

        when(service.listar()).thenReturn(personas);

        mockMvc.perform(get(PersonaController.BASE_URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void listarPorId() throws Exception {

        Persona persona1 = Persona.builder()
                .idPersona(1L)
                .nombres("Carmen")
                .apellidos("Menjivar")
                .build();

        when(service.leerPorId(anyLong())).thenReturn(persona1);

        mockMvc.perform(get(PersonaController.BASE_URL + "/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombres", equalTo("Carmen")));
    }

    @Test
    public void registrar() throws Exception {
        Persona persona = Persona.builder()
                .idPersona(1L)
                .nombres("Alejandra")
                .apellidos("Medrano")
                .build();

        when(service.registrar(any())).thenReturn(persona);

        mockMvc.perform(post(PersonaController.BASE_URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(persona)))
                .andExpect(status().isCreated());
    }

    @Test
    public void modificar() throws Exception {
        Persona persona = Persona.builder()
                .idPersona(1L)
                .nombres("Magali")
                .apellidos("Erazo")
                .build();

        when(service.modificar(any(Persona.class))).thenReturn(persona);

        mockMvc.perform(put(PersonaController.BASE_URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(persona)))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.nombres", equalTo("Magali")))
                .andExpect(jsonPath("$.apellidos", equalTo("Erazo")));
    }

    @Test
    public void eliminar() throws Exception {

        mockMvc.perform(delete(PersonaController.BASE_URL + "/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
}