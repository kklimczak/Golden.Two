#include "wezly.h"
#include "functions.h"

Wezly::Wezly(std::vector<double> wartosciWielomianu, int stopienWielomianu)
{
    this->wartosciWielomianu = wartosciWielomianu;
    this->stopienWielomianu = stopienWielomianu;
}

void Wezly::dowolne(int wyborFunkcji){

    argumenty.clear();
    wartosci.clear();

    std::ifstream plik;
    plik.open("wezly.txt");

    if(!plik.good()){
        std::cout << "Blad pliku 'wezly.txt'\n";
        return;
    }

    int iloscWezlow;
    plik >> iloscWezlow;

    if(iloscWezlow<2){
        std::cerr << "Zbyt malo wezlow";
        return;
    }


    double tmp;
    for (int i = 0 ; i < iloscWezlow ; i++){
        plik >> tmp;
        argumenty.push_back(tmp);
        wartosci.push_back(Functions::wyborFunkcji(wyborFunkcji,tmp,this->wartosciWielomianu,this->stopienWielomianu));
    }

    plik.close();

}

void Wezly::czybeszewa(int wyborFunkcji, int iloscWezlow, double a, double b){
    argumenty.clear();
    wartosci.clear();

    double x;
    for(int i=0 ; i<iloscWezlow ; i++){
        x = cos(((2*i + 1)*M_PI) / (2*iloscWezlow + 1));        //wzory
        argumenty.push_back(0.5*(((b - a) * x) + (a + b)));     //z wykladu
        wartosci.push_back(Functions::wyborFunkcji(wyborFunkcji,argumenty[i],this->wartosciWielomianu,this->stopienWielomianu));
    }
}

void Wezly::rownomiernie(int wyborFunkcji, int iloscWezlow, double x0, double xn){
    argumenty.clear();
    wartosci.clear();

    double krok = fabs(xn - x0) / iloscWezlow;

    x0 += krok / 2.0;       //przesuniecie węzłów, tak zeby pierwszy i ostatni byly rowno oddalone od kranców przedziału

    for(int i = 0 ; i < iloscWezlow ; i++){
        wartosci.push_back(Functions::wyborFunkcji(wyborFunkcji,x0,this->wartosciWielomianu,this->stopienWielomianu));
        argumenty.push_back(x0);
        x0 += krok;
    }

}

std::vector<double> Wezly::getArgumenty(){
    return argumenty;
}


std::vector<double> Wezly::getWartosci(){
    return wartosci;
}

std::vector<double> Wezly::getWartosciWielomianu(){
    return wartosciWielomianu;
}

int Wezly::getStopienWielomianu(){
    return stopienWielomianu;
}
