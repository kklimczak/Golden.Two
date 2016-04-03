
public class StartApp {

	public static void main(String[] args) {
		
		Book otherBook = new Book(new OtherBook("Ziemniaki", "Gorgre", 352));
		
		BookComparator bc = new BookComparator();
		
		Library library = new Library(10);
		library.addBook(new Book("Pinokio", "Collodi", 132));
		library.addBook(new Book("Alladyn", "Kowalska", 324));
		library.addBook(new Book("Dzialo", "Nowak", 654));
		library.addBook(new Book("Ksiazka Stara", "Klimczak", 231));
		library.addBook(new Book("Basn", "Kuta", 967));
		library.addBook(new Book("Cos Innego", "Meastrii", 345));
		library.addBook(new Book("Lubie tak", "Smith", 987));
		library.addBook(new Book("Moze to", "John", 927));
		library.addBook(new Book("Fajnie", "Malinowski", 164));
		library.addBook(otherBook);
		System.out.println("");
		System.out.println(library);
		System.out.println("");
		library.sortBooks();
		System.out.println(library);

	}

}
