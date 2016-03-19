package week03;

import java.util.Arrays;

public class Library {
	Book books[];
	int iterator;
	
	Library(int size) {
		books = new Book[size];
		iterator = -1;
	}
	
	public int getBooksAmount() {
		return iterator + 1;
	}
	
	public Book getBook(int index) {
		return books[index];
	}
	
	public Book getBook(String name) {
		for (int i = 0; i < iterator+1; i++) {
			if(books[i].getName().toLowerCase().equals(name.toLowerCase()) || books[i].getAuthor().toLowerCase().equals(name.toLowerCase())) {
				return books[i];
			}
		}
		return null;
	}
	
	public void addBook(Book book) {
		books[iterator+1] = book;
		iterator++;
	}
	
	public void sortBooks () {
		Arrays.sort(books, 0, iterator);
	}
	
	public String toString() {
		String str = "Książki w bibliotece:\n";
		for (int i = 0; i < iterator+1; i++) {
			str += books[i] + "\n";
		}
		str += "\nŁącznie: " + (iterator+1);
		return str;
	}
 }
