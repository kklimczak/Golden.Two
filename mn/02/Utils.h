#include <stdio.h>

#ifndef UTILS_H
#define UTILS_H


class Utils
{
public:
    Utils();
    static void write(double **macierz, int n);
    static void reset(double *x);
    static void stepsToZero(double **macierz, int i, int j, int size);
    static double resolve(double **macierz, int i, int size);
    static int isEqual(double **macierz, int i, int size);
    static int check(double **macierz, int size);

};

#endif // UTILS_H
