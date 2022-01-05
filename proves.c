#include <stdio.h>
#include <stdlib.h>
#include <time.h>

int main (){
    int aleatori;
    float aleatori01;

    srand(time(NULL));

    aleatori = rand();
    // aleatori01 = (float) aleatori / RAND_MAX;

    printf ("\n %d  \n", RAND_MAX);
}