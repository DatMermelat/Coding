#include <stdio.h>
void funcio_error (int ** matriu_gens, int num_fil,int num_col, int * errors){
    int error;
    int suma;
    int producte;

    for(int i = 0; i < num_fil; i++){
        suma = 0;
        for(int j = 0; j < num_col; j++){
            producte = (matriu_gens[i][j]) * (j+1) * (j+1);
            suma += producte;
            
            if(j == num_col - 1){
                error = 1977 - suma;
                if(error < 0){
                    error = -error; 
                }
                errors[i] = error;
            }
        }
    }
}