#include <stdio.h>
#include <stdbool.h>
#include <stdlib.h>
#include <time.h>
#include <math.h>
#include "algorisme_genetic.h" 

#define DEF_GENER 100 //Nombre de generacions per defecte
#define DEF_CROM 40 //Nombre de cromosomes per defecte
#define DEF_PROB_M 0.05 //Probabilitat de mutació per defecte 
#define DEF_TOUR_SEL 5 //Nombre de cromosomes al tournament selection per defecte
#define N_GENS 30

void imprimir_matriu (int ** matriu, int num_fil, int num_col){
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

void imprimir_vector(int * vector, int n){
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
    //Variables per generacio de nombres aleatoris
    float aleatori01;
    //Taules 
    int ** taula_croms;
    int ** croms_ts;
    int * taula_errors;
    //Altres variables
    int args_tractats;

    srand(time(NULL));  

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
                   args_tractats += 2;
               }
               else if (strcmp(argv[args_tractats],"-ts") == 0){ //Cromosomes pel tour. sel.

                   tour_sel = atoi(argv[args_tractats + 1]);
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

    printf("\nDades introduïdes:\n");

    printf("\ngeneracions %d   cromosomes %d   probabilitat de mutacio %.4f   cromosomes pel tour.sel. %d \n", generacions, cromosomes, prob_m, tour_sel);

    //Crearcio de taules dinamiques i comprovacions

    taula_croms = (int **) malloc(cromosomes * sizeof(int *));
    croms_ts = (int **) malloc(tour_sel * sizeof(int *));
    taula_errors = (int *) malloc(cromosomes * sizeof(int)); 


    if (taula_croms == NULL || croms_ts == NULL || taula_errors == NULL){
        printf ("\nERROR: No hi ha suficient espai a la taula de cromosomes\n");
        printf ("\nIntrodueix un nombre de cromosomes i/o tour. sel. mes petit\n");
        return -1;
    }

    for (int i = 0; i < tour_sel; i++){
        croms_ts[i] = (int *) malloc(N_GENS * sizeof(int));
        
        if(croms_ts[i] == NULL){
            printf ("\nERROR: No hi ha suficient espai a la taula pel tour.sel.\n");
            printf ("\nIntrodueix un argument -ts mes petit\n");
            return -1;
        }

    }

    for(int i = 0; i < cromosomes; i++){
        taula_croms[i] = (int *) malloc(N_GENS * sizeof(int));
        
        if (taula_croms[i] == NULL){
            printf ("\nERROR: No hi ha suficient espai a la taula de cromosomes\n");
            printf ("\nIntrodueix un nombre de cromosomes mes petit\n");
            return -1;
        }
    }
    
    //Generar gens aleatoris pel tournament selection
    for(int i = 0; i < tour_sel; i++){
        for(int j = 0; j < N_GENS; j++){
            croms_ts[i][j] = round((float)rand() / RAND_MAX);            
        }
    }

    imprimir_matriu(croms_ts, tour_sel, N_GENS);

    funcio_error(croms_ts, tour_sel, N_GENS, taula_errors);

    imprimir_vector(taula_errors, tour_sel);

    free(taula_croms);
    free(croms_ts);
    free(taula_errors);

    return 0;
}