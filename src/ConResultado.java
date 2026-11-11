/** Responsabilidad exclusiva de los encuentros que reparten puntos de tabla. */
public interface ConResultado {
    void registrarResultado(int marcador1, int marcador2);

    String getDescripcion();
}
