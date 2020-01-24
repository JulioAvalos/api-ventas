package com.mitocode.tarea.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPersona;
    private String nombres;
    private String apellidos;
}
