package com.goldentwo;

public class Chronometer implements Runnable
{
	private long last;
	private Monitor monitor;
	private long begin;
	private long end;
	
	public Chronometer(Monitor monitor)
	{
		last = -1;
		this.monitor = monitor;
		begin = System.currentTimeMillis();
	}

	public void run()
	{
		while(!OneLaneBridge.get_finish())
		{
			get_time();
		}
	}

	public void get_time()
	{
		long s = 0;

		double time = define_time();
		s = (long) (time / 1000.0);
		
		if(s % 2 == 0 && s != last) 
		{
			last = s; 
//			if(monitor.get_direction() == 'S') monitor.change_direction_to_north();
//			else monitor.change_direction_to_south();
			System.out.println(s +" seconds ("+monitor.get_direction()+")");
		}

	}
	
	private long define_time() 
	{
		return ((end = System.currentTimeMillis()) - begin);
	}
}