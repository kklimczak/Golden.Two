package com.goldentwo;

import java.util.Random;

public class Car implements Runnable
{
	private int number;
	private int travels;
	private Monitor monitor;
	private char direction;

	public Car(int number, Monitor monitor, int travels)
	{
		this.number = number;
		this.monitor = monitor;
		this.travels = travels;

		Random rand = new Random();
		if(rand.nextInt(101) <= 50) direction = 'N';
		else direction = 'S';
	}

	public void run()
	{
		int t = 0, completed;
		
		while(t < travels)
		{
			try {
				travel_to_bridge();
				monitor.dislocate(direction);
				++t;
				crossed();
				distantiate_from_bridge(t);
				change_direction();
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();		
			}
		}

		OneLaneBridge.set_completed((OneLaneBridge.get_completed()) + 1);
		if((OneLaneBridge.get_completed()) == OneLaneBridge.get_all_cars()) OneLaneBridge.set_finish(true);

		System.out.println("Car " + number + " has arrived at home!");
	}

	private void distantiate_from_bridge(int t)
	{
		if(t < travels - 1)
		{
			if(direction == 'N') System.out.println("Car " + number + " has finished his business at North. He must now return to South.");
			else System.out.println("Car " + number + " has finished his business at South. He must now return to North.");
		}
		else System.out.println("Car " + number + " has finished his business for today. He is now returning home.");
	}

	private void crossed() throws InterruptedException
	{
		if(direction == 'N') System.out.println("Car " + number + " has crossed the bridge. He will now do his business at North.");
		else System.out.println("Car " + number + " has crossed the bridge. He will now do his business at South.");
		Thread.sleep(10000);
	}

	private void arrive()
	{
		if(direction == 'N') System.out.println("Car " + number + " has arrived at the bridge. He wants to cross it to get to North.");
		else System.out.println("Car " + number + " has arrived at the bridge. He wants to cross it to get to South.");
	}

	private void change_direction()
	{
		if(direction == 'N') direction = 'S';
		else direction = 'N';
	}

	private void travel_to_bridge() throws InterruptedException
	{
		int max = 8000, min = 1000;
		if(direction == 'N') System.out.println("Car " + number + " is heading to the bridge. He is going North.");
		else System.out.println("Car " + number + " is heading to the bridge. He is going South.");
		Random rand = new Random();
		Thread.sleep(rand.nextInt((max - min) + 1) + min);
		arrive();
	}
}