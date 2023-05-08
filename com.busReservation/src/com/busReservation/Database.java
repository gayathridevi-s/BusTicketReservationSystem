package com.busReservation;

import java.sql.*;


public class Database {
	
	
	
	public  void BusTable() throws Exception {
		Connection con=DBconnection.getConnection();
		Statement st=con.createStatement();
		 String createBus ="create table if not exists bus (busID varchar(2) ,busType varchar(20),totalSeats int,availableSeats int,fare int,CollectedFare double,FilledSeat int);";
		 int count=st.executeUpdate(createBus);
		 System.out.println(count+1 +" bus table created succesfully!");
		 st.close();
		 con.close();
		
	}
	public void customersTable() throws Exception {
		Connection con =DBconnection.getConnection();
		Statement st=con.createStatement();
		String createCustomer="create table if not exists customer(CustID INT,Name varchar(20),Address varchar(20),Mobile long,Gender VARCHAR(8),Username varchar(20),UserPassword varchar(20),Roles varchar(15));";
		String insertAdmin="INSERT IGNORE INTO customer (custID, Name, Address, Mobile, Gender,Username, userPassword, Roles)\n"
				+ "VALUES (1, 'Zerin', 'Ernakulam',1234567890,'male', 'zbus', 'zbus123', 'admin');";
			int count =st.executeUpdate(createCustomer)	;
			int count1=st.executeUpdate(insertAdmin);
			System.out.println(count+1 +" customer table created successfully!");
			System.out.println(count1+1);
			st.close();
			con.close();
	}

	public void busATable() throws Exception {
		Connection con =DBconnection.getConnection();
		Statement st=con.createStatement();
		String createBusA="create table if not exists busA(seat varchar(5),bookStatus varchar(10),passengerName varchar(20),gender varchar(10),custID int,price int,cancellationFee double);";
		int count =st.executeUpdate(createBusA)	;
		System.out.println(count+1 +" BusA table created successfully!");
		st.close();
		con.close();
	}
	public void busBTable() throws Exception {
		Connection con =DBconnection.getConnection();
		Statement st=con.createStatement();
		String createBusB="create table if not exists busB(seat varchar(5),bookStatus varchar(10),passengerName varchar(20),gender varchar(10),custID int,price int,cancellationFee double);";
		int count =st.executeUpdate(createBusB)	;
		System.out.println(count+1 +" BusB table created successfully!");
		st.close();
		con.close();
	}
	public void busCTable() throws Exception {
		Connection con =DBconnection.getConnection();
		Statement st=con.createStatement();
		String createBusC="create table if not exists busC(seat varchar(5),bookStatus varchar(10),passengerName varchar(20),gender varchar(10),custID int,price int,cancellationFee double);";
		int count =st.executeUpdate(createBusC)	;
		System.out.println(count+1 +" BusC table created successfully!");
		st.close();
		con.close();
	}
	public void busDTable() throws Exception {
		Connection con =DBconnection.getConnection();
		Statement st=con.createStatement();
		String createBusD="create table if not exists busD(seat varchar(5),bookStatus varchar(10),passengerName varchar(20),gender varchar(10),custID int,price int,cancellationFee double);";
		int count =st.executeUpdate(createBusD)	;
		System.out.println(count+1 +" BusD table created successfully!");
		st.close();
		con.close();
	}
	
	public static void main(String[] args) throws Exception {
		Database d=new Database();
		d.BusTable();
		d.customersTable();
		d.busATable();
		d.busBTable();
		d.busCTable();
		d.busDTable();
	}
}
