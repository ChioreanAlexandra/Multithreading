package ro.tuc.pt.assignment2;

import java.util.Comparator;

public class Client implements Comparable<Client> {
	private int id; 
	public int arrivelTime;
	public int processTime;
	
	public Client()
	{
		super();
	}
	public Client(int id,int arrivelTime, int processTime)
	{
		this.id=id;
		this.arrivelTime=arrivelTime;
		this.processTime=processTime;
	}
	public int getId()
	{
		return this.id;
	}
	public void setId(int id)
	{
		this.id=id;
	}
	public String toString()
	{
		return "Id:"+this.id+" AT:" +this.arrivelTime+" PT:"+this.processTime;
	}
	public int compareTo(Client a)
	{
		return a.arrivelTime-this.arrivelTime;
	}
	
}
