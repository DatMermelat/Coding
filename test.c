#include <stdio.h>
#include "suma.h"

int main (){

    int a, b;

    printf("\n Introduce dos numeros \n");

    scanf("%d", &a);
    scanf("%d", &b);

    printf("\n %d \n", suma(a,b));

    return 0;
}