package gui;

import java.awt.GridLayout;
import java.util.LinkedList;
import java.util.List;

import javax.swing.*;

import ro.tuc.pt.assignment2.*;
 
public class View  {
	public JTextField simulationTime;
	public JTextField maxQueues;
	public JTextField maxServiceTime;
	public JTextField minServiceTime;
	public JTextField maxArriving;
	public JTextField minArriving;
	public JTextField maxClients;
	private static JPanel p1;
	private static JFrame f1;
	public static JTextArea ta=new JTextArea();
	public View()
	{
		 f1=new JFrame("Shop");
		f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f1.setSize(700, 400);
		p1=new JPanel();
		SpringLayout layout=new SpringLayout();
		this.p1.setLayout(layout);
		JLabel l1=new JLabel("Simulation time:");
		simulationTime=new JTextField(5);
		JLabel l2=new JLabel("Number of queue:");
		maxQueues=new JTextField(5);
		JLabel l3=new JLabel("Maxim service time:");
		minServiceTime=new JTextField(5);
		JLabel l4=new JLabel("Minim service time:");
		maxArriving=new JTextField(5);
		JLabel l5=new JLabel("Minim arrival time:");
		minArriving=new JTextField(5);
		JLabel l6=new JLabel("Maxim arrival time:");
		maxServiceTime=new JTextField(5);
		JButton b1;
		b1=new JButton("Start");
		maxClients=new JTextField(5);
		JLabel l7=new JLabel("Maxim number of clients");
		
		p1.add(l1);
		p1.add(l2);
		p1.add(l3);
		p1.add(l4);
		p1.add(l5);
		p1.add(l6);
		p1.add(simulationTime);
		p1.add(maxQueues);
		p1.add(minServiceTime);
		p1.add(maxServiceTime);
		p1.add(maxArriving);
		p1.add(minArriving);
		p1.add(b1);		
		p1.add(l7);
		p1.add(maxClients);
		
		layout.putConstraint(SpringLayout.WEST, l1, 5, SpringLayout.WEST, p1);
		layout.putConstraint(SpringLayout.NORTH, l1, 5, SpringLayout.NORTH, p1);
		
		layout.putConstraint(SpringLayout.WEST, simulationTime, 25, SpringLayout.EAST, l1);
		layout.putConstraint(SpringLayout.NORTH, simulationTime, 5, SpringLayout.NORTH, p1);
		
		layout.putConstraint(SpringLayout.WEST, l2, 5, SpringLayout.WEST, p1);
		layout.putConstraint(SpringLayout.NORTH, l2, 25, SpringLayout.NORTH, l1);
		
		layout.putConstraint(SpringLayout.WEST, maxQueues, 25, SpringLayout.EAST, l2);
		layout.putConstraint(SpringLayout.NORTH, maxQueues, 25, SpringLayout.NORTH, simulationTime);
		
		layout.putConstraint(SpringLayout.WEST, l4, 5, SpringLayout.WEST, p1);
		layout.putConstraint(SpringLayout.NORTH, l4, 25, SpringLayout.NORTH, l2);
		
		layout.putConstraint(SpringLayout.WEST, minServiceTime, 25, SpringLayout.EAST, l4);
		layout.putConstraint(SpringLayout.NORTH, minServiceTime, 25, SpringLayout.NORTH, maxQueues);
		
		layout.putConstraint(SpringLayout.WEST, l3, 5, SpringLayout.WEST, p1);
		layout.putConstraint(SpringLayout.NORTH, l3, 25, SpringLayout.NORTH, l4);
		
		layout.putConstraint(SpringLayout.WEST, maxServiceTime, 25, SpringLayout.EAST, l3);
		layout.putConstraint(SpringLayout.NORTH, maxServiceTime, 25, SpringLayout.NORTH, minServiceTime);
		
		layout.putConstraint(SpringLayout.WEST, l5, 5, SpringLayout.WEST, p1);
		layout.putConstraint(SpringLayout.NORTH, l5, 25, SpringLayout.NORTH, l3);
		
		layout.putConstraint(SpringLayout.WEST, minArriving, 25, SpringLayout.EAST, l5);
		layout.putConstraint(SpringLayout.NORTH, minArriving, 25, SpringLayout.NORTH, maxServiceTime);
		
		layout.putConstraint(SpringLayout.WEST, l6, 5, SpringLayout.WEST, p1);
		layout.putConstraint(SpringLayout.NORTH, l6, 25, SpringLayout.NORTH, l5);
		
		layout.putConstraint(SpringLayout.WEST, maxArriving, 25, SpringLayout.EAST, l6);
		layout.putConstraint(SpringLayout.NORTH, maxArriving, 25, SpringLayout.NORTH, minArriving);
		
		layout.putConstraint(SpringLayout.WEST, l7, 5, SpringLayout.WEST, p1);
		layout.putConstraint(SpringLayout.NORTH, l7, 25, SpringLayout.NORTH, l6);
		
		layout.putConstraint(SpringLayout.WEST, maxClients, 25, SpringLayout.EAST, l7);
		layout.putConstraint(SpringLayout.NORTH, maxClients, 25, SpringLayout.NORTH, maxArriving);
		
		layout.putConstraint(SpringLayout.WEST, b1, 5, SpringLayout.WEST, p1);
		layout.putConstraint(SpringLayout.NORTH, b1, 50, SpringLayout.NORTH, l6);
		b1.addActionListener(new Controller(this));
		f1.add(p1);
		f1.setVisible(true);
	}
	public static void updateView(List<Server> list)
	{
		f1.remove(p1);
		p1=new JPanel();
		for(Server i:list)
		{
			int nr=-1;
			Client[] clients=new Client[i.getClients().size()];
			for(Client c:i.getClients())
			{
				nr++;
				clients[nr]=c;
			}
			if(nr!=-1)
			{
				JLabel l1=new JLabel("Queue"+i.index);
				JList<Client> l=new JList<Client>(clients);
				JScrollPane sp=new JScrollPane(l);
				p1.add(l1);
				p1.add(sp);
				
			}	
		}
		f1.add(p1);
		f1.setVisible(true);
	}
	public static void printStatistics(List<Server> l,int noClients,int peakHour)
	{
		f1.remove(p1);
		JPanel p3=new JPanel();
		p3.setLayout(new GridLayout(1,2));
		JPanel p2=new JPanel();
		SpringLayout layout=new SpringLayout();
		p2.setLayout(layout);
		JLabel l1= new JLabel("The peak hour was at "+peakHour+" with "+noClients+" clients");
		JLabel[] array=new JLabel[3*l.size()];
		int maxWaiting=0,maxServ=0,maxEmpty=0,maxNoClients=0;
		for(int i=0;i<l.size();i++)
		{
			maxWaiting+=l.get(i).status[0];
			maxServ+=l.get(i).status[1];
			maxEmpty+=l.get(i).status[2];
			maxNoClients+=l.get(i).status[3];
			float wt=(float)l.get(i).status[0]/l.get(i).status[3];
			float st=(float)l.get(i).status[1]/l.get(i).status[3];
			float et=(float)l.get(i).status[2]/l.get(i).status[3];
			array[i*3]=new JLabel("Queue"+i+1+"avg waiting time:"+wt);
			p2.add(array[i*3]);
			if(i==0)
			{
				layout.putConstraint(SpringLayout.WEST, array[0], 5, SpringLayout.WEST, p2);
				layout.putConstraint(SpringLayout.NORTH, array[0], 5, SpringLayout.NORTH, p2);
			}
			else
			{
				layout.putConstraint(SpringLayout.WEST, array[i*3], 5, SpringLayout.WEST, p2);
				layout.putConstraint(SpringLayout.NORTH, array[i*3], 25, SpringLayout.NORTH, array[i*3-1]);
			}
			
			array[i*3+1]=new JLabel("Queue"+i+1+"avg service time:"+st);
			p2.add(array[i*3+1]);
			layout.putConstraint(SpringLayout.WEST, array[i*3+1], 5, SpringLayout.WEST, p2);
			layout.putConstraint(SpringLayout.NORTH, array[i*3+1], 25, SpringLayout.NORTH,array[i*3] );
			array[i*3+2]=new JLabel("Queue"+i+1+"avg empty time:"+et);
			p2.add(array[i*3+2]);
			layout.putConstraint(SpringLayout.WEST, array[i*3+2], 5, SpringLayout.WEST, p2);
			layout.putConstraint(SpringLayout.NORTH, array[i*3+2], 25, SpringLayout.NORTH,array[i*3+1] );
			
		}
		p2.add(l1);
		layout.putConstraint(SpringLayout.WEST, l1, 5, SpringLayout.WEST, p2);
		layout.putConstraint(SpringLayout.NORTH, l1, 25, SpringLayout.NORTH, array[3*l.size()-1]);
		JLabel finalresult=new JLabel("The avg WT:"+(float)maxWaiting/maxNoClients+" ST:"+(float)maxServ/maxNoClients+" ET:"+(float)maxEmpty/maxNoClients);
		p2.add(finalresult);
		layout.putConstraint(SpringLayout.WEST, finalresult, 5, SpringLayout.WEST, p2);
		layout.putConstraint(SpringLayout.NORTH, finalresult, 25, SpringLayout.NORTH, l1);
		p3.add(p2);
		JScrollPane sp=new JScrollPane(ta);
		p3.add(sp);
		f1.add(p3);
		f1.setVisible(true);
	}
}
