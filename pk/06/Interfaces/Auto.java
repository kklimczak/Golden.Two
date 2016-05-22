package Interfaces;

public class Auto implements MyInterfacePojazd, MyInterfaceTransformers {

	public Auto() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void ruch() {
		System.out.println("Samochód ruszył!");
	}

	@Override
	public void stop() {
		System.out.println("Samochód stanął!");
	}

	@Override
	public void sygnal() {
		System.out.println("BEEP BEEP");
	}

	@Override
	public void robotForm() {
		System.out.println("My name is Optimus Prime");
	}

	@Override
	public void carForm() {
		System.out.println("My name is Volkswagen Polo");
	}

}
