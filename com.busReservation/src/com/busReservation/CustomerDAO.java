package com.busReservation;

import java.sql.*;


public class CustomerDAO {
public void insertCustomerData( Customer customer) throws Exception {
	Connection con=DBconnection.getConnection();
	String customerData="insert into customer (CustID,Name,Address,Mobile,Gender,Username,userPassword) values(?,?,?,?,?,?,?);";
			
	PreparedStatement pst=con.prepareStatement(customerData);
	pst.setInt(1, customer.getCustID());
	pst.setString(2, customer.getName());
	pst.setString(3, customer.getAddress());
	pst.setLong(4, customer.getMobile());
	pst.setString(5, customer.getGender());
	pst.setString(6, customer.getUsername());
	pst.setString(7, customer.getPassword());
	int dataUpdate=pst.executeUpdate();
	if (dataUpdate==1) {
		System.out.println("Account created Successfully!");
	}
	pst.close();
	con.close();
}

public boolean validateCredentials(String username, String password) throws Exception {
    boolean isValid = false;
    try {

    	Connection con=DBconnection.getConnection();
        PreparedStatement stmt = con.prepareStatement("SELECT * FROM customer WHERE Username = ? AND userPassword = ?");
        stmt.setString(1, username);
        stmt.setString(2, password);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            isValid = true;
        }
        rs.close();
       stmt.close();
       con.close();
    } catch (SQLException e) {
        System.out.println("SQL Exception: " + e.getMessage());
    }
    return isValid;


}

}