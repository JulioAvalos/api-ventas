package com.mitocode.tarea.service.impl;

import com.mitocode.tarea.model.Producto;
import com.mitocode.tarea.repo.ProductoRepo;
import com.mitocode.tarea.service.ProductoService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepo repo;

    public ProductoServiceImpl(ProductoRepo repo) {
        this.repo = repo;
    }

    @Override
    public Producto registrar(Producto obj) {
        return repo.save(obj);
    }

    @Override
    public Producto modificar(Producto obj) {
        return repo.save(obj);
    }

    @Override
    public List<Producto> listar() {
        return repo.findAll();
    }

    @Override
    public Producto leerPorId(Long id) {
        Optional<Producto> op = repo.findById(id);
        return op.orElseGet(Producto::new);
    }

    @Override
    public boolean eliminar(Long id) {
        repo.deleteById(id);
        return true;
    }
}
