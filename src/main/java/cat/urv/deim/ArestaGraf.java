package cat.urv.deim;

public class ArestaGraf<K extends Comparable<K>, E> implements Comparable<ArestaGraf<K,E>>{
    // Atributs Aresta
    public K from,to;
    public E pes;

    // Constructors Aresta
    public ArestaGraf(K from, K to, E pes) {
        this.pes = pes;
        this.from = from;
        this.to = to;
    }

    public ArestaGraf(K from, K to) {
        this.from = from;
        this.to = to;
        this.pes = null;
    }

    // Metode de comparacio
    public int compareTo(ArestaGraf<K,E> other) {
        return this.to.compareTo(other.to);
    }
}
