#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <math.h>

#define MAX 40
int main (){
    int aleatori;
    

    srand(time(NULL));

    aleatori = rand() % MAX;

    printf ("\n %f \n", aleatori);
}