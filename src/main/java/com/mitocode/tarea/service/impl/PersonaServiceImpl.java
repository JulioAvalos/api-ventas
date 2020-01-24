package com.mitocode.tarea.service.impl;

import com.mitocode.tarea.model.Persona;
import com.mitocode.tarea.repo.PersonaRepo;
import com.mitocode.tarea.service.PersonaService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class PersonaServiceImpl implements PersonaService {

    private final PersonaRepo repo;

    public PersonaServiceImpl(PersonaRepo repo) {
        this.repo = repo;
    }

    @Override
    public Persona registrar(Persona obj) {
        return repo.save(obj);
    }

    @Override
    public Persona modificar(Persona obj) {
        return repo.save(obj);
    }

    @Override
    public List<Persona> listar() {
        return repo.findAll();
    }

    @Override
    public Persona leerPorId(Long id) {
        Optional<Persona> op = repo.findById(id);
        return op.orElseGet(Persona::new);
    }

    @Override
    public boolean eliminar(Long id) {
        repo.deleteById(id);
        return true;
    }
}
