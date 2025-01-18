package com.literalura.catalogo_libros.service;

import com.literalura.catalogo_libros.models.Autor;
import com.literalura.catalogo_libros.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AutorService {
    @Autowired
    private AutorRepository autorRepository;

    public void listarAutores() {
        List<Autor> autores = autorRepository.findAll();
        if (autores.isEmpty()) {
            System.out.println("No hay autores registrados.");
        } else {
            autores.forEach(autor -> System.out.println("Autor: " + autor.getNombre() +"\nNacimiento: " + autor.getNacimiento() + "\nMuerte" + autor.getMuerte() + "\n"));
        }
    }

    public void listarAutoresVivosPorAno(int ano) {
        List<Autor> autores = autorRepository.findByMuerteAfter(ano);
        if (autores.isEmpty()) {
            System.out.println("No se encontraron autores vivos en el año " + ano);
        } else {
            autores.forEach(autor -> System.out.println("Autor: " + autor.getNombre()));
        }
    }
}
