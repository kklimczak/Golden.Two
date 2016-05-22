package Exceptions;

public class WyjatekBiblioteczny {

	private int[] tab;
	
	public WyjatekBiblioteczny(){
		tab = new int [2];
		tab[0] = 21;
		tab[1] = 4;
	}
	
	public int throwException() throws IndexOutOfBoundsException{
	
		int[] tab = {1, 2};
		int i = tab[10];
		return i;
		
	}
	
	public void printArray(){
		for(int i : tab){
			System.out.println(i);
		}
	}

}
