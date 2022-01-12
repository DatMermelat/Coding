#include <stdio.h>
#include <stdbool.h>
#include <stdlib.h>
#include <time.h>
#include <math.h>
#include <string.h>
#include "algorisme_genetic.h" 

#define DEF_GENER 100 //Nombre de generacions per defecte
#define DEF_CROM 40 //Nombre de cromosomes per defecte
#define DEF_PROB_M 0.05 //Probabilitat de mutació per defecte 
#define DEF_TOUR_SEL 5 //Nombre de cromosomes al tournament selection per defecte
#define N_GENS 30
#define VALOR_FUNCIO 1977 //El valor que volem trobar

void print_matriu (int ** matriu, int num_fil, int num_col){
    printf("\n");
    for (int i = 0; i < num_fil; i++){
        for (int j = 0; j < num_col; j++){
            printf ("%d ", matriu[i][j]);
            if (j == num_col - 1){
                printf ("\n");
            }
        }
    }

}

void print_vector(int * vector, int n){
    printf ("\n");
    for (int i = 0; i < n; i++){
        printf("%d ", vector[i]);
    }
    printf("\n");
}

int main (int argc, char* argv[]){ 
    //Variables pels command-line args  
    int generacions = DEF_GENER;
    int cromosomes = DEF_CROM;
    int tour_sel = DEF_TOUR_SEL;
    float prob_m = DEF_PROB_M;
    //Taules 
    int ** m_pool;
    int ** poblacio;
    int best[N_GENS];
    //Booleans
    bool trobat = false;
    //Altres variables
    int args_tractats;
    int gen_counter = 0;

    srand(123);  

    //Llegir els command-line args 
    if (argc > 1){
        args_tractats = 1;

        while ((argc - args_tractats) > 0){
               
               if (strcmp(argv[args_tractats],"-g") == 0){ //Nombre de generacions 
                  
                   generacions = atoi(argv[args_tractats + 1]);
                   args_tractats += 2; 
               } 
               else if (strcmp(argv[args_tractats],"-c") == 0){ //Nombre de cromosomes 

                   cromosomes = atoi(argv[args_tractats + 1]);
                   if (cromosomes % 2 != 0){
                       printf ("\nERROR: El nombre de cromosomes ha de ser parell\n");
                       return -1;
                   }

                   args_tractats += 2;
               }
               else if (strcmp(argv[args_tractats],"-prob") == 0){ //Probabilitat de mutacio

                   prob_m = atof(argv[args_tractats + 1]);
                   if (prob_m > 1 || prob_m < 0){
                       printf("\nERROR: La probabilitat de mutacio no pot ser major a 1 o inferior a 0\n");
                       return -1;
                   }
                   args_tractats += 2;
               }
               else if (strcmp(argv[args_tractats],"-ts") == 0){ //Cromosomes pel tour. sel.

                   tour_sel = atoi(argv[args_tractats + 1]);
                   if (tour_sel > cromosomes){
                       printf("\nERROR: El nombre de cromosomes pel tournament selection no pot ser superiror al total de cromosomes\n");
                       return -1;
                   }
                   args_tractats += 2;
               }
               else{

                   printf("\nERROR: Argument desconegut\n");
                   printf("\nLes comandes reconegudes son:\n");
                   printf("-g  generacions \n-c  cromosomes \n-prob  probabilitat de mutacio \n-ts  cromosomes pel tournament selection \n");

                   return -1;
               }
 
        }
         
    }

    printf("\nDades introduides:\n");

    printf("\ngeneracions %d   cromosomes %d   probabilitat de mutacio %.4f   cromosomes pel tour.sel. %d \n", generacions, cromosomes, prob_m, tour_sel);

    //Crearcio de taules dinamiques i comprovacions

    m_pool = (int **) malloc(cromosomes * sizeof(int *));
    poblacio = (int **) malloc(cromosomes * sizeof(int *)); 

    if (m_pool == NULL || poblacio == NULL){
        printf ("\nERROR: No hi ha suficient espai a la taula de cromosomes\n");
        printf ("\nIntrodueix un nombre de cromosomes mes petit\n");
        return -1;
    }

    for(int i = 0; i < cromosomes; i++){
        
        m_pool[i] = (int *) malloc(N_GENS * sizeof(int));
        poblacio[i] = (int *) malloc(N_GENS * sizeof(int));
        
        if (m_pool[i] == NULL || poblacio[i] == NULL){
            printf ("\nERROR: No hi ha suficient espai a la taula de cromosomes\n");
            printf ("\nIntrodueix un nombre de cromosomes mes petit\n");
            return -1;
        }
    }

    //Començament de l'algorisme genetic
    
    //Omplir la taula de poblacio amb nombres aleatoris (0 o 1)
    fill_rand(poblacio, cromosomes, N_GENS);

    //Inicialitzar el vector que guarda el millor resultat
    for(int i = 0; i < N_GENS; i++){
        best[i] = poblacio [0][i];
    }


    //Alliberar taules
    for(int i = 0; i < cromosomes; i++){
        free(m_pool[i]);
        free(poblacio[i]);
    }

    free(m_pool);
    free(poblacio);

    return 0;
}