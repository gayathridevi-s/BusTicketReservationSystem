package com.busReservation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Customer {
	private int custID;
	private String username;
	private String password;
	private String name;
	private String address;
	private long mobile;
	private String gender;
	Scanner scanner = new Scanner(System.in);

	public Customer(int custID, String name, String address, long mobile, String gender, String username,
			String password) {
		this.custID = custID;
		this.name = name;
		this.address = address;
		this.mobile = mobile;
		this.gender = gender;
		this.username = username;
		this.password = password;
	}

	public Customer() {

	}

	public void signUp() throws Exception {
		System.out.println("enter CustID :");
		custID = scanner.nextInt();
		System.out.println("enter name :");
		name = scanner.next();
		System.out.println("enter address:");
		address = scanner.next();

		while (true) {
			try {
				System.out.println("enter mobile :");
				mobile = scanner.nextLong();
				break;
			} catch (InputMismatchException e) {
				System.out.println("Invalid input! Please enter an integer value for mobile.");
				scanner.nextLine(); // consume the invalid input
			}
		}

		System.out.println("enter gender :");
		gender = scanner.next();
		System.out.println("enter username :");
		username = scanner.next();
		System.out.println("enter password :");
		password = scanner.next();

		Customer customer = new Customer(custID, name, address, mobile, gender, username, password);
		CustomerDAO data = new CustomerDAO();
		data.insertCustomerData(customer);

	}

	public String[] login() throws Exception {
		while (true) {
			System.out.println("enter username :");
			username = scanner.next();
			System.out.println("enter password :");
			password = scanner.next();
			CustomerDAO data = new CustomerDAO();
			boolean isValid = data.validateCredentials(username, password);

			if (isValid) {
				System.out.println("Login Successful!");
				BookingDAO bookObject=new BookingDAO();
				
				String roles=bookObject.getRole(username, password);
				String[] credentials = { username, password,roles};
				return credentials;
			} else {
				System.out.println("Invalid Username/password. Please try again.");
			}
		}

	}

	public int getCustID() {
		return custID;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

	public long getMobile() {
		return mobile;
	}

	public String getGender() {
		return gender;
	}



	public void bookTickets(String[] credentials) throws Exception {
		if (credentials == null) {
			return; // Login failed, return without booking ticket
		}

		String username = credentials[0];
		String password = credentials[1];

		BusDAO busObj = new BusDAO();
	
		busObj.displayBusList();
		System.out.println("Do you wanna filter bus? (Y/N)");
//		
		while (true) {
			try {

				String confirmation = scanner.next();
				if (confirmation.equalsIgnoreCase("Y")) {

					busFiltering();
					break;
				} else if (confirmation.equalsIgnoreCase("N")) {

					break;
				} else {
					throw new InputMismatchException("Invalid input. Please enter Y or N.");
				}
			} catch (InputMismatchException e) {
				System.out.println(e.getMessage());
			}

		}

		while (true) {
			String busID = null;
			while (true) {
				try {
					System.out.println("Select the bus (give busID):");
					busID = scanner.next();
					if (busID.equalsIgnoreCase("A")) {
						busObj.getAvailableSeatsA();
						break;
					} else if (busID.equals("B")) {
						busObj.getAvailableSeatsB();
						break;
					} else if (busID.equalsIgnoreCase("C")) {
						busObj.getAvailableSeatsC();
						break;
					} else if (busID.equalsIgnoreCase("D")) {
						busObj.getAvailableSeatsD();
						break;
					} else {
						System.out.println("Invalid ID. Please enter valid bus");
					}
				} catch (InputMismatchException e) {
					System.out.println("Invalid input. Please enter a valid bus ID.");
					scanner.nextLine();
				}
			}

			if (busID != null) {

				System.out.print("Enter number of seats to book: ");
				int numSeats = scanner.nextInt();

				ArrayList<String> seatNumbers = new ArrayList<>();
				int availableSeats = busObj.availableSeats(busID);

				if (numSeats < availableSeats) {
					for (int i = 0; i < numSeats; i++) {
						System.out.print("Enter seat number for ticket " + (i + 1) + ": ");
						seatNumbers.add(scanner.next());

					}

					Connection conn = DBconnection.getConnection();
					PreparedStatement stmt = conn
							.prepareStatement("SELECT bookStatus FROM bus" + busID + " WHERE seat = ?");
					for (int i = 0; i < seatNumbers.size(); i++) {
						String seatNumber = seatNumbers.get(i);
						stmt.setString(1, seatNumber);
						ResultSet rs = stmt.executeQuery();

						if (rs.next()) {
							String bookStatus = rs.getString("bookStatus");
							if ("booked".equals(bookStatus)) {
								System.out.println("Seat " + seatNumber + " is already booked.");
								seatNumbers.remove(seatNumber);
								i--;
							} else {
								System.out.println("Seat " + seatNumber + " is available for booking.");
							}
						}

						else {
							System.out.println("Seat " + seatNumber + " does not exist in bus " + busID + ".");
							seatNumbers.remove(seatNumber);
							i--;

						}
					}

					
					for (String seatNumber : seatNumbers) {
						boolean adjacentAvailable = busObj.checkAdjacentSeatAvailability(busID, seatNumber);
						boolean femaleAvailable = busObj.checkFemaleSeatAvailability(busID, seatNumber);
						String passengerName = "";
						String gender = "";
						BookingDAO bookObj = new BookingDAO();
						int custID = bookObj.dbCustID(username, password);

						int ticketPrice = busObj.ticketFare(busID);
					

						while (true) {
							if (!adjacentAvailable) {
								System.out.print("Enter passenger name: ");
								passengerName = scanner.next();

								System.out.print("Enter passenger gender (male/female): ");
								gender = scanner.next();

								if (femaleAvailable) {
									while (true) {
										try {
											System.out.println("Your tickets cost Rs " + ticketPrice
													+ ".Do you want to confirm your booking? (Y/N)");
											String confirmation = scanner.next();
											if (confirmation.equalsIgnoreCase("Y")) {
												busObj.updateSeatsData(busID, seatNumber, custID, passengerName, gender,
														ticketPrice);
												System.out.println("Ticket " + seatNumber + " is booked!");
//												
												break;
											} else if (confirmation.equalsIgnoreCase("N")) {
												System.out.println("Booking cancelled!");
												break;
											} else {
												throw new InputMismatchException("Invalid input. Please enter Y or N.");
											}
										} catch (InputMismatchException e) {
											System.out.println(e.getMessage());
										}
										//
									}
								
							

									break;
								} else {
									if (gender.equalsIgnoreCase("female")) {
										while (true) {
											try {
												System.out.println("Your tickets cost Rs " + ticketPrice
														+ ".Do you want to confirm your booking? (Y/N)");
												String confirmation = scanner.next();
												if (confirmation.equalsIgnoreCase("Y")) {
													busObj.updateSeatsData(busID, seatNumber, custID, passengerName, gender,
															ticketPrice);
													System.out.println("Ticket " + seatNumber + " is booked!");
//													
													break;
												} else if (confirmation.equalsIgnoreCase("N")) {
													System.out.println("Booking cancelled!");
													break;
												} else {
													throw new InputMismatchException("Invalid input. Please enter Y or N.");
												}
											} catch (InputMismatchException e) {
												System.out.println(e.getMessage());
											}
											//
										}
										break;
									} else {
										System.out.println(
												"Seat " + seatNumber + " can only be booked by female passengers.");
										break;
									}
								}
							} else {
								System.out.print("Enter passenger name: ");
								passengerName = scanner.next();

								System.out.print("Enter passenger gender (male/female): ");
								gender = scanner.next();
								
								while (true) {
									try {
										System.out.println("Your tickets cost Rs " + ticketPrice
												+ ".Do you want to confirm your booking? (Y/N)");
										String confirmation = scanner.next();
										if (confirmation.equalsIgnoreCase("Y")) {
											busObj.updateSeatsData(busID, seatNumber, custID, passengerName, gender,
													ticketPrice);
											System.out.println("Ticket " + seatNumber + " is booked!");
//											
											break;
										} else if (confirmation.equalsIgnoreCase("N")) {
											System.out.println("Booking cancelled!");
											break;
										} else {
											throw new InputMismatchException("Invalid input. Please enter Y or N.");
										}
									} catch (InputMismatchException e) {
										System.out.println(e.getMessage());
									}
									//
								}

								break;
							}
						}
					}

					break;
				} else {
					System.out.println("Not enough seats for booking!");

				}
			}
		}
	}

	public void busFiltering() throws Exception {
		BusDAO busObject = new BusDAO();
		while (true) {
			System.out.println("1:AC");
			System.out.println("2:Non-AC");
			System.out.println("3:Sleeper");
			System.out.println("4:seater");
			System.out.println("5:Both AC-NonAC/Both sleeper-seater");
			System.out.println("enter a choice:");
			int option = scanner.nextInt();
			switch (option) {
			case 1:
				busObject.ACFiltering();
				return;
			case 2:
				busObject.NonACFiltering();
				return;
			case 3:
				busObject.sleeperFiltering();
				return;
			case 4:
				busObject.seaterFiltering();
				return;
			case 5:
				busObject.BothType();
				return;

			default:
				System.out.println("invalid choice");
			}
		}
	}

	public void ticketCancellation(String[] credentials) throws Exception {
		String username = credentials[0];
		String password = credentials[1];
		System.out.println("Select the bus (give busID):");
		String bus = scanner.next();
		BookingDAO bookObj = new BookingDAO();
		int custID = bookObj.dbCustID(username, password);
		BusDAO busObj = new BusDAO();
		ArrayList<String> bookedSeats=busObj.displayTicketsBooked(bus, custID);
		System.out.println(bookedSeats);
		System.out.println("1: delete all tickets");
		System.out.println("2: delete selected seats");
		int choice = scanner.nextInt();
		switch (choice) {
		case 1:
			busObj.allSeatsCancellation(bus, custID,bookedSeats);
			break;
		case 2:
			busObj.selectedSeatsCancellation(bus, custID);
			break;
		}

	}


}