package cat.urv.deim;

import cat.urv.deim.exceptions.ElementNoTrobat;
import cat.urv.deim.exceptions.PosicioForaRang;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LlistaOrdenada<E extends Comparable<E>> extends LlistaAbstracta<E> {
    // Atributs heredats de LlistaAbstracta

    // Constructor
    public LlistaOrdenada(){
        super();
    }

    // Metode per insertar un element a la llista. Ordenem la llista de mes petit a mes gran
    public void inserir(E e){
        Node<E> nouNode = new Node<E>(e);
        Node<E> nodeActual = ghost.seguent;
        Node<E> nodeAnterior = ghost;

        // Busquem on hem d'inserir l'element
        while (nodeActual != null && nouNode.info.compareTo(nodeActual.info) > 0) {
            // Avancem un node
            nodeAnterior = nodeActual;
            nodeActual = nodeActual.seguent;
        }

        nodeAnterior.seguent = nouNode;
        nouNode.seguent = nodeActual;
        numElements++;
    }

    //Metode per a esborrar un element de la llista
    public void esborrar(E e) throws ElementNoTrobat {
        E infoPerEsborrar = e;
        Node<E> nodeActual = ghost.seguent;
        Node<E> nodeAnterior = ghost;
        int comparador;
        boolean trobat = false;
        boolean continuar = true;

        // Busquem el node a esborrar
        while (nodeActual != null && continuar && !trobat) {
            // Fem comparacio
            comparador = infoPerEsborrar.compareTo(nodeActual.info);

            // Comprovacions
            if (comparador > 0) { // Continuem amb la cerca
                nodeAnterior = nodeActual;
                nodeActual = nodeActual.seguent;
            } else if (comparador < 0){ // Hem d'aturar la cerca, no trobarem l'element
                continuar = false;
            } else{ // Hem trobat l'element
                trobat = true;
            }
        }
        // Comprovacions
        if (!trobat) { // No s'ha trobat l'element
            throw new ElementNoTrobat();
        }
        // "Esborrem" i connectem adequadament els nodes
        nodeAnterior.seguent = nodeActual.seguent;
        numElements--;
    }

    //Metode per a consultar un element de la llista per posicio
    //La primera dada esta a la posicio 0
    public E consultar(int pos) throws PosicioForaRang{
        Node<E> nodeActual = ghost.seguent;
        int posPerTrobar = pos;
        int posActual = 0;

        // Comprovacions
        if (posPerTrobar < 0 || posPerTrobar >= numElements){
            throw new PosicioForaRang();
        }
        // Busquem el node en la posicio introduida
        while (posActual != posPerTrobar){
            nodeActual = nodeActual.seguent;
            posActual++;
        }
        // Retornem la info a posActual
        return nodeActual.info;
    }

    //Metode per a comprovar en quina posicio esta un element a la llista
    //La primera dada esta a la posicio 0
    public int buscar(E e) throws ElementNoTrobat{
        Node<E> nodeActual = ghost.seguent;
        int posActual = 0;
        int comparador;
        E infoPerTrobar = e;
        boolean continuar = true;
        boolean trobat = false;

        // Busquem l'element
        while (nodeActual != null && continuar && !trobat){
            // Fem la comparacio
            comparador = infoPerTrobar.compareTo(nodeActual.info);

            // Comprovacions
            if (comparador > 0) { // Continuem amb la cerca
                nodeActual = nodeActual.seguent;
                posActual++;
            } else if (comparador < 0){ // Hem d'aturar la cerca, no trobarem l'element
                continuar = false;
            } else{ // Hem trobat l'element
                trobat = true;
            }
        }
        // Comprovacions
        if (!trobat){ // No s'ha trobat l'element
            throw new ElementNoTrobat();
        }
        // Retornem la posicio trobada
        return posActual;
    }

    //Metode per a comprovar si un element esta a la llista
    public boolean existeix(E e) {
        E infoPerTrobar = e;
        Node<E> nodeActual = ghost.seguent;
        int comparador;
        boolean trobat = false;
        boolean continuar = true;

        // Cas contrari, busquem l'element
        while (nodeActual != null && continuar && !trobat) {
            // Fem comparacio
            comparador = infoPerTrobar.compareTo(nodeActual.info);

            // Comprovacions
            if (comparador > 0) { // Continuem amb la cerca
                nodeActual = nodeActual.seguent;
            } else if (comparador < 0) { // Hem d'aturar la cerca, no trobarem l'element
                continuar = false;
            } else { // Hem trobat l'element
                trobat = true;
            }
        }
        // Retornem el resultat de la cerca
        return trobat;
    }

    // Metode per iterar sobre la llista
    public Iterator<E> iterator() {
        return new IteradorLlista();
    }

    // Iterador
    private class IteradorLlista implements Iterator<E> {
        // Atributs Iterador
        private Node<E> nodeActual;

        // Constructor Iterador
        private IteradorLlista() {
            this.nodeActual = ghost.seguent;
        }

        // METODES D'ITERACIO
        public boolean hasNext() {
            return nodeActual != null;
        }

        public E next() {
            E infoActual;

            // Comprovacions
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            // Avancem un node i retornem la info actual
            infoActual = nodeActual.info;
            nodeActual = nodeActual.seguent;
            return infoActual;
        }
    }

    // Resta de metodes son heredats de LlistaAbstracta
}
