void fill_rand(int** matriu, int n_fil, int n_col);
 
int funcio_error(int gens[], int n_gens, int valor_funcio);

void seleccio(int** m_pool, int ** poblacio, int cromosomes, int n_gens, int k, int valor_funcio, int best[]);

void crossover(int** m_pool, int cromosomes, int n_gens);

void flipmut(int** m_pool, int cromosomes, int n_gens, float prob_m);

void relleu(int** m_pool, int** poblacio, int cromosomes, int n_gens);