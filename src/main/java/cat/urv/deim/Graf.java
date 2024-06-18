package cat.urv.deim;

import cat.urv.deim.exceptions.VertexNoTrobat;
import cat.urv.deim.exceptions.ArestaNoTrobada;
import cat.urv.deim.exceptions.ElementNoTrobat;

import java.util.Iterator;

public class Graf<K extends Comparable<K>, V, E> implements IGraf<K, V, E> {

    // Atributs Graf
    private IHashMap<K, VertexGraf<K,V,E>> taulaVertexs;

    // Constructor Graf
    public Graf(int capacitat) {
        this.taulaVertexs = new HashMapIndirecte<>(capacitat);
    }

    // Operacions per a treballar amb els vertexs

    // Metode per insertar un nou vertex al graf. El valor de K es l'identificador del vertex i V es el valor del vertex
    public void inserirVertex(K key, V value) {
        taulaVertexs.inserir(key, new VertexGraf<K,V,E>(value));
    }

    // Metode per a obtenir el valor d'un vertex del graf a partir del seu identificador
    public V consultarVertex(K key) throws VertexNoTrobat {
        try {
            return taulaVertexs.consultar(key).info;
        } catch (ElementNoTrobat e) {
            throw new VertexNoTrobat();
        }
    }

    // Metode per a esborrar un vertex del graf a partir del seu identificador
    // Aquest metode tambe ha d'esborrar totes les arestes associades a aquest vertex
    public void esborrarVertex(K key) throws VertexNoTrobat {
        ILlistaGenerica<K> claus;
        K clauPerEsobrrar = key;

        // Primer esborrem totes les conexions al vertex per esborrar
        claus = obtenirVertexIDs();

        // Iterem sobre totes les claus del graf
        for (K clau : claus) {
            try { // Intentem esborrar la conexio al vertex per esborrar
                esborrarAresta(clau, clauPerEsobrrar);
            } catch (VertexNoTrobat | ArestaNoTrobada e) {} // No existeix la conexio, no cal esborrar
        }
        // Esborrem el vertex de la taula
        try {
            taulaVertexs.esborrar(clauPerEsobrrar);
        } catch (ElementNoTrobat e) {
            throw new VertexNoTrobat();
        }
    }

    // Metode per a comprovar si hi ha algun vertex introduit al graf
    public boolean esBuida() {
        return taulaVertexs.esBuida();
    }

    // Metode per a comprovar el nombre de vertexs introduits al graf
    public int numVertex() {
        return taulaVertexs.numElements();
    }

    // Metode per a obtenir tots els ID de vertex de l'estrucutra
    public ILlistaGenerica<K> obtenirVertexIDs() {
        return taulaVertexs.obtenirClaus();
    }

    ////////////////////////////////////////////////////////////////////////////////////
    // Operacions per a treballar amb les arestes

    // Metode per a insertar una aresta al graf. Els valors de vertex 1 i vertex 2 son els vertex a connectar i E es el pes de la aresta
    // Si ja existeix l'aresta se li actualitza el seu pes
    public void inserirAresta(K v1, K v2, E pes) throws VertexNoTrobat {
        VertexGraf<K,V,E> vei1, vei2;
        ArestaGraf<K,E> conexio12, conexio21;

        try {
            // Busquem els dos veretexs a traves de les claus
            vei1 = taulaVertexs.consultar(v1);
            vei2 = taulaVertexs.consultar(v2);

            // Creem les arestes corresponents
            conexio12 = new ArestaGraf<>(v1, v2, pes);
            conexio21 = new ArestaGraf<>(v2, v1, pes);

            // Les inserim a les llistes de conexions d'ambods vertexs
            vei1.conexions.inserir(conexio12);
            vei2.conexions.inserir(conexio21);

        } catch (ElementNoTrobat e) { // Algun dels vertexs no esta al graf
            throw new VertexNoTrobat();
        }
    }

    // Metode equivalent a l'anterior, afegint com a pes el valor null
    public void inserirAresta(K v1, K v2) throws VertexNoTrobat {
        inserirAresta(v1, v2, null);
        }

