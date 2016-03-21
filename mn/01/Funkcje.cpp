#include "Funkcje.h"

#define WIELOMIAN x*x*x-4*x
#define TRYGONOMETRYCZNA sin(x*x)
#define WYKLADNICZA powl(0.5,x) - 13


Funkcje::Funkcje()
{

}

Funkcje::~Funkcje()
{
    //dtor
}


double Funkcje::f(double x, int nrFunkcji)
{
    /*
    double wynik = 1;
    double wykladnik = 0.5;
    int calosc = x;
    double ulamek = x - calosc;
    */

    switch (nrFunkcji)
    {
    case 1:
        return WIELOMIAN;


    case 2:
        return TRYGONOMETRYCZNA;                    ///trygo


    case 3:
        /*
        for (int i = 0; i < calosc; i++){
            wynik *= wykladnik;
        }

        if(calosc > 0.0){
            wynik *= (exp(log(wykladnik)/(1/ulamek))) - 13;
        }
        */
        return WYKLADNICZA;

    case 4:
        return WIELOMIAN + TRYGONOMETRYCZNA;

    case 5:
        return WIELOMIAN + WYKLADNICZA;

    case 6:
        return TRYGONOMETRYCZNA + WYKLADNICZA;

    case 7:
        return WIELOMIAN + WYKLADNICZA + TRYGONOMETRYCZNA;

    default:
        break;

    }

    return 0;
}

double Funkcje::regulaFalsi(double wymagana_dokladnosc, unsigned int maks_iteracji, int wyborFunkcji, double a, double b, unsigned int& iter)
{

    double x1, x2, fa, fb, f0;

      x1 = a;
      x2 = b;

      fa = f(a, wyborFunkcji);
      fb = f(b, wyborFunkcji);


        while( (fabs(x1 - x2) > wymagana_dokladnosc) && (iter < maks_iteracji))
        {
          iter++;

          x1 = x2;
          x2 = a - fa * (b - a) / (fb - fa); /// punkt przeciecia falszywej prostej z osia OX
          f0 = f(x2,wyborFunkcji);                        /// wartosc funkcji w punkcie przeciecia falszywej prostej z osia OX

          if(f0 == 0) return x2;

        ///warunek zmieniajacy zakres falszywej prostej z odpowiedniej strony
          if(fa * f0 < 0)
          {
            b = x2;
            fb = f0;
          }
              else
              {
                a = x2;
                fa = f0;
              }

        }//koniec while'a


       return x2;

}

double Funkcje::metodaBisekcji(double wymagana_dokladnosc, unsigned int maks_iteracji, int wyborFunkcji, double a, double b, unsigned int& iter)
{
    double x2, fa, f0;

      x2 = b;

      fa = f(a, wyborFunkcji);

    while( (fabs(a - b) > wymagana_dokladnosc) && (iter < maks_iteracji))
    {
            iter++;
            x2 = (a + b) / 2.0;

            f0 = f(x2,wyborFunkcji);

            if (f0 == 0)return x2;

            if(fa * f0 < 0) {
                b = x2;
            }
                else
                {
                  a = x2;
                  fa = f0;
                }
    }

        return x2;


}

std::string Funkcje::wyswietlMiejscaZerowe(int nrFunkcji)
{
    switch (nrFunkcji)
    {
    case 1:
        return "-2; 0; 2";
    case 2:
        return "-3.06998012; -2.50662827; -1.77245385; 1.77245385; 2.50662827; 3.06998012...";
    case 3:
        return "-3.70044";
    case 4:
        return "-1.92809; 0; 2.11149";
    case 5:
        return "-9.86795; 2.90364";
    case 6:
        return "-3.63318620";
    case 7:
        return "-9.86790999; 2.85791464";
    default:
        return 0;

    }

}
