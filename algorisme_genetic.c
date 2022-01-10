#include <stdio.h>

void funcio_error (int * errors, int ** matriu_gens, int num_fil, int num_col){

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

void seleccio (int * errors, int ** matriu_in, int ** matriu_out, int n_croms, int n_gens){
    int posicio_min = 0;
    int error_min = errors[0];

    for (int i = 1; i < n_croms; i++){
        if (errors[i] < error_min){
            error_min = errors[i];
            posicio_min = i;
        }
    }

    for (int j = 0; j < n_gens; j++){
        matriu_out[0][j] = matriu_in[posicio_min][j]; 
    }
}
