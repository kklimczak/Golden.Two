#include "functions.h"

Functions::Functions()
{

}


double Functions::schematHornera(std::vector<double> wsp,int st, double x)
{
    double wynik = wsp[0];

    for(int i=1;i<=st;i++){
        wynik = wynik*x + wsp[i];
    }

        return wynik;
}

double Functions::liniowa(double x){
    return 2*x + 5;
}

double Functions::bezwzgledna(double x){
    return fabs(x);
}

std::vector<double> Functions::wczytajWartosciWielomianu(int& stopien){
    std::ifstream plik;
    std::vector<double>wsp;
    plik.open("wielomian.txt");

    if(!plik.good()){
        std::cerr << "Nie wczytano pliku\n";
        return wsp;
    }

    plik >> stopien;

    if(stopien <= 0 ){
        std::cerr << "Błąd danych w pliku\n";
        return wsp;
    }

    double tmp;
    for(int i = 0; i<=stopien; i++){
        plik >> tmp;
        wsp.push_back(tmp);
    }

    plik.close();
    return wsp;
}

double Functions::wielomian(double x, std::vector<double> wsp, int st){

    return schematHornera(wsp, st, x);

}

double Functions::trygonom(double x){
    return sin(x);
}

double Functions::wielomianTrygonom(double x, std::vector<double> wsp, int st){
    return wielomian(x,wsp,st) * trygonom(x);
}

double Functions::liniowaBezwzgledna(double x){
    return liniowa(x) * bezwzgledna(x);
}

double Functions::wyborFunkcji(int wybor, double x, std::vector<double> wsp, int st){
    switch(wybor){
    case 1:
        return liniowa(x);
    case 2:
        return bezwzgledna(x);
    case 3:
        return wielomian(x, wsp, st);
    case 4:
        return trygonom(x);
    case 5:
        return wielomianTrygonom(x, wsp, st);
    case 6:
        return liniowaBezwzgledna(x);
    default:
        std::cerr << "Nie rozmoznano funkcji\n";
        return 0;

    }
}

double Functions::interpolacjaNewtona(std::vector<double> arg, std::vector<double> wart, double x0){

    int n = arg.size();
    int i,j;
    double sum = 0, mult;

    for(j = 0 ; j < n-1 ; j++){
        for(i = n-1 ; i>j ; i--)
            wart[i] = (wart[i] - wart[i-1]) / (arg[i] - arg[i-j-1]);
    }

    for(i = n-1 ; i >= 0 ; i--){
        mult = 1;
        for(j = 0 ; j<i ; j++)
            mult *= (x0 - arg[j]);

        mult *= wart[j];
        sum += mult;
    }

    return sum;
}


