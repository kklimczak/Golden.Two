import javax.swing.*;

public class MyClass {

	public static void wypelnijTrojkat(int[][] t, int nn){
		if (nn == 1){
			t[0][0] = 1;
		}
		
		if (nn == 2 || nn > 2){
			t[0][0] = 1;
			t[1][0] = 1;
			t[1][1] = 1;
		}
		for (int i = 2; i < nn; i++){
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
	
	void public static main(String[] args) {
		
	String txt; //deklaracja zmiennej tekstowej
	txt = JOptionPane.showInputDialog("Wprowadz liczbe calkowita");
	
	int	n = Integer.parseInt(txt); 
	
	int[][] tab = new int[n][n];
	
	wypelnijTrojkat(tab,n);
	
	wyswietlTrojkat(tab,n);
	

	
	}

}
