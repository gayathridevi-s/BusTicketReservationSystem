package com.busReservation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Random;
public class BusDAO {

public void getAvailableSeatsA() throws Exception {
	Connection conn=DBconnection.getConnection();
    Statement stmt = conn.createStatement();
    String query="SELECT seat,bookStatus,gender FROM busA ;";
    
    ResultSet res = stmt.executeQuery(query);
    System.out.println("seat\tStatus gender");
    while(res.next()) {
    	String seat=res.getString("seat");
    	String bookStatus=res.getString("bookStatus");
    	String gender=res.getString("gender");
    	System.out.println(seat+"\t"+bookStatus+"\t"+gender);
    }
    res.close();
    stmt.close();
    conn.close();
   
    		
}
public void getAvailableSeatsB() throws Exception {
	Connection conn=DBconnection.getConnection();
    Statement stmt = conn.createStatement();
    String query="SELECT seat,bookStatus,gender FROM busB;";
    
    
    ResultSet res = stmt.executeQuery(query);
    System.out.println("seat\tStatus gender");
    while(res.next()) {
    	String seat=res.getString("seat");
    	String bookStatus=res.getString("bookStatus");
    	String gender=res.getString("gender");
    	System.out.println(seat+"\t"+bookStatus+"\t"+gender);
    }
    res.close();
    stmt.close();
    conn.close();
   
    		
}
public void getAvailableSeatsC() throws Exception {
	Connection conn=DBconnection.getConnection();
    Statement stmt = conn.createStatement();
    String query="SELECT seat,bookStatus,gender FROM busC;";
    
    ResultSet res = stmt.executeQuery(query);
    System.out.println("seat\tStatus gender");
    while(res.next()) {
    	String seat=res.getString("seat");
    	String bookStatus=res.getString("bookStatus");
    	String gender=res.getString("gender");
    	System.out.println(seat+"\t"+bookStatus+"\t"+gender);
    }
    res.close();
    stmt.close();
    conn.close();
   
    		
}
public void getAvailableSeatsD() throws Exception {
	Connection conn=DBconnection.getConnection();
    Statement stmt = conn.createStatement();
    String query="SELECT seat,bookStatus,gender FROM busD;";
    
    ResultSet res = stmt.executeQuery(query);
    System.out.println("seat\tStatus gender");
    while(res.next()) {
    	String seat=res.getString("seat");
    	String bookStatus=res.getString("bookStatus");
    	String gender=res.getString("gender");
    	System.out.println(seat+"\t"+bookStatus+"\t"+gender);
    }
    res.close();
    stmt.close();
    conn.close();
   
    		
}

public void updateSeatsData(String bus,String seat,int custID,String passengerName,String gender,int price) throws Exception {
	Connection con=DBconnection.getConnection();
	String updatedData="update "+  "bus"+bus+ " set bookStatus='booked',passengerName=?,gender=? ,custID=?,price=? where seat=?;";
	PreparedStatement pst=con.prepareStatement(updatedData);
	 
	pst.setString(1,passengerName);
	pst.setString(2, gender);
	pst.setInt(3, custID);
	pst.setInt(4, price);
	pst.setString(5, seat);
	int dataUpdate=pst.executeUpdate();
//
	String seatsUpdate="update bus set availableSeats=? where busID=? ;";
	int seatAvailable=availableSeats(bus);
	PreparedStatement pst1=con.prepareStatement(seatsUpdate);
	pst1.setInt(1, seatAvailable);
	pst1.setString(2, bus);
	int update=pst1.executeUpdate();
	//System.out.println(update);
	
	 String seatsFilledQuery = "update bus set FilledSeat=? where busID=? ;";
	    int filledSeats= FilledSeats(bus);
	    PreparedStatement pst5 = con.prepareStatement(seatsFilledQuery);
	    pst5.setInt(1, filledSeats);
	    pst5.setString(2, bus);
	    int updates = pst5.executeUpdate();
	pst5.close();
	pst.close();
	con.close();
}
public void displayBusList() throws Exception {
	Connection conn=DBconnection.getConnection();
	String query="select * from bus;";
	Statement st=conn.createStatement();
	ResultSet rs=st.executeQuery(query);
	System.out.println("BusID\tBusType\t        totalSeats\tAvailableSeats\tFare");
	while(rs.next()) {
		String busID=rs.getString(1);
		String busType=rs.getString(2);
		int totalSeats=rs.getInt(3);
		int availableSeats=rs.getInt(4);
		int fare=rs.getInt(5);
		System.out.println(busID+"\t"+busType+"\t"+totalSeats+"\t\t"+availableSeats+"\t\t"+fare);
		
	}
	rs.close();
	st.close();
	conn.close();
}
public int totalCount(String busID) throws Exception {
	Connection conn=DBconnection.getConnection();
    PreparedStatement stmt = conn.prepareStatement("SELECT totalSeats FROM bus WHERE busID=?;");
    stmt.setString(1, busID);
    ResultSet res = stmt.executeQuery();
    res.next();
    int count=res.getInt("totalSeats");
    res.close();
    stmt.close();
    conn.close();
    return count;
    
}

public int availableSeats(String busID) throws Exception {
	Connection conn=DBconnection.getConnection();
	String query="select count(bookStatus) from bus"+busID;
	Statement stmt = conn.createStatement();
	ResultSet rs=stmt.executeQuery(query);
	rs.next();
	int count=rs.getInt(1);
	int totalSeats=totalCount(busID);
	int availableSeats=totalSeats-count;
	rs.close();
	stmt.close();
	conn.close();
	return availableSeats;
    
}
public int FilledSeats(String busID) throws Exception {
	Connection conn=DBconnection.getConnection();
	String query="select count(bookStatus) from bus"+busID;
	Statement stmt = conn.createStatement();
	ResultSet rs=stmt.executeQuery(query);
	rs.next();
	int count=rs.getInt(1);
	rs.close();
	stmt.close();
	conn.close();
	return count;
}
public int ticketFare(String busID) throws Exception {
	Connection con=DBconnection.getConnection();
    PreparedStatement stmt = con.prepareStatement("SELECT fare FROM  bus WHERE busID =?;");
    stmt.setString(1, busID);
   
    ResultSet rs = stmt.executeQuery();
    rs.next();
    int ticketPrice= rs.getInt("fare");
   rs.close();
    stmt.close();
    con.close();
    return ticketPrice;
    
}
public ArrayList<String> displayTicketsBooked(String busID, int custID) throws Exception {
    Connection conn = DBconnection.getConnection();
    PreparedStatement stmt = conn.prepareStatement("SELECT seat FROM bus" + busID + " WHERE custID = ?");
    stmt.setInt(1, custID);
    ResultSet res = stmt.executeQuery();

    ArrayList<String> bookedSeats = new ArrayList<>();
    while (res.next()) {
        String seat = res.getString("seat");
        bookedSeats.add(seat);
    }

    res.close();
    stmt.close();
    conn.close();

    return bookedSeats;
}

public void allSeatsCancellation(String busID,int custID,ArrayList<String>bookedSeats) throws Exception {
	Connection conn=DBconnection.getConnection();
	 double cancellationFee = 0;
	 for (String seats : bookedSeats) {
	        // Get the current cancellation fee
	        String query = "SELECT cancellationFee FROM bus" + busID + " WHERE seat = ?;";
	        PreparedStatement stmt = conn.prepareStatement(query);
	        
	        stmt.setString(1, seats);
	     
	        ResultSet rs = stmt.executeQuery();
	        

	        if (rs.next()) {
	            cancellationFee = rs.getDouble("cancellationFee");
	           
	        }else{
	        	cancellationFee=0;
	        }
	        if (busID.equalsIgnoreCase("A") || busID.equalsIgnoreCase("B")) {
	            double newCancellationFee = 0.5 * ticketFare(busID);
	            cancellationFee += newCancellationFee;
	            
	        } else {
	            double newCancellationFee = 0.25 * ticketFare(busID);
	            cancellationFee += newCancellationFee;
	        }
	       
	        String querys="UPDATE bus"+busID+" SET bookStatus=NULL, Gender=NULL, custID=NULL, passengerName=NULL, price=NULL, cancellationFee=? WHERE seat =? AND bookStatus='booked'";

	     
	        PreparedStatement stmt1 = conn.prepareStatement(querys);
	        
	        stmt1.setDouble(1, cancellationFee);
	        stmt1.setString(2, seats);
	       
	        int affectedRows = stmt1.executeUpdate();
	      
	        if (affectedRows > 0) {
	            System.out.println("seat" + seats + " is cancelled!");
	        } else {
	            System.out.println("Unable to cancel the ticket" + seats);
	        }

	        stmt1.close();
	        rs.close();
	        stmt.close();
	    }

	
	 String seatsUpdate="update bus set availableSeats=? where busID=? ;";
		int seatAvailable=availableSeats(busID);
		PreparedStatement pst1=conn.prepareStatement(seatsUpdate);
		pst1.setInt(1, seatAvailable);
		pst1.setString(2, busID);
		
		int update=pst1.executeUpdate();
		
		
		 String seatsFilledQuery = "update bus set Filledseat=? where busID=? ;";
		    int filledSeats= FilledSeats(busID);
		    PreparedStatement pst5 = conn.prepareStatement(seatsFilledQuery);
		    pst5.setInt(1, filledSeats);
		    pst5.setString(2, busID);
		    int updates = pst5.executeUpdate();
		
		String cancellationQuery="select sum(cancellationFee) as cancellationPrice from bus"+busID;
		Statement s1=conn.createStatement();
		ResultSet rs1=s1.executeQuery(cancellationQuery);
		
		rs1.next();
		Double cancellationPrice=rs1.getDouble("cancellationPrice");
		
		String totalpriceQuery="select sum(price) as totalPrice from bus"+busID;
		Statement ps2=conn.createStatement();
		
		
		ResultSet rs2=ps2.executeQuery(totalpriceQuery);
		rs2.next();
		int totalPrice;
		if(rs2.wasNull()) {
			totalPrice=0;
		}else {
			totalPrice=rs2.getInt("totalPrice");
		}
		
		
		double collectedFare=totalPrice+cancellationPrice;
		String busQuery="update bus set collectedFare=? where busID=?";
		PreparedStatement ps3=conn.prepareStatement(busQuery);
		ps3.setDouble(1, collectedFare);
		ps3.setString(2, busID);
		
		int update1=ps3.executeUpdate();
	pst5.close();	
	ps3.close();
	rs2.close();
	rs1.close();
	s1.close();
	ps2.close();
	
	
	conn.close();
}
public void selectedSeatsCancellation(String busID, int custID) throws Exception {
    Connection conn = DBconnection.getConnection();
    List<String> seatsToDelete = new ArrayList<>();
    Scanner scanner = new Scanner(System.in);
    double cancellationFee = 0;
    System.out.print("Enter the number of seats to delete: ");
    int numSeats = scanner.nextInt();

    for (int i = 1; i <= numSeats; i++) {
        System.out.print("Enter the seat number to delete: ");
        String seatNumber = scanner.next();
        seatsToDelete.add( seatNumber );
    }
    for (String seats : seatsToDelete) {
        // Get the current cancellation fee
        String query = "SELECT cancellationFee FROM bus" + busID + " WHERE seat = ?;";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, seats);
        ResultSet rs = stmt.executeQuery();
        

        if (rs.next()) {
            cancellationFee = rs.getDouble("cancellationFee");
           
        }else{
        	cancellationFee=0;
        }
        if (busID.equalsIgnoreCase("A") || busID.equalsIgnoreCase("B")) {
            double newCancellationFee = 0.5 * ticketFare(busID);
            cancellationFee += newCancellationFee;
            //System.out.println(cancellationFee);
        } else {
            double newCancellationFee = 0.25 * ticketFare(busID);
            cancellationFee += newCancellationFee;
        }
       
        String querys="UPDATE bus"+busID+" SET bookStatus=NULL, Gender=NULL, custID=NULL, passengerName=NULL, price=NULL, cancellationFee=? WHERE seat =? AND bookStatus='booked'";

       
        PreparedStatement stmt1 = conn.prepareStatement(querys);
        
        stmt1.setDouble(1, cancellationFee);
        stmt1.setString(2, seats);
      
        int affectedRows = stmt1.executeUpdate();
      
        if (affectedRows > 0) {
            System.out.println("seat" + seats + " is cancelled!");
        } else {
            System.out.println("Unable to cancel the ticket" + seats);
        }

        stmt1.close();
        rs.close();
        stmt.close();
    }

