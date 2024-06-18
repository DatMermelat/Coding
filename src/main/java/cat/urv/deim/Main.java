package cat.urv.deim;

import cat.urv.deim.exceptions.ComunitatNoTrobada;
import cat.urv.deim.exceptions.VertexNoTrobat;

public class Main {
    public Main() {
    }

    public static void main(String[] args) {
        PajekComunitats test = new PajekComunitats(15, "graph4+4.net");
        double modularitat = test.calcularModularitat();
        ILlistaGenerica<Integer> vertexIDs = test.obtenirVertexIDs();

        for (Integer vertexID : vertexIDs) {
            try {
                if (vertexID != 1) {
                    test.canviDeComunitat(vertexID, 1);
                }
            } catch (VertexNoTrobat | ComunitatNoTrobada e) {
                System.out.println(e.getMessage());
            }
        }

        test.crearFitxer("graph4+4.net.out");
    }
}
