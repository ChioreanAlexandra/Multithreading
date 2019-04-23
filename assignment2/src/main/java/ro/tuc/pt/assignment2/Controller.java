package ro.tuc.pt.assignment2;
import java.awt.event.*;
import java.util.*;

import gui.*;
public class Controller implements ActionListener
{
	public View vw;
	public Controller(View vw)
	{
		this.vw=vw;
	}
	public void actionPerformed(ActionEvent e)
	{
		int time=Integer.parseInt(vw.simulationTime.getText());
		int maxQueues=Integer.parseInt(vw.maxQueues.getText());
		int minServ=Integer.parseInt(vw.minServiceTime.getText());
		int maxServ=Integer.parseInt(vw.maxServiceTime.getText());
		int minArrival=Integer.parseInt(vw.minArriving.getText());
		int maxArrival=Integer.parseInt(vw.maxArriving.getText());
		int maxClients=Integer.parseInt(vw.maxClients.getText());
		Manager m=new Manager(time,maxQueues,minServ,maxServ,minArrival,maxArrival,maxClients);
		Thread th=new Thread(m);
		th.start();
		
	}

}
