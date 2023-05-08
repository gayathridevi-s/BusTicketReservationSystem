package com.busReservation;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBconnection {
	public static Connection getConnection() throws Exception {
		
		final String url="jdbc:mysql://localhost:3306/reservation";
		final String user="root";
		final String pass="MyN3wP4ssw0rd";
		
			Class.forName("com.mysql.cj.jdbc.Driver");
			return DriverManager.getConnection(url, user, pass);
			
}
}