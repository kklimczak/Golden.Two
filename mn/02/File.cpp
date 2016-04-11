#include "File.h"

File::File(char* nazwa)
{
    this->name = nazwa;
}

double** File::przetworzPlikDoMacierzy() {
    int n, a;
    if(!(file = fopen(this->name, "r"))) {
        cout << "Nie można otworzyć pliku!\n";
    } else if (!fscanf(file, "%d", &n) || n < 0) {
        cout << "Niepoprawny rozmiar macierzy!\n";
    } else {
        this->size = n;
        double** macierz = new double*[n];
        for (int k = 0; k < n; k++) {
            macierz[k] = new double[n+1];
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n+1; j++) {
                if(((a = fscanf(file, "%lf", &macierz[i][j])) == EOF || !a)) {
                    cout << "Niepoprawne dane w pliku!\n";
                }
            }
        }
        fclose(file);
        return macierz;
    }

}

int File::getSize() {
    return this->size;
}

