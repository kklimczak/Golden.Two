package week03;

public class Book implements Comparable<Book> {
	private String name;
	private String author;
	private int pages;

	Book(String name, String author, int pages) {
		this.name = name;
		this.author = author;
		this.pages = pages;
	}
	
	Book(OtherBook book) {
		this.name = book.getNazwa();
		this.author = book.getAutor();
		this.pages = book.getStrony();
	}

	@Override
	public int compareTo(Book book) {
		int name = this.getName().toLowerCase().compareTo(book.getName().toLowerCase());
		if (name != 0) {
			return name;
		}
		int author = this.getAuthor().toLowerCase().compareTo(book.getAuthor().toLowerCase());
		if (author != 0) {
			return author;
		}
		return this.getPages() - book.getPages();

	}

	public String toString() {
		return "Name: " + this.name + ", Author: " + this.author + ", Pages: " + this.pages;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {

		this.name = name;
	}

	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getPages() {
		return this.pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}
}
