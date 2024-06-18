package cat.urv.deim;

import java.util.Iterator;

import cat.urv.deim.exceptions.ElementNoTrobat;
import cat.urv.deim.exceptions.PosicioForaRang;

public class LlistaNoOrdenada<E extends Comparable<E>> extends LlistaAbstracta<E> {
    // Atributs heredats de LlistaAbstracta

    // Constructor
    public LlistaNoOrdenada(){
        super();
    }

    // Metode per insertar un element a la llista. No importa la posicio on s'afegeix l'element
    public void inserir(E e){
        // Construim nouNode amb la informacio a inserir
        Node<E> nouNode = new Node<E>(e);
        Node<E> nodeActual = ghost.seguent;
        Node<E> nodeAnterior = ghost;

        // Mirem si cal sobreescriure o inserir al final de la llista
        while (nodeActual != null && nodeActual.info.compareTo(e) != 0) {
            nodeActual = nodeActual.seguent;
            nodeAnterior = nodeAnterior.seguent;
        }
        // Comprovacions
        if (nodeActual != null) { // Cal sobreescriure
            nodeActual.info = e;
        } else { // Insrecio al final de la llista
            nodeActual = nouNode;
            nodeAnterior.seguent = nodeActual;

            // Actualitzem comptador d'elements
            numElements++;
        }
    }

    // Metode per a esborrar un element de la llista
    public void esborrar(E e) throws ElementNoTrobat{
        E infoPerEsborrar = e;
        int comparador;
        boolean trobat = false;
        Node<E> nodeActual = ghost.seguent;
        Node<E> nodeAnterior = ghost;

        // Busquem el node a esborrar
        while (nodeActual != null && !trobat) {
            // Fem comparacio
            comparador = infoPerEsborrar.compareTo(nodeActual.info);

            // Comprovacions
            if (comparador == 0){ // Hem trobat l'element
                trobat = true;
            } else{ // Continuem amb la cerca
                nodeAnterior = nodeActual;
                nodeActual = nodeActual.seguent;
            }
        }
        // Comprovacions
        if(!trobat){
            throw new ElementNoTrobat();
        }
        // "Esborrem" i connectem adequadament els nodes
        nodeAnterior.seguent = nodeActual.seguent;
        numElements--;
    }

    // Metode per a consultar un element de la llista per posicio
    // La primera dada esta a la posicio 0
    public E consultar(int pos) throws PosicioForaRang{
        int posActual = 0;
        int posPerTrobar = pos;
        Node<E> nodeActual = ghost.seguent;

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

    // Metode per a comprovar en quina posicio esta un element a la llista
    // La primera dada esta a la posicio 0
    public int buscar(E e) throws ElementNoTrobat{
        Node<E> nodeActual = ghost.seguent;
        int posActual = 0;
        int comparador;
        E infoPerTrobar = e;
        boolean trobat = false;

        // Busquem l'element
        while (nodeActual != null && !trobat){
            // Fem comparacio
            comparador = infoPerTrobar.compareTo(nodeActual.info);

            // Comprovacions
            if (comparador == 0){ // Hem trobat l'element
                trobat = true;
            } else{ // Continuem amb la cerca
                nodeActual = nodeActual.seguent;
                posActual++;
            }
        }
        // Comprovacions
        if (!trobat){
            throw new ElementNoTrobat();
        }
        // Retornem la posicio trobada
        return posActual;
    }

    // Metode per a comprovar si un element esta a la llista
    public boolean existeix(E e) {
        E infoPerTrobar = e;
        Node<E> nodeActual = ghost.seguent;
        int comparador;
        boolean trobat = false;

        // Cas contrari, busquem l'element
        while (nodeActual != null && !trobat) {
            // Fem comparacio
            comparador = infoPerTrobar.compareTo(nodeActual.info);

            // Comprovacions
            if (comparador == 0) { // Hem trobat l'element
                trobat = true;
            } else { // Continuem la cerca
                nodeActual = nodeActual.seguent;
            }
        }
        // Retornem el resultat de la cerca
        return trobat;
    }

    // Metode per iterar sobre la llista de forma ordenada
    public Iterator<E> iterator() {
        return new IteradorLlista();
    }

    // Iterador
    protected class IteradorLlista implements Iterator<E> {
        // Atributs Iterador
        private Node<E> nodeActual;
        LlistaOrdenada<E> elemsOrdenats;
        Iterator<E> iterador;

        // Constructor Iterador
        protected IteradorLlista() {
            this.nodeActual = ghost.seguent;
            this.elemsOrdenats = new LlistaOrdenada<>();

            // Ordenem els elements de la llista
            while (nodeActual != null) {
                elemsOrdenats.inserir(nodeActual.info);
                nodeActual = nodeActual.seguent;
            }
            // Utilitzem l'iterador de la llista ordenada
            this.iterador = elemsOrdenats.iterator();
        }

        // Metdoes d'iteracio
        public boolean hasNext() {
            return iterador.hasNext();
        }

        public E next() {
            return iterador.next();
        }
    }
    // Resta de metodes son heredats de LlistaAbstracta
}
