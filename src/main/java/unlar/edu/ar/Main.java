package unlar.edu.ar;

import java.time.LocalDate;
import java.util.List;
import unlar.edu.ar.exception.EstudianteNoEncontradoException;
import unlar.edu.ar.exception.LibroNoDisponibleException;
import unlar.edu.ar.exception.LimitePrestamosExcedidoException;
import unlar.edu.ar.model.Estudiante;
import unlar.edu.ar.model.Libro;
import unlar.edu.ar.model.Prestamo;
import unlar.edu.ar.service.BibliotecaService;
import unlar.edu.ar.ui.BibliotecaUI;

/**
 * Clase principal del sistema de biblioteca.
 *
 * Casos de prueba requeridos por el TP:
 *   - Carga de datos (5 libros, 3 estudiantes)
 *   - Busqueda de libros
 *   - Prestamos exitosos
 *   - Manejo de las 3 excepciones personalizadas
 *   - Calculo de multa recursiva (15 dias de retraso)
 *   - Analisis de la pila de llamadas recursivas (30 iteraciones)
 */
public class Main {

    public static void main(String[] args) {
        BibliotecaService servicio = new BibliotecaService();

        cargarDatos(servicio);
        testBusqueda(servicio);
        testPrestamosExitosos(servicio);
        testLibroNoDisponible(servicio);
        testEstudianteNoEncontrado(servicio);
        testLimiteExcedido(servicio);
        testDevolucionConMulta(servicio);
        testAnalisisPila(servicio);

        System.out.println("\n" + "=".repeat(55));
        System.out.println("   Iniciando menu interactivo...");
        System.out.println("=".repeat(55));

        BibliotecaService servicioUI = new BibliotecaService();
        cargarDatos(servicioUI);

        BibliotecaUI ui = new BibliotecaUI(servicioUI);
        ui.iniciar();
    }

    private static void cargarDatos(BibliotecaService servicio) {
        servicio.agregarLibro(new Libro("13110",
                "El Lenguaje de Programacion C", "Kernighan & Ritchie", 1978));
        servicio.agregarLibro(new Libro("20133",
                "Clean Code", "Robert C. Martin", 2008));
        servicio.agregarLibro(new Libro("13235",
                "Java: The Complete Reference. McGraw-Hill", "Schildt, H", 2018));
        servicio.agregarLibro(new Libro("59651",
                "Code Conventions for the Java Programming Language.", "Oracle", 2005));
        servicio.agregarLibro(new Libro("13468",
                "The Pragmatic Programmer", "Hunt & Thomas", 2019));

        servicio.registrarEstudiante(new Estudiante(
                "EISI676", "Ana Perez", "Ingenieria en Sistemas", "ana@unlar.edu.ar"));
        servicio.registrarEstudiante(new Estudiante(
                "EISI1234", "Carlos Lopez", "Ingenieria en Sistemas", "carlos@unlar.edu.ar"));
        servicio.registrarEstudiante(new Estudiante(
                "LSI4321", "Maria Garcia", "Ingenieria Civil", "maria@unlar.edu.ar"));

        System.out.println("========================================================");
        System.out.println("      SISTEMA DE BIBLIOTECA UNIVERSITARIA               ");
        System.out.println("========================================================");
        System.out.println("[OK] Datos cargados: " + servicio.getCatalogo().size()
                + " libros, " + servicio.getEstudiantes().size() + " estudiantes.");
    }

    private static void testBusqueda(BibliotecaService servicio) {
        System.out.println("\n--- TEST 1: Busqueda por titulo ----------------------------");

        List<Libro> resultadosJava = servicio.buscarLibrosPorTitulo("java");
        System.out.println("Busqueda de 'java': " + resultadosJava.size() + " resultado(s).");
        for (Libro libro : resultadosJava) {
            System.out.println("  -> " + libro.getTitulo() + " (" + libro.getAutor() + ")");
        }

        List<Libro> resultadosProgramacion = servicio.buscarLibrosPorTitulo("programacion");
        System.out.println("Busqueda de 'programacion': "
                + resultadosProgramacion.size() + " resultado(s).");
        for (Libro libro : resultadosProgramacion) {
            System.out.println("  -> " + libro.getTitulo());
        }
    }

    private static void testPrestamosExitosos(BibliotecaService servicio) {
        System.out.println("\n--- TEST 2: Prestamos exitosos -----------------------------");
        try {
            Prestamo prestamo1 = servicio.registrarPrestamo("20133", "EISI676");
            System.out.println("[OK] Prestamo registrado: " + prestamo1);

            Prestamo prestamo2 = servicio.registrarPrestamo("13235", "EISI1234");
            System.out.println("[OK] Prestamo registrado: " + prestamo2);
        } catch (Exception e) {
            System.out.println("[ERROR] Excepcion inesperada: " + e.getMessage());
        }
    }

