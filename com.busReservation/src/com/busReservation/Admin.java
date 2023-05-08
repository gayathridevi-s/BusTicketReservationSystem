package com.busReservation;



public class Admin {
	int busID;
	String busType;
	int totalSeats;
	int fare;
	public static void addBus(String busID,String busType,int totalSeats,int availableSeats,int fare) throws Exception {
		AdminDAO obj=new AdminDAO();
		obj.addBusData(busID,busType,totalSeats,availableSeats,fare);
	}
	public static void main(String[] args) throws Exception {
		addBus("A","AC sleeper",20,20,700);
		addBus("B","AC Seater",20,20,550);
		addBus("C","Non-AC sleeper",20,20,600);
		addBus("D","Non-AC seater",20,20,450);
		AdminDAO obj=new AdminDAO();
		obj.addBusAdata();
		obj.addBusBdata();
		obj.addBusCdata();
		obj.addBusDdata();
		viewTickets("A");
	}
	public static void viewTickets(String busID) throws Exception {
		BusDAO newObj=new BusDAO();
		
		if (busID.equals("A")) {
			newObj.getAvailableSeatsA();
		} else if (busID.equals("B")) {
			newObj.getAvailableSeatsB();
		} else if (busID.equals("C")) {
			newObj.getAvailableSeatsC();
		} else if (busID.equals("D")) {
			newObj.getAvailableSeatsD();
		} else {
			System.out.println("invalid ID");
	
		}
	}
	
		
	}

