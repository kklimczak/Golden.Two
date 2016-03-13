import javax.swing.JOptionPane;
import java.util.Arrays;
import java.util.Date;


public class ArraysClass {

	public static void main(String[] args) {
		int i;

		// New array
		int tab[] = new int[10];

		// Write in array - the same number
		for (i = 1; i < 10; i++)
			tab[i] = 7;

		// Display array
		System.out.println("Ta sama liczba:");
		for (i = 1; i < 10; i++)
			System.out.print(tab[i] + " ");

		System.out.println("");

		// Write in array - numbers depend on i
		for (i = 1; i < 10; i++)
			tab[i] = 3 * i;

		// Display array
		System.out.println("Liczby zależne od i:");
		for (i = 1; i < 10; i++)
			System.out.print(tab[i] + ", ");
		
		System.out.println("");

		// Dynamic array
		int size = 20;
		int dynamic[] = new int[size];

		System.out.println("Dynamiczna tablica:");
		for (i = 0; i < size; i++) {
			dynamic[i] = 20 - 2 * i;
			System.out.print(dynamic[i] + " ");
		}

		System.out.println("");

		// Write in array - random numbers

		for (i = 0; i < size; i++)
			dynamic[i] = (int) (Math.random() * 10);

		// Display array
		System.out.println("Dynamiczna tablica z losowymi liczbami:");
		for (i = 0; i < size; i++)
			System.out.print(dynamic[i] + " ");

		System.out.println("");
		
		// Get array size
		String txt1;
		txt1 = JOptionPane.showInputDialog("Wprowadz rozmiar tablicy");
		
		// Parse String to int
		int size2 = Integer.parseInt(txt1);
		int min = Integer.parseInt(args[0]);
		int max = Integer.parseInt(args[1]);
		int prompt[] = new int[size2];
		
		for (i = 0; i < size2; i++)
			prompt[i] = min + (int)(Math.random() * ((max - min) + 1));
		
		System.out.println("Losowe wartości z zakresu ["+args[0]+","+args[1]+"]:");
		for (i = 0; i < size2; i++)
			System.out.print(prompt[i] + " ");
		
		System.out.println("");
		
		int promptCopy[] = new int[size2];
		System.arraycopy(prompt, 0, promptCopy, 0, size2);
		
		int j;
		
		Date beforePrompt = new Date();
		
		for (i = 0; i < size2; i++) {
			for (j = 0; j < size2 - 1; j++) {
				if (prompt[j] > prompt[j+1]) {
					int temp;
					temp = prompt[j+1];
					prompt[j+1] = prompt[j];
					prompt[j] = temp;
				}
			}
		}
		
		Date afterPrompt = new Date();
		
		System.out.println("Posortowane metodą bąbelkową:");
		for (i = 0; i < size2; i++)
			System.out.print(prompt[i] + " ");
		
		System.out.println("");
		System.out.println("Czas operacji "+(afterPrompt.getTime() - beforePrompt.getTime()) +"ms");
		
		Date beforePromptCopy = new Date();
		
		Arrays.sort(promptCopy);
		
		Date afterPromptCopy = new Date();
		
		System.out.println("Posortowane metodą Arrays.sort():");
		for (i = 0; i < size2; i++)
			System.out.print(promptCopy[i] + " ");
		
		System.out.println("");
		System.out.println("Czas operacji "+(afterPromptCopy.getTime() - beforePromptCopy.getTime()) +"ms");
	}

}
