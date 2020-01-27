package com.mitocode.devops.repo;

import com.mitocode.devops.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VentaRepo extends JpaRepository<Venta, Long> {
}
