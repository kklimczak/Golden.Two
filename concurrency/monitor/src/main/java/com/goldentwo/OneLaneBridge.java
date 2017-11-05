package com.goldentwo;

import java.util.ArrayList;
import java.util.Random;

public class OneLaneBridge 
{
	private static int completed;
	private static boolean finish;
	private static int all_cars;

	public static void main(String[] args)
	{
		int total_cars, travels, i = 0;
		ArrayList<Car> cars = new ArrayList<Car>();
		Chronometer ch;

		if(!correct_number_of_arguments(args.length)) use();

		ch = initiate_cars(cars, total_cars = get_int_argument(args[0]), travels = get_int_argument(args[1]));

		completed = 0;
		finish = false;
		all_cars = total_cars;

		//Create threads
		for (Car c : cars) 
		{ 
			(new Thread(c)).start();
			if(i == 0) { (new Thread(ch)).start(); i++; }
		}
	}

	private static int get_int_argument(String argument)
	{
		if(!is_integer(argument)) return -1;
		return Integer.parseInt(argument);
	}

	private static Chronometer initiate_cars(ArrayList<Car> cars, int total_cars, int travels)
	{
		char direction;
		Random rand = new Random();
		Chronometer ch;

		if(rand.nextInt(101) <= 50) direction = 'N';
		else direction = 'S';

		Monitor m = new Monitor(direction);

		for(int i = 0; i < total_cars; i++)
		{
			Car c = new Car(i, m, travels);
			cars.add(c);
		}

		return (ch = new Chronometer(m));
	}

	//Must be integer number
	private static boolean is_integer(String str)
	{
		try {
			Integer.parseInt(str);
		} catch(NumberFormatException e) {
			return false;
		} catch(NullPointerException e) {
			return false;
		}
		return true;
	}

	private static void use()
	{
		System.out.print("Incorrect number of arguments. Use:\n\n\tjava OneLaneBridge C T\n\nWhere:\n");
		System.out.print("C is an integer representing the total number of cars.\n");
		System.out.print("T is an integer representing the number of travels the cars will do.\n");
		System.exit(0);
	}

	private static boolean correct_number_of_arguments(int length) { return length == 2; }

	public static int get_all_cars() { return all_cars; }
	public static int get_completed() { return completed; }
	public static boolean get_finish() { return finish; }

	public static void set_completed(int t) { completed = t; }
	public static void set_finish(boolean t) { finish = t; }
}