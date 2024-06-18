package cat.urv.deim;

import java.util.Iterator;

import javax.naming.CommunicationException;

import cat.urv.deim.exceptions.ComunitatNoTrobada;
import cat.urv.deim.exceptions.ElementNoTrobat;


// Estructura auxiliar per optimitzar el calcul de modularitat
// Utilitzarem aquesta estrucutra per emmagatzemar els vertexs de cada comunitat per separat,
// d'aquesta manera podrem accedir a tots els vertexs d'una comunitat de forma mes eficient.
public class TADComunitats implements Iterable<HashMapIndirecte<Integer,Integer>> {
    // Atributs
    private IHashMap<Integer,HashMapIndirecte<Integer,Integer>> comunitats;
    private int numComunitats;
    private final int vertexsPerComunitat = 15; // Estimacio arbitraria de la quanitat de vertexs per comunitat. Modificar segons la mida de la xarxa

    // Constructor
    public TADComunitats(int maxComunitats) {
        this.comunitats = new HashMapIndirecte<>(maxComunitats);
        this.numComunitats = 0;
    }

    // Metodes

    // Metode per crear una comunitat nova en cas que sigui necesari
    public void crearComunitat(Integer comunitat) {
        this.comunitats.inserir(comunitat, new HashMapIndirecte<>(vertexsPerComunitat));
        this.numComunitats++;
    }

    // Metode per consultar el nombre de comunitats
    public int numComunitats() {
        return this.numComunitats;
    }

    // Metode per accedir a una comunitat
    public IHashMap<Integer,Integer> getComunitat(Integer comunitat) throws ComunitatNoTrobada {
        try {
            return this.comunitats.consultar(comunitat);
        } catch (ElementNoTrobat e) {
            throw new ComunitatNoTrobada();
        }
    }

    // Metode per afegir un vertex a una comunitat
    public void afegirVertex(Integer vertexID, Integer comunitat) throws ComunitatNoTrobada {
        try {
            // Accedim a la comunitat desitjada i inserim el vertex amb el seu ID.
            // Com a informacio guardem el mateix ID per augmentar la eficiencia del
            // calcul de la modularitat
            comunitats.consultar(comunitat).inserir(vertexID, vertexID);
        } catch (ElementNoTrobat e) { // No existeix la comunitat
            throw new ComunitatNoTrobada();
        }
    }

    // Metode per treure un vertex d'una comunitat
    public void eliminarVertex(Integer vertexID, Integer comunitat){
        try {
            // Comprovem que el vertex pertany a la comunitat en questio
            if (!comunitats.consultar(comunitat).buscar(vertexID)) { // No esta a la comunitat
                throw new Error("Es demana esborrar un vertex d'una comunitat a la qual no pertany.");
            }

            // Accedim a la comunitat desitjada i esborrem el vertex
            comunitats.consultar(comunitat).esborrar(vertexID);

            // Comprovem si queden vertexs a la comunitat
            if (comunitats.consultar(comunitat).esBuida()) { // La comunitat es buida, la podem esborrar per estalviar espai
                comunitats.esborrar(comunitat);

                // Decrementem comptador de comunitats
                numComunitats--;
            }
        } catch (ElementNoTrobat e) { // En cas que la comunitat o el vertex no existeixen
            throw new Error("No s'ha trobat la comunitat o el vertex. (ESBORRAT)");
        }
    }

    // Metode per obtenir els IDs de les comunitats en us
    public ILlistaGenerica<Integer> getComunitatIDs() {
        return comunitats.obtenirClaus();
    }

    // Iterador
    public Iterator<HashMapIndirecte<Integer,Integer>> iterator() {
        return this.comunitats.iterator();
    }
}
