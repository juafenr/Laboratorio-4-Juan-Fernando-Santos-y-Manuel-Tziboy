import java.time.LocalDate;
import java.util.List;

/** Contrato común de los eventos que pueden aparecer en el calendario. */
public interface Programable extends Comparable<Programable> {
    void programar(LocalDate fecha, int hora, String sede);

    LocalDate getFecha();

    int getHora();

    String getSede();

    String getEstado();

    String getDescripcion();

    List<Equipo> getEquipos();

    /** Único criterio cronológico; los eventos sin fecha quedan al final. */
    @Override
    default int compareTo(Programable otro) {
        if (getFecha() == null) {
            return otro.getFecha() == null ? 0 : 1;
        }
        if (otro.getFecha() == null) {
            return -1;
        }
        int orden = getFecha().compareTo(otro.getFecha());
        if (orden == 0) {
            orden = Integer.compare(getHora(), otro.getHora());
        }
        if (orden == 0) {
            orden = getSede().compareToIgnoreCase(otro.getSede());
        }
        return orden;
    }
}
