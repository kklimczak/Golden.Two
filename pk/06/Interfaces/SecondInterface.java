package Interfaces;

public class SecondInterface implements Runnable {

	@Override
	public void run() {
		System.out.println("\n\nInterfejs uruchomiony na innym wątku");
		
		for (int i = 0; i < 10; i++){
			try{
				Thread.sleep(3000);
			}
			catch(InterruptedException e){
				e.printStackTrace();
			}
			
			System.out.println("Twój szczęśliwy numerek to: " + (int)(Math.random() * 48 + 1));
		}
	}

}
