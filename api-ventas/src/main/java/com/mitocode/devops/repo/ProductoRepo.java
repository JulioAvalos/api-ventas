package com.mitocode.devops.repo;

import com.mitocode.devops.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepo extends JpaRepository<Producto, Long> {
}
