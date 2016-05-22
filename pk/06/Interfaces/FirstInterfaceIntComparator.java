package Interfaces;
import java.util.Comparator;

public class FirstInterfaceIntComparator implements Comparator<Integer>{

	public int compare(Integer int1, Integer int2){
		if (int1 % 2 > int2 % 2) return 1;
		else
			return -1;
	}
}
