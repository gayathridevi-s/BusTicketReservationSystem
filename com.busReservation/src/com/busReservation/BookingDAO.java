package com.busReservation;

import java.sql.*;


public class BookingDAO {

public int dbCustID(String username,String password) throws Exception {
	Connection con=DBconnection.getConnection();
    PreparedStatement stmt = con.prepareStatement("SELECT CustID FROM customer WHERE Username = ? AND userPassword = ?");
    stmt.setString(1, username);
    stmt.setString(2, password);
    ResultSet rs = stmt.executeQuery();
    rs.next();
    int customerID=rs.getInt("CustID");
    rs.close();
    stmt.close();
    con.close();
    return customerID;
    
}
public String dbGender(String username,String password) throws Exception {
	Connection con=DBconnection.getConnection();
    PreparedStatement stmt = con.prepareStatement("SELECT Gender FROM customer WHERE Username = ? AND userPassword = ?");
    stmt.setString(1, username);
    stmt.setString(2, password);
    ResultSet rs = stmt.executeQuery();
    rs.next();
    String gender =rs.getString("Gender");
    rs.close();
    stmt.close();
    con.close();
    return gender;
}
public String getRole(String username,String password) throws Exception {
	Connection con=DBconnection.getConnection();
    PreparedStatement stmt = con.prepareStatement("SELECT Roles FROM customer WHERE Username = ? AND userPassword = ?");
    stmt.setString(1, username);
    stmt.setString(2, password);
    ResultSet rs = stmt.executeQuery();
    rs.next();
    String role =rs.getString("Roles");
    rs.close();
    stmt.close();
    con.close();
    return role;
	
}


}
