package com.literalura.catalogo_libros.repository;

import com.literalura.catalogo_libros.models.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.time.LocalDate;


public interface AutorRepository extends JpaRepository<Autor, Long> {
    List<Autor> findByMuerteIsNull();
    List<Autor> findByMuerteAfter(Integer ano);
    List<Autor> findByNombre(String nombre);
}
