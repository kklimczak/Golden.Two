package com.goldentwo;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Monitor
{
	private char direction;
	private int counter = 0;
	private final Lock lock;
	private final Condition north;
	private final Condition south;

	public Monitor(char direction)
	{
		this.direction = direction;

		lock = new ReentrantLock(true);
		north = lock.newCondition();
		south = lock.newCondition();
	}

	public void dislocate(char car_direction) throws InterruptedException
	{
		if(car_direction != direction) {
			lock.lock();
			try {
				
				if(car_direction == 'S') while(direction == 'N') south.await();
				else while(direction == 'S') north.await();

			} finally {
				lock.unlock();
			}
		} else {
			counter++;
			if (counter == 10) {
				if (direction == 'N') {
					change_direction_to_south();
				} else {
					change_direction_to_north();
				}
				counter = 0;
			}
		}
	}

	public void change_direction_to_north()
	{
		lock.lock();
		try {
			direction = 'N';
			north.signal();
		}
		finally{
			lock.unlock();
		}
	}

	public void change_direction_to_south()
	{
		lock.lock();
		try {
			direction = 'S';
			south.signal();
		}
		finally{
			lock.unlock();
		}
	}

	public char get_direction() { return direction; }
}