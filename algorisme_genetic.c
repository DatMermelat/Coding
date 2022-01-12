#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <math.h>
#include <limits.h>

void fill_rand(int** matriu, int n_fil, int n_col){ //Funcio per omplir una taula de aleatoris entre 0 o 1
    
    int  aleatori01;

    for(int i = 0; i < n_fil; i++){
        for(int j = 0; j < n_col; j++){
            aleatori01 = rand() % 2; //Generar un aleatori que sigui 0 o 1

            matriu[i][j] = aleatori01;            
        }
    }
}

int funcio_error (int gens[], int n_gens, int valor_funcio){ //Funcio que retorna l'error d'un cromosoma respecte a un valor previament introduit

    int error;
    int suma = 0;
    int producte;

    for (int i = 0; i < n_gens; i++){ //Calcular la funcio
        producte = gens[i] * (i+1) * (i+1);
        suma += producte;
    }

    error = suma - valor_funcio; //Calcular l'error
    
    if(error < 0){ //Convertir en positiu si escau
        error = -error;
    }
    return error;
}


void seleccio (int** m_pool, int** poblacio, int cromosomes, int n_gens, int k, int valor_funcio, int best[]){ //Accio que passa els millors cromosomes de la poblacio al mating poo

     int aleatori;
     int error;
     int error_min;  

     for(int i = 0; i < cromosomes; i++){ //Omplir el mating pool

        error_min = INT_MAX;

        for(int j = 0; j < k; j++){ //Seleccionar k cromosomes de forma aleatoria per comparar els seus errors

            aleatori = rand() % cromosomes; //Generar un aleatori entre 0 i el nombre de cromosomes -1
            
            error = funcio_error(poblacio[aleatori], n_gens, valor_funcio);
            
            if(error < error_min){ //Comprovar l'error de cada cromosoma de la matriu de població
                
                error_min = error;

                for(int a  = 0; a < n_gens; a++){ //Passar el millor cromosoma al mating pool
                    m_pool[i][a] = poblacio[aleatori][a];
                }            

                if(error_min < funcio_error(best, n_gens, valor_funcio)){ //Comprovar si el millor cromosoma del grup k es el millor en general
                    
                    for(int b = 0; b < n_gens; b++){ //En cas que sigui el millor, passar-ho a un vector que guarda el millor resultat
                        best[b] = m_pool[i][b];
                    }
                }
            }
        }
    }
}

void crossover(int** m_pool, int cromosomes, int n_gens){ //Accio que encreua els cromosomes del mating poo
     int crosspoint;
     int aux;
     
     for(int i = 0; i < cromosomes - 1; i += 2){

         crosspoint = rand() % (n_gens - 1) + 1; //Generar un aleatori entre 1 i n_gens - 1 per assegurar que el cross-point mai serà ni la posició 0 ni la posicio final

         for(int j = 0; j < crosspoint; j++){ //Intercanviar els gens de cada parella de cromosomes
            aux = m_pool[i][j];
            m_pool[i][j] = m_pool[i+1][j];
            m_pool[i+1][j] = aux;
         }
     }
}

void flipmut(int** m_pool, int cromosomes, int n_gens, float prob_m){ //Accio que muta de forma aleatoria els gens dels cromosomes
    float aleatori01;

    for(int i = 0; i < cromosomes; i++){
        for(int j = 0; j < n_gens; j++){

            aleatori01 = (float)rand() / RAND_MAX; //Generar un aleatori entre 0 i 1 qualsevol

            if(aleatori01 <= prob_m){  //Comrpovar si el gen ha de mutar o no
                if(m_pool[i][j] == 0){
                    m_pool[i][j] = 1;
                }
                else{
                    m_pool[i][j] = 0;
                }
            }
        }
    }
}

void relleu (int** m_pool, int** poblacio, int cromosomes, int n_gens){ //Funcio que passa els cromosomes del mating pool a la poblacio
    for(int i = 0; i < cromosomes; i++){
        for(int j = 0; j < n_gens; j++){
            poblacio[i][j] = m_pool[i][j];
        }
    }
}