    private static void testLibroNoDisponible(BibliotecaService servicio) {
        System.out.println("\n--- TEST 3: LibroNoDisponibleException ---------------------");
        try {
            servicio.registrarPrestamo("20133", "LSI4321");
            System.out.println("[ERROR] No se lanzo la excepcion esperada.");
        } catch (LibroNoDisponibleException e) {
            System.out.println("[OK] Excepcion capturada: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("[ERROR] Excepcion inesperada: " + e.getMessage());
        }
    }

    private static void testEstudianteNoEncontrado(BibliotecaService servicio) {
        System.out.println("\n--- TEST 4: EstudianteNoEncontradoException ----------------");
        try {
            servicio.registrarPrestamo("59651", "LEGAJO-INEXISTENTE");
            System.out.println("[ERROR] No se lanzo la excepcion esperada.");
        } catch (EstudianteNoEncontradoException e) {
            System.out.println("[OK] Excepcion capturada: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("[ERROR] Excepcion inesperada: " + e.getMessage());
        }
    }

    private static void testLimiteExcedido(BibliotecaService servicio) {
        System.out.println("\n--- TEST 5: LimitePrestamosExcedidoException ---------------");
        try {
            servicio.registrarPrestamo("59651", "EISI676");
            System.out.println("[OK] Segundo prestamo de EISI676 registrado.");

            servicio.registrarPrestamo("13468", "EISI676");
            System.out.println("[OK] Tercer prestamo de EISI676 registrado.");

            servicio.registrarPrestamo("13110", "EISI676");
            System.out.println("[ERROR] No se lanzo la excepcion esperada.");
        } catch (LimitePrestamosExcedidoException e) {
            System.out.println("[OK] Excepcion capturada: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("[ERROR] Excepcion inesperada: " + e.getMessage());
        }
    }

    private static void testDevolucionConMulta(BibliotecaService servicio) {
        System.out.println("\n--- TEST 6: Devolucion con multa ---------------------------");

        Libro libro = new Libro("77777", "Domain-Driven Design", "Eric Evans", 2003);
        Estudiante estudiante = new Estudiante(
                "EISI9000", "Lucia Torres", "Ingenieria en Sistemas", "lucia@unlar.edu.ar");

        servicio.agregarLibro(libro);
        servicio.registrarEstudiante(estudiante);

        try {
            double valorLibro = 5000.0;
            int diasRetraso = 15;

            servicio.registrarPrestamo(
                    libro.getIsbn(), estudiante.getLegajo(), LocalDate.now().minusDays(7 + diasRetraso));

            double multa = servicio.registrarDevolucion(
                    libro.getIsbn(), estudiante.getLegajo(), valorLibro);
            System.out.printf("  Libro valuado en : $%.2f%n", valorLibro);
            System.out.printf("  Dias de retraso  : %d dias%n", diasRetraso);
            System.out.printf("  Multa calculada  : $%.2f (%.0f%% del valor)%n",
                    multa, (multa / valorLibro) * 100);
        } catch (Exception e) {
            System.out.println("[ERROR] Excepcion inesperada: " + e.getMessage());
        }
    }

    /**
     * Muestra como se apilan y se resuelven las llamadas recursivas de
     * calcularMulta para 30 dias.
     */
    private static void testAnalisisPila(BibliotecaService servicio) {
        System.out.println("\n--- TEST 7: Analisis de pila recursiva (30 dias) -----------");

        double valorLibro = 1000.0;
        System.out.println("  Simulando calcularMulta(30, $" + valorLibro + ")");
        System.out.println("  Cada nivel suma 1% de $" + valorLibro
                + " = $" + (valorLibro * 0.01));
        System.out.println();

        System.out.println("  APILANDO (llamadas recursivas hacia abajo):");
        for (int i = 30; i >= 27; i--) {
            System.out.printf("    nivel %2d -> calcularMulta(%d, %.2f)%n", i, i, valorLibro);
        }
        System.out.println("    ...");
        System.out.printf("    nivel  1 -> calcularMulta(1, %.2f)%n", valorLibro);
        System.out.println("    nivel  0 -> caso base: devuelve 0.0");

        System.out.println();
        System.out.println("  DESAPILANDO (resultados hacia arriba):");
        System.out.printf("    nivel  0 devuelve: $0.00%n");
        System.out.printf("    nivel  1 devuelve: $%.2f%n", valorLibro * 0.01);
        System.out.printf("    nivel  2 devuelve: $%.2f%n", valorLibro * 0.02);
        System.out.println("    ...");
        System.out.printf("    nivel 30 devuelve: $%.2f%n", valorLibro * 0.30);

        double multaFinal = servicio.calcularMulta(30, valorLibro);
        System.out.printf("%n  [OK] Resultado final calcularMulta(30, %.2f) = $%.2f%n",
                valorLibro, multaFinal);
        System.out.println("       (= 30%% del valor del libro, tope maximo)");
    }
}
