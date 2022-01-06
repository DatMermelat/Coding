#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <math.h>

int main (){
    int aleatori;
    float aleatori01;

    srand(time(NULL));

    //aleatori = rand();
    aleatori01 = rand();

    printf ("\n %f \n", round(aleatori01));
}