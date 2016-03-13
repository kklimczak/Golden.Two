import javax.swing.*;

public class Args {

	public static void main(String[] args) {
		// Print first argument.
		System.out.println(args[0]);

		// Print second and third arguments.
		System.out.println(args[1] + " | " + args[2]);

		// Throw information about not enough arguments.
		if (args.length < 3)
			System.err.print("Za malo argumentow");
		else
			System.out.println(" * " + args[0] + " ** " + args[1] + " *** " + args[2]);

		// Print all arguments.
		int i;
		for (i = 0; i < args.length; i++)
			System.out.println(args[i]);

		//

		String txt1;
		txt1 = JOptionPane.showInputDialog("Wprowadz pierwsza liczbe");

		String txt2;
		txt2 = JOptionPane.showInputDialog("Wprowadz druga liczbe");

		String txt3;
		txt3 = JOptionPane.showInputDialog("Wprowadz znak dzialania");

		int liczba1 = Integer.parseInt(txt1);
		int liczba2 = Integer.parseInt(txt2);
		
		System.out.println("Wynik dzialania: " + txt1 + " " + txt3 + " " + txt2 + " to:");

		switch (txt3) {
		case "+":
			System.out.println(liczba1 + liczba2);
			break;
		case "-":
			System.out.println(liczba1 + liczba2);
			break;
		case "*":
			System.out.println(liczba1 + liczba2);
			break;
		case "/":
			System.out.println(liczba1 + liczba2);
			break;
		case "%":
			System.out.println(liczba1 + liczba2);
			break;
		default:
			System.out.println("Nie rozpoznano znaku!");
		}
	}

}

