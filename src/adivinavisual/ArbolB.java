package adivinavisual;

import java.io.Serializable;

/**
 * @param <T>
 */

 /*serializable es una clase ubicada en el paquete de java,
sirve para convertir un objeto en bytes y pueda despues recuperarlo.
al convertir el objeto a bytes se puede enviar a traves de la red.
   
*/
public class ArbolB<T> implements Serializable {

    private static final long serialVersionUID = 1;
    int altura;

    static class Nodo<T> implements Serializable {

        int cant;
        T info;
        Nodo<T> izq, der, raiz;
        Nodo<T> nuevo; 

        public Nodo() {

        }

        public Nodo(T palabra) {
            info = palabra;
            izq = null;
            der = null;
        }

        public boolean validaEmpty(Nodo<T> nodo) {
            if (nodo.izq != null || nodo.der != null) {
                return true;
            }
            return false;
        }
        
        // Calcula la altura del arbol.
        public int altura(Nodo<T> ab) {
            if (ab == null) {
                return -1;
            } else {
                cant = 0;
            }
            return (1 + Math.max(altura(ab.izq), altura(ab.der)));
        }

        // Calcula la cantida de nodos del arbol.
        public int cantidad(Nodo<T> reco) {
            if (reco != null) {
                cant++;
                cantidad(reco.izq);
                cantidad(reco.der);
                return cant;
            }
            //cant = 0;
            return -1;
        }
    }
}
