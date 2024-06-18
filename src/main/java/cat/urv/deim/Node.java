package cat.urv.deim;

// Definicio Node
public class Node<E> {
// Atributs
E info;
Node<E> seguent;

//Constructor
public Node(E info){
    this.info = info;
    this.seguent = null;
    }
}
