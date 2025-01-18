package com.literalura.catalogo_libros.repository;

import com.literalura.catalogo_libros.models.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface LibroRepository extends JpaRepository<Libro, Long> {
    List<Libro> findByTituloContainingIgnoreCase(String titulo);
    List<Libro> findByIdioma(String idioma);
}
