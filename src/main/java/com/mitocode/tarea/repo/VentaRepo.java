package com.mitocode.tarea.repo;

import com.mitocode.tarea.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VentaRepo extends JpaRepository<Venta, Long> {
}
