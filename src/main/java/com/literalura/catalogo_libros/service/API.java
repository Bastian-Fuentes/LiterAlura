package com.literalura.catalogo_libros.service;

import com.literalura.catalogo_libros.models.Libro;
import com.literalura.catalogo_libros.models.Autor;
import com.literalura.catalogo_libros.repository.AutorRepository;
import com.literalura.catalogo_libros.repository.LibroRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

@Service
public class API {

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private AutorRepository autorRepository;

    public void buscarLibroEnGutendex(String titulo) {
        // URL de la API de Gutendex con el título
        String apiUrl = "https://gutendex.com/books?search=" + titulo.replace(" ", "%20");
        RestTemplate restTemplate = new RestTemplate();
        String response;

        try {
            // Realizar solicitud a la API
            response = restTemplate.getForObject(apiUrl, String.class);
        } catch (Exception e) {
            System.out.println("Error al consumir la API de Gutendex: " + e.getMessage());
            return;
        }

        try {
            JSONObject jsonResponse = new JSONObject(response);
            JSONArray results = jsonResponse.getJSONArray("results");

            if (results.length() == 0) {
                System.out.println("No se encontraron libros en Gutendex con el título: " + titulo);
                return;
            }

            for (int i = 0; i < results.length(); i++) {
                JSONObject bookJson = results.getJSONObject(i);

                // Extraer información del libro
                String bookTitle = bookJson.getString("title");
                String language = bookJson.getJSONArray("languages").getString(0);
                double downloads = bookJson.getDouble("download_count");

                // Obtener información del autor
                JSONArray authorsJson = bookJson.getJSONArray("authors");
                String authorName = authorsJson.getJSONObject(0).getString("name");
                Integer birth = authorsJson.getJSONObject(0).getInt("birth_year");
                Integer death = authorsJson.getJSONObject(0).getInt("death_year");

                // Evitar duplicados verificando por título
                List<Libro> existingBook = libroRepository.findByTituloContainingIgnoreCase(bookTitle);
                if (!existingBook.isEmpty()) {
                    System.out.println("El libro ya existe: " + bookTitle);
                    continue;
                }

                // Guardar autor si no existe
                List<Autor> existingAuthors = autorRepository.findByNombre(authorName);
                Autor autor;

                if (existingAuthors.isEmpty()) {
                    Autor newAuthor = new Autor();
                    newAuthor.setNombre(authorName);
                    newAuthor.setNacimiento(birth);
                    newAuthor.setMuerte(death);
                    autorRepository.save(newAuthor);
                    autor = newAuthor;
                } else {
                    autor = existingAuthors.get(0); // Usar el primer autor encontrado
                }

                // Crear y guardar libro
                Libro newLibro = new Libro();
                newLibro.setTitulo(bookTitle);
                newLibro.setIdioma(language);
                newLibro.setDescargas((int) downloads);
                newLibro.setAutor(autor);
                libroRepository.save(newLibro);

                System.out.println("Libro registrado: " + bookTitle);
            }
        } catch (JSONException e) {
            System.out.println("Error al procesar la respuesta de la API: " + e.getMessage());
        }
    }
}
