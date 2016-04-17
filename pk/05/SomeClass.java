import java.io.Serializable;


public class SomeClass implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1140990972981512633L;
	private String name;
	private int number;
	
	SomeClass(String _name, int _number){
		setName(_name);
		setNumber(_number);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
	public String toString(){
		return "Name: " + name + ", number: " + Integer.toString(number);
	}
	
	
	
}
