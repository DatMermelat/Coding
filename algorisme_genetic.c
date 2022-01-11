#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <math.h>

void fill_rand(int** matriu, int n_fil, int n_col){
    
    float aleatori01;

    for(int i = 0; i < n_fil; i++){
        for(int j = 0; j < n_col; j++){
            aleatori01 = round((float)rand() / RAND_MAX);

            matriu[i][j] = aleatori01;            
        }
    }
}

int funcio_error (int gens[], int n_gens, int valor_funcio){

    int error;
    int suma = 0;
    int producte;

    for (int i = 0; i < n_gens; i++){
        producte = gens[i] * (i+1) * (i+1);
        suma += producte;
    }

    error = suma - valor_funcio;
    
    if(error < 0){ //Convertir en positiu si escau
        error = -error;
    }
    return error;
}


void seleccio (int** m_pool, int** poblacio, int cromosomes, int n_gens, int k, int valor_funcio, int best[]){

     int aleatori;
     int error;
     int error_min;

    for(int i = 0; i < cromosomes; i++){

        error_min = INT_MAX;

        for(int j = 0; j < k; j++){ //Seleccionar k cromosomes de forma aleatoria per comparar els seus errors

            aleatori = rand() % cromosomes; //Generar un aleatori entre 0 i el nombre de cromosomes -1
            
            error = funcio_error(poblacio[aleatori], n_gens, valor_funcio);
            
            if(error <= error_min){ //Comprovar l'error de cada cromosoma de la matriu de població
                
                error_min = error;

                for(int a  = 0; a < n_gens; a++){ //Passar el millor cromosoma al mating pool
                    m_pool[i][a] = poblacio[aleatori][a];
                    best[a] = poblacio[aleatori][a];
                }            
            }
        }
    }
}
