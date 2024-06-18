
package cat.urv.deim;

import cat.urv.deim.exceptions.ElementNoTrobat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class HashMapIndirecte<K extends Comparable<K>,V> implements IHashMap<K,V>{
    // Atributs
    private ArrayList<HashNode<K,V>> taulaEstatica;
    private int numElements;
    private int capacitatActual;
    private final double maxFactor;

    // Constructor
    public HashMapIndirecte(int capacitat) {

        // Comprovacions
        if (capacitat <= 0) {
            throw new IllegalArgumentException();
        }

        this.taulaEstatica = new ArrayList<>();
        this.numElements = 0;
        this.capacitatActual = capacitat;
        this.maxFactor = 0.75;

        // Inicialitzem la taula nodes fantasma
        for (int i = 0; i < capacitatActual; i++) {
            taulaEstatica.add(new HashNode <> (null, null));
        }
    }

    // Metodes

    // Funcio Hash
    private int hash(K key) {
        return Math.abs(key.hashCode()) % midaTaula();
    }

    // Metode per redimensionar la taula en cas d'exces de carrega
    private void redimensionar() {
        ArrayList<HashNode<K,V>> novaTaula = new ArrayList<>();
        HashNode<K, V> nodeCopiat, nodeAux;
        int index;

        capacitatActual = 2 * capacitatActual;

        // Inicialitzem la nova taula
        for (int i = 0; i < midaTaula(); i++) {
            novaTaula.add(new HashNode<>(null, null));
        }
        // Copiem els elements
        for (HashNode<K, V> ghost : taulaEstatica) { // Passem per totes les entrades de la taula estatica
            nodeAux = ghost.seguent;

            while (nodeAux != null) { // Inserim els elements de la llista de colisions al nou hash
                // Obtenim nou index
                index = hash(nodeAux.getKey());

                // Copiem el node
                nodeCopiat = nodeAux;

                // Inserim i enllacem
                nodeCopiat.seguent = novaTaula.get(index).seguent;
                novaTaula.get(index).seguent = nodeCopiat;

                // Avancem nodeAux
                nodeAux = nodeAux.seguent;
            }
        }
        // Acutalitzem la taula estatica i el factor de carrega
        taulaEstatica = novaTaula;
    }

    // Metode per insertar un element a la taula. Si existeix un element amb aquesta clau s'actualitza el valor
    public void inserir(K key, V value) {
        int index;
        HashNode<K, V> nouNode = new HashNode<>(key, value);
        HashNode<K, V> nodeActual, nodeAnterior;

        // Calculem l'index mitjancant la funcio de hash i trobem la llista corresponent
        index = hash(key);
        nodeActual = taulaEstatica.get(index).seguent;
        nodeAnterior = taulaEstatica.get(index);

        // Comprovem si cal sobreescriure alguna clau
        while (nodeActual != null && !key.equals(nodeActual.getKey())) {
            nodeActual = nodeActual.seguent;
            nodeAnterior = nodeAnterior.seguent;
        }

        // Fem insecio
        if (nodeActual != null) { // Hem sortit del bucle abans d'arribar al final. Cal sobreescriure
            nodeActual.setValue(value);
        } else { // Hem recorregut totes les col·lisions. Insrecio al final de la llista de col·lisions
            nodeActual = nouNode;
            nodeAnterior.seguent = nodeActual;
            numElements++;
        }
        // Comprovem si cal redimensionar
        if (factorCarrega() >= maxFactor) {
            redimensionar();
        }
    }

    // Metode per a obtenir un array amb tots els elements de K
    public V consultar(K key) throws ElementNoTrobat {
        int index = hash(key);
        HashNode<K, V> nodeActual = taulaEstatica.get(index).seguent;
        boolean trobat = false;

        // Busquem l'element
        while (nodeActual != null && !trobat) {
            // Comprovacions
            if (nodeActual.getKey().equals(key)) { // Hem trobat l'element
                trobat = true;
            } else { // Continuem amb la cerca
                nodeActual = nodeActual.seguent;
            }
        }
        // Excepcions
        if (!trobat) {
            throw new ElementNoTrobat();
        }
        // Retornem valor
        return nodeActual.getValue();
    }

    // Metode per a esborrar un element de la taula de hash
    public void esborrar(K key) throws ElementNoTrobat {
        int index = hash(key);
        HashNode<K, V> nodeActual = taulaEstatica.get(index).seguent;
        HashNode<K, V> nodeAnterior = taulaEstatica.get(index);
        boolean trobat = false;

        // Busquem el node a esborrar
        while (nodeActual != null && !trobat) {
            // Comprovacions
            if (nodeActual.getKey().equals(key)) { // Hem trobat l'element a esborrar
                trobat = true;
            } else { // Continuem la cerca
                nodeAnterior = nodeAnterior.seguent;
                nodeActual = nodeActual.seguent;
            }
        }
        // Excepcions
        if (!trobat) {
            throw new ElementNoTrobat();
        }
        // Esborrem el node i enllacem
        nodeAnterior.seguent = nodeActual.seguent;
        numElements--;
    }

    // Metode per a comprovar si un element esta a la taula de hash
    public boolean buscar(K key) {
        int index = hash(key);
        HashNode<K, V> nodeActual = taulaEstatica.get(index).seguent;
        boolean trobat = false;

        // Busquem l'element
        while (nodeActual != null && !trobat) {
            // Comprovacions
            if (nodeActual.getKey().equals(key)) { // Hem trobat l'element
                trobat = true;
            } else { // Continuem amb la cerca
                nodeActual = nodeActual.seguent;
            }
        }
        // Retornem
        return trobat;
    }

    // Metode per a comprovar si la taula te elements
    public boolean esBuida() {
        return numElements() == 0;
    }

    // Metode per a obtenir el nombre d'elements de la llista
    public int numElements() {
        return numElements;
    }

    // Metode per a obtenir les claus de la taula
    public ILlistaGenerica<K> obtenirClaus() {
        ILlistaGenerica<K> claus = new LlistaOrdenada<>();
        HashNode<K, V> nodeActual;
        int i = 0;

        // Recorrem totes les entrades de la taula estatica
        for (HashNode<K, V> ghost : taulaEstatica) {
            nodeActual = ghost.seguent;

            // Recorrem llista de col·lisions i introduim claus a la llista
            while (nodeActual != null && i < numElements()) {
                claus.inserir(nodeActual.getKey());
                nodeActual = nodeActual.seguent;
                i++;
            }
        }
        // Retornem llista de claus
        return claus;
    }

    // Metode per a saber el factor de carrega actual de la taula
    public float factorCarrega() {
        return (float) numElements() / midaTaula();
    }

    // Metode per a saber la mida actual de la taula (la mida de la part estatica)
    public int midaTaula(){
        return capacitatActual;
    }

    // Metode per a poder iterar pels elements de la taula
    // IMPORTANT: El recorregut s'ha de fer de forma ORDENADA SEGONS LA CLAU
    public Iterator<V> iterator() {
        return new IteradorHash();
    }

    // Iterador sobre els valors del HashMap
    private class IteradorHash implements Iterator<V> {
        // Atributs Iterador
        private LlistaOrdenada<K> clausOrdenades;
        private HashNode<K,V> nodeActual;
        private Iterator<K> iteradorLlista;

        // Constructor Iterador
        private IteradorHash() {
            this.clausOrdenades = new LlistaOrdenada<>();
            this.nodeActual = null;

            // Ordenem totes les claus
            for (HashNode<K, V> ghost : taulaEstatica) {
                nodeActual = ghost.seguent;

                // Ordenem les col·lisions
                while (nodeActual != null) {
                    clausOrdenades.inserir(nodeActual.getKey());
                    nodeActual = nodeActual.seguent;
                }
            }
            // Utilitzem iteradorLlista per a iterar sobre les claus
            this.iteradorLlista = clausOrdenades.iterator();
        }

        // Metodes d'iteracio sobre els valors dels elements
        public boolean hasNext() {
            return iteradorLlista.hasNext();
        }

        public V next() {
            V valorActual;

            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            // Accedim als valors corresponents a les claus ordenades
            try {
                valorActual = consultar(iteradorLlista.next());
                return valorActual;
            } catch (ElementNoTrobat e){
                throw new NoSuchElementException();
            }
        }
    }
}
