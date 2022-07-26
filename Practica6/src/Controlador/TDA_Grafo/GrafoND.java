package Controlador.TDA_Grafo;

import Controlador.Exceptions.VerticeException;

/**
 *
 * @author hilar_c9usj1g
 */
public class GrafoND extends GrafoD{

    public GrafoND(Integer numV) {
        super(numV);
    }
    
    @Override
    public void insertarArista(Integer i, Integer j, Double peso) throws VerticeException {
        if (i > 0 && j > 0 &&i <= numV && j <= numV) {
            Object[] existe = existeArista(i, j);
            if (!((Boolean) existe[0])) {
                numA++;
                listaAdyacente[i].insertarCabecera(new Adyacencia(j, peso));
                listaAdyacente[j].insertarCabecera(new Adyacencia(i, peso));
            }
        } else {
            throw new VerticeException("Algun vertice ingresado no existe");
        }
    }
//    
//    public static void main(String[] args) {
//        GrafoND gnd = new GrafoND(6);
//        
//        try {
//            gnd.insertarArista(1, 4);
//            //System.out.println(gnd.existeArista(1, 6)[0]);
//            System.out.println(gnd.toString());
//        } catch (Exception e) {
//            System.out.println("ERROR: " +e);
//        }
//    } 
}
