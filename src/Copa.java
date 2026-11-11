import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Modelo de la copa. La aplicación programa y registra resultados por esta
 * clase.
 */
public class Copa implements Iterable<Programable> {
    private final Map<String, Equipo> equipos;
    private final List<Programable> eventos;
    private final List<ConResultado> encuentros;

    public Copa() {
        equipos = new LinkedHashMap<>();
        eventos = new ArrayList<>();
        encuentros = new ArrayList<>();
    }

    public void registrarEquipo(String codigo, String nombre, String empresa) {
        Equipo nuevo = new Equipo(codigo, nombre, empresa);
        if (equipos.containsKey(nuevo.getCodigo())) {
            throw new IllegalArgumentException("Ya existe un equipo con el código " + nuevo.getCodigo() + ".");
        }
        equipos.put(nuevo.getCodigo(), nuevo);
    }

    public Equipo buscarEquipo(String codigo) {
        Equipo equipo = codigo == null ? null : equipos.get(codigo.trim());
        if (equipo == null) {
            throw new IllegalArgumentException("No existe un equipo con ese código.");
        }
        return equipo;
    }

    public List<Equipo> getEquipos() {
        return List.copyOf(equipos.values());
    }

    public void agregarEncuentro(Encuentro encuentro) {
        if (encuentro == null || eventos.contains(encuentro)) {
            throw new IllegalArgumentException("El encuentro es nulo o ya está agregado.");
        }
        for (Equipo equipo : encuentro.getEquipos()) {
            if (equipos.get(equipo.getCodigo()) != equipo) {
                throw new IllegalArgumentException("Los participantes deben ser los equipos registrados en esta copa.");
            }
        }
        if (encuentro.getFecha() != null) {
            validarDisponibilidad(encuentro, encuentro.getFecha(), encuentro.getHora(), encuentro.getSede());
        }
        eventos.add(encuentro);
        encuentros.add(encuentro);
    }

    public void agregarExhibicion(Exhibicion exhibicion) {
        if (exhibicion == null || eventos.contains(exhibicion)) {
            throw new IllegalArgumentException("La exhibición es nula o ya está agregada.");
        }
        if (exhibicion.getFecha() != null) {
            validarDisponibilidad(exhibicion, exhibicion.getFecha(), exhibicion.getHora(), exhibicion.getSede());
        }
        eventos.add(exhibicion);
    }

    public List<Programable> getEventos() {
        return List.copyOf(eventos);
    }

    public List<ConResultado> getEncuentros() {
        return List.copyOf(encuentros);
    }

    public void programarEvento(Programable evento, LocalDate fecha, int hora, String sede) {
        if (!eventos.contains(evento)) {
            throw new IllegalArgumentException("El evento no pertenece a esta copa.");
        }
        validarDisponibilidad(evento, fecha, hora, sede);
        evento.programar(fecha, hora, sede);
    }

    private void validarDisponibilidad(Programable evento, LocalDate fecha, int hora, String sede) {
        if (fecha == null || hora < 0 || hora > 23 || sede == null || sede.isBlank()) {
            throw new IllegalArgumentException("Indique una fecha válida, hora entre 0 y 23 y sede no vacía.");
        }
        for (Programable otro : eventos) {
            if (otro == evento || !fecha.equals(otro.getFecha()) || hora != otro.getHora()) {
                continue;
            }
            if (sede.trim().equalsIgnoreCase(otro.getSede())) {
                throw new IllegalArgumentException("La sede ya está ocupada en esa fecha y hora.");
            }
            for (Equipo equipo : evento.getEquipos()) {
                if (otro.getEquipos().contains(equipo)) {
                    throw new IllegalArgumentException(
                            "El equipo " + equipo.getNombre() + " ya tiene otro encuentro en esa fecha y hora.");
                }
            }
        }
    }

    public void registrarResultado(ConResultado encuentro, int marcador1, int marcador2) {
        if (!encuentros.contains(encuentro)) {
            throw new IllegalArgumentException("El encuentro no pertenece a esta copa.");
        }
        encuentro.registrarResultado(marcador1, marcador2);
    }

    public List<Equipo> obtenerTablaPosiciones() {
        List<Equipo> tabla = new ArrayList<>(equipos.values());
        Collections.sort(tabla);
        return List.copyOf(tabla);
    }

    /** Instantánea ordenada: remove() no puede alterar la colección interna. */
    @Override
    public Iterator<Programable> iterator() {
        List<Programable> calendario = new ArrayList<>();
        for (Programable evento : eventos) {
            if (evento.getFecha() != null) {
                calendario.add(evento);
            }
        }
        Collections.sort(calendario);
        return List.copyOf(calendario).iterator();
    }
}
