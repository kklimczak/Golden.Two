package week03;

public class OtherBook {
	private String nazwa;
	private String autor;
	private int strony;
	
	OtherBook(String nazwa, String autor, int strony) {
		this.nazwa = nazwa;
		this.autor = autor;
		this.strony = strony;
	}
	
	public String getNazwa() {
		return this.nazwa;
	}
	
	public String getAutor() {
		return this.autor;
	}
	
	public int getStrony() {
		return this.strony;
	}
}
