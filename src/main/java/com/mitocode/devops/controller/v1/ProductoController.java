package com.mitocode.devops.controller.v1;

import com.mitocode.devops.model.Producto;
import com.mitocode.devops.service.ProductoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(ProductoController.BASE_URL)
public class ProductoController {

    public static final String BASE_URL = "/api/v1/productos";
    
    private final ProductoService service;

    public ProductoController(ProductoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Producto>> lista() {
        List<Producto> lista = service.listar();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> listarPorId(@PathVariable Long id) {
        Producto obj = service.leerPorId(id);
        return new ResponseEntity<>(obj, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Producto> registrar(@RequestBody Producto Producto){
        Producto obj = service.registrar(Producto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(Producto.getIdProducto()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping
    public ResponseEntity<Producto> modificar(@RequestBody Producto Producto){
        Producto obj = service.modificar(Producto);
        return new ResponseEntity<>(obj, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> eliminar(@PathVariable Long id){
        Producto obj = service.leerPorId(id);
        if(Objects.nonNull(obj) && obj.getIdProducto() != null){
            service.eliminar(id);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
