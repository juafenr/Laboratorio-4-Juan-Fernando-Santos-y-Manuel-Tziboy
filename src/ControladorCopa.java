import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

/** Coordina modelo y vista; no contiene reglas de puntuación ni de orden. */
public class ControladorCopa {
    private final Copa modelo;
    private final VistaConsola vista;

    public ControladorCopa(Copa modelo, VistaConsola vista) {
        this.modelo = modelo;
        this.vista = vista;
    }

    public void iniciar() {
        cargarDatosIniciales();
        vista.mostrarMensaje("Datos iniciales cargados: 4 equipos, 4 encuentros (3 finalizados) y 1 exhibición.");
        boolean continuar = true;
        while (continuar) {
            try {
                vista.mostrarMenu();
                switch (vista.leerEntero("Opción: ")) {
                    case 1:
                        registrarEquipo();
                        break;
                    case 2:
                        crearEncuentro();
                        break;
                    case 3:
                        crearExhibicion();
                        break;
                    case 4:
                        programarEvento();
                        break;
                    case 5:
                        registrarResultado();
                        break;
                    case 6:
                        mostrarTablaPosiciones();
                        break;
                    case 7:
                        mostrarCalendario();
                        break;
                    case 0:
                        continuar = false;
                        break;
                    default:
                        vista.mostrarMensaje("Opción inválida. Seleccione de 0 a 7.");
                }
            } catch (IllegalArgumentException | IllegalStateException error) {
                vista.mostrarMensaje("No se realizó la operación: " + error.getMessage());
            } catch (NoSuchElementException error) {
                vista.mostrarMensaje("Fin de la entrada.");
                continuar = false;
            }
        }
        vista.mostrarMensaje("Programa finalizado.");
    }

    private void cargarDatosIniciales() {
        modelo.registrarEquipo("FAR", "Faro", "Empresa Norte");
        modelo.registrarEquipo("SOL", "Sol", "Empresa Sur");
        modelo.registrarEquipo("LUN", "Luna", "Empresa Norte");
        modelo.registrarEquipo("NUB", "Nube", "Empresa Sur");
        Equipo faro = modelo.buscarEquipo("FAR");
        Equipo sol = modelo.buscarEquipo("SOL");
        Equipo luna = modelo.buscarEquipo("LUN");
        Equipo nube = modelo.buscarEquipo("NUB");
        Encuentro futbol = new FutbolSala(faro, sol);
        Encuentro baloncesto = new Baloncesto(sol, faro);
        Encuentro empate = new FutbolSala(luna, nube);
        Encuentro pendiente = new Baloncesto(luna, sol);
        modelo.agregarEncuentro(futbol);
        modelo.agregarEncuentro(baloncesto);
        modelo.agregarEncuentro(empate);
        modelo.agregarEncuentro(pendiente);
        LocalDate fecha = LocalDate.of(2026, 11, 12);
        modelo.programarEvento(futbol, fecha, 9, "Cancha Norte");
        modelo.programarEvento(baloncesto, fecha, 14, "Gimnasio");
        modelo.programarEvento(empate, fecha, 9, "Cancha Sur");
        modelo.registrarResultado(futbol, 3, 1);
        modelo.registrarResultado(baloncesto, 78, 74);
        modelo.registrarResultado(empate, 2, 2);
        Exhibicion exhibicion = new Exhibicion("Clínica de baloncesto");
        modelo.agregarExhibicion(exhibicion);
        modelo.programarEvento(exhibicion, fecha, 11, "Gimnasio");
    }

    private void registrarEquipo() {
        String codigo = vista.leerTexto("Código único: ");
        String nombre = vista.leerTexto("Nombre del equipo: ");
        String empresa = vista.leerTexto("Empresa: ");
        modelo.registrarEquipo(codigo, nombre, empresa);
        vista.mostrarMensaje("Equipo registrado.");
    }

    private void crearEncuentro() {
        if (modelo.getEquipos().size() < 2) {
            throw new IllegalStateException("Registre al menos dos equipos.");
        }
        vista.mostrarOpcionesDeporte();
        int deporte = vista.leerEntero("Deporte: ");
        if (deporte != 1 && deporte != 2) {
            throw new IllegalArgumentException("Seleccione 1 para fútbol sala o 2 para baloncesto.");
        }
        vista.mostrarEquipos(modelo.getEquipos());
        Equipo equipo1 = modelo.buscarEquipo(vista.leerTexto("Código del primer equipo: "));
        Equipo equipo2 = modelo.buscarEquipo(vista.leerTexto("Código del segundo equipo: "));
        Encuentro encuentro = deporte == 1 ? new FutbolSala(equipo1, equipo2) : new Baloncesto(equipo1, equipo2);
        modelo.agregarEncuentro(encuentro);
        vista.mostrarMensaje("Encuentro creado sin programar.");
    }

    private void crearExhibicion() {
        modelo.agregarExhibicion(new Exhibicion(vista.leerTexto("Título de la exhibición: ")));
        vista.mostrarMensaje("Exhibición creada sin programar.");
    }

    private void programarEvento() {
        List<Programable> eventos = modelo.getEventos();
        vista.mostrarEventos(eventos);
        if (eventos.isEmpty()) {
            return;
        }
        int indice = vista.leerEntero("Número de evento: ") - 1;
        if (indice < 0 || indice >= eventos.size()) {
            throw new IllegalArgumentException("Número de evento inexistente.");
        }
        LocalDate fecha = vista.leerFecha("Fecha (AAAA-MM-DD): ");
        int hora = vista.leerEntero("Hora de inicio (0 a 23): ");
        String sede = vista.leerTexto("Sede: ");
        modelo.programarEvento(eventos.get(indice), fecha, hora, sede);
        vista.mostrarMensaje("Evento programado correctamente.");
    }

    private void registrarResultado() {
        List<ConResultado> encuentros = modelo.getEncuentros();
        vista.mostrarEncuentros(encuentros);
        if (encuentros.isEmpty()) {
            return;
        }
        int indice = vista.leerEntero("Número de encuentro: ") - 1;
        if (indice < 0 || indice >= encuentros.size()) {
            throw new IllegalArgumentException("Número de encuentro inexistente.");
        }
        int marcador1 = vista.leerEntero("Anotaciones del primer equipo: ");
        int marcador2 = vista.leerEntero("Anotaciones del segundo equipo: ");
        modelo.registrarResultado(encuentros.get(indice), marcador1, marcador2);
        vista.mostrarMensaje("Resultado registrado; puntos de tabla actualizados.");
    }

    private void mostrarTablaPosiciones() {
        vista.mostrarTablaPosiciones(modelo.obtenerTablaPosiciones());
    }

    private void mostrarCalendario() {
        vista.mostrarCalendario(modelo);
    }
}
