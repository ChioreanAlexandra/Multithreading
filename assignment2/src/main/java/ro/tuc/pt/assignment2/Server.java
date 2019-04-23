 package ro.tuc.pt.assignment2;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

import gui.View;

public class Server implements Runnable{
	private BlockingQueue<Client> clients;
	public AtomicInteger waitingPeriod;
	public AtomicInteger timer;
	public int index;
	public int[] status;
	public Server(int index)
	{
		this.clients=new LinkedBlockingQueue<Client>();
		this.waitingPeriod=new AtomicInteger(0);
		this.index=index;
		status=new int[4];
		timer=new AtomicInteger(0);
	}
	
	public synchronized void enqueueClient(Client c)
	{
		this.clients.add(c);
		this.waitingPeriod.addAndGet(c.processTime);
		this.status[0]+=c.processTime;
		this.status[3]++;
		if(timer.get()==0)
		{
			timer.set(c.arrivelTime);
		}
		
	}
	public void listClients()
	{
		System.out.println("Queue"+this.index+":");
		for(Client c:clients)
		{
			System.out.println(c.toString());
		}
	}
	public BlockingQueue<Client> getClients()
	{
		return this.clients;
	}
	public void setClients(BlockingQueue<Client> list)
	{
		this.clients=list;
	}
	public void run()
	{
		String s;
		boolean ok=true;
		while(ok)
		{
			try
			{
				if(!this.clients.isEmpty())
				{
					Client c=clients.peek();
					this.waitingPeriod.set(this.waitingPeriod.get()-c.processTime);;
					long sleepingTime=1000*c.processTime;
					Thread.sleep(sleepingTime);
					clients.take();
					timer.addAndGet(c.processTime);
					if(Manager.currentTime!=0)
					{
						this.status[1]+=c.processTime;
						s="Client "+c.getId()+" left the queue: "+index+" at "+timer.get()+"\n";
					}
					else
						s="Client "+c.getId()+" left the queue: "+index+"\n";
					
					Manager.addLogInfo(s);
					if(Manager.currentTime==0)
					{
						Manager.print();
						
					}
				}
				if(Manager.currentTime==0 && this.clients.isEmpty())
				{
					Manager.print();
					ok=false;
				}
				
			}
			catch(Exception e)
			{
				e.getMessage();
			}
			
		}
		Thread.currentThread().interrupt();;
		System.out.println(Thread.currentThread().isInterrupted());
		
	}
}
