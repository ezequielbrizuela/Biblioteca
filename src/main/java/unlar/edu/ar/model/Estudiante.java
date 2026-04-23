package unlar.edu.ar.model;

/**
 * Representa a un estudiante universitario registrado en la biblioteca.
 */
public class Estudiante {

    private String legajo;
    private String nombre;
    private String carrera;
    private String email;

    /** Constructor por defecto. */
    public Estudiante() {
    }

    /** Constructor parametrizado. */
    public Estudiante(String legajo, String nombre, String carrera, String email) {
        this.legajo = legajo;
        this.nombre = nombre;
        this.carrera = carrera;
        this.email = email;
    }

    public String getLegajo() { return legajo; }

    public void setLegajo(String legajo) { this.legajo = legajo; }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCarrera() { return carrera; }

    public void setCarrera(String carrera) { this.carrera = carrera; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    /** Dos estudiantes se consideran iguales si tienen el mismo legajo. */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Estudiante)) return false;
        Estudiante otro = (Estudiante) obj;
        return this.legajo != null && this.legajo.equals(otro.legajo);
    }

    @Override
    public int hashCode() {
        return legajo != null ? legajo.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Estudiante{legajo='" + legajo + "', nombre='" + nombre
                + "', carrera='" + carrera + "', email='" + email + "'}";
    }
}
