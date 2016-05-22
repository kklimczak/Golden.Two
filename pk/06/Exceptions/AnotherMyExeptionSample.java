package Exceptions;

public class AnotherMyExeptionSample {
	
	int[] lottoNumbers;
	public AnotherMyExeptionSample() {
		lottoNumbers = new int[3];
	}
	
	public void setYourNumbers(int num1, int num2, int num3) throws AnotherMyExeption{
		if(num1 < 0 || num1 > 48) throw new AnotherMyExeption();
		if(num2 < 0 || num2 > 48) throw new AnotherMyExeption();
		if(num3 < 0 || num3 > 48) throw new AnotherMyExeption();
		
		lottoNumbers[0] = num1;
		lottoNumbers[1] = num2;
		lottoNumbers[2] = num3;
	}
	
	public void printYourNumbers(){
		System.out.println("\nTwoje numerki: ");
		for (int i : lottoNumbers){
			System.out.println(i);
		}
	}

}
