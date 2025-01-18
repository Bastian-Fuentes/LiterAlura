package com.literalura.catalogo_libros.service;

import com.literalura.catalogo_libros.models.Libro;
import com.literalura.catalogo_libros.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class LibroService {
    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private API apigut;

    public void buscarYRegistrar(String titulo){
        apigut.buscarLibroEnGutendex(titulo);
    }

    public void buscarPorTitulo(String titulo) {
        List<Libro> libros = libroRepository.findByTituloContainingIgnoreCase(titulo);
        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros con el título: " + titulo);
        } else {
            libros.forEach(libro -> {
                System.out.println("------ LIBRO ------");
                System.out.println("Título: " + libro.getTitulo());
                System.out.println("Autor: " + libro.getAutor().getNombre());
                System.out.println("Idioma: " + libro.getIdioma());
                System.out.println("Número de descargas: " + libro.getDescargas());
            });
        }
    }

    public void listarLibros() {
        List<Libro> libros = libroRepository.findAll();
        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados.");
        } else {
            libros.forEach(libro -> {
                System.out.println("Título: " + libro.getTitulo() + "\nAutor: " + libro.getAutor().getNombre() + "\n");
            });
        }
    }

    public void guardarLibro(Libro libro){
        if(libroRepository.findByTituloContainingIgnoreCase(libro.getTitulo()).isEmpty()){
            libroRepository.save(libro);
        } else {
            throw new RuntimeException("El libro ya existe");
        }
    }

    public void listarPorIdioma(String idioma) {
        List<Libro> libros = libroRepository.findByIdioma(idioma);
        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros en el idioma: " + idioma);
        } else {
            libros.forEach(libro -> System.out.println("Título: " + libro.getTitulo()));
        }
    }
}
