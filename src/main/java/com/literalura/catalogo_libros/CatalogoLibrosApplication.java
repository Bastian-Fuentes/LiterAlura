package com.literalura.catalogo_libros;

import com.literalura.catalogo_libros.service.AutorService;
import com.literalura.catalogo_libros.service.LibroService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Scanner;

@SpringBootApplication
public class CatalogoLibrosApplication {

	public static void main(String[] args) {
		SpringApplication.run(CatalogoLibrosApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(LibroService libroService, AutorService autorService) {
		return args -> {
			Scanner scanner = new Scanner(System.in);
			while (true) {
				System.out.println("\nElija la opción a través de su número:");
				System.out.println("1- Buscar libro por título");
				System.out.println("2- Listar libros registrados");
				System.out.println("3- Listar autores registrados");
				System.out.println("4- Listar autores vivos en un determinado año");
				System.out.println("5- Listar libros por idioma");
				System.out.println("0- Salir");
				System.out.print("Opción: ");
				int opcion = scanner.nextInt();
				scanner.nextLine(); // Consume la línea

				switch (opcion) {
					case 1:
						System.out.print("Ingrese el nombre del libro que desea buscar: ");
						String titulo = scanner.nextLine();
						libroService.buscarYRegistrar(titulo);
						break;
					case 2:
						libroService.listarLibros();
						break;
					case 3:
						autorService.listarAutores();
						break;
					case 4:
						System.out.print("Ingrese el año: ");
						int ano = scanner.nextInt();
						scanner.nextLine(); // Consume la línea
						autorService.listarAutoresVivosPorAno(ano);
						break;
					case 5:
						System.out.print("Ingrese el idioma: ");
						String idioma = scanner.nextLine();
						libroService.listarPorIdioma(idioma);
						break;
					case 0:
						System.out.println("Saliendo...");
						return;
					default:
						System.out.println("Opción no válida, intente nuevamente.");
				}
			}
		};
	}
}
