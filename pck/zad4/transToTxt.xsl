<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" 
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	
	<xsl:output method="text" encoding="utf-8"/>

	<xsl:template match="/">
		<xsl:apply-templates/>
	</xsl:template>

	<xsl:template match="spis-książek">
		<xsl:apply-templates />
	</xsl:template>

	<xsl:template match="książka">
		<xsl:value-of select="concat('TYTUŁ:&#x9;', tytuł-ksiazki)"/>
		<xsl:text>&#xD;&#xA;</xsl:text>
		<xsl:value-of select="concat('AUTOR:&#x9;', autor-ksiazki)"/>
		<xsl:text>&#xD;&#xA;</xsl:text>
		<xsl:apply-templates select="cena" />
    </xsl:template>

    <xsl:template match="cena">
        <xsl:value-of select="concat('CENA:&#x9;',cena-z-vat, 'PLN &#x9;|&#x9;', cena-w-euro, 'EUR &#x9;|&#x9; ', cena-w-usd, 'USD')" />
		<xsl:text>&#xD;&#xA;&#xD;&#xA;&#xD;&#xA;</xsl:text>
    </xsl:template> 

    <xsl:template match="podsumowanie">
		<xsl:text>&#xD;&#xA;</xsl:text>
		<xsl:text>&#xD;&#xA;</xsl:text>
        <xsl:value-of select="concat('Liczba autorów:&#x9;&#x9;', ilość-autorów)"/>
        <xsl:value-of select="concat('&#xD;Ilość książek:&#x9;&#x9;', ilosc-książek)"/>
        <xsl:value-of select="concat('&#xD;Wartość książek:&#x9;', wartość-książek/pln,'PLN &#x9;|&#x9;', wartość-książek/euro, 'EURO')"/>
        <xsl:value-of select="concat('&#xD;Ocena księgarni:&#x9;', ocena-księgarni)"/>

    </xsl:template>
</xsl:stylesheet>