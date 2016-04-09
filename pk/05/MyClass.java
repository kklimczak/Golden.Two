import java.io.*; 
import java.util.Arrays;

public class MyClass {

	private static void zapiszTekstDoPliku(File plik, String tekst){
		

			String tekstDoZapisania = tekst;	// Tekst, na ktorym bedziemy operowac 

			try	{
				plik.createNewFile();								// Utworzenie pliku pod sciezka zapisana w plik 
				FileWriter strumienZapisu = new FileWriter(plik);	// Konstrukcja i otwarcie strumienia
				strumienZapisu.write(tekstDoZapisania, 0, tekst.length());		// Zapis do pliku liter od 0 do 7 z txt  
				strumienZapisu.close(); 							// Zamkniecie strumienia
			}

			// Instrukcje lapiace wyjatki
			catch (IOException io)												
				{System.out.println(io.getMessage());}

			catch (Exception se)
				{System.err.println("blad sec");}
				

	}
	
	private static void odczytajTekstZPliku(File plik){

		
			char buf[] = new char[17]; 								// bufor (tablica) na odczytane znaki
			try {
				FileReader strumienOdczytu = new FileReader(plik);	// Konstrukcja i otwarcie strumienia odczytujacego
				strumienOdczytu.read(buf, 0, 17);					// Odczytanie znakow od 0 do 7 ze strumienia do bufora  
			}	
			
			catch (FileNotFoundException io)												
				{System.out.println(io.getMessage());}
			
			catch (IOException io)												
			{System.out.println(io.getMessage());} 
			
			String tekstOdczytany = new String(buf);					// Konwersja zawartosci bufora na String
			System.out.println("Odczytalem znaki: "+ tekstOdczytany); 	// Wydruk
			
			
	}
	
	private static void zapiszTabliceDoPliku(int rozmiar, int[] tab){
		
		int zakres = 30;	
		int i; 
		
		for (i=0; i< rozmiar; i++) 	
			tab[i] = (int)(Math.random()*zakres);
		try {
			DataOutputStream strumienTablicy = 									// Strumien zapisujacy liczby
				new DataOutputStream(new FileOutputStream("IO_files/tablica.bin")); 
			for (i=0; i< rozmiar; i++)
				strumienTablicy.writeInt(tab[i]);
		}
		catch (IOException io)												
			{System.out.println(io.getMessage());}

		catch (Exception se)
			{System.err.println("blad sec");}
	}

	private static void odczytajTabliceZPliku(int rozmiar, int[] tab){
		
		int i;
		
		try {
		DataInputStream strumienTablicaZPliku = 
					new DataInputStream(new FileInputStream("IO_files/tablica.bin"));
			for (i=0; i< rozmiar; i++) 	
				tab[i] = strumienTablicaZPliku.readInt();
		}
		catch (FileNotFoundException io)												
			{System.out.println(io.getMessage());}
	
		catch (IOException io)												
			{System.out.println(io.getMessage());} 
		
		
		Arrays.sort(tab);
		for (i=0; i< rozmiar; i++)
			System.out.print(tab[i]+" ");
		
	}
	
	public static void main(String[] args) {

		
		File plik = new File("IO_files/pliczek.txt"); 		// Obiekt klasy File reprezentuje plik 
															// w pamieci programu
		int rozmiar = 15; 									// Rozmiar tablicy z liczbami calkowitymi
		int tab[] = new int[rozmiar];
		
		
		zapiszTekstDoPliku(plik, "Przykladowy tekst");
		odczytajTekstZPliku(plik);
		
		zapiszTabliceDoPliku(rozmiar, tab);
		odczytajTabliceZPliku(rozmiar, tab);
		

		}

	}

