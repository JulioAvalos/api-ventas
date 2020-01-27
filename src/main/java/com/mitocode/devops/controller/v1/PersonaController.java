package com.mitocode.devops.controller.v1;

import com.mitocode.devops.model.Persona;
import com.mitocode.devops.service.PersonaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(PersonaController.BASE_URL)
public class PersonaController {

    public static final String BASE_URL = "/api/v1/personas";

    private final PersonaService service;

    public PersonaController(PersonaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Persona>> lista() {
        List<Persona> lista = service.listar();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Persona> listarPorId(@PathVariable Long id) {
        Persona obj = service.leerPorId(id);
        return new ResponseEntity<>(obj, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Persona> registrar(@RequestBody Persona persona){
        Persona obj = service.registrar(persona);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(persona.getIdPersona()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping
    public ResponseEntity<Persona> modificar(@RequestBody Persona persona){
        Persona obj = service.modificar(persona);
        return new ResponseEntity<>(obj, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> eliminar(@PathVariable Long id){
        Persona obj = service.leerPorId(id);
        if(Objects.nonNull(obj) && obj.getIdPersona() != null){
            service.eliminar(id);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
