import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

/** Única clase de producción que lee o imprime en consola. */
public class VistaConsola {
    private final Scanner entrada;

    public VistaConsola() {
        entrada = new Scanner(System.in);
    }

    public void mostrarMenu() {
        System.out.println("\n=== ArenaGT | Copa entre empresas ===");
        System.out.println("1. Registrar equipo");
        System.out.println("2. Crear encuentro");
        System.out.println("3. Crear exhibición");
        System.out.println("4. Programar o reprogramar evento");
        System.out.println("5. Registrar resultado");
        System.out.println("6. Mostrar tabla de posiciones");
        System.out.println("7. Mostrar calendario");
        System.out.println("0. Salir");
    }

    public void mostrarOpcionesDeporte() {
        System.out.println("1. Fútbol sala\n2. Baloncesto");
    }

    public String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return entrada.nextLine().trim();
    }

    public int leerEntero(String mensaje) {
        while (true) {
            try {
                return Integer.parseInt(leerTexto(mensaje));
            } catch (NumberFormatException error) {
                mostrarMensaje("Entrada inválida: escriba un número entero dentro del rango de int.");
            }
        }
    }

    public LocalDate leerFecha(String mensaje) {
        while (true) {
            try {
                return LocalDate.parse(leerTexto(mensaje));
            } catch (DateTimeParseException error) {
                mostrarMensaje("Fecha inválida. Use AAAA-MM-DD, por ejemplo 2026-11-12.");
            }
        }
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    public void mostrarEquipos(List<Equipo> equipos) {
        System.out.println("\nEquipos registrados (seleccione por código):");
        for (Equipo equipo : equipos) {
            System.out.printf("%s | %s | %s%n", equipo.getCodigo(), equipo.getNombre(), equipo.getEmpresa());
        }
        if (equipos.isEmpty()) {
            mostrarMensaje("No hay equipos registrados.");
        }
    }

    public void mostrarEventos(List<Programable> eventos) {
        System.out.println("\nEventos (seleccione por número):");
        for (int i = 0; i < eventos.size(); i++) {
            Programable evento = eventos.get(i);
            System.out.println((i + 1) + ". " + evento.getDescripcion());
            if (evento.getFecha() != null) {
                System.out.printf("   %s %02d:00 | %s%n", evento.getFecha(), evento.getHora(), evento.getSede());
            }
        }
        if (eventos.isEmpty()) {
            mostrarMensaje("No hay eventos creados.");
        }
    }

    public void mostrarEncuentros(List<ConResultado> encuentros) {
        System.out.println("\nEncuentros (marcador en el orden de los equipos mostrados):");
        for (int i = 0; i < encuentros.size(); i++) {
            System.out.println((i + 1) + ". " + encuentros.get(i).getDescripcion());
        }
        if (encuentros.isEmpty()) {
            mostrarMensaje("No hay encuentros creados.");
        }
    }

    public void mostrarTablaPosiciones(List<Equipo> equipos) {
        System.out.println("\n=== Tabla de posiciones ===");
        System.out.printf("%-4s %-10s %-20s %-20s %s%n", "Pos.", "Código", "Equipo", "Empresa", "Puntos de tabla");
        int posicion = 1;
        for (Equipo equipo : equipos) {
            System.out.printf("%-4d %-10s %-20s %-20s %d%n", posicion++, equipo.getCodigo(),
                    equipo.getNombre(), equipo.getEmpresa(), equipo.getPuntosTabla());
        }
        if (equipos.isEmpty()) {
            mostrarMensaje("No hay equipos registrados.");
        }
    }

    public void mostrarCalendario(Iterable<Programable> calendario) {
        System.out.println("\n=== Calendario ===");
        boolean vacio = true;
        for (Programable evento : calendario) {
            System.out.printf("%s %02d:00 | %s | %s%n", evento.getFecha(), evento.getHora(),
                    evento.getSede(), evento.getDescripcion());
            vacio = false;
        }
        if (vacio) {
            mostrarMensaje("No hay eventos programados.");
        }
    }
}
