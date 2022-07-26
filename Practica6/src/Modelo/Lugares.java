package Modelo;

/**
 *
 * @author HMCC
 */
public class Lugares {
    private String nombre;
    private Ubicacion ubicacion;
    private String tipoLugar;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Ubicacion getUbicacion() {
         if (ubicacion == null) {
            ubicacion = new Ubicacion();
        }
        return ubicacion;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getTipoLugar() {
        return tipoLugar;
    }

    public void setTipoLugar(String tipoLugar) {
        this.tipoLugar = tipoLugar;
    }

    public Lugares(String nombre, Ubicacion ubicacion, String tipoLugar) {
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.tipoLugar = tipoLugar;
    }

    public Lugares() {
    }

    @Override
    public String toString() {
        return nombre;
    }
   
}
