#ifndef USERINTERFACE
#define USERINTERFACE

#include <iostream>
#include <vector>
#include "gnuplot_i.hpp"
#include "functions.h"
#include "wezly.h"

using namespace std;

int ktoraFunkcja(){

    cout << "***WYBÓR FUNKCJI***\n"
         << "1. Liniowa - 2*x + 5\n"
         << "2. Wartosc bezwzgledna |x|\n"
         << "3. Wielomian (wartosci pobrane z pliku)\n"
         << "4. Trygonometryczna - sin(x)\n"
         << "5. Wielomian * sin(x)\n"
         << "6. Liniowa * |x|\n\n";

    cout << "Twoj wybór: ";

    int wyborFunkcji;
    cin >> wyborFunkcji;

    return wyborFunkcji;
}

int ktoreRozlokowanie(){

    cout << "\n***ROZLOKOWANIE WĘZŁÓW***\n"
         << "1. Dane z pliku\n"
         << "2. Węzły Czebyszewa\n"
         << "3. Wężły rozmieszczone równolegle\n\n";

    cout << "Twoj wybor: ";

    int wyborRozlokowania;
    cin >> wyborRozlokowania;

    return wyborRozlokowania;

}

void przedzialWezly(int& ileWezlow, double& x0, double& xn){

    cout << "\n\nPodaj ilosc wezlow:";
    cin >> ileWezlow;

    cout << "Podaj przedzial\n  x0 = ";
    cin >> x0;
    cout << "  x1 = ";
    cin >> xn;

}

void rysuj(int wyborFunkcji, std::vector<double> wsp, int st, Wezly* wezel, double a, double b){

    Gnuplot main_plot;

    // Podpisy na wykresie, zeby bylo wiadomo co na nim widac
    main_plot.set_title( "Zadanie 3 " );
    main_plot.set_xlabel( "X" );
    main_plot.set_ylabel( "Y" );

    // styl rysowania wykresu
    main_plot.set_style( "lines" );

    // siatka poprawia czytelnosc
    main_plot.set_grid();

    // zakres osi x
    main_plot.set_xrange( a , b ) ;


    ///tworzenie tablic wartosci x'ow i y'ow oryginalnej funkcji oraz wielomianu interpolującego

    double zakres = fabs(b - a);
    double krok = zakres / 100.0;

    vector<double> x;
    vector<double> y;

    vector<double> xNewton;
    vector<double> yNewton;

    double tmpa = a;
    for (int i = 0; i < 100; i++){
        x.push_back(tmpa);
        y.push_back(Functions::wyborFunkcji(wyborFunkcji,tmpa,wsp,st));

        xNewton.push_back(tmpa);
        yNewton.push_back(Functions::interpolacjaNewtona(wezel->getArgumenty(),wezel->getWartosci(), tmpa));

        tmpa += krok;
    }

    main_plot.plot_xy( x, y, "Funkcja oryginalna");

    main_plot.plot_xy( xNewton, yNewton, "Wykres wielomianu interpolującego");


      ///rysowanie wezlow

    main_plot.set_style( "points" );
    main_plot.set_pointsize( 2.0 );


    main_plot.plot_xy( wezel->getArgumenty(), wezel->getWartosci(), "Wezly");

    fflush(stdin);
    getchar();
}

#endif // USERINTERFACE

