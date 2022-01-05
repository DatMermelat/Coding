#include <stdio.h>
#include <stdlib.h>
#include <time.h>

int main (){
    int aleatori;
    float aleatori01;

    srand(time(NULL));

    aleatori = rand();
    aleatori01 = (float) aleatori / RAND_MAX;
    round(aleatori01);

    printf ("\n %f \n", aleatori01);
}