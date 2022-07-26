package Controlador.TDA_Grafo;

/**
 *
 * @author hilar_c9usj1g
 */
public class TodoCaminoMinimo {
    
    private int [][] pesos;
    private int [][] traza;
    private int [][] d;
    private int n;
    
    public TodoCaminoMinimo(GrafoEND grafo){
        n = grafo.numVertices();
        // faltan los pesos
        d = new int[n][n];
        traza = new int [n][n];
    }
    
    public void todosCaminosMinimo(){
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                d[i][j] = pesos[i][j];
                traza[i][j] = -1;
            }
        }
        for (int i = 0; i < n; i++) {
            d[i][i] = 0;
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    if (d[j][i] + d[i][k] < d[j][k]) {
                        d[j][k] = d[j][i] + d[i][k];
                        traza[j][k] = i;
                    }
                }
            }
        }
    }
     
    //revisar dudosa procedencia
    public void recuperarCamino(int vi, int vj){
        int []anterior = traza[vi];
        if(vi != vj){
            recuperarCamino(anterior[vi], vj);
            System.out.println("----- V" + vi);
            
        }else{
            System.out.println("V " + vj);
        }
    }
    
}
