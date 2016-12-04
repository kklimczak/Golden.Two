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
          <link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/foundation/6.2.4/foundation.css" />
        </head>
        <body>
          <table>
            <thead>
              <th>ID</th>
              <th>Tytuł</th>
              <th>Autor</th>
              <th>Dział</th>
              <th>Cena</th>
              <th>ISBN</th>
            </thead>
            <tbody>
              <xsl:for-each select="księgarnia/książki/książka">
                <xsl:apply-templates select="."/>
              </xsl:for-each>
            </tbody>
          </table>
        </body>
      </html>
    </xsl:template>

    <xsl:template match="książka">
      <xsl:variable name="autor" select="key('autorId', autor-książki/@id-autora)" />
      <xsl:variable name="dzial" select="key('dzialId', dział-książki/@id-działu)" />
      <tr>
        <td>
          <xsl:value-of select="@id-książki" />
        </td>
        <td>
          <xsl:value-of select="tytuł" />
        </td>
        <td>
          <xsl:value-of select="concat($autor/imię, ' ', $autor/nazwisko)" />
        </td>
        <td>
          <xsl:value-of select="concat($dzial/nazwa, ' &lt;', $dzial/regał/@numer, '&gt;')" />
        </td>
        <td>
          <xsl:variable name="cena" select="format-number(cena, '####.00')" />
          <xsl:variable name="euro" select="4.48" />
          <xsl:variable name="cena-euro" select="format-number(cena div $euro, '####.00')" />
          <xsl:value-of select="concat($cena, ' PLN / ', $cena-euro, ' EUR')" />
        </td>
        <td>
          <xsl:value-of select="isbn" />
        </td>
      </tr>
    </xsl:template>
</xsl:stylesheet>
