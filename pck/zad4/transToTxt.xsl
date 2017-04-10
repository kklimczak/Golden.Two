<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" 
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	
	<xsl:output method="text" encoding="utf-8"/>

    <xsl:variable name="tab"><xsl:text>    </xsl:text></xsl:variable>

    <xsl:template match="text()">
        <xsl:if test="normalize-space(.)">
            <xsl:value-of select="concat(normalize-space(.), '')"/>
        </xsl:if>
        <xsl:apply-templates/>
    </xsl:template>

	<xsl:template match="/">
		<xsl:apply-templates/>
	</xsl:template>

	<xsl:template match="spis-książek">
		<xsl:value-of select="concat($tab,$tab,$tab,'TYTUŁ',$tab,$tab,$tab,$tab,'|',$tab,$tab,$tab, 'AUTOR',$tab,$tab,$tab,'|',$tab,'CENA(PLN)',$tab,'|',$tab,'CENA(EUR)',$tab,'|',$tab,'CENA(USD)')"/>
		<xsl:text>&#xD;&#xA;</xsl:text>
		<xsl:text>________________________________________________________________________________________________________________________</xsl:text>
		<xsl:text>&#xD;&#xA;</xsl:text>
		<xsl:apply-templates />
	</xsl:template>

	<xsl:template match="książka">
		<xsl:value-of select="concat($tab,tytuł-ksiazki)"/>
		<xsl:value-of select="substring('                     ', 1, 29 - string-length(tytuł-ksiazki))"/>
		<xsl:text>|</xsl:text>
		<xsl:value-of select="concat($tab,autor-ksiazki)"/>
		<xsl:value-of select="substring('                     ', 1, 25 - string-length(autor-ksiazki))"/>
		<xsl:text>|</xsl:text>
		<xsl:apply-templates select="cena" />
    </xsl:template>

    <xsl:template match="cena">
        <xsl:value-of select="concat($tab,cena-z-vat)"/>
		<xsl:value-of select="substring('                     ', 1, 13 - string-length(cena-z-vat))"/>
		<xsl:text>|</xsl:text>

        <xsl:value-of select="concat($tab,cena-w-euro)"/>
		<xsl:value-of select="substring('                     ', 1, 13 - string-length(cena-w-euro))"/>
		<xsl:text>|</xsl:text>

        <xsl:value-of select="concat($tab,cena-w-usd)"/>
		<xsl:value-of select="substring('                     ', 1, 13 - string-length(cena-w-usd))"/>

		<xsl:text>&#xD;&#xA;&#xD;&#xA;&#xD;&#xA;</xsl:text>
    </xsl:template> 

    <xsl:template match="podsumowanie">
		<xsl:text>&#xD;&#xA;</xsl:text>
		<xsl:text>&#xD;&#xA;</xsl:text>
        <xsl:value-of select="concat('Liczba autorów:',$tab,' ', ilość-autorów)"/>
        <xsl:value-of select="concat('&#xD;Ilość książek:',$tab,'  ', ilosc-książek)"/>
        <xsl:value-of select="concat('&#xD;Wartość książek:',$tab, wartość-książek/pln,'PLN ',$tab,'|',$tab, wartość-książek/euro, 'EURO')"/>
        <xsl:value-of select="concat('&#xD;Ocena księgarni:',$tab, ocena-księgarni)"/>

    </xsl:template>
</xsl:stylesheet>