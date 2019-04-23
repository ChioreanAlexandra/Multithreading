package ro.tuc.pt.assignment2;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class QueuesScheduler {
	public List<Server> list;
	public int noQueues;
	public int maxClientsOnQueue;
	
	public QueuesScheduler(int noQueues,int maxClientsOnQueue)
	{
		this.noQueues=noQueues;
		this.maxClientsOnQueue=maxClientsOnQueue;
		list=new LinkedList<Server>();
		for(int i=0;i<this.noQueues;i++)
		{
			Server q=new Server(i+1);
			list.add(q);
			Thread t=new Thread(q);
			t.start();
		}
	}
	public synchronized BlockingQueue<Client> redistribute(int nrQueues)
	{
		BlockingQueue<Client> dest=new LinkedBlockingQueue<Client>();
		for(Server p:list)
		{
			int size=p.getClients().size();
			//System.out.println("Size of list "+size);
			if(size!=0 && size==this.maxClientsOnQueue)
			{
				Object[] c=p.getClients().toArray();
				for(int i=size-1;i>size/nrQueues;i--)
				{
					Client cl=new Client();
					if(cl instanceof Object)
					{
						cl=(Client)c[i];
						if(dest.size()<=this.maxClientsOnQueue)
						{
							dest.add(cl);
							BlockingQueue<Client> coada=p.getClients();
							coada.remove(cl);
							p.setClients(coada);
						}
							
					}
					
				}
			}
		}
		return dest;
	}
	public int dispatchClient(Client c)
	{
		int min=this.maxClientsOnQueue;
		int ok=0,nrOfOpenQueues=0;
		Server queueIndex=new Server(0);
		for(Server q:list)
		{
			int sizeOfQueue=q.getClients().size();
			if(sizeOfQueue!=0)
			{
				nrOfOpenQueues++;
				ok=1;
			}
			if(sizeOfQueue!=0 && sizeOfQueue<min) //exista coada care mai poate primi clienti si nu e o coada goala(noua)
			{
				min=sizeOfQueue;
				queueIndex=q;
			}
		}
		if(ok==1 && min!=this.maxClientsOnQueue)
		{
			queueIndex.enqueueClient(c);
			return queueIndex.index;
		}
		if(ok==1 && min==this.maxClientsOnQueue) //nu se gaseste nicio o coada avand conditia de mai sus-> deschide coada noua
		{
			int i=0;
			while(i<noQueues && list.get(i).getClients().size()!=0)
			{
				i++;
			}
			if(i!=noQueues)
			{
				nrOfOpenQueues++;
				list.get(i).setClients(redistribute(nrOfOpenQueues));
				list.get(i).enqueueClient(c);
				return i+1;
			}
			else
			{
				Random r=new Random();
				int choseQueue=Math.abs(r.nextInt()%nrOfOpenQueues);
				list.get(choseQueue).enqueueClient(c);
				return choseQueue+1;
			}
			
		}
		if(ok==0) 
		{
			list.get(0).enqueueClient(c);
			return 1;
		}
		return 0;
	}
}
