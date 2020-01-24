package com.mitocode.tarea.repo;

import com.mitocode.tarea.model.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonaRepo extends JpaRepository<Persona, Long> {
}
