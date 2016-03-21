#include "GnuPlotClass.h"
#include "gnuplot_i.hpp"
#include <vector>

GnuPlotClass::GnuPlotClass()
{
    Gnuplot::set_GNUPlotPath( "E:/gnuplot/bin/" );
}

GnuPlotClass::~GnuPlotClass()
{
    //dtor
}

void GnuPlotClass::wyswietl()
{


  Gnuplot main_plot;

  // Podpisy na wykresie, zeby bylo wiadomo co na nim widac
  main_plot.set_title( "tytul wykresu" );
  main_plot.set_xlabel( "podpis osi odcietych" );
  main_plot.set_ylabel( "podpis osi rzednych" );

  // styl rysowania wykresu
  main_plot.set_style( "lines" );

  // siatka poprawia czytelnosc
  main_plot.set_grid();

  // zakres osi x
  main_plot.set_xrange( 0 , 6 ) ;



  std::vector<double> x( 3 );
  x[ 0 ] = 0.5;
  x[ 1 ] = 3.0;
  x[ 2 ] = 4.0;
  std::vector<double> y ( 3 );
  y[ 0 ] =  0.7;
  y[ 1 ] =  0.2;
  y[ 2 ] = -0.4;
  main_plot.plot_xy( x, y, "podpis - opcjonalnie" );
}
