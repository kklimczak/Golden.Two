<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="xhtml" version="1.0" encoding="utf-8"
    doctype-public="-//W3C//DTD XHTML 1.1//EN"
    doctype-system="http://www.w3.org/TR/xthml11/DTD/xhtml11.dtd"/>

    <xsl:key name="autorId" match="księgarnia/autorzy/autor" use="id-autora"/>
    <xsl:key name="dzialId" match="księgarnia/działy/dział" use="id-działu"/>

    <xsl:template match="/">
      <html>
        <head>
          <title>Raport</title>
          <link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/foundation/6.2.4/foundation.css" />
        </head>
        <body>
          <h1>Raport (XHTML)</h1>
          <xsl:apply-templates />
        </body>
      </html>

    </xsl:template>

    <xsl:template match="spis-książek">
      <table>
        <thead>
          <th>Tytuł</th>
          <th>Autor</th>
          <th>Cena z VAT</th>
          <th>Cena [EUR]</th>
          <th>Cena [USD]</th>
        </thead>
        <tbody>
            <xsl:apply-templates />
        </tbody>
      </table>
    </xsl:template>

    <xsl:template match="książka">
      <tr>
        <td>
          <xsl:value-of select="tytuł-ksiazki" />
        </td>
        <td>
          <xsl:value-of select="autor-ksiazki" />
        </td>
        <xsl:apply-templates select="cena" />
      </tr>
    </xsl:template>

    <xsl:template match="cena">
      <td>
        <xsl:value-of select="cena-z-vat" />
      </td>
      <td>
        <xsl:value-of select="cena-w-euro" />
      </td>
      <td>
        <xsl:value-of select="cena-w-usd" />
      </td>
    </xsl:template>

</xsl:stylesheet>
