package com.mitocode.devops.service.impl;

import com.mitocode.devops.model.Persona;
import com.mitocode.devops.repo.PersonaRepo;
import com.mitocode.devops.service.PersonaService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class PersonaServiceImplTest {

    @Mock
    PersonaRepo repo;

    PersonaService service;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        service = new PersonaServiceImpl(repo);
    }

    @Test
    public void registrar() {
        Persona persona1 = Persona.builder()
                .idPersona(1L)
                .nombres("Camila")
                .apellidos("Lopez")
                .build();

        when(repo.save(any(Persona.class))).thenReturn(persona1);

        Persona personaRegistrada = service.registrar(persona1);
        assertEquals("Camila", personaRegistrada.getNombres());
    }

    @Test
    public void modificar() {

        Persona persona = Persona.builder()
                .idPersona(1L)
                .nombres("Sid")
                .apellidos("Guardian")
                .build();

        when(repo.save(any(Persona.class))).thenReturn(persona);

        Persona personaRegistrada = service.registrar(persona);
        personaRegistrada.setNombres("Edwin Eduardo");

        Persona modificada = service.modificar(personaRegistrada);

        assertEquals(new Long(1L), modificada.getIdPersona());
        assertEquals("Edwin Eduardo", modificada.getNombres());
        assertEquals("Guardian", modificada.getApellidos());

    }

    @Test
    public void listar() {
        Persona persona1 = Persona.builder()
                .idPersona(1L)
                .nombres("Romina")
                .apellidos("Alfaro")
                .build();

        Persona persona2 = Persona.builder()
                .idPersona(2L)
                .nombres("Veronica")
                .apellidos("Pleitz")
                .build();

        when(repo.findAll()).thenReturn(Arrays.asList(persona1, persona2));

        List<Persona> personas = service.listar();

        assertEquals(2, personas.size());
    }

    @Test
    public void leerPorId() {
        Persona persona1 = Persona.builder()
                .idPersona(1L)
                .nombres("Aaron")
                .apellidos("Gonzalez")
                .build();

        when(repo.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(persona1));

        Persona persona = service.leerPorId(1l);

        assertEquals("Aaron", persona.getNombres());
    }

    @Test
    public void eliminar() {

        Long id = 1L;

        repo.deleteById(id);

        verify(repo,times(1)).deleteById(anyLong());
    }
}