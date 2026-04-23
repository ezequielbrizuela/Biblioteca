package unlar.edu.ar.ui;

import java.util.List;
import java.util.Scanner;
import unlar.edu.ar.exception.EstudianteNoEncontradoException;
import unlar.edu.ar.exception.LibroNoDisponibleException;
import unlar.edu.ar.exception.LimitePrestamosExcedidoException;
import unlar.edu.ar.model.Libro;
import unlar.edu.ar.model.Prestamo;
import unlar.edu.ar.service.BibliotecaService;

/**
 * Interfaz de usuario por consola.
 *
 * Analogia: si BibliotecaService es el "empleado que sabe todo",
 * BibliotecaUI es la "ventanilla" donde el usuario interactua.
 * La UI solo muestra y pide datos; la logica la maneja el servicio.
 */
public class BibliotecaUI {

    private BibliotecaService servicio;
    private Scanner           scanner;

    public BibliotecaUI(BibliotecaService servicio) {
        this.servicio = servicio;
        this.scanner  = new Scanner(System.in);
    }

    /** Inicia el loop principal del menu. */
    public void iniciar() {
        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero();
            procesarOpcion(opcion);
        } while (opcion != 0);
    }

    // -------------------------------------------------------------------------
    // Menu
    // -------------------------------------------------------------------------

    private void mostrarMenu() {
        System.out.println("\n========================================");
        System.out.println("   SISTEMA DE BIBLIOTECA UNIVERSITARIA  ");
        System.out.println("========================================");
        System.out.println("  1. Buscar libros por titulo");
        System.out.println("  2. Registrar prestamo");
        System.out.println("  3. Registrar devolucion");
        System.out.println("  4. Ver prestamos de un estudiante");
        System.out.println("  5. Listar todos los libros");
        System.out.println("  0. Salir");
        System.out.print("Selecciona una opcion: ");
    }

    private void procesarOpcion(int opcion) {
        switch (opcion) {
            case 1: buscarLibros();                 break;
            case 2: registrarPrestamo();            break;
            case 3: registrarDevolucion();          break;
            case 4: listarPrestamosPorEstudiante(); break;
            case 5: listarTodosLosLibros();         break;
            case 0: System.out.println("Cerrando..."); break;
            default: System.out.println("Opcion no valida. Intenta de nuevo.");
        }
    }

    // -------------------------------------------------------------------------
    // Opcion 1: Buscar libros
    // -------------------------------------------------------------------------

    private void buscarLibros() {
        System.out.print("Ingresa parte del titulo: ");
        String fragmento = scanner.nextLine().trim();
        List<Libro> resultados = servicio.buscarLibrosPorTitulo(fragmento);

        if (resultados.isEmpty()) {
            System.out.println("No se encontraron libros con ese titulo.");
        } else {
            System.out.println("\nSe encontraron " + resultados.size() + " libro(s):");
            for (int i = 0; i < resultados.size(); i++) {
                mostrarLibroDetallado(resultados.get(i), i + 1);
            }
        }
    }

    // -------------------------------------------------------------------------
    // Opcion 2: Registrar prestamo
    // -------------------------------------------------------------------------

    private void registrarPrestamo() {
        System.out.print("ISBN del libro: ");
        String isbn = scanner.nextLine().trim();
        System.out.print("Legajo del estudiante: ");
        String legajo = scanner.nextLine().trim();

        try {
            Prestamo prestamo = servicio.registrarPrestamo(isbn, legajo);
            System.out.println("\n[OK] Prestamo registrado correctamente:");
            mostrarPrestamoDetallado(prestamo, 1);
        } catch (LibroNoDisponibleException e) {
            System.out.println("[ERROR] " + e.getMessage());
        } catch (EstudianteNoEncontradoException e) {
            System.out.println("[ERROR] " + e.getMessage());
        } catch (LimitePrestamosExcedidoException e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }

    // -------------------------------------------------------------------------
    // Opcion 3: Registrar devolucion
    // -------------------------------------------------------------------------

    private void registrarDevolucion() {
        System.out.print("ISBN del libro a devolver: ");
        String isbn = scanner.nextLine().trim();
        System.out.print("Legajo del estudiante: ");
        String legajo = scanner.nextLine().trim();
        System.out.print("Valor del libro ($): ");
        double valor = leerDouble();

        try {
            double multa = servicio.registrarDevolucion(isbn, legajo, valor);
            if (multa > 0) {
                System.out.printf("[OK] Devolucion registrada. Multa por retraso: $%.2f%n", multa);
            } else {
                System.out.println("[OK] Devolucion registrada a tiempo. Sin multa.");
            }
        } catch (EstudianteNoEncontradoException e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }

    // -------------------------------------------------------------------------
    // Opcion 4: Prestamos por estudiante
    // -------------------------------------------------------------------------

    private void listarPrestamosPorEstudiante() {
        System.out.print("Legajo del estudiante: ");
        String legajo = scanner.nextLine().trim();

        try {
            List<Prestamo> prestamos = servicio.listarPrestamosPorEstudiante(legajo);
            if (prestamos.isEmpty()) {
                System.out.println("El estudiante no tiene prestamos activos.");
            } else {
                System.out.println("\nPrestamos activos (" + prestamos.size() + "):");
                for (int i = 0; i < prestamos.size(); i++) {
                    mostrarPrestamoDetallado(prestamos.get(i), i + 1);
                }
            }
        } catch (EstudianteNoEncontradoException e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }

    // -------------------------------------------------------------------------
    // Opcion 5: Listar todos los libros
    // -------------------------------------------------------------------------

    private void listarTodosLosLibros() {
        List<Libro> todos = servicio.getCatalogo();
        if (todos.isEmpty()) {
            System.out.println("El catalogo esta vacio.");
        } else {
            System.out.println("\nCatalogo completo (" + todos.size() + " libro(s)):");
            for (int i = 0; i < todos.size(); i++) {
                mostrarLibroDetallado(todos.get(i), i + 1);
            }
        }
    }

    private void mostrarLibroDetallado(Libro libro, int numero) {
        System.out.println();
        System.out.println("  [" + numero + "] " + libro.getTitulo());
        System.out.println("      ISBN  : " + libro.getIsbn());
        System.out.println("      Autor : " + libro.getAutor());
        System.out.println("      Anio  : " + libro.getAnio());
        System.out.println("      Estado: " + (libro.isDisponible() ? "Disponible" : "Prestado"));
    }

    private void mostrarPrestamoDetallado(Prestamo prestamo, int numero) {
        System.out.println();
        System.out.println("  [" + numero + "] " + prestamo.getLibro().getTitulo());
        System.out.println("      ISBN       : " + prestamo.getLibro().getIsbn());
        System.out.println("      Estudiante : " + prestamo.getEstudiante().getNombre());
        System.out.println("      Legajo     : " + prestamo.getEstudiante().getLegajo());
        System.out.println("      Prestamo   : " + prestamo.getFechaPrestamo());
        System.out.println("      Devolucion : "
                + (prestamo.getFechaDevolucion() != null
                ? prestamo.getFechaDevolucion()
                : "Pendiente"));
    }

    // -------------------------------------------------------------------------
    // Utilidades de lectura: 
    // -------------------------------------------------------------------------

    private int leerEntero() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private double leerDouble() {
        try {
            return Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
