import java.time.LocalDate;
import java.util.List;

/** Evento sin equipos ni resultados; no hereda de Encuentro. */
public class Exhibicion implements Programable {
    private final String titulo;
    private String estado;
    private LocalDate fecha;
    private int hora;
    private String sede;

    public Exhibicion(String titulo) {
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("El título de la exhibición es obligatorio.");
        }
        this.titulo = titulo.trim();
        estado = "Sin programar";
        hora = -1;
    }

    @Override
    public void programar(LocalDate fecha, int hora, String sede) {
        if (fecha == null || hora < 0 || hora > 23 || sede == null || sede.isBlank()) {
            throw new IllegalArgumentException("Indique una fecha válida, hora entre 0 y 23 y sede no vacía.");
        }
        this.fecha = fecha;
        this.hora = hora;
        this.sede = sede.trim();
        estado = "Programada";
    }

    public String getTitulo() {
        return titulo;
    }

    @Override
    public LocalDate getFecha() {
        return fecha;
    }

    @Override
    public int getHora() {
        return hora;
    }

    @Override
    public String getSede() {
        return sede;
    }

    @Override
    public String getEstado() {
        return estado;
    }

    @Override
    public String getDescripcion() {
        return "Exhibición: " + titulo + " [" + estado + "]";
    }

    @Override
    public List<Equipo> getEquipos() {
        return List.of();
    }
}