    String seatsUpdate = "update bus set availableSeats=? where busID=? ;";
    int seatAvailable = availableSeats(busID);
    PreparedStatement pst1 = conn.prepareStatement(seatsUpdate);
    pst1.setInt(1, seatAvailable);
    pst1.setString(2, busID);
    int update = pst1.executeUpdate();
    
    String seatsFilledQuery = "update bus set Filledseat=? where busID=? ;";
    int filledSeats= FilledSeats(busID);
    PreparedStatement pst5 = conn.prepareStatement(seatsFilledQuery);
    pst5.setInt(1, filledSeats);
    pst5.setString(2, busID);
    int updates = pst5.executeUpdate();

    String cancellationQuery = "select sum(cancellationFee) as cancellationPrice from bus" + busID;
    Statement s1 = conn.createStatement();
    ResultSet rs1 = s1.executeQuery(cancellationQuery);
    rs1.next();
    Double cancellationPrice = rs1.getDouble("cancellationPrice");

    String totalpriceQuery = "select sum(price) as totalPrice from bus" + busID + " where custID=?";
    PreparedStatement ps2 = conn.prepareStatement(totalpriceQuery);
    ps2.setInt(1, custID);
    ResultSet rs2 = ps2.executeQuery();
    rs2.next();
    int totalPrice = rs2.getInt("totalPrice");
    double collectedFare = totalPrice + cancellationPrice;
    String busQuery = "update bus set collectedFare=? where busID=?";
    PreparedStatement ps3 = conn.prepareStatement(busQuery);
    ps3.setDouble(1, collectedFare);
    ps3.setString(2, busID);
    int update1 = ps3.executeUpdate();
    pst5.close();
    ps3.close();
    rs2.close();
    ps2.close();
    rs1.close();
    pst1.close();

