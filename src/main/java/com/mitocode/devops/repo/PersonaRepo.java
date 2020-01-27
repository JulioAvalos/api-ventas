package com.mitocode.devops.repo;

import com.mitocode.devops.model.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonaRepo extends JpaRepository<Persona, Long> {
}
