#include <stdio.h>
int dotProduct(int a[], int b[], int n)
{
    int i, dot = 0;
    for (i = 0; i < n; i++)
    {
        dot += a[i] * b[i];
    }
    return dot;
}

int main()
{
    int a[3] = {1, 2, 3};
    int b[3] = {4, 5, 6};
    int n = 3;
    printf("Dot product of a and b is %d\n", dotProduct(a, b, n));
}