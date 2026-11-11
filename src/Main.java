/** Punto de entrada de la aplicación MVC. */
public class Main {
    public static void main(String[] args) {
        Copa copa = new Copa();
        VistaConsola vista = new VistaConsola();
        ControladorCopa controlador = new ControladorCopa(copa, vista);
        controlador.iniciar();
    }
}
