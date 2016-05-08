#ifndef FUNCTIONS_H
#define FUNCTIONS_H

#include <math.h>
#include <fstream>
#include <iostream>
#include <vector>

class Functions
{
public:
    Functions();
    static double liniowa(double x);
    static double bezwzgledna(double x);
    static double wielomian(double x, std::vector<double> wsp, int st);
    static double trygonom(double x);
    static double wielomianTrygonom(double x, std::vector<double> wsp, int st);
    static double liniowaBezwzgledna(double x);
    static double wyborFunkcji(int wybor, double x, std::vector<double> wsp, int st);
    static std::vector<double> wczytajWartosciWielomianu(int& stopien);
    static double interpolacjaNewtona(std::vector<double> arg, std::vector<double> wart, double x0);
private:
    static double schematHornera(std::vector<double> wsp,int st, double x);
    static double ilorazRozn(std::vector<double> tab, int wielkosc, double x);
};

#endif // FUNCTIONS_H
