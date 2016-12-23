<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" 
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:fo="http://www.w3.org/1999/XSL/Format">

	<xsl:output method="xml" encoding="utf-8"/>
	<xsl:template match="/">
		<fo:root>
			<fo:layout-master-set>
				<fo:simple-page-master 
					master-name="A4-portrail" 
					page-height="297mm" 
					page-width="210mm" 
					margin-top="5mm" 
					margin-bottom="5mm" 
					margin-left="5mm" 
					margin-right="5mm">
					
		          	<fo:region-body margin="3"/>
		          	<fo:region-before extent="2" />
		          	<fo:region-after extent="2" />
		          	<fo:region-start extent="2" />
		          	<fo:region-end extent="2" />
				</fo:simple-page-master>
			</fo:layout-master-set>

			<fo:page-sequence master-reference="A4-portrail">
        		<fo:static-content flow-name="xsl-region-before">
          			<fo:block text-align="center" font-family="Segoe UI"  font-size="40px">
            			<fo:block text-align="right" font-family="Segoe UI" font-size="8px" margin-bottom="10px" margin-right="10px">
            				Raport z dnia: 
              				<xsl:value-of select="substring(raport/podsumowanie/data-raportu/data-raportu,1,10)"/> <xsl:text> </xsl:text>
              				<xsl:value-of select="substring(raport/podsumowanie/data-raportu/data-raportu,12,5)"/>
            			</fo:block>
            			<xsl:text>Raport z biblioteki</xsl:text>
          			</fo:block>
        		</fo:static-content>
        		<fo:static-content flow-name="xsl-region-after">
         			<fo:block text-align="center" font-family="Segoe UI"  font-size="6px">
            			<xsl:text>strona </xsl:text> 
            			<fo:page-number />
         		 	</fo:block>
        		</fo:static-content>
        		<fo:flow flow-name="xsl-region-body"> 
          			<xsl:apply-templates/> 
        		</fo:flow>
      		</fo:page-sequence>
		</fo:root>
	</xsl:template>

	<xsl:template match="spis-książek">

			<fo:block 
				font-family="Segoe UI"
				font-size="12px"
				text-decoration="underline"
				margin-top="45" 
				margin-left="25">
				<xsl:text> Wykaz książek </xsl:text>
			</fo:block>

			<fo:block 
				font-size="10px" 
				text-align="left" 
				font-family="Segoe UI"
				margin="25" 
				margin-top="15"
				border-width="1.5pt"
				border-style="solid"
				border-color="#664400"
				background-color="#FFFFEE">

				<fo:table border="solid black" width="100%">

		          <fo:table-header>
		            <fo:table-row font-size="15px">
		              <fo:table-cell border="solid black" display-align="center">
		                <fo:block font-weight="bold" text-align="center">Tytuł</fo:block>
		              </fo:table-cell>
		              <fo:table-cell border="solid black" display-align="center">
		                <fo:block font-weight="bold" text-align="center">Autor</fo:block>
		              </fo:table-cell>
		              <fo:table-cell border="solid black">
		                <fo:block font-weight="bold" text-align="center">Cena (PLN)</fo:block>
		              </fo:table-cell>
		              <fo:table-cell border="solid black">
		                <fo:block font-weight="bold" text-align="center">Cena (EUR)</fo:block>
		              </fo:table-cell>
		              <fo:table-cell border="solid black">
		                <fo:block font-weight="bold" text-align="center">Cena (USD)</fo:block>
		              </fo:table-cell>
		            </fo:table-row>
		          </fo:table-header>

		          <fo:table-body>
		            <xsl:for-each select="książka">
		            <xsl:sort select="autor-ksiazki" data-type="text" lang="pl"/>
		              <fo:table-row>
		                <fo:table-cell border="dotted black" display-align="center"> 
		                  <fo:block text-align="center" font-style="italic"> 
		                    <xsl:value-of select="tytuł-ksiazki" />
		                  </fo:block>
		                </fo:table-cell>
		                <fo:table-cell border="dotted black" display-align="center">
		                  <fo:block text-align="center" text-transform="uppercase">
		                    <xsl:value-of select="autor-ksiazki" />
		                  </fo:block>
		                </fo:table-cell>
		                <fo:table-cell border="dotted black" display-align="center">
		                  <fo:block text-align="center">
		                    <xsl:choose>
			                    <xsl:when test="cena/cena-z-vat &lt; 20">
			                      <fo:block color="#00FF00"><xsl:value-of select="cena/cena-z-vat" /></fo:block>
			                    </xsl:when>
			                    <xsl:when test="cena/cena-z-vat &gt; 100">
			                      <fo:block color="#FF0000"><xsl:value-of select="cena/cena-z-vat" /></fo:block>
			                    </xsl:when>
			                    <xsl:otherwise>
			                      <fo:block><xsl:value-of select="cena/cena-z-vat" /></fo:block>
			                    </xsl:otherwise>
                    		</xsl:choose>
		                  </fo:block>
		                </fo:table-cell>
		                <fo:table-cell border="dotted black" display-align="center">
		                  <fo:block text-align="center">
							<xsl:value-of select="cena/cena-w-euro" />
		                  </fo:block>
		                </fo:table-cell>
		                <fo:table-cell border="dotted black" display-align="center">
		                  <fo:block text-align="center"> 
							<xsl:value-of select="cena/cena-w-usd" />
		                  </fo:block>
		                </fo:table-cell>
		              </fo:table-row>
		            </xsl:for-each>
		          </fo:table-body>
		        </fo:table>
			</fo:block>
	</xsl:template>


	<xsl:template match="podsumowanie">

			<fo:block 
				font-family="Segoe UI"
				font-size="12px"
				text-decoration="underline"
				margin-top="55" 
				margin-left="25">
				<xsl:text> Podsumowanie </xsl:text>
			</fo:block>

			<fo:block 
				font-size="10px" 
				margin="25"
				margin-top="10"
				font-family="Segoe UI">

				<fo:table border="solid black" width="50%">

		          <fo:table-body>
		              <fo:table-row>
		                <fo:table-cell border="dashed black" display-align="center" font-weight="bold"> 
		                  <fo:block text-align="center"> 
		                    <xsl:text>Całkowita wartość książek</xsl:text>
		                  </fo:block>
		                </fo:table-cell>
		                <fo:table-cell border="dashed black" display-align="center"> 
		                  <fo:block> 
		                    <fo:list-block>
		                    	<fo:list-item>
		                    		<fo:list-item-label> <fo:block margin-left="5em"> PLN </fo:block> </fo:list-item-label>
		                    		<fo:list-item-body>
		                    			<fo:block>
		                    				<xsl:value-of select="wartość-książek/pln" /> 
		                    			</fo:block>
		                    		</fo:list-item-body>
		                    	</fo:list-item>
		                    	<fo:list-item>
		                    		<fo:list-item-label> <fo:block margin-left="5em"> EURO </fo:block> </fo:list-item-label>
		                    		<fo:list-item-body>
		                    			<fo:block>
											<xsl:value-of select="wartość-książek/euro" />
		                    			</fo:block>
		                    		</fo:list-item-body>
		                    	</fo:list-item>
		                    </fo:list-block>
		                  </fo:block>
		                </fo:table-cell>
		              </fo:table-row>

		              <fo:table-row>
		                <fo:table-cell border="dashed black" display-align="center" font-weight="bold"> 
		                  <fo:block text-align="center"> 
		                    <xsl:text>Liczba autorów</xsl:text>
		                  </fo:block>
		                </fo:table-cell>
		                <fo:table-cell border="dashed black" display-align="center"> 
		                  <fo:block> 
		                  	<xsl:value-of select="ilość-autorów" />
		                  </fo:block>
		                </fo:table-cell>
		              </fo:table-row>

		              <fo:table-row>
		                <fo:table-cell border="dashed black" display-align="center" font-weight="bold"> 
		                  <fo:block text-align="center"> 
		                    <xsl:text>Liczba książek</xsl:text>
		                  </fo:block>
		                </fo:table-cell>
		                <fo:table-cell border="dashed black" display-align="center"> 
		                  <fo:block> 
		                  	<xsl:value-of select="ilosc-książek" />
		                  </fo:block>
		                </fo:table-cell>
		              </fo:table-row>

		              <fo:table-row>
		                <fo:table-cell border="dashed black" display-align="center" font-weight="bold"> 
		                  <fo:block text-align="center"> 
		                    <xsl:text>Ocena księgarni</xsl:text>
		                  </fo:block>
		                </fo:table-cell>
		                <fo:table-cell border="dashed black" display-align="center"> 
		                  <fo:block> 
		                  	<xsl:value-of select="ocena-księgarni" />
		                  </fo:block>
		                </fo:table-cell>
		              </fo:table-row>
		          </fo:table-body>
		        </fo:table>
			</fo:block>
	</xsl:template>

</xsl:stylesheet>