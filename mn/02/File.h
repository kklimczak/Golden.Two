#include <stdio.h>
#include <iostream>

#ifndef FILE_H
#define FILE_H

using namespace std;

class File
{
    FILE *file;
    char *name;
    int size;
public:
    File(char* name);
    double** przetworzPlikDoMacierzy();
    int getSize();
};

#endif // FILE_H
