package com.mitocode.devops.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(exclude = "producto")
public class DetalleVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDetalleVenta;
    private int cantidad;

    @ManyToOne
    @JoinColumn(name = "id_producto", nullable = false, foreignKey = @ForeignKey(name="fk_producto"))
    private Producto producto;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_venta", nullable = false, foreignKey = @ForeignKey(name="fk_venta"))
    private Venta venta;
}
