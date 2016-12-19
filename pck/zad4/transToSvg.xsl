<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:svg="http://www.w3.org/2000/svg" xmlns="http://www.w3.org/2000/svg" version="1.0" xmlns:xlink="http://www.w3.org/1999/xlink">

    <xsl:output method="xml" media-type="image/svg" encoding="utf-8" doctype-public="-//W3C//DTD SVG 1.1//EN" doctype-system="http://www.w3.org/TR/2001/REC-SVG-20010904/DTD/svg10.dtd"/>

    <xsl:template match="/">
        <svg:svg width="800" height="400" font-family="Calibri">
            <svg:desc>Test</svg:desc>

            <defs>
                <linearGradient id="bg-color">
                    <stop stop-color="rgb(225,225,225)"/>
                </linearGradient>
                <linearGradient id="chart-color">
                    <stop stop-color="rgb(240,240,240)"/>
                </linearGradient>
                <linearGradient id="bar-color">
                    <stop stop-color="rgb(150,150,150)"/>
                </linearGradient>
                <linearGradient id="bar-highlight-color">
                    <stop stop-color="rgb(150,30,30)"/>
                </linearGradient>
                <linearGradient id="legend-color">
                    <stop stop-color="rgb(210,210,210)"/>
                </linearGradient>
                <linearGradient id="font-color">
                    <stop stop-color="rgb(100,100,100)"/>
                </linearGradient>
            </defs>

            <svg:rect x="2" y="2" width="796" height="396" fill="url(#bg-color)" stroke="rgb(210, 210, 210)" stroke-width="1"/>

            <svg:text x="50%" y="30" font-size="23" fill="url(#font-color)" font-weight="bold" text-anchor="middle">
                Statystyki z biblioteki
            </svg:text>

            <script type="text/ecmascript">
                <![CDATA[
                function onMouseEnter(evt, text) {
                  var element = evt.srcElement;
                  element.setAttribute("fill", "url(#bar-highlight-color)");
                  var text = document.getElementById(text);
                  text.setAttribute("visibility", "visible");
                }]]>
            </script>

            <script type="text/ecmascript">
                <![CDATA[
                function onMouseOut(evt, text) {
                  var element = evt.srcElement;
                  element.setAttribute("fill", "url(#bar-color)");
                  var text = document.getElementById(text);
                  text.setAttribute("visibility", "hidden");
                }]]>
            </script>

            <script type="text/ecmascript">
                <![CDATA[
                function onMouseClick(evt, id) {
                  var e = document.getElementById(id);
                  attr = e.getAttribute("visibility")
                  if (attr === "visible") {
                    e.setAttribute("visibility", "hidden");
                  } else {
                    e.setAttribute("visibility", "visible");
                  }
                }]]>
            </script>

            <xsl:apply-templates select="raport/podsumowanie" />
        </svg:svg>
    </xsl:template>

    <xsl:template match="podsumowanie">
        <svg:g id="rect" width="600" height="200">
            <svg:rect x="100" y="60" width="600" height="200" fill="url(#chart-color)" stroke="rgb(220, 220, 220)" stroke-width="1"/>
        </svg:g>

        <line x1="110" y1="220" x2="680" y2="220" style="stroke:rgb(0,0,0);stroke-width:1" />
        <line x1="120" y1="230" x2="120" y2="80" style="stroke:rgb(0,0,0);stroke-width:1" />

        <svg:text x="114" y="219" font-size="10" file="url(#font-color)">0</svg:text>

        <svg:text x="165" y="240" font-size="16" file="url(#font-color)">Ilość autorów</svg:text>
        <svg:text x="307" y="240" font-size="16" file="url(#font-color)">Ilość książek</svg:text>

        <svg:g id="bars">
          <xsl:variable name="autorzy" select="ilość-autorów"/>
          <svg:text id="text1" x="200" font-size="16" file="url(#font-color)"
            visibility="hidden">
            <xsl:attribute name="y">
                <xsl:value-of select=" 214 - $autorzy*5"/>
            </xsl:attribute>
            <svg:animate id="bar1" begin="0s" attributeName="y" from="219" to="{214-$autorzy*5}" dur="1s" fill="freeze"/>
            <xsl:value-of select="$autorzy" />
          </svg:text>
          <svg:rect x="160" width="100" fill="url(#bar-color)"
            onmouseenter="onMouseEnter(evt, 'text1')"
            onmouseout="onMouseOut(evt, 'text1')">
              <xsl:attribute name="y">
                  <xsl:value-of select=" 219 - $autorzy*5"/>
              </xsl:attribute>
              <xsl:attribute name="height">
                  <xsl:value-of select="$autorzy*5"/>
              </xsl:attribute>
              <svg:animate id="first" begin="0s" attributeName="y" from="219" to="{219-$autorzy*5}" dur="1s" fill="freeze"/>
              <svg:animate id="first" begin="0s" attributeName="height" from="1" to="{$autorzy*5}" dur="1s" fill="freeze"/>
          </svg:rect>

          <xsl:variable name="ksiazki" select="ilosc-książek"/>
          <svg:text id="text2" x="340" font-size="16" file="url(#font-color)"
            visibility="hidden">
            <xsl:attribute name="y">
                <xsl:value-of select=" 214 - $ksiazki*5"/>
            </xsl:attribute>
            <svg:animate id="bar2" begin="0s" attributeName="y" from="219" to="{214-$ksiazki*5}" dur="1s" fill="freeze"/>
            <xsl:value-of select="$ksiazki" />
          </svg:text>
          <svg:rect x="300" width="100" fill="url(#bar-color)"
            onmouseenter="onMouseEnter(evt, 'text2')"
            onmouseout="onMouseOut(evt, 'text2')">
              <xsl:attribute name="y">
                  <xsl:value-of select=" 219 - $ksiazki*5"/>
              </xsl:attribute>
              <xsl:attribute name="height">
                  <xsl:value-of select="$ksiazki*5"/>
              </xsl:attribute>
              <svg:animate id="second" begin="0s" attributeName="y" from="219" to="{219-$ksiazki*5}" dur="1s" fill="freeze"/>
              <svg:animate id="second" begin="0s" attributeName="height" from="1" to="{$ksiazki*5}" dur="1s" fill="freeze"/>
          </svg:rect>

          <svg:g>
            <svg:rect x="500" y="100" width="140" height="100" fill="url(#legend-color)" />
            <svg:text x="510" y="135" font-size="16" file="url(#font-color)">Wartość książek</svg:text>
            <svg:text x="510" y="155" font-size="16" file="url(#font-color)">
              <xsl:value-of select="concat(wartość-książek/pln, ' PLN')" />
            </svg:text>
            <svg:text x="510" y="175" font-size="16" file="url(#font-color)">
              <xsl:value-of select="concat(wartość-książek/euro, ' EUR')" />
            </svg:text>
          </svg:g>
        </svg:g>

        <svg:g id="more" onclick="onMouseClick(evt, 'more-content')">
          <svg:text x="150" y="300" file="url(#font-color)" text-decoration="underline">Więcej...</svg:text>
        </svg:g>

        <svg:g id="more-content" visibility="hidden">
          <svg:text x="150" y="330" file="url(#font-color)">
            <xsl:value-of select="ocena-księgarni" />
          </svg:text>
          <svg:text x="150" y="350" file="url(#font-color)">
            <xsl:value-of select="concat('Utworzono: ', format-dateTime(data-raportu/data-raportu,'[M01]/[D01]/[Y0001] [H01]:[m01]'))" />
          </svg:text>
        </svg:g>
    </xsl:template>

</xsl:stylesheet>
