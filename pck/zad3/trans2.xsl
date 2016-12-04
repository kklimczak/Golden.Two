<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns="http://www.w3.org/1999/xhtml">
  <xsl:output method="xhtml" version="1.1" encoding="utf-8"
    doctype-public="-//W3C//DTD XHTML 1.1//EN"
    doctype-system="http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd"/>

    <!-- Main node -->

    <xsl:template match="/">
      <xsl:element name="html">
        <xsl:attribute name="xml:lang">pl</xsl:attribute>
        <xsl:attribute name="lang">pl</xsl:attribute>
        <head>
          <title>Raport</title>
          <link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/foundation/6.2.4/foundation.css" />
        </head>
        <body>
          <h1>Raport <small>(XHTML)</small></h1>
          <xsl:apply-templates />
        </body>
        </xsl:element>

    </xsl:template>

    <!-- books list -->

    <xsl:template match="spis-książek">
      <h2>Spis książek</h2>
      <xsl:apply-templates />
    </xsl:template>

    <xsl:template match="książka">
      <hr />
        <h3><xsl:value-of select="tytuł-ksiazki" /></h3>
        <h4><xsl:value-of select="autor-ksiazki" /></h4>
        <xsl:apply-templates select="cena" />
    </xsl:template>

    <xsl:template match="cena">
      <p>
        <xsl:value-of select="cena-z-vat" /><b> PLN</b><span> - </span>
        <xsl:value-of select="cena-w-euro" /><b> EUR</b><span> - </span>
        <xsl:value-of select="cena-w-usd" /><b> USD </b>
      </p>
    </xsl:template>

    <!-- summary -->

    <xsl:template match="podsumowanie">
      <hr />
      <h2>Podsumowanie</h2>
      <p>
        <b>Ilość autorów: </b><xsl:value-of select="ilość-autorów" />
      </p>
      <p>
        <b>Ilość książek: </b><xsl:value-of select="ilosc-książek" />
      </p>
      <p>
        <b>Cena: </b><xsl:value-of select="wartość-książek/pln" />
        <span> PLN (</span><xsl:value-of select="wartość-książek/euro" />
        <span> EUR)</span>
      </p>
      <p>
        <b>Ocena księgarni: </b><xsl:value-of select="ocena-księgarni" />
      </p>
      <p><b>Utworzono: </b><xsl:value-of select="format-dateTime(data-raportu/data-raportu,'[M01]/[D01]/[Y0001] [H01]:[m01]')" /></p>
    </xsl:template>

</xsl:stylesheet>
