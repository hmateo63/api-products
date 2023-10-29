package com.company.intecap.apiproducts.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity(name = "Fabricate")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Fabricate implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String descripcion;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(
                cascade = CascadeType.ALL,
                fetch = FetchType.EAGER,
                orphanRemoval = true,
                mappedBy = "Fabricate"
    )
    @JsonIgnore
    private Set<Producto> productos;
}
