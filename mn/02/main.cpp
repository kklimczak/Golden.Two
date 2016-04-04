#include <iostream>
#include <cmath>
#include <vector>
#include <fstream>

const double EPS = 1.e-15;

using namespace std;

void print(vector< vector<double> > A) {
    int n = A.size();
    for (int i=0; i<n; i++) {
        for (int j=0; j<n+1; j++) {
            cout << A[i][j] << "\t";
            if (j == n-1) {
                cout << "| ";
            }
        }
        cout << "\n";
    }
    cout << endl;
}

int gauss (vector < vector<double> > a, vector<double> & ans) {
    int n = (int) a.size();
    int m = (int) a[0].size() - 1;

    vector<int> where (m, -1);
    for (int kolumna=0, wiersz=0; kolumna<m && wiersz<n; ++kolumna) {
        int wybrany = wiersz;
        for (int i=wiersz; i<n; ++i)
            if (abs (a[i][kolumna]) > abs (a[wybrany][kolumna]))
                wybrany = i;
        if (abs (a[wybrany][kolumna]) < EPS)
            continue;
        for (int i=kolumna; i<=m; ++i)
            swap (a[wybrany][i], a[wiersz][i]);
        where[kolumna] = wiersz;

        for (int i=0; i<n; ++i)
            if (i != wiersz) {
                double c = a[i][kolumna] / a[wiersz][kolumna];
                for (int j=kolumna; j<=m; ++j)
                    a[i][j] -= a[wiersz][j] * c;
            }
        ++wiersz;
    }

    ans.assign (m, 0);
    for (int i=0; i<m; ++i)
        if (where[i] != -1)
            ans[i] = a[where[i]][m] / a[where[i]][i];
    for (int i=0; i<n; ++i) {
        double sum = 0;
        for (int j=0; j<m; ++j)
            sum += ans[j] * a[i][j];
        if (abs (sum - a[i][m]) > EPS)
            return 0;
    }

    for (int i=0; i<m; ++i)
        if (where[i] == -1)
            return -1;
    return 1;
}

int main(int argc, char* argv[]) {
    cout << "Witaj w programie rozwiązującym układ N równań liniowych z N parametrami\n\n";

    cout << "Podaj ilość równań:\nPodaj wymiar macierzy taką jaką masz w pliku. Inna wartość może spowodować BŁĘDNE OBLICZENIA!!! \n$ ";

    int n;
    cin >> n;

    cout << "\nPodana liczba równań: " << n << ". Zgodnie z założeniami programu\nliczba niewiadomych jest równa liczbie podanej wyżej.\n\n";

    string fileName;
    if(argc > 1) {
        fileName = argv[1];
        cout << "Wybrany dokument tekstowy: " << fileName << ".\n";
    } else {
        fileName = "wspolczynniki.txt";
        cout << "Nie wybrano dokumentu tekstowego. Zostanie załadowany domyślny.\n\n";
    }

    vector<double> line(n+1,0);
    vector< vector<double> > A(n,line);

    fstream file;

    file.open(fileName);
    if(file.is_open()) {
        for (int i=0; i<n; i++) {
            for (int j=0; j<n+1; j++) {
                file >> A[i][j];
            }
        }
        file.close();
    } else {
        cout << "brak dostępu lub błędna nazwa pliku\n\n";
    }

    // Print input
    cout << "Załadowany układ równan: \n";
    print(A);

    // Calculate solution
    vector<double> answer;
    int z = gauss(A, answer);

    // Print result
    if (z == 1) {
        cout << "Wyniki:\n";

        for (int m = 0; m < n; m++) {
            cout << "x" << m+1 << " = " << answer[m] << " ";
        }
        cout << endl;
    } else if (z == -1) {
        cout << "Układ nieoznaczony!\n";
    } else {
        cout << "Układ sprzeczny!\n";
    }


}