        // Metode per a saber si una aresta existeix a partir dels vertex que connecta
        public boolean existeixAresta(K v1, K v2) throws VertexNoTrobat {
        VertexGraf<K,V,E> vertex;
        ArestaGraf<K,E> conexioPerTrobar = new ArestaGraf<>(v1,v2);

        try{
            // Busquem el vertex amb la clau
            vertex = taulaVertexs.consultar(v1);

            // Comprovem si existeix l'aresta a dins de la llista de conexions i retornem
            return vertex.conexions.existeix(conexioPerTrobar);

        } catch (ElementNoTrobat e) { // El vertex v1 no esta al graf
            throw new VertexNoTrobat();
        }
    }

    // Metode per a obtenir el pes d'una aresta a partir dels vertex que connecta
    public E consultarAresta(K v1, K v2) throws VertexNoTrobat, ArestaNoTrobada {
        VertexGraf<K,V,E> vertex;
        ArestaGraf<K,E> conexioPerTrobar = new ArestaGraf<>(v1,v2);
        ArestaGraf<K,E> conexioActual = null;
        Iterator<ArestaGraf<K,E>> iteraArestes;
        boolean trobat = false;

        try{
            // Busquem el vertex amb la clau
            vertex = taulaVertexs.consultar(v1);

            // Obtenim l'iterador de la llista de conexions del vertex
            iteraArestes = vertex.conexions.iterator();

            // Busquem la aresta a la llista de conexions
            while (iteraArestes.hasNext() && !trobat) {
                conexioActual = iteraArestes.next();

                if(conexioActual.compareTo(conexioPerTrobar) == 0){ // Hem trobat la aresta
                    trobat = true;
                }
            }
            // Comprovacions
            if (!trobat){
                throw new ArestaNoTrobada();
            }
            // Retornem el pes de la aresta
            return conexioActual.pes;

        } catch (ElementNoTrobat e) { // El vertex v1 no esta al graf
            throw new VertexNoTrobat();
        }
    }

    // Metode per a esborrar una aresta a partir dels vertex que connecta
    public void esborrarAresta(K v1, K v2) throws VertexNoTrobat, ArestaNoTrobada {
        VertexGraf<K,V,E> vei1, vei2;
        ArestaGraf<K,E> conexio12 = new ArestaGraf<>(v1,v2);
        ArestaGraf<K,E> conexio21 = new ArestaGraf<>(v2,v1);

        try{
            // Busquem els vertexs amb les claus
            vei1 = taulaVertexs.consultar(v1);
            vei2 = taulaVertexs.consultar(v2);

            try{
                // Esborrem la arresta corresponent de cada llista de conexions
                vei1.conexions.esborrar(conexio12);
                vei2.conexions.esborrar(conexio21);
            } catch (ElementNoTrobat e) { // La aresta no existeix
                throw new ArestaNoTrobada();
            }

        } catch (ElementNoTrobat e) { // Algun dels dos vertexs no existeix al graf
            throw new VertexNoTrobat();
        }
    }