    conn.close();
}

	public boolean checkAdjacentSeatAvailability(String busID, String seatNumber) throws Exception {
	    Connection conn = DBconnection.getConnection();
	    String query;
	    int adjacentSeatNumber;
	    char busType = busID.charAt(0);
	    // Check if seat number is odd or even
	    if (Integer.parseInt(seatNumber.substring(1)) % 2 == 1) { // odd seat number
	        adjacentSeatNumber = Integer.parseInt(seatNumber.substring(1)) + 1;
	        query = "SELECT bookStatus FROM bus" + busID + " WHERE seat = '"+busType +adjacentSeatNumber + "'";
	    } else { // even seat number
	        adjacentSeatNumber = Integer.parseInt(seatNumber.substring(1)) - 1;
	        query = "SELECT bookStatus FROM bus" + busID + " WHERE seat = '" +busType+ adjacentSeatNumber + "'";
	    }

	    PreparedStatement stmt = conn.prepareStatement(query);
	    ResultSet rs = stmt.executeQuery();

	    if (rs.next()) { // adjacent seat exists in database
	        String status = rs.getString("bookStatus");
	        if (status == null) { // adjacent seat is available
	        	System.out.println("adjacent seat of "+seatNumber+" is vacant");
	            return true;
	        }
	    }
	    rs.close();
	    stmt.close();
	    conn.close();
	    // adjacent seat is booked or does not exist
	    System.out.println("adjacent seat of "+seatNumber+ " is booked");
	    return false;
	    
	}
	
	public void busSummary() throws Exception {
		 Connection conn = DBconnection.getConnection();
		 	Scanner scanner=new Scanner(System.in);
			String query="select * from bus;";
			Statement st=conn.createStatement();
			ResultSet rs=st.executeQuery(query);
			System.out.println("BusID\tBusType\t\tcollectedFare\tFilledSeat");
			while(rs.next()) {
				String busID=rs.getString(1);
				String busType=rs.getString(2);
				//int totalSeats=rs.getInt(3);
				//int availableSeats=rs.getInt(4);
				//int fare=rs.getInt(5);
				double collectedFare=rs.getDouble(6);
				int FilledSeat=rs.getInt(7);
				System.out.println(busID+"\t"+busType+"\t"+collectedFare+"\t\t"+FilledSeat);
				
			}
			System.out.println("Enter the busID to view seat details");
			 String busID=scanner.next();
			 String newQuery="select * from bus"+busID+" where bookStatus='booked'";
			 Statement st1=conn.createStatement();
			 ResultSet rs1=st1.executeQuery(newQuery);
			 System.out.println("Seat\tPassengerName\tGender\tCustomerID");
			 while(rs1.next()) {
				 String seat=rs1.getString(1);
				 String name=rs1.getString(3);
				 String gender=rs1.getString(4);
				 int custID =rs1.getInt(5);
				 System.out.println(seat+"\t"+name+"\t\t"+gender+"\t"+custID);
			 }
			rs1.close();
			st1.close();
			rs.close();
			st.close();
			conn.close();
		 
	}
	public boolean checkFemaleSeatAvailability(String busID, String seatNumber) throws Exception {
	    Connection conn = DBconnection.getConnection();
	    String query;
	    int adjacentSeatNumber;
	    char busType = busID.charAt(0);
	    // Check if seat number is odd or even
	    if (Integer.parseInt(seatNumber.substring(1)) % 2 == 1) { // odd seat number
	        adjacentSeatNumber = Integer.parseInt(seatNumber.substring(1)) + 1;
	        query = "SELECT Gender FROM bus" + busID + " WHERE seat = '"+busType +adjacentSeatNumber + "'";
	    } else { // even seat number
	        adjacentSeatNumber = Integer.parseInt(seatNumber.substring(1)) - 1;
	        query = "SELECT Gender FROM bus" + busID + " WHERE seat = '" +busType+ adjacentSeatNumber + "'";
	    }

	    PreparedStatement stmt = conn.prepareStatement(query);
	   
	    ResultSet rs = stmt.executeQuery();

	    if (rs.next()) { // adjacent seat exists in database
	        String gender = rs.getString("Gender");
	        if (gender != null && gender.equalsIgnoreCase("female")) { // adjacent seat is booked by female
	        	System.out.println(seatNumber+" is a ladies seat");
	            return false;
	        }
	    }
	    rs.close();
	    stmt.close();
	    conn.close();
	    // adjacent seat is not booked by female or does not exist
	    System.out.println(seatNumber+" can be booked by male or female");
	    return true;
	}

