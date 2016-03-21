#ifndef FUNKCJE_H
#define FUNKCJE_H
#include <cmath>
#include <string>

class Funkcje
{
    public:
        Funkcje();
        virtual ~Funkcje();
        static double f(double x, int nrFunkcji);
        static std::string wyswietlMiejscaZerowe(int wyborFunkcji);
        static double regulaFalsi(double wymagana_dokladnosc, unsigned int maks_iteracji, int wyborFunkcji, double a, double b, unsigned int& iter);
        static double metodaBisekcji(double wymagana_dokladnosc, unsigned int maks_iteracji, int wyborFunkcji, double a, double b, unsigned int& iter);
};

#endif // FUNKCJE_H
