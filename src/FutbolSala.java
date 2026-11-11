public class FutbolSala extends Encuentro {
    public FutbolSala(Equipo equipo1, Equipo equipo2) {
        super(equipo1, equipo2);
    }

    @Override
    protected boolean validarMarcador(int marcador1, int marcador2) {
        return marcador1 >= 0 && marcador2 >= 0;
    }

    @Override
    protected int calcularPuntos(int marcadorPropio, int marcadorRival) {
        return marcadorPropio > marcadorRival ? 3 : marcadorPropio == marcadorRival ? 1 : 0;
    }

    @Override
    public String getDescripcion() {
        return describir("Fútbol sala");
    }
}
