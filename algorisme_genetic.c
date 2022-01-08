#include <stdio.h>
void funcio_error (int ** matriu_gens, int num_fil,int num_col, int * errors){
    int error;
    int suma;
    int producte;

    pirntf ("\n");

    for(int a  = 0; a  < num_fil; a++){
        for(int b  = 0; b < num_col; b++){
            printf("%d ", matriu_gens[a][b]);
            if (b < num_col - 1){
                printf("\n");
            }
        }
    }

    for(int i = 0; i < num_fil; i++){
        suma = 0;
        for(int j = 1; j <= num_col; j++){
            producte = matriu_gens[i][j] * j * j;
            printf("\n%d ", producte);
            suma += producte;
            printf("\n%d ", suma);
            
            if(j == num_col){
                error = 1977 - suma;
                if(error < 0){
                    error = -error; 
                }
                errors[i] = error;
            }
        }
    }
}