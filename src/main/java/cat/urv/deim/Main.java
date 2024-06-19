package cat.urv.deim;


public class Main {
    public Main() {
    }

    public static void main(String[] args) {
        PajekComunitats test = new PajekComunitats(15, "star.net");

        test.optimitzarModularitat(100, 1000);

        test.crearFitxer("star.clu");
    }
}
