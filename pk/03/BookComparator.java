package week03;

import java.util.Comparator;

public class BookComparator implements Comparator<Book> {
	public int compare(Book b1, Book b2) {
		int name = b1.getName().toLowerCase().compareTo(b2.getName().toLowerCase());
		if (name != 0) {
			return name;
		}
		int author = b1.getAuthor().toLowerCase().compareTo(b2.getAuthor().toLowerCase());
		if (author != 0) {
			return author;
		}
		return b1.getPages() - b2.getPages();
	}
}