public void ACFiltering() throws Exception {
	Connection conn=DBconnection.getConnection();
	String query="SELECT busID,busType,availableSeats FROM bus WHERE busType LIKE 'AC%' ORDER BY availableSeats DESC, CASE WHEN busType LIKE '%Sleeper%' THEN 0 ELSE 1 END";
	Statement stmt=conn.createStatement();
	ResultSet res = stmt.executeQuery(query);
	System.out.println("busID\tbusType\tavailableSeats");
	while(res.next()) {
		String busID=res.getString(1);
		String busType=res.getString(2);
		
		int availableSeats=res.getInt(3);
		
		System.out.println(busID+"\t"+busType+"\t"+availableSeats);
}	res.close();
	stmt.close();
	conn.close();
}
	public void NonACFiltering() throws Exception {
		Connection conn=DBconnection.getConnection();
		String query="SELECT busID,busType,availableSeats FROM bus WHERE busType LIKE 'Non-AC%' ORDER BY availableSeats DESC, CASE WHEN busType LIKE '%Sleeper%' THEN 0 ELSE 1 END";
		Statement stmt=conn.createStatement();
		ResultSet res = stmt.executeQuery(query);
		System.out.println("busID\tbusType\tavailableSeats");
		while(res.next()) {
			String busID=res.getString(1);
			String busType=res.getString(2);
			int availableSeats=res.getInt(3);
			
			System.out.println(busID+"\t"+busType+"\t"+availableSeats);
	}	res.close();
		stmt.close();
		conn.close();
}
public void sleeperFiltering() throws Exception	{
	Connection conn=DBconnection.getConnection();
	String query="SELECT busID,busType,availableSeats FROM bus WHERE busType LIKE '%Sleeper%' ORDER BY availableSeats DESC, CASE WHEN busType LIKE 'AC%' THEN 0 ELSE 1 END";
	Statement stmt=conn.createStatement();
	ResultSet res = stmt.executeQuery(query);
	System.out.println("busID\tavailableSeats");
	while(res.next()) {
		String busID=res.getString(1);
		String busType=res.getString(2);
		
		int availableSeats=res.getInt(3);
		
		System.out.println(busID+"\t"+busType+"\t"+availableSeats);
}	res.close();
	stmt.close();
	conn.close();
}
public void seaterFiltering() throws Exception	{
	Connection conn=DBconnection.getConnection();
	String query="SELECT busID,busType,availableSeats FROM bus WHERE busType LIKE '%seater%' ORDER BY availableSeats DESC, CASE WHEN busType LIKE 'AC%' THEN 0 ELSE 1 END";
	Statement stmt=conn.createStatement();
	ResultSet res = stmt.executeQuery(query);
	System.out.println("busID\tbusType\tavailableSeats");
	while(res.next()) {
		String busID=res.getString(1);
		String busType=res.getString(2);
		
		int availableSeats=res.getInt(3);
		
		System.out.println(busID+"\t"+busType+"\t"+availableSeats);
}	res.close();
	stmt.close();
	conn.close();
}
public void BothType() throws Exception	{
	Connection conn=DBconnection.getConnection();
	String query="SELECT busID,busType,availableSeats FROM bus  ORDER BY availableSeats DESC";
	Statement stmt=conn.createStatement();
	ResultSet res = stmt.executeQuery(query);
	System.out.println("busID\tbusType\tavailableSeats");
	while(res.next()) {
		String busID=res.getString(1);
		String busType=res.getString(2);
		
		int availableSeats=res.getInt(3);
		
		System.out.println(busID+"\t"+busType+"\t"+availableSeats);
}
	res.close();
	stmt.close();
	conn.close();
}

	
}


