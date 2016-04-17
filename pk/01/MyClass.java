import javax.swing.*;

public class MyClass {

	

	private static int rekurencja(int n, int m){
	
		if(n==m || n==0)		   
			return 1;
		if(m==1 || m==n-1)
			return n;
		   
		return rekurencja(n-1,m-1)+rekurencja(n-1,m);
	}
	
	
	public static void wypelnijTrojkat(int[][] t, int nn){

		t[0][0] = 1;
		
		for (int i=1; i < nn; i++){
			t[i][0]=1;
			t[i][i]=1;
			
			if(i >= 2)
				for (int j = 1; j <= i-1; j++)				
					t[i][j]=rekurencja(i,j);

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
	
	wypelnijTrojkat(tab, n);
	
	wyswietlTrojkat(tab, n);

	
	}

}