    // Metode per a comptar quantes arestes te el graf en total
    public int numArestes() {
        int comptador = 0;

        // Comptem les conexions de cada vertex
        for (VertexGraf<K,V,E> vertex : taulaVertexs) {
            comptador = comptador + vertex.conexions.numElements();
        }
        // Gestionem la redundància d'arestes
        if (comptador % 2 == 0) { // Nombre parell d'arestes
            return comptador / 2;
        } else { // Nombre imparell d'arestes
            return (comptador / 2) + 1;
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////
    // Metodes auxiliars per a treballar amb el graf

    // Metode per a saber si un vertex te veins
    public boolean vertexAillat(K v1) throws VertexNoTrobat {
        VertexGraf<K,V,E> vertex;

        try{
            // Busquem el vertex amb la clau
            vertex = taulaVertexs.consultar(v1);

            // Comprovem si la seva llista de conexions es buida
            return vertex.conexions.esBuida();

        } catch (ElementNoTrobat e) { // El vertex no existeix al graf
            throw new VertexNoTrobat();
        }

    }

    // Metode per a saber quants veins te un vertex
    public int numVeins(K v1) throws VertexNoTrobat {
        VertexGraf<K,V,E> vertex;

        try{
            // Busquem el vertex amb la clau
            vertex = taulaVertexs.consultar(v1);

            // Retornem el numero de conexions del vertex
            return vertex.conexions.numElements();

        } catch (ElementNoTrobat e) { // El vertex no existeix al graf
            throw new VertexNoTrobat();
        }
    }

    // Metode per a obtenir tots els ID de vertex veins d'un vertex
    public ILlistaGenerica<K> obtenirVeins(K v1) throws VertexNoTrobat {
        VertexGraf<K,V,E> vertex;
        ILlistaGenerica<K> veins = new LlistaNoOrdenada<>();

        try{
            // Busquem el vertex amb la clau
            vertex = taulaVertexs.consultar(v1);

            // Iterem sobre la seva llista de conexions i inserim els veins a una nova llista
            for (ArestaGraf<K,E> conexio : vertex.conexions){
                veins.inserir(conexio.to);
            }
            // Retornem llista de veins
            return veins;

        } catch (ElementNoTrobat e) { // El vertex no existeix al graf
            throw new VertexNoTrobat();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////
    // Metodes OPCIONALS - Si es fa la part obligatoria la nota maxima sera un 8
    // Si s'implementen aquests dos metodes correctament es podra obtenir fins a 2 punts addicionals

    // recorregut en profunditat
    public void dfs(K v1, ILlistaGenerica<K> visitats) throws VertexNoTrobat{
        VertexGraf<K,V,E> vertex;
        try{
            // Busquem el vertex amb la clau
            vertex = taulaVertexs.consultar(v1);

            // Insreim el vertex a la llista de visitats
            visitats.inserir(v1);

            // Fem recursió
            if (!vertexAillat(v1)) {
                // Iterem sobre els veins del vertex
                for (ArestaGraf<K,E> conexio : vertex.conexions) {
                    // Comprovacions
                    if (!visitats.existeix(conexio.to)) { // Si el vei no s'ha visitat
                        // Repetim el proces amb el vei
                        dfs(conexio.to, visitats);
                    }
                }
            }

        } catch (ElementNoTrobat e) { // El vertex no existeix al graf
            throw new VertexNoTrobat();
        }
    }

    // Metode per a obtenir tots els nodes que estan connectats a un vertex
    // es a dir, nodes als que hi ha un cami directe des del vertex
    // El node que es passa com a parametre tambe es retorna dins de la llista!
    public ILlistaGenerica<K> obtenirNodesConnectats(K v1) throws VertexNoTrobat {
        ILlistaGenerica<K> connectats = new LlistaNoOrdenada<>();
        // Comprovacions
        if (!taulaVertexs.buscar(v1)) {
            throw new VertexNoTrobat();
        }
        // Fem recorregut en profunditat
        dfs(v1, connectats);

        // Retornem la llista amb els connectats
        return connectats;
    }

    // Metode per a obtenir els nodes que composen la Component Connexa mes gran del graf
    public ILlistaGenerica<K> obtenirComponentConnexaMesGran() {
        ILlistaGenerica<K> componentConnexa = new LlistaNoOrdenada<>();
        ILlistaGenerica<K> vertexIDs = obtenirVertexIDs();

        // Iterem sobre totes les claus del graf i comparem les components connexes
        for (K clau : vertexIDs) {
            // Comprovacions
            if (!componentConnexa.existeix(clau)) { // Si el vertex no esta a la component connexa actual
                // Comprovem si la component connexa associada al node es mes gran que la component actual
                try {
                    if (obtenirNodesConnectats(clau).numElements() > componentConnexa.numElements()) { // Si es mes gran
                        // Actualitzem la component connexa actual
                        componentConnexa = obtenirNodesConnectats(clau);
                    }
                } catch (VertexNoTrobat e) {
                } // Mai s'arribara a aquest error
            }
        }
        // Retornem component connexa mes gran
        return componentConnexa;
    }
}
