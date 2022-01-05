#include <stdio.h>
#include <stdbool.h>
#include <stdlib.h>
#include <time.h>
#include <math.h>

#define DEF_GENER 100 //Nombre de generacionsper per defecte
#define DEF_CROM 40 //Nombre de cromosomes per defecte
#define DEF_PROB_M 0.05 //Probabilitat de mutació per defecte 
#define DEF_TOUR_SEL 5 //Nombre de cromosomes al tournament selection per defecte 

int main (int argc, char* argv[]){ 

    int generacions = DEF_GENER;
    int crom = DEF_CROM;
    float prob_m = DEF_PROB_M;
    int k = DEF_TOUR_SEL;
    int args_tractats;

    if (argc > 1){
        args_tractats = 1;

        while ((argc - args_tractats) > 0){
               
               if (strcmp(argv[args_tractats],"-g") == 0){
                  
                   generacions = atoi(argv[args_tractats + 1]);
                   args_tractats += 2; 
               } 
               else if (strcmp(argv[args_tractats],"-c") == 0){

                   crom = atoi(argv[args_tractats + 1]);
                   args_tractats += 2;
               }
               else if (strcmp(argv[args_tractats],"-prob") == 0){

                   prob_m = atof(argv[args_tractats + 1]);
                   args_tractats += 2;
               }
               else if (strcmp(argv[args_tractats],"-k") == 0){

                   k = atoi(argv[args_tractats + 1]);
                   args_tractats += 2;
               }
               else{

                   printf("\n ERROR: Argument desconegut\n");
                   printf("\n Les comandes reconegudes son:\n");
                   printf("-g  generacions \n-c  cromosomes \n-prob  probabilitat de mutacio \n-k  cromosomes per el tournament selection \n");

                   return -1;
               }
 
        }
         
    }

    printf("gen %d  crom %d  prob %.4f  k %d \n", generacions, crom, prob_m, k);
 
    return 0;
}