#include <iostream>
#include "wezly.h"
#include "functions.h"
#include "userinterface.h"

using namespace std;

void rysuj();

int main()
{

    int wybor, ileWezlow, stopien, wyborFunkcji, wyborRozlokowania;

    std::vector<double> wsp = Functions::wczytajWartosciWielomianu(stopien);    // wczytanie wspolczynnikow wielomianu z pliku
                                                                                // oraz jego stopienia
    Wezly* wezel = new Wezly(wsp,stopien);

    do{
        double x0 = -10, xn = 10;                                               //domyślny przedział wykresu

        //Wybory uzytkownika
        wyborFunkcji = ktoraFunkcja();
        wyborRozlokowania = ktoreRozlokowanie();


        switch(wyborRozlokowania){
        case 1: //wczytanie z pliku
            wezel->dowolne(wyborFunkcji);
            break;
        case 2: //wezly Czebyszewa
            przedzialWezly(ileWezlow, x0, xn);                                  //referencyjny zapis ilosci wezlow oraz przedzialu
            wezel->czybeszewa(wyborFunkcji, ileWezlow, x0, xn);
            break;
        case 3: //rownoodlegle
            przedzialWezly(ileWezlow, x0, xn);
            wezel->rownomiernie(wyborFunkcji, ileWezlow, x0, xn);
            break;
        }



        getchar();
        rysuj(wyborFunkcji,wsp,stopien, wezel, x0, xn);

        cout << "WPISZ (0) ABY WYJSC: ";
        cin >> wybor;

    }while(wybor != 0);



    delete wezel;
    return 0;
}

