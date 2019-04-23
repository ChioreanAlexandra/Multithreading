package ro.tuc.pt.assignment2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.Random;

import gui.View;

public class Manager implements Runnable{
	public int time;
	public int maxQueues;
	public int minServ;
	public int maxServ;
	public int minArrival;
	public int maxArrival;
	private static QueuesScheduler scheduler;
	private List<Client> generatedClients;
	public static int currentTime;
	
	
	public Manager(int time,int maxQueues,int minServ,int maxServ,int minArrival,int maxArrival,int maxClients)
	{
		scheduler=new QueuesScheduler(maxQueues,maxClients);
		this.time=time;
		this.maxQueues=maxQueues;
		this.minServ=minServ;
		this.maxServ=maxServ;
		this.minArrival=minArrival;
		this.maxArrival=maxArrival;
		currentTime=time;
	}
	public void generateRandomTasks(int arrivelTime)
	{
		generatedClients=new LinkedList<Client>();
		Random r=new Random();
		int n=Math.abs(r.nextInt()%(this.maxArrival-this.minArrival+1))+this.minArrival;
		for(int i=0;i<n;i++)
		{
			int id=Math.abs(r.nextInt()%500);
			int processingTime=Math.abs(r.nextInt()%(maxServ-minServ+1))+this.minServ;
			Client c=new Client(id,arrivelTime,processingTime);
			generatedClients.add(c);
		}
		//Collections.sort(generatedClients);
	}
	public static void addLogInfo(String s)
	{
		View.ta.append(s);
		System.out.print(s);
	}
	public static synchronized void print()
	{
		View.updateView(scheduler.list);
	}
	public synchronized int nrClients()
	{
		int count=0;
		for(Server q:scheduler.list)
		{
			count+=q.getClients().size();
			if(q.getClients().size()==0)
			{
				q.status[2]++;
			}
		}
		return count;
	}
	public boolean testEmptiness()
	{
		for(Server q:scheduler.list)
		{
			if(q.getClients().size()!=0)
				return false;
		}
		return true;
	}
	public void run()
	{
		int runningTime=0,peakHour=0,maxNoClients=0;
		while (runningTime<time)
		{
			generateRandomTasks(runningTime);
			//System.out.println("Time: "+runningTime);
			for(Client c:generatedClients)
			{
				int nrQueue=scheduler.dispatchClient(c);
				View.updateView(scheduler.list);
				try{
					long f=1000;
					Thread.sleep(f);
				}
				catch(Exception e)
				{
					System.out.println("Error run Manager");
				}
				String s="Added: "+c.toString()+"in queue "+nrQueue+"\n";
				addLogInfo(s);
			}
			int maxLocal=nrClients();
			if(maxNoClients<maxLocal)
			{
				maxNoClients=maxLocal;
				peakHour=runningTime;
			}
			runningTime++;
			currentTime--;
		}
		long timp=this.maxServ*1000;
		try {
			Thread.sleep(timp);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		boolean ok=true;
		while(ok)
		{
			if(testEmptiness())
			{
				ok=false;
			}
		}
		Thread.currentThread().interrupt();
		View.printStatistics(scheduler.list,maxNoClients,peakHour);
		
	}
	
}
