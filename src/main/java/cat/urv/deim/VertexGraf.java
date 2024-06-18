package cat.urv.deim;

public class VertexGraf<K extends Comparable<K>,V,E> {
    // Atributs Vertex
        public V info;
        public ILlistaGenerica<ArestaGraf<K,E>> conexions;

        // Constructor vertex
        public VertexGraf(V info) {
            this.info = info;
            this.conexions = new LlistaNoOrdenada<>();
        }
}
