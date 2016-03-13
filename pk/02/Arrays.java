
public class Arrays {

	public static void main(String[] args) {
		int i;

		// New array
		int tab[] = new int[10];

		// Write in array - the same number
		for (i = 1; i < 10; i++)
			tab[i] = 7;

		// Display array
		for (i = 1; i < 10; i++)
			System.out.print(tab[i] + " ");

		System.out.println("");

		// Write in array - numbers depend on i
		for (i = 1; i < 10; i++)
			tab[i] = 3 * i;

		// Display array
		for (i = 1; i < 10; i++)
			System.out.print(tab[i] + ", ");
		
		System.out.println("");

		// Dynamic array
		int size = 20;
		int dynamic[] = new int[size];

		for (i = 0; i < size; i++) {
			dynamic[i] = 20 - 2 * i;
			System.out.print(dynamic[i] + " ");
		}

		System.out.println("");

		// Write in array - random numbers

		for (i = 0; i < size; i++)
			dynamic[i] = (int) (Math.random() * 10);

		for (i = 0; i < size; i++)
			System.out.print(dynamic[i] + " ");

		System.out.println("");
	}

}

