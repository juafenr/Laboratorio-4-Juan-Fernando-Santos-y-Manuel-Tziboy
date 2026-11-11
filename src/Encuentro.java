import java.time.LocalDate;
import java.util.List;

/** Proceso común de los partidos; cada deporte aporta sus reglas. */
public abstract class Encuentro implements Programable, ConResultado {
    private final Equipo equipo1;
    private final Equipo equipo2;
    private Integer marcador1;
    private Integer marcador2;
    private String estado;
    private LocalDate fecha;
    private int hora;
    private String sede;

    protected Encuentro(Equipo equipo1, Equipo equipo2) {
        if (equipo1 == null || equipo2 == null) {
            throw new IllegalArgumentException("Se necesitan dos equipos.");
        }
        if (equipo1.getCodigo().equals(equipo2.getCodigo())) {
            throw new IllegalArgumentException("Un equipo no puede jugar contra sí mismo.");
        }
        this.equipo1 = equipo1;
        this.equipo2 = equipo2;
        estado = "Sin programar";
        hora = -1;
    }

    @Override
    public void programar(LocalDate fecha, int hora, String sede) {
        if ("Finalizado".equals(estado)) {
            throw new IllegalStateException("Un encuentro finalizado no puede reprogramarse.");
        }
        if (fecha == null || hora < 0 || hora > 23 || sede == null || sede.isBlank()) {
            throw new IllegalArgumentException("Indique una fecha válida, hora entre 0 y 23 y sede no vacía.");
        }
        this.fecha = fecha;
        this.hora = hora;
        this.sede = sede.trim();
        estado = "Programado";
    }

    /** Método plantilla: valida y calcula todo antes de modificar el estado. */
    @Override
    public final void registrarResultado(int marcador1, int marcador2) {
        if (!"Programado".equals(estado)) {
            throw new IllegalStateException(
                    "Solo se puede registrar resultado en un encuentro programado sin resultado previo.");
        }
        if (!validarMarcador(marcador1, marcador2)) {
            throw new IllegalArgumentException(
                    "Marcador inválido: use enteros no negativos; en baloncesto no se permiten empates.");
        }
        int puntos1 = calcularPuntos(marcador1, marcador2);
        int puntos2 = calcularPuntos(marcador2, marcador1);
        if (puntos1 < 0 || puntos2 < 0
                || (long) equipo1.getPuntosTabla() + puntos1 > Integer.MAX_VALUE
                || (long) equipo2.getPuntosTabla() + puntos2 > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("Los puntos calculados son inválidos o exceden el límite.");
        }
        equipo1.sumarPuntos(puntos1);
        equipo2.sumarPuntos(puntos2);
        this.marcador1 = marcador1;
        this.marcador2 = marcador2;
        estado = "Finalizado";
    }

    protected abstract boolean validarMarcador(int marcador1, int marcador2);

    protected abstract int calcularPuntos(int marcadorPropio, int marcadorRival);

    @Override
    public abstract String getDescripcion();

    /** Formato compartido; no imprime ni decide reglas de ningún deporte. */
    protected String describir(String deporte) {
        String resultado = marcador1 == null ? "sin resultado" : marcador1 + " - " + marcador2;
        return deporte + ": " + equipo1.getNombre() + " vs " + equipo2.getNombre()
                + " [" + estado + "; " + resultado + "]";
    }

    public Integer getMarcador1() {
        return marcador1;
    }

    public Integer getMarcador2() {
        return marcador2;
    }

    @Override
    public List<Equipo> getEquipos() {
        return List.of(equipo1, equipo2);
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
}
