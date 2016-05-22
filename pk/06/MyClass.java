import java.util.Arrays;

import Exceptions.*;
import Interfaces.*;


public class MyClass {


	public static void main(String[] args) {
		  		
		WyjatekBiblioteczny wb = new WyjatekBiblioteczny();

		try{	
			wb.throwException();
		}
		catch(IndexOutOfBoundsException e){
			System.out.println("Block catch - Złapano wyjątek: " + e.getClass());
		}
		finally{
			System.out.println("Blok finally - Wszystkie liczby w tablicy: ");
			wb.printArray();
		}
		
		
		//WŁASNE WYJĄTKI
		
		  try {
			  MyExceptionSample mySample = new MyExceptionSample();
			  mySample.doSomething();
		  } 
		  catch (MyException e) {
			  System.out.println("Block catch - Przechwycono mój wyjątek\n");
		  }
		  
		  
		  try {
			  AnotherMyExeptionSample aMySample = new AnotherMyExeptionSample();
			  aMySample.setYourNumbers(2, 15, 50);
		  }
		  catch(AnotherMyExeption e){
			  System.out.println("Blok catch - Pamiętaj że zakres liczb to 0 - 48");
		  }
		  
		  ////////
		  /*Interfejsy*/
		  ////////
		  
		  Integer[] tabInt = { 10, 1, 9, 2, 8, 3, 7, 4, 6, 5 };
		  System.out.println("\n\nSortowanie tablicy - liczby parzyste przed nieparzystymi");
		  Arrays.sort(tabInt, new FirstInterfaceIntComparator());
		  
		  for (int i : tabInt){
			  System.out.print(i + " ");
		  }
		  
		  new Thread(new SecondInterface()).start();
		  
		  
	}
}
