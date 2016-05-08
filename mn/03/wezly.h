#ifndef WEZLY_H
#define WEZLY_H

#include <fstream>
#include <iostream>
#include <vector>

class Wezly
{
private:
    std::vector<double> argumenty;              //arg i wartosci wezla
    std::vector<double> wartosci;

    std::vector<double> wartosciWielomianu;
    int stopienWielomianu;
public:
    Wezly(std::vector<double> wartosciWielomianu, int stopienWielomianu);
    void dowolne(int wyborFunkcji);
    void czybeszewa(int wyborFunkcji, int iloscWezlow, double x0, double xn );
    void rownomiernie(int wyborFunkcji, int iloscWezlow, double x0, double xn );
    std::vector<double> getArgumenty();
    std::vector<double> getWartosci();
    std::vector<double> getWartosciWielomianu();
    int getStopienWielomianu();
};

#endif // WEZLY_H
