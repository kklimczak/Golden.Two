import javax.swing.*;

public class MyClass {

	
	
	public static void wypelnijTrojkat(int[][] t, int nn){

		
		for (int i = 0; i < nn; i++){
			for (int j = 0; j < i+1; j++){
				if (j==0 || j == i ){
					t[i][j] = 1;
				}
				else {
					t[i][j] = t[i-1][j-1] + t[i-1][j];
				}
				
			}
		}
	}
	
	static public void wyswietlTrojkat(int[][] t,int nn){
		for (int i = 0; i < nn; i++){			
			for (int j=0; j < i+1; j++ ){
				System.out.print(t[i][j] + " ");
			}
			System.out.print("\n");
		}
	}
	
    public static void main(String[] args) {
		
	String txt; //deklaracja zmiennej tekstowej
	txt = JOptionPane.showInputDialog("Podaj glebokosc trojkata");
	
	int	n = Integer.parseInt(txt);
	int x = 0;
	
	int[][] tab = new int[n][];
	for (int i = 0; i < n; i++) {
		  tab[i] = new int[++x];
		}
	
	wypelnijTrojkat(tab,n);
	
	wyswietlTrojkat(tab,n);

	
	}

}
