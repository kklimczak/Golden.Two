package Exceptions;

@SuppressWarnings("serial")
public class MyException extends Exception {

	public MyException() {
		System.out.println("\nMoj wyjatek został zgłoszony");
	}

}
