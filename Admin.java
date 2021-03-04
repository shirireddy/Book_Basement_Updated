package com.valuemomentum.training.training.Book_Basement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Admin {
	Connection con;
	Statement stmt;
	ResultSet rs;
	int k;
	Scanner s=new Scanner(System.in);
	Admin()
	{
		con=ConnectionEstablishment.getConnection();
	}
		void adminLogin() throws SQLException {
			System.out.println("ENTER USERID AND PASSWORD");
			int userid=s.nextInt();
			String password=s.next();
			boolean b=checkPassword(userid,password);
			//System.out.println(b);
			loginAdmin2(b);
			
		}
		void loginAdmin2(Boolean b) throws SQLException {
			boolean t = false;
			if(b==false) {
				System.out.println("INVALID CREDENTIALS.....LOGIN FAILED......");
				adminLogin();
			}
			else if(b==true) {
				System.out.println("---------------------LOGIN SUCCESSFUL-------------------");
				 t=true;}
				while(t) {
				System.out.println("*****************************************************************************************");
				System.out.println("1. VIEW CUSTOMERS\t2.VIEW ORDERS");
				System.out.println("3.ADD BOOKS\t\t4.EXIT");
				int task=s.nextInt();
				switch(task) {
				case 1:
					viewCustomers();
					break;
				case 2:
					viewOrders();
					break;
				case 3:
					addBooks();
					break;
				case 4:
					exit();
					t=false;
					break;
				default:
					System.out.println("INVALID INPUT");
					break;
				}
				
				
			}
			
		}
		
		
		
		boolean checkPassword(int id,String password) throws SQLException {
			String keyboardPass=password;
			boolean t;
			int keyboardentry=id;
			//System.out.println(keyboardPass);
			stmt=con.createStatement();
			String query="SELECT PASSWORD from admin where adminid="+keyboardentry;
			rs=stmt.executeQuery(query);
			String tablePassword="";
			while(rs.next()) {
				tablePassword=rs.getString(1);
				//System.out.println(tablePassword);
			}
			if(keyboardPass.equals(tablePassword)) {
				t=true;
			}
			else {
				t=false;
			}
			return t;
			
		}
		
		
		void addBooks() {
			try {
				
				System.out.println("TITLE: ");
				s.nextLine();
				String title=s.nextLine();
				System.out.println("AUTHOR FIRST NAME:");
				String fName=s.next();
				System.out.println("AUTHOR LAST NAME");
				String lName=s.next();
				System.out.println("GENRE: ");
				String genre=s.next();
				System.out.println("PRICE: ");
				int price=s.nextInt();
				PreparedStatement pstmt=con.prepareStatement("insert into books(title,authorFirstName,authorLastName,genre,price) values(?,?,?,?,?)");
					pstmt.setString(1, title);
					pstmt.setString(2, fName);
					pstmt.setString(3, lName);
					pstmt.setString(4, genre);
					pstmt.setInt(5, price);
					int count=pstmt.executeUpdate();
					if(count>0) {
						System.out.println("--------------------------BOOK ADDED---------------------");
					}
					else
						System.out.println("-------------------------FAILED TO ADD BOOK---------------");
				
			}
			catch(Exception e) {
				System.out.println(e);
			}
		}
			
			
			
		
		void viewOrders() throws SQLException {
			stmt=con.createStatement();
			String query="SELECT * FROM orders";
			rs=stmt.executeQuery(query);
			System.out.println("-----------------------------ORDERS DETAILS-----------------------------------------------------------------------------------");
			//System.out.printf( "%-15s %15s -%15s %15s %n",h1,h2,h3,h4);
			while(rs.next()) {
				
				System.out.println("ORDERID: "+rs.getInt(1)+" ID OF USER: "+rs.getInt(2)+" BOOK REFERENCE CODE: "+rs.getInt(3)+" ORDER DATE: "+rs.getString(4));
				System.out.println();
				System.out.println();
			}
			
			rs.close();
			stmt.close();
			
		}
		
		
		
		void viewCustomers() throws SQLException {
			stmt=con.createStatement();
			rs=stmt.executeQuery("SELECT * FROM user");
			String h1="USER ID:";
			String h2="NAME: ";
			String h3="GENDER: ";
			String h4="MAIL-ID: ";
			String h5="CONTACT NUMBER";
			System.out.println("-------------------------------------------------------CUSTOMERS--------------------------------------------------------");
			//System.out.printf("%-15s %-40s %-28s %-15s%n",h1,h2,h3,h4);
			System.out.printf("%-15s",h1);
			System.out.printf("%-30s", h2);
			System.out.printf("%-33s", h3);
			System.out.printf("%-28s", h4);
			System.out.printf("%20s", h5);
			System.out.println();
			while(rs.next()) {
				//System.out.printf("%-15s", h1+" "+rs.getInt(1));
				//System.out.printf("%15s",h2+rs.getString(2)+" "+rs.getString(3));
				//System.out.printf("%35s", h3+rs.getString(4));
				//System.out.printf("%40s",h4+rs.getString(6));
				String name=rs.getString(2)+" "+rs.getString(3);
				System.out.printf("%-17d",rs.getInt(1));
				System.out.printf("%-30s",name);
				System.out.printf("%-31s", rs.getString(5));
				System.out.printf("%-25s", rs.getString(7));
				System.out.printf("%20s", rs.getString(4));
				//System.out.printf("%-15d %-40s %-10s %-15s%n",rs.getInt(1),name,rs.getString(5),rs.getString(7));
				//System.out.println("USERID: "+rs.getInt(1)+"  NAME: "+rs.getString(2)+" "+rs.getString(3)+" GENDER: "+rs.getString(4)+"  "+"MAILID: "+rs.getString(6));
				//System.out.println();
				System.out.println();
			}
			rs.close();
			stmt.close();
			
		}
		public void exit() throws SQLException {
			con.close();
			System.exit(1);
			
		}
		
}
