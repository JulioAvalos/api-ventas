package com.mitocode.devops.service.impl;

import com.mitocode.devops.model.Venta;
import com.mitocode.devops.repo.VentaRepo;
import com.mitocode.devops.service.VentaService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class VentaServiceImpl implements VentaService {

    private final VentaRepo repo;

    public VentaServiceImpl(VentaRepo repo) {
        this.repo = repo;
    }

    @Override
    public Venta registrar(Venta obj) {
        obj.getDetalleVenta().forEach(detalle -> {
            detalle.setVenta(obj);
        });
        return repo.save(obj);
    }

    @Override
    public Venta modificar(Venta obj) {
        return null;
    }

    @Override
    public List<Venta> listar() {
        return repo.findAll();
    }

    @Override
    public Venta leerPorId(Long id) {
        return null;
    }

    @Override
    public boolean eliminar(Long id) {
        return false;
    }
}
