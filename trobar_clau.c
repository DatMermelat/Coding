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

int main (int argc, char* argv[]){ 

    int generacions = DEF_GENER;
    int crom = DEF_CROM;
    float prob_m = DEF_PROB_M;
    int tour_sel = DEF_TOUR_SEL;
    int args_tractats;

    srand(time(NULL)); 

    if (argc > 1){
        args_tractats = 1;

        while ((argc - args_tractats) > 0){ //Llegir els command-line args
               
               if (strcmp(argv[args_tractats],"-g") == 0){ //Nombre de generacions 
                  
                   generacions = atoi(argv[args_tractats + 1]);
                   args_tractats += 2; 
               } 
               else if (strcmp(argv[args_tractats],"-c") == 0){ //Nombre de cromosomes 

                   crom = atoi(argv[args_tractats + 1]);
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

                   printf("\n ERROR: Argument desconegut\n");
                   printf("\n Les comandes reconegudes son:\n");
                   printf("-g  generacions \n-c  cromosomes \n-prob  probabilitat de mutacio \n-ts  cromosomes pel tournament selection \n");

                   return -1;
               }
 
        }
         
    }

    printf("\nDades introduïdes:\n");

    printf("\ngeneracions %d   cromosomes %d   probabilitat de mutacio %.4f   cromosomes pel tour.sel. %d \n", generacions, crom, prob_m, tour_sel);
 
    return 0;
}