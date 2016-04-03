import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Library {
	List<Book> books;
	int iterator;
	
	Library(int size) {
		books = new ArrayList<Book>();
		iterator = -1;
	}
	
	public int getBooksAmount() {
		return iterator + 1;
	}
	
	public Book getBook(int index) {
		return books.get(index);
	}
	
	public Book getBook(String name) {
		for (int i = 0; i < iterator+1; i++) {
			if(books.get(i).getName().toLowerCase().equals(name.toLowerCase()) || books.get(i).getAuthor().toLowerCase().equals(name.toLowerCase())) {
				return books.get(i);
			}
		}
		return null;
	}
	
	public void addBook(Book book) {
		books.add(book);
		iterator++;
	}
	
	public void sortBooks () {
		Collections.sort(books);
	}
	
	public String toString() {
		String str = "Ksiazki w bibliotece:\n";
		for (int i = 0; i < iterator+1; i++) {
			str += books.get(i) + "\n";
		}
		str += "£¹czcznie: " + (iterator+1);
		return str;
	}
 }
