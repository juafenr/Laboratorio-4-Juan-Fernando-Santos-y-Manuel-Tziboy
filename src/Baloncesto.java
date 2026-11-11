public class Baloncesto extends Encuentro {
    public Baloncesto(Equipo equipo1, Equipo equipo2) {
        super(equipo1, equipo2);
    }

    @Override
    protected boolean validarMarcador(int marcador1, int marcador2) {
        return marcador1 >= 0 && marcador2 >= 0 && marcador1 != marcador2;
    }

    @Override
    protected int calcularPuntos(int marcadorPropio, int marcadorRival) {
        return marcadorPropio > marcadorRival ? 2 : 1;
    }

    @Override
    public String getDescripcion() {
        return describir("Baloncesto");
    }
}
