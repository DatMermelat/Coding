#include <stdio.h>
void funcio_error (int ** matriu_gens, int num_fil,int num_col, int * errors){
    int error;
    int suma;
    int producte;

    printf("\n%d\n", matriu_gens[0][0]);

    for(int i = 0; i < num_fil; i++){
        suma = 0;
        for(int j = 1; j <= num_col; j++){
            producte = matriu_gens[i][j] * j * j;
            suma += producte;
            
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