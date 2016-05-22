package Exceptions;
public class MyExceptionSample {

	public void doSomething() throws MyException{
		   System.out.println("\nZgłaszenie własnego wyjatku");
		   throw new MyException();
		  }
}
