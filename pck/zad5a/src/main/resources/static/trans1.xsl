<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="xml" encoding="utf-8"/>
  
  <xsl:key name="autorId" match="księgarnia/autorzy/autor" use="id-autora"/>
  <xsl:key name="dzialId" match="księgarnia/działy/dział" use="id-działu"/>

  <xsl:variable name="vat" select="1.05"/>
  <xsl:variable name="kursEuro" select="4.48"/>
  <xsl:variable name="kursDolara" select="4.20"/>

  <xsl:template match="/">
    <xsl:element name="raport"> 
      <spis-książek>
          <xsl:for-each select="księgarnia/książki/książka">
            <xsl:sort select="tytuł" data-type="text" lang="pl"/>
            <xsl:variable name="wybranyAutor" select="key('autorId', autor-książki/@id-autora)" />
            <książka>
              <tytuł-ksiazki>
                <xsl:value-of select="tytuł"/>
              </tytuł-ksiazki>
              <autor-ksiazki>
                <xsl:value-of select="concat($wybranyAutor/imię, ' ', $wybranyAutor/nazwisko)"/>
              </autor-ksiazki>
              <cena>
                <cena-z-vat>
                  <xsl:value-of select="format-number((cena * $vat),'####.00')"/>
                </cena-z-vat>
                <cena-w-euro>
                  <xsl:value-of select="format-number((cena * $vat) div $kursEuro,'####.00')"/>
                </cena-w-euro>
                <cena-w-usd>
                  <xsl:value-of select="format-number((cena * $vat) div $kursDolara,'####.00')"/>
                </cena-w-usd>
              </cena>
            </książka>
          </xsl:for-each>
      </spis-książek>

      <podsumowanie>
        <ilość-autorów>
          <xsl:call-template name="ilość-autorów"/>
        </ilość-autorów>
        <ilosc-książek>
          <xsl:call-template name="ilość-książek"/>
        </ilosc-książek>
        <wartość-książek>
          <xsl:call-template name="wartość-książek"/>
        </wartość-książek> 
        <ocena-księgarni>
          <xsl:call-template name="ocena"/>
        </ocena-księgarni>
      </podsumowanie>

    </xsl:element>
  </xsl:template>

  <xsl:template name="ilość-autorów">
    <xsl:value-of select="count(księgarnia/autorzy/autor)"/>
  </xsl:template>

  <xsl:template name="ilość-książek">
    <xsl:value-of select="count(księgarnia/książki/książka)"/>
  </xsl:template>

  <xsl:template name="wartość-książek">
    <pln><xsl:value-of select="sum(księgarnia/książki/książka/cena)"/></pln>
    <euro><xsl:value-of select="format-number(sum(księgarnia/książki/książka/cena) div $kursEuro, '######.00')"/></euro>
  </xsl:template>

  <xsl:template name="ocena">
    <xsl:choose>
      <xsl:when test="count(księgarnia/książki/książka) &lt; '10'">
        <xsl:value-of select="'Księgarnia jest mała'"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="'Księgarnia daje możliwości wyboru książki'"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>


</xsl:stylesheet>
