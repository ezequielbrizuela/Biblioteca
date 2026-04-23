package unlar.edu.ar.model;

/**
 * Representa un libro dentro del catalogo de la biblioteca.
 */
public class Libro {

    private String isbn;
    private String titulo;
    private String autor;
    private int anio;
    private boolean disponible;

    /** Constructor por defecto. */
    public Libro() {
        this.disponible = true;
    }

    /** Constructor parametrizado. */
    public Libro(String isbn, String titulo, String autor, int anio) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
        this.anio = anio;
        this.disponible = true;
    }

    public String getIsbn() { return isbn; }

    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getTitulo() { return titulo; }

    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getAutor() { return autor; }

    public void setAutor(String autor) { this.autor = autor; }

    public int getAnio() { return anio; }

    public void setAnio(int anio) { this.anio = anio; }

    public boolean isDisponible() { return disponible; }

    public void setDisponible(boolean disponible) { this.disponible = disponible; }

    /** Dos libros se consideran iguales si tienen el mismo ISBN. */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Libro)) return false;
        Libro otro = (Libro) obj;
        return this.isbn != null && this.isbn.equals(otro.isbn);
    }

    @Override
    public int hashCode() {
        return isbn != null ? isbn.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Libro{ISBN='" + isbn + "', titulo='" + titulo
                + "', autor='" + autor + "', anio=" + anio
                + ", disponible=" + (disponible ? "Si" : "No") + "}";
    }
}
