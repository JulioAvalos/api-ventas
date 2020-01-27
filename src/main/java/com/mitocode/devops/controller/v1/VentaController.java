package com.mitocode.devops.controller.v1;

import com.mitocode.devops.model.Venta;
import com.mitocode.devops.service.VentaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(VentaController.BASE_URL)
public class VentaController {

    public static final String BASE_URL = "/api/v1/ventas";

    private final VentaService service;

    public VentaController(VentaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Object> registrar(@Valid @RequestBody Venta venta) {
        Venta obj = service.registrar(venta);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdVenta()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public ResponseEntity<List<Venta>> listar(){
        List<Venta> lista = service.listar();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

}
