package it.polimi.deepse.dagsymb.examples;


import java.io.Serializable;

public class Call implements Serializable
{
	private static final long serialVersionUID = -2685443218382696366L;

	private String callerId;
	private String receiverId;
	private int length;
	private double cost;
	
	public Call(String callerId, String receiverId, int length, double cost){
		this.callerId = callerId;
		this.receiverId = receiverId;
		this.length = length;
		this.cost = cost;
	}
	
	public String getCallerId()
	{
		return callerId;
	}

	public String getReceiverId()
	{
		return receiverId;
	}
	
	public int getLength()
	{
		return length;
	}
	
	public double getCost(){
		return cost;
	}
	
	public Call setCost(double cost){
		this.cost = cost;
		return this;
	}



}
