package com.literalura.catalogo_libros.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.cglib.core.Local;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;
    private Integer nacimiento;
    private Integer muerte;
}
