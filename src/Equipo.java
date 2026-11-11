/**
 * Equipo participante: las anotaciones de un partido no son puntos de tabla.
 */
public class Equipo implements Comparable<Equipo> {
    private final String codigo;
    private final String nombre;
    private final String empresa;
    private int puntosTabla;

    public Equipo(String codigo, String nombre, String empresa) {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("El código del equipo no puede estar vacío.");
        }
        if (nombre == null || nombre.isBlank() || empresa == null || empresa.isBlank()) {
            throw new IllegalArgumentException("El nombre y la empresa son obligatorios.");
        }
        this.codigo = codigo.trim();
        this.nombre = nombre.trim();
        this.empresa = empresa.trim();
        this.puntosTabla = 0;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmpresa() {
        return empresa;
    }

    public int getPuntosTabla() {
        return puntosTabla;
    }

    public void sumarPuntos(int puntos) {
        if (puntos < 0 || (long) puntosTabla + puntos > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("La cantidad de puntos es inválida o excede el límite.");
        }
        puntosTabla += puntos;
    }

    @Override
    public int compareTo(Equipo otro) {
        int orden = Integer.compare(otro.puntosTabla, puntosTabla);
        return orden != 0 ? orden : nombre.compareToIgnoreCase(otro.nombre);
    }
}
