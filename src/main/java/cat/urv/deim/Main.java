package cat.urv.deim;


public class Main {
    public Main() {
    }

    public static void main(String[] args) {
        PajekComunitats test = new PajekComunitats(15, "graph4+4.net");

        test.optimitzarModularitat(10, 100);

        test.crearFitxer("graph4+4.net.out");
    }
}
