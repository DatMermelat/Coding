package cat.urv.deim;

import cat.urv.deim.exceptions.ElementNoTrobat;
import cat.urv.deim.exceptions.VertexNoTrobat;
import cat.urv.deim.exceptions.ArestaNoTrobada;
import cat.urv.deim.exceptions.ComunitatNoTrobada;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;

import java.util.Iterator;

import javax.naming.CommunicationException;


public class PajekComunitats {
    // Atributs
    private IGraf<Integer, Integer, Integer> xarxa;
    private TADComunitats comunitats;
    private Double modularitat;

    // Constructor
    public PajekComunitats(int capacitat, String filename) {
        this.xarxa = new Graf<>(capacitat);
        this.modularitat = null;

        // Lectura del fitxer (Pajek)
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {

            String line;
            boolean readingVertices = false;
            boolean readingEdges = false;

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("*Vertices")) { // Toca llegir vertexs

                    readingVertices = true;
                    readingEdges = false;

                } else if (line.startsWith("*Edges")) { // Toca llegir arestes

                    readingVertices = false;
                    readingEdges = true;

                } else if (readingVertices) {

                    String[] parts = line.split("\\s+", 2);
                    int id = Integer.parseInt(parts[0]);

                    // Afegim el vertex al graf. La comunitat del vertex comenca sent igual al seu id
                    xarxa.inserirVertex(id, id);

                } else if (readingEdges) {

                    String[] parts = line.split("\\s+");
                    int source = Integer.parseInt(parts[0]);
                    int target = Integer.parseInt(parts[1]);

                    try{
                        xarxa.inserirAresta(source, target);
                    } catch (VertexNoTrobat e) {
                        e.printStackTrace();
                        System.err.println("Error llegint la aresta " + source + "," + target);
                    }

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Creem i Inicialitzem la taula de comunitats
        this.comunitats = new TADComunitats((int) Math.round(xarxa.numVertex() / 0.8)); // Ens assegurem de reservar prou espai per no haver de redimensionar

        try{
            for (Integer vertexID : xarxa.obtenirVertexIDs()) {
                // Afegim cada vertex a la seva propia comunitat (tantes comunitats com vertexs)
                comunitats.crearComunitat(vertexID);
                comunitats.afegirVertex(vertexID, vertexID);
            }
        } catch (ComunitatNoTrobada e) {
            throw new Error("No s'hauria de poder arribar a aquest error (INICIALITZACIO COMUNITATS)");
        }
    }

    // Metodes

    // Metode per consultar la particio en comunitats actual
    public TADComunitats getTADComunitats() {
        return comunitats;
    }

    // Metode per consultar la xarxa carregada
    public IGraf<Integer, Integer, Integer> getNetwork() {
        return xarxa;
    }

    // Metode per consultar la comunitat d'un vertex
    public Integer consultarComunitat(Integer vertexID) {
        try{
            return xarxa.consultarVertex(vertexID);
        } catch (VertexNoTrobat e) {
            throw new Error("S'ha consultat un vertex que no pertany a la xarxa");
        }
    }
    // Metode per calcular la modularitat
    public double calcularModularitat() {
        double numArestes = (double) xarxa.numArestes();
        ILlistaGenerica<Integer> comunitatIDs = comunitats.getComunitatIDs();
        double suma = 0;

        // Bucle per calcular la modularitat
        for (Integer id : comunitatIDs) {
            try{
                // Fem cast a double a les varibales que ho requereixen
                double connexionsComunitat = (double) connexionsComunitat(id);
                double sumaGraus = (double) sumaGraus(id);

                // Afegim el terme a la suma
                suma += connexionsComunitat / numArestes - Math.pow(sumaGraus / (2 * numArestes), 2);

            } catch (ComunitatNoTrobada e) {
                throw new Error("Error calculant modularitat: No s'ha trobat alguna comunitat");
            }
        }
        // Retornem
        return suma;
    }

    // Metode per obtenir el nombre de connexions internes d'una comunitat
    public int connexionsComunitat(Integer c) throws ComunitatNoTrobada{
        int connexions = 0;

        try {
            // Accedim a la comunitat
            IHashMap<Integer, Integer> comunitat = comunitats.getComunitat(c);

            // Bucle per comptar les connexions intracomunitaries
            for (Integer vertexID : comunitat){
                for (Integer vei : xarxa.obtenirVeins(vertexID)){
                    // Comprovem si el vei pertany a la mateixa comunitat
                    if(comunitat.buscar(vei)){ // El vei es de la comunitat. Incrementem comptador
                        connexions++;
                    }
                }
            }
            // Tractem la redundancia d'informacio
            if (connexions % 2 == 0){ // Parell
                return connexions/2;
            }
            return (connexions + 1) / 2;

        } catch (VertexNoTrobat e) {
            throw new Error("No hauria de ser possible arribar a aquest error. (CONNEXIONS COMUNITAT)");
        } catch (ComunitatNoTrobada e) {
            throw new ComunitatNoTrobada();
        }
    }

    // Metode per obtenir la suma dels graus de tots els vertexs d'una comunitat
    public int sumaGraus(Integer comunitat) throws ComunitatNoTrobada{
        int suma = 0;

        try {
            // Bucle per sumar els graus
            for (Integer vertexID : comunitats.getComunitat(comunitat)) {
                suma += xarxa.numVeins(vertexID);
            }
            // Retornem la suma de graus
            return suma;
        } catch (VertexNoTrobat e) {
            throw new Error("No hauria de ser possible arribar a aquest error. (SUMA GRAUS)");
        } catch (ComunitatNoTrobada e) {
            throw new ComunitatNoTrobada();
        }
    }

    // Metode per canviar un node de comunitat
    public void canviDeComunitat(Integer vertexID, Integer comunitat) throws VertexNoTrobat, ComunitatNoTrobada {
        try {
            // Obtenim la comunitat actual del vertex
            Integer comunitatActual = xarxa.consultarVertex(vertexID);

            // Afegim el vertex a la comunitat indicada
            comunitats.afegirVertex(vertexID, comunitat);

            // Eliminem el vertex de la comunitat actual
            comunitats.eliminarVertex(vertexID, comunitatActual);

        } catch (VertexNoTrobat e) {
            throw new VertexNoTrobat();
        } catch (ComunitatNoTrobada e) {
            throw new ComunitatNoTrobada();
        }
    }

    // Metode per obtenir una llista amb els IDs dels vertexs
    public ILlistaGenerica<Integer> obtenirVertexIDs() {
        return xarxa.obtenirVertexIDs();
    }

    // Metode per generar un fitxer de sortida amb la particio en comunitats
    public void crearFitxer(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            // Primera linia
            writer.write("*Vertices " + xarxa.numVertex());
            writer.newLine();

            try{
                // Escrivim la comunitat de cada vertex linea per linea
                for (int i = 1; i <= xarxa.numVertex(); i++) {
                    writer.write(String.valueOf(xarxa.consultarVertex(i)));
                    writer.newLine();
                }
            } catch (VertexNoTrobat e) {
                throw new Error("Error consultant vertexs");
            }
        } catch (IOException e) {
            System.err.println("Error creant fitxer");
        }
    }
}
