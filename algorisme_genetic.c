#include <stdio.h>

void funcio_error (int errors[], int valor_fucio, int ** matriu_gens, int num_croms, int num_gens){

    int error;
    int suma;
    int producte;

    for(int i = 0; i < num_croms; i++){
        suma = 0;
        for(int j = 0; j < num_gens; j++){
            producte = (matriu_gens[i][j]) * (j+1) * (j+1);
            suma += producte;
            
            if(j == num_col - 1){
                error = valor_funcio - suma;
                if(error < 0){
                    error = -error; 
                }
                else if (error == 0){
                    
                }
                errors[i] = error;
            }
        }
    }
}

void seleccio (int errors[], int ** matriu_in, int ** matriu_out, int n_croms, int n_gens){
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
