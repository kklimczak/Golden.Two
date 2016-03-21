#include <iostream>
#include <iomanip>
#include <cstdlib>
#include <stdio.h>
#include <limits.h>
#include "gnuplot_i.hpp"
#include "Funkcje.h"

#define GNUPLOT_PATH "E:/gnuplot/bin/"

using namespace std;



int main() {

      double a0, b0, miejsce_zerowe_falsi, miejsce_zerowe_bisekcji;
      int wyborFunkcji, wybor, czyPowtorzycProgram, iterf, iterb;
      bool bladZakresu;



   do
    {
        ///jedna z tych wartosci zmienia uzytkownik
      double wymagana_dokladnosc = -1;
      unsigned int maks_iteracji = UINT_MAX;

      /// Liczniki ilosci iteracji
      iterf = 0;
      iterb = 0;

      cout << "1) Policz miejsce zerowe, wyswietl i powtorz program\n"
           << "2) Policz miejsce zerowe, rysuj wykres i wyjdz z programu\n "
           << "Co robimy: ";

      cin >> czyPowtorzycProgram;


      cout << "\nWybierz funkcje: \n"
           << "1) x^3 - 4x \n"
           << "2) sin(x^2)\n"
           << "3) (1/2)^x - 13 \n"
           << "4) x^3 - 4x + sin(x^2)\n"
           << "5) x^3 - 4x + (1/2)^x - 13\n"
           << "6) sin(x^2) + (1/2)^x - 13\n"
           << "7) x^3 - 4x + sin(x^2) + (1/2)^x - 13 \n"
           << "Podaj nr funkcji: ";
      cin >> wyborFunkcji;



      cout << "\n1. Dokladnosc\n2. Ilosc iteracji \n\nPodaj wybor:";
      cin >> wybor;

      if(wybor == 1){
        cout << "\nPodaj dokladnosc: ";
        cin >> wymagana_dokladnosc;
      }

            else {
                cout << "\nPodaj ilosc iteracji: ";
                cin >> maks_iteracji;
            }


      cout << setprecision(8) << fixed;    ///ilosc wyswietlanych miejsc po przecinku

    do  ///petla w przypadku podania bledznego zakresu
      {
      cout << "Funkcja posiada miejsca zerowe w punktach: " << Funkcje::wyswietlMiejscaZerowe(wyborFunkcji);

      cout << "\nPodaj przedzial: \na = ";
        cin >> a0;
      cout << "b = ";
        cin >> b0;



      if(Funkcje::f(a0,wyborFunkcji) * Funkcje::f(b0,wyborFunkcji) >= 0)  /// sprawdzenie czy wartosci na krancach funkcji maja przeciwny znak
      {
          cout << "\nFunkcja nie spelnia zalozen. Podaj zakres raz jeszcze: \n";
          bladZakresu = true;
      }

      else
      {

          miejsce_zerowe_falsi = Funkcje::regulaFalsi(wymagana_dokladnosc, maks_iteracji, wyborFunkcji, a0, b0, iterf);
          cout << "\n\nZnalezione miejsce zerowe regula Falsi:    "
               << miejsce_zerowe_falsi
               << "\nIlosc wykonanych iteracji: " << iterf;

          miejsce_zerowe_bisekcji = Funkcje::metodaBisekcji(wymagana_dokladnosc, maks_iteracji, wyborFunkcji, a0, b0, iterb);
          cout << "\nZnalezione miejsce zerowe metoda bisekcji: "
               << miejsce_zerowe_bisekcji
               << "\nIlosc wykonanych iteracji: " << iterb;
          cout << "\n\n===================================================\n\n";
          bladZakresu = false;
      }

    } while (bladZakresu == true);

    } while (czyPowtorzycProgram == 1);




///////////////////////////////////////////////////////////////////////
    ///
    ///WYSWIETLANIE WYKRESU
    ///

  Gnuplot::set_GNUPlotPath( GNUPLOT_PATH );

  Gnuplot main_plot;

  // Podpisy na wykresie, zeby bylo wiadomo co na nim widac
  main_plot.set_title( "Zadanie 1 " );
  main_plot.set_xlabel( "X" );
  main_plot.set_ylabel( "Y" );

  // styl rysowania wykresu
  main_plot.set_style( "lines" );

  // siatka poprawia czytelnosc
  main_plot.set_grid();

  // zakres osi x
  main_plot.set_xrange( a0 , b0 ) ;



///tworzenie tablic wartosci x'ow i y'ow wybranej funkcji

  vector<double> x ( 30 );
  vector<double> y ( 30 );

  double krok = (fabs(b0 - a0)) / 30.0;     ///przeruszanie sie po osi X co wyliczony krok
  double a0cp = a0;

  for (int i = 0; i < 30; i++){
      x [i] = a0cp;
      y [i] = Funkcje::f(a0cp,wyborFunkcji);
      a0cp += krok;
  }
///

  main_plot.plot_xy( x, y);

    ///rysowanie znalezionego m. zerowego

  main_plot.set_style( "points" );
  main_plot.set_pointsize( 2.0 );


  vector<double> x_miejsca_zerowego( 2 );
  x_miejsca_zerowego[ 0 ] = miejsce_zerowe_falsi;
  x_miejsca_zerowego[ 1 ] = miejsce_zerowe_bisekcji;
  vector<double> y_miejsca_zerowego( 2 );
  y_miejsca_zerowego[ 0 ] = Funkcje::f(miejsce_zerowe_falsi, wyborFunkcji);
  y_miejsca_zerowego[ 1 ] = Funkcje::f(miejsce_zerowe_bisekcji, wyborFunkcji);


  main_plot.plot_xy( x_miejsca_zerowego, y_miejsca_zerowego);

  ///

  fflush(stdin);
  getchar();

  return 0;
}
