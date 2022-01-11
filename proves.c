#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <math.h>

#define COL 5
#define MAX 40

int main (){
    int test [6][COL] = {1,2,3,4,5,1,2,3,4,5,1,2,3,4,5,1,2,3,4,5,1,2,3,4,5,1,2,3,4,5};

    printf("\n");
    for(int i = 0; i < 6; i++){
        for(int j = 0;j  < COL; j++){
            printf("%d ", test[i][j]);
            if (j == COL - 1){
                printf("\n");
            }
        }
    }
}