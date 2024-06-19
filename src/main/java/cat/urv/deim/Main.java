package cat.urv.deim;


public class Main {
    public Main() {
    }

    public static void main(String[] args) {
        PajekComunitats test = new PajekComunitats(15, "zachary_unwh.net");

        test.optimitzarModularitat(100, 1000);

        System.out.println(test.numComunitats());

        test.crearFitxer("zachary_unwh.clu");
    }
}
