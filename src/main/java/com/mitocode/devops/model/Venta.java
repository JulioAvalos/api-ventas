package com.mitocode.devops.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "detalleVenta")
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVenta;

    @Column(name = "fecha", nullable = true)
    private LocalDateTime fecha;

    private double importe;

    @ManyToOne
    @JoinColumn(name = "id_persona", nullable = false, foreignKey = @ForeignKey(name = "fk_persona"))
    private Persona persona;

    @OneToMany(mappedBy = "venta", cascade = { CascadeType.ALL }, orphanRemoval = true)
    private Set<DetalleVenta> detalleVenta;

}
