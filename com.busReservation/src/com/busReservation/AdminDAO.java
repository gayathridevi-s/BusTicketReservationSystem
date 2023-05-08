package com.busReservation;

import java.sql.*;

public class AdminDAO {
	public  void addBusData(String busID,String busType,int totalSeats,int availableSeats,int fare) throws Exception {
	Connection con=DBconnection.getConnection();
	String busData="insert into bus (busID,busType,totalSeats,availableSeats,fare)values(?,?,?,?,?);";
	PreparedStatement pst=con.prepareStatement(busData);
	pst.setString(1, busID);
	pst.setString(2, busType);
	pst.setInt(3, totalSeats);
	pst.setInt(4, availableSeats);
	pst.setInt(5, fare);
	int dataUpdate=pst.executeUpdate();
	if (dataUpdate==1) {
		System.out.println("Bus Added");
	}
	pst.close();
	con.close();
	}
	public void addBusAdata() throws Exception {
		Connection con=DBconnection.getConnection();
		String insertQuery = "INSERT INTO busA (seat) VALUES (?)";
		PreparedStatement pst = con.prepareStatement(insertQuery);
		for (int i = 1; i <= 20; i++) {
		    String seat = "A" + i;
		    pst.setString(1, seat);
		    pst.executeUpdate();
		}
		pst.close();
		con.close();
	}
	public void addBusBdata() throws Exception {
		Connection con=DBconnection.getConnection();
		String insertQuery = "INSERT INTO busB (seat) VALUES (?)";
		PreparedStatement pst = con.prepareStatement(insertQuery);
		for (int i = 1; i <= 20; i++) {
		    String seat = "B" + i;
		    pst.setString(1, seat);
		    pst.executeUpdate();
		}
		pst.close();
		con.close();
	}
	public void addBusCdata() throws Exception {
		Connection con=DBconnection.getConnection();
		String insertQuery = "INSERT INTO busC (seat) VALUES (?)";
		PreparedStatement pst = con.prepareStatement(insertQuery);
		for (int i = 1; i <= 20; i++) {
		    String seat = "C" + i;
		    pst.setString(1, seat);
		    pst.executeUpdate();
		}
		pst.close();
		con.close();
	}
	public void addBusDdata() throws Exception {
		Connection con=DBconnection.getConnection();
		String insertQuery = "INSERT INTO busD (seat) VALUES (?)";
		PreparedStatement pst = con.prepareStatement(insertQuery);
		for (int i = 1; i <= 20; i++) {
		    String seat = "D" + i;
		    pst.setString(1, seat);
		    pst.executeUpdate();
		}
		pst.close();
		con.close();
	}
}
