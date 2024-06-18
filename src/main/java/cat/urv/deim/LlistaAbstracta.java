package cat.urv.deim;

import cat.urv.deim.exceptions.ElementNoTrobat;
import cat.urv.deim.exceptions.PosicioForaRang;

import java.util.Iterator;
public abstract class LlistaAbstracta<E extends Comparable<E>>  implements ILlistaGenerica<E>{
    // Atributs
    protected Node<E> ghost;
    protected int numElements;

    // Constructor
    public LlistaAbstracta() {
        this.ghost = new Node<E>(null);
        this.numElements = 0;
    }

    // METODES ABSTRACTES

    //Metode per insertar un element a la llista. No importa la posicio on s'afegeix l'element
    public abstract void inserir(E e);

    //Metode per a esborrar un element de la llista
    public abstract void esborrar(E e) throws ElementNoTrobat;

    //Metode per a consultar un element de la llista per posicio
    //La primera dada esta a la posicio 0
    public abstract E consultar(int pos) throws PosicioForaRang;

    //Metode per a comprovar en quina posicio esta un element a la llista
    //La primera dada esta a la posicio 0
    public abstract int buscar(E e) throws ElementNoTrobat;

    //Metode per a comprovar si un element esta a la llista
    public abstract boolean existeix(E e);

    // Metode per a poder itearar sobre la llista
    public abstract Iterator<E> iterator();

    // METODES IMPLEMENTATS

    //Metode per a comprovar si la llista te elements
    public boolean esBuida(){
        return numElements == 0;
    }

    //Metode per a obtenir el nombre d'elements de la llista
    public int numElements() {
        return numElements;
    }

    // METODES NO DISPONIBLES
    /*
    //Metode per a obtenir un array amb tots els elements
    public Object[] elements() {
        Node<E> nodeActual = ghost.seguent;
        Object[] elements = new Object[numElements]; // Definim array de tipus Object i fem cast al tipus generic E

        // Omplim l'array amb els elements de la llista
        for (int i=0; i<numElements; i++){
            elements[i] = nodeActual.info;
            nodeActual = nodeActual.seguent;
        }
        // Retornem l'array
        return elements;
    }
    */
}
