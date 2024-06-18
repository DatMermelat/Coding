package cat.urv.deim;

public class HashNode<K extends Comparable<K>,V> implements Comparable<HashNode<K,V>>{
    // Atributs
    private K clau;
    private V valor;
    HashNode<K, V> seguent;

    // Constructor
    public HashNode(K clau, V valor) {
        this.clau = clau;
        this.valor = valor;
        this.seguent = null;
    }

    // Getters i setters
    public K getKey() {
        return this.clau;
    }

    public V getValue() {
        return this.valor;
    }

    public void setKey(K clau) {
        this.clau = clau;
    }

    public void setValue(V valor) {
        this.valor = valor;
    }

    // Metodes
    public int compareTo(HashNode<K,V> other) {
        K clau1 = this.clau;
        K clau2 = other.clau;
        return clau1.compareTo(clau2);
    }
}
