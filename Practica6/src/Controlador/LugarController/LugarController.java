package Controlador.LugarController;

import Controlador.TDA_Grafo.GrafoEND;
import Modelo.Lugares;
import Modelo.Ubicacion;

/**
 *
 * @author HMCC
 */
public class LugarController {

    private GrafoEND<Lugares> grafoLugar;
    private Lugares lugar;
    int i = 1;

    public LugarController() {
    }

    public Lugares guardarDatos(String nombre, String tipo, Double latitud, Double longitud) {
        Lugares lugar = new Lugares();
        lugar.setNombre(nombre);
        lugar.setTipoLugar(tipo);
        Ubicacion u = new Ubicacion();
        u.setLatitud(latitud);
        u.setLongitud(longitud);
        lugar.setUbicacion(u);

        return lugar;
    }

    public void agregarGrafo(Lugares lugar) {
        grafoLugar.nuevoGrafoE();
        grafoLugar.etiquetarVertice(i, lugar);
        i++;
    }

    public GrafoEND<Lugares> getGrafoLugar() {
        if (grafoLugar == null) {
            grafoLugar = new GrafoEND<>(0, Lugares.class);
        }
        return grafoLugar;
    }

    public void setGrafoLugar(GrafoEND<Lugares> grafoLugar) {
        this.grafoLugar = grafoLugar;
    }

    public Lugares getLugar() {
        if (lugar == null) {
            lugar = new Lugares();
        }
        return lugar;
    }

    public void setLugar(Lugares lugar) {
        this.lugar = lugar;
    }

    public Double calcularDistancia(Lugares puntoOrigen, Lugares puntoDestino) {
        Double distancia = 0.0;
        Double x = puntoOrigen.getUbicacion().getLongitud() - puntoDestino.getUbicacion().getLongitud();
        Double y = puntoOrigen.getUbicacion().getLatitud() - puntoDestino.getUbicacion().getLatitud();
        distancia = Math.sqrt((x * x) + (y * y));
        return Math.round(distancia)*100.0/100.0;
    }

}
