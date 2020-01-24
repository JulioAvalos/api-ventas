package com.mitocode.tarea.service;

import java.util.List;

public interface Crud<T> {
    T registrar(T obj);
    T modificar(T obj);
    List<T> listar();
    T leerPorId(Long id);
    boolean eliminar(Long id);
}
