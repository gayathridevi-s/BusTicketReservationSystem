package com.busReservation;

import java.util.InputMismatchException;
import java.util.Scanner;

public class BusReservationSystem {
	public static void main(String[] args) throws Exception {
		Scanner scanner = new Scanner(System.in);
		boolean exit = false;
		do {
		    try {
		        System.out.println("**************************************************");
		        System.out.println("*                                                *");
		        System.out.println("*        Welcome to the ZBUS Transportation      *");
		        System.out.println("*                                                *");
		        System.out.println("**************************************************");
		          
		        System.out.println("1:Sign up");
		        System.out.println("2:login ");
		        System.out.println("3:exit");
		        System.out.println("enter a choice:");
		          
		        int choice = scanner.nextInt();
		        Customer c = new Customer();
		        switch (choice) {
		            case 1:
		                c.signUp();
		                break;
		            case 2:
		                String[] credentials = c.login();
		                String admin="admin";
		                if (admin.equalsIgnoreCase(credentials[2])) {
		                    System.out.println("ADMIN PORTAL");
		                    
		                    	System.out.println("Booking summary");
		                    	BusDAO newObj=new BusDAO();
		                    	newObj.busSummary();
		                    	
		                    
		                } else {
		                	 boolean flag=false;
				                do {
				                    System.out.println("1:Book Tickets");
				                    System.out.println("2:Cancel Tickets");
				                    System.out.println("3:Back");
				                    System.out.println("enter your choice:");
				                    int choice1=scanner.nextInt();
				                    switch(choice1) {
				                        case 1:
				                            c.bookTickets(credentials);
				                            break;
				                        case 2:
				                            c.ticketCancellation(credentials);
				                            break;
				                        case 3:
				                            System.out.println("back to main menu!!");
				                            flag=true;
				                            break;
				                    }
				                } while(!flag);
		                }
		               
		                break;
		            case 3:
		                System.out.println("exiting the program!");
		                exit=true;
		                break;
		            default:
		                System.out.println("Invalid choice.Please enter a valid number.");
		        }
		    } catch (InputMismatchException e) {
		        System.out.println("Invalid input. Please enter a valid choice.");
		        scanner.nextLine(); 
		    }
		} while (!exit);

	}
}
