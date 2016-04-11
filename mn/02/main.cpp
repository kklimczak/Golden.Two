#include <stdio.h>
#include <stdlib.h>
#include <File.h>
#include <Utils.h>

int main(int argc, char* argv[]) {

    int solution;
    char* fileName;
    int liczbaRownan;
    if (argc > 1) {
        fileName = argv[1];
    } else {
        fileName = "wspolczynniki.txt";
    }
    cout << "Wprowadź liczbę równań: ";
    cin >> liczbaRownan;
    File* file = new File(fileName);
    double **macierz = file->przetworzPlikDoMacierzy();
    int size = file->getSize();
    if (size == liczbaRownan) {
        Utils::write(macierz, size);
        Utils::stepsToZero(macierz, 0, 0, size);
        solution = Utils::check(macierz, size);
        if(!solution) {
            cout << "Układ sprzeczny!\n";
        } else if (solution == 1){
            cout << "Układ nieoznaczony!\n";
        } else {
            cout << "Układ ten posiada rozwiązanie: \n";
            for (int k = 0; k < size; k++) {
                cout << "x[" << k << "] = " << Utils::resolve(macierz, k, size) << " ";
            }
        }
        cout << endl;
    } else {
        cout << "Podana liczba równań jest niezgodna z liczbą zawartą w pliku!\n";
    }
}
