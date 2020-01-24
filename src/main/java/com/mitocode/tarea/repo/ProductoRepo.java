package com.mitocode.tarea.repo;

import com.mitocode.tarea.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepo extends JpaRepository<Producto, Long> {
}
