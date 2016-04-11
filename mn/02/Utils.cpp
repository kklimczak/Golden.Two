#include "Utils.h"

Utils::Utils()
{

}

void Utils::write(double **macierz, int n) {
    for (int i = 0; i < n; i++) {
        for (int j = 0; j <= n; j++) {
            printf("%lf ", macierz[i][j]);
        }
        printf("\n");
    }
}

void Utils::reset(double *x) {
    int a = 1;
    if ((*x) < 0) {
        a = -1;
    }
    if ((a ** x) < 10e-6 && (a ** x) > 0) {
        *x = 0;
    }
}

void Utils::stepsToZero(double **macierz, int i, int j, int size) {
    /* funkcja rekurencyjnie sprowadza macierz do postaci schodkowej */
    int wie,m,n;
    double tmp;
    if (i < size) {		/* warunek konca rekurencji */
        wie = i;
        while (wie < size - 1 && macierz[wie][j] == 0)
            wie++;
        /* szukamy w danej kolumnie elementu
         * roznego od zera i jesli taki istnieje
         * to przechodzimy dalej przez IF'a */
        if (macierz[wie][j]) {
            if (wie != i) {
                /* jesli znaleziony wyzej wiersz z
                 * niezerowym elementem nie jest wierszem
                 * i-tym to zamieniamy te wiersze */
                for (m = j; m <= size; m++) {
                    tmp = macierz[wie][m];
                    macierz[wie][m] = macierz[i][m];
                    macierz[i][m] = tmp;
                }
            }
            for (m = i + 1; m < size; m++) {
                tmp = macierz[m][j] / macierz[i][j];
                reset(&tmp);
                for (n = j; n <= size; n++) {
                    /* Odejmujemy od m-tego
                     * wiersza macierzy tmp
                     * krotnosc wiersza
                     * i-tego. */
                    macierz[m][n] -= (macierz[i][n]) * tmp;
                    reset(&macierz[m][n]);
                }
            }
        }
        stepsToZero(macierz, i + 1, j + 1, size);
        /* wywolujemy rekurencyjnie ta
         * funkcje dla macierzy pomniejszonej o
         * pierwsza kolumne i pierwszy wiersz */
    }
}

double Utils::resolve(double **macierz, int i, int size) {
    /* Ta funkcja wyliczy rozwiazania x1 x2 itd. */
        double a = 0;
        int m;
        for (m = i + 1; m < size; m++)
            a += (macierz[i][m] * resolve(macierz, m, size));
        return ((macierz[i][size] - a) / macierz[i][i]);
}

int Utils::isEqual(double **macierz, int i, int size) {
    /* Jest to podfunkcja funkcji "check",
     * wywolana w odpowiednim momencie pozwala
     * wykryc sprzecznosc ukladu, kiedy i po
     * co ja wywolujemy opisane zostalo nizej */
    int m,n,flag;
    for (m = 0; m < size; m++) {
        if (m != i) {
            flag = 1;
            for (n = 0; n < size; n++) {
                if (!(macierz[m][n] == macierz[i][n])) {
                    flag = 0;
                }
            }
            if (flag) {
                return 1;
            }
        }
    }
    return 0;
}

int Utils::check(double **macierz, int size) {

    /* Funkcja zwraca: 0 gdy uklad jest
     * sprzeczny, 1 gdy ma nieskonczenie wiele
     * rozwiazan, 2 gdy istnieje jedno rozwiazanie */

    int m,n,accident = 0,licznik = 0;

    /* Ta funkcja jak i wszystkie jej
     * wyrazanie i komentarze odnosza sie do
     * POSTACI SCHODKOWEJ MACIERZY!! */

    double a;
    a = (double) 0 / (double) 2;
    for (m = 0; m < size; m++) {
        if ((macierz[m][m]) == 0) {
            accident = 1;
            /* jezeli wspolczynnik na przekatnej
             * glownej macierzy jest rowny 0, to
             * oznacza to sprzecznosc lub nieskonczona
             * ilosc rozwiazan ukladu, co za chwile ustalimy */

            if (macierz[m][size] != 0) {
                /* jezeli wyraz wolny tego wiersza
                 * jest rozny od 0 to moze zajsc sprzecznosc */

                licznik = 0;

                for (n = m + 1; n < size; n++) {
                    if ((macierz[m][n]) != 0) {
                        licznik++;
                    }
                }

                if (!licznik) {
                    return 0;
                }
                /* jezeli wszystkie wspolczynniki w tym
                 * wierszu sa rowne 0 a wyraz wolny rozny
                 * od 0 to uklad jest sprzeczny, dlatego
                 * tak jak wczesniej ustalilismy zwracamy 0 */

                else if (isEqual(macierz, m, size)) {
                    return 0;
                }
            }
            /* jezeli w tym wierszu byl co najmniej 1
             * niezerowy wspolczynnik to uklad moze
             * byc sprzeczny jezeli istnieje inny
             * wiersz o tych samych wspolczynnikach
             * lecz innym wyrazie wolnym, co sprawdza
             * funkcja isEqual */
        }
    }
    if (accident) {
        return 1;
    }
    /* jezeli ktorys element na przekatnej byl
         * rowny 0 a nie wykrylismy wczesniej
         * sprzecznosci to uklad bedzie posiadal
         * nieskonczona ilosc rozwiazan i tak jak
         * wczeniej ustalilismy zwracamy 1 */
    return 2;
    /* jezeli wszystkie wspolczynniki na
         * przekatnej byly rozne od 0 to istnieje
         * jedno rozwiazanie, zwracamy 2 */
}
