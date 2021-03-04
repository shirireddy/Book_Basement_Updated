package com.valuemomentum.training.training.Book_Basement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class User {
	Connection con;
	Statement stmt;
	ResultSet rs;
	Scanner sc=new Scanner(System.in);
	boolean b=true;
	boolean register=false;
	//ArrayList<Integer> books=new ArrayList<Integer>();
	
	User() throws SQLException{
		con=ConnectionEstablishment.getConnection();
		start();
	}
	
	void start() throws SQLException {
		
		
		System.out.println("--------------------------------------------------USER-------------------------------------------------");
		System.out.println("1. LOGIN\t\t2. REGISTER");
		int choice=sc.nextInt();
		switch(choice) {
		case 1:
			while(b) {
			System.out.println("-------------------------------------------LOGIN-------------------------------------------------------");
			System.out.println("ENTER USERID: ");
			int userid=sc.nextInt();
			System.out.println("ENTER PASSWORD");
			String password=sc.next();
			userLogin(userid,password);
			break;
			}
			
		case 2:
			register=true;
			while(register) {
			registerUser();
			userTasks();
			register=false;
			break;
			}
		default:
			System.out.println("INVALID INPUT");
			break;
			
			
		}
		
		
	}
	
	void userLogin(int id,String pass) throws SQLException {
		int keyboardid=id;
		String keyboardpass=pass;
		boolean t=false;
		stmt=con.createStatement();
		String query="SELECT password from user where user_id="+keyboardid;
		rs=stmt.executeQuery(query);
		while(rs.next()) {
			if(keyboardpass.equals(rs.getString(1))) {
				t=true;
			}
			else
				t=false;
				
		}
		if(t==true) {
			b=false;
			userTasks();
			
		}
		else if(t==false) {
			System.out.println("--------------------------------INVALID CREDENTIALS/ACCOUNT NOT FOUND-----------------------------");
			b=true;
			start();
		}
		//stmt.close();
		//rs.close();
			
	}
	
	void userTasks() throws SQLException {
		Boolean bool=true;
		if(bool==true){
		//System.out.println("*******************************USER TASKS***************************************************");
		System.out.println("-------------------------------------------------------------------------------------------------------");
		System.out.println(">>1. VIEW BOOKS AND ORDER BOOKS\t\t>>2.ENTER ADDRESS");
		System.out.println(">>3.VIEW ORDERS\t\t\t\t>>4. VIEW PAYMENTS");
		System.out.println(">>5. DELETE ACCOUNT\t\t\t>>6.OPEN WALLET");
		System.out.println(">>7.UPDATE WALLET\t\t\t>>8.EXIT");
		System.out.println("--------------------------------------------------------------------------------------------------------");
		//System.out.println("TO PLACE ORDER FIRST VIEW BOOKS AND THEN PLACE ORDER");
		int task=sc.nextInt();
		
		switch(task) {
		case 1:
			bool=true;
			viewBooks(); //workd
			break;
		case 2:
			bool=true;
			
			registerAddress(); //works
			break;
		
		case 3:
			bool=true;
			viewOrders(); //works
			break;
		case 4:
			bool=true;
			viewPayments();  //works
			break;
		case 5:
			bool=true;
			deleteAccount();
			break;
		case 6:
			bool=true;
			openWallet(); //works
			break;
		case 7:
			bool=true;
			updateWallet(); //works
			break;
		case 8:
			bool=false;
			end();
			break;
	    default:
	    	System.out.println("INVALID INPUT ");
	    	break;
		}
		
		}
		else if(bool==false) {
			System.out.println("-----------------------------------------------THANKYOU VISIT AGAIN---------------------------------");
		}
	}
	void viewBooks() throws SQLException {
		ArrayList<Integer> books=new ArrayList<Integer>();
		stmt=con.createStatement();
		String query="SELECT * FROM books";
		rs=stmt.executeQuery(query);
		while(rs.next()) {
			System.out.println("BOOKID:"+rs.getInt(1)+"\t\tTITLE:"+rs.getString(2));
		}
		//rs.close();
		//stmt.close();
		System.out.println();
		System.out.println("ENTER THE NUMBER OF BOOKS YOU WANT TO ORDER");
		int size=sc.nextInt(); //2
		System.out.println("ENTER THE ID's OF THE BOOKS YOU WANT TO ORDER");
		for(int i=0;i<size;i++) {
		int bd=sc.nextInt();
		books.add(bd);
		}
		orderBooks(books);
		
		
	}
		
	void orderBooks(ArrayList<Integer> l) {
		//int bookid1=bookid;
		System.out.println("--------------------------------------------------------------------------");
		System.out.println("|                         INVOICE                                         |");
		System.out.println("---------------------------------------------------------------------------");
		//String query=("select * from books where book_id="+bookid1);
		int due=0;
		String h1="BookId";
		String h2="Title";
		String h3="Author Name";
		String h4="Genre";
		String h5="Price";
		System.out.printf("%-40s %-30s %-25s %-5s",h2,h3,h4,h5);
		System.out.println();
		try {
			for(int j=0,count=1;j<l.size();j++,count++) {
				int bookid1=l.get(j);
			String query="select * from books where book_id="+bookid1;
				stmt=con.createStatement();
				rs=stmt.executeQuery(query);
				
				while(rs.next()) {
					//System.out.println("-------------------------order "+count+"--------------------------------------------");
					String name=rs.getString(3)+" "+rs.getString(4);
					System.out.printf("%-40s %-30s %-25s %-5d",rs.getString(2),name,rs.getString(5),rs.getInt(6) );
					
					//System.out.println("BOOK TITLE: "+rs.getString(2)+"\tAUTHOR: "+rs.getString(3)+" "+rs.getString(4)+"\tGENRE: "+rs.getString(5)+"\tPRICE: "+rs.getInt(6));
					due+=rs.getInt(6);
					System.out.println();
				
				
				}
				
			}
			System.out.println("-------------------------------BILL-----------------------------------------");
			String heading1="AMOUNT";
			System.out.printf( "%107s %n", heading1+" "+due);
		//System.out.println("    |TO PAY: "+due                                                            +"|");
			
		System.out.println("ENTER WALLET ID AND PASSWORD TO CHECK FOR BALANCE");
		System.out.println("ENTER WALLET ID");
		int walletid=sc.nextInt();
		System.out.println("ENTER PASSWORD");
		String walletpass=sc.next();
		int bal=checkBalance(walletid,walletpass,due);
		if(bal>=due) {
			makeDeductionsfromPayments(walletid,due,bal,l);
		}
		else {
			System.out.println("-------------------INSUFFICIENT FUNDS,ADD MONEY----------------------");
			userTasks();
		}
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}

	void registerUser() {
		
		System.out.println("ENTER THE DETAILS");
		System.out.println("FIRST NAME: ");
		String fname=sc.next();
		System.out.println("LAST NAME");
		String lname=sc.next();
		System.out.println("GENDER");
		String gender=sc.next();
		System.out.println("PASSWORD");
		String password=sc.next();
		System.out.println("EMAIL");
		String email=sc.next();
		System.out.println("CONTACT NUMBER");
		String number=sc.next();
		
		try {
			PreparedStatement pstmt=con.prepareStatement("insert into user(Firstname,Lastname,PhoneNumber,gender,password,mail_id) values(?,?,?,?,?,?)");
			PreparedStatement pstmt2=con.prepareStatement("insert into userList(userFirstName,userLastName) values(?,?)");
			pstmt2.setString(1, fname);
			pstmt2.setString(2, lname);
			
					int useid=0;
			pstmt.setString(1, fname);
			pstmt.setString(2, lname);
			pstmt.setString(3, number);
			pstmt.setString(4, gender);
			pstmt.setString(5, password);
			pstmt.setString(6, email);
			int rowCount=pstmt.executeUpdate();
			int row2Count=pstmt2.executeUpdate();
			if(rowCount>0) {
				System.out.println("USER REGISTERED SUCCESSFULLY");
				//stmt.close();
				String query="Select user_id from user where password='"+password+"'";
				stmt=con.createStatement();
				rs=stmt.executeQuery(query);
				while(rs.next()) {
					System.out.println("PLEASE REMEMBER YOUR USER ID :");
					useid=rs.getInt(1);
					System.out.println("USER ID: "+useid);
					System.out.println("ENTER ADDRESS AND OPEN WALLET TO PLACE ORDERS");
					System.out.println("PRESS 2 TO REGISTER YOUR ADDRESS");
					System.out.println("PRESS 6 TO OPEN ACCOUNT IN WALLET");
				}
				
			}
			else
				System.out.println("insertion unsuccessful");
			
		} catch (SQLIntegrityConstraintViolationException e) {
			// TODO Auto-generated catch block
			System.out.println("Enter email in the form of xyz@abc.com");
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	void registerAddress() {
		//int useid=userid;
		System.out.println("ENTER USERID:");
		int useid=sc.nextInt();
		System.out.println("ENTER HOUSE NUMBER");
		String housenumber=sc.next();
		System.out.println("ENTER CITY");
		String city=sc.next();
		System.out.println("ENTER STATE");
		String state=sc.next();
		System.out.println("ENTER PINCODE");
		String pincode=sc.next();
		PreparedStatement pstmt2;
		try {
			pstmt2 = con.prepareStatement("insert into user_details(userid,house_number,city,state,Pincode) values(?,?,?,?,?)");
		
		pstmt2.setInt(1, useid);
		pstmt2.setString(2, housenumber);
		pstmt2.setString(3, city);
		pstmt2.setString(4, state);
		pstmt2.setString(5, pincode);
		int count=pstmt2.executeUpdate();
		if(count>0) {
			System.out.println("ADDRESS INSERTED");
			userTasks();
		}
		else
			System.out.println("FAILED........TRY AGAIN");
			userTasks();
		
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	private void makeDeductionsfromPayments(int walletid, int due,int bal,ArrayList<Integer> l) {
		// TODO Auto-generated method stub
		ArrayList<Integer> l1=l;
		int toPay=due;
		int pocket=bal;
		int id=walletid;
		int deduction=pocket-toPay;
		String str="update wallet set balance="+deduction+" where walletId="+id;
		try {
			int count=stmt.executeUpdate(str);
		
				updateOrders(id,l1);
				updatePayments(id,toPay);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void updatePayments(int id, int toPay) {
		// TODO Auto-generated method stub
		int customer_id=id;
		int payment=toPay;
		try {
			PreparedStatement pstmt=con.prepareStatement("insert into payments(customerNumber,payments) values(?,?)");
			pstmt.setInt(1, customer_id);
			pstmt.setInt(2, payment);
			int rowseffected=pstmt.executeUpdate();
			if(rowseffected>0) {
				System.out.println("--------------------------------ORDER PLACED--------------------------------");
				userTasks();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void updateOrders(int id, ArrayList<Integer> l) {
		// TODO Auto-generated method stub
		ArrayList <Integer> l1=l;
		Object request;
		Calendar calendar = Calendar.getInstance();
		java.util.Date currentTime = calendar.getTime();
		long time = currentTime.getTime();
		System.out.println("ENTER USERID: ");
		int userid=sc.nextInt();
		for(int j=0,count=0;j<l1.size();j++,count++){
		//int count=1;
		int bookreferenceNum=l.get(count);
		try {
			PreparedStatement pstmt=con.prepareStatement("insert into orders(customer_id,book_reference_code,orderDate) values(?,?,?)");
			pstmt.setInt(1, userid);
			pstmt.setInt(2, bookreferenceNum);
			pstmt.setTimestamp(3, new Timestamp(time));
			int count1=pstmt.executeUpdate();
			
		}
		catch(Exception e) {
			System.out.println(e);
		}
		}
		
	}

	int checkBalance(int userid,String password,int price) throws SQLException {
		int walletid=userid;
		int bal1=0;
		String walletpass=password;
		int cost=price;
		String query="select balance from wallet where walletId="+walletid+" AND PASSWORD='"+walletpass+"'";
		
			rs=stmt.executeQuery(query);
			/*if(!rs.next()) {
				System.out.println("YOU DID NOT CREATE AN ACCOUNT IN OUR WALLET........TO ORDER PLEASE OPEN ACCOUNT IN WALLET");
				userTasks();
				return bal1;
			}*/
			while(rs.next()) {
				bal1=rs.getInt(1);
				//System.out.println(bal1);
				
			}
			
			return bal1;
			
		
	}
		

	void viewOrders() throws SQLException {
		System.out.println("ENTER YOUR USERID");
		int id=sc.nextInt();
		stmt=con.createStatement();
		String query="SELECT * FROM orders where customer_id="+id;
		rs=stmt.executeQuery(query);
		while(rs.next()) {
			System.out.println("ORDERID: "+rs.getInt(1)+"\tID OF USER: "+rs.getInt(2)+"\tBOOK REFERENCE CODE: "+rs.getInt(3)+"\tORDER DATE: "+rs.getString(4));
		}
		/*if(!rs.next()) {
			System.out.println("----------------NO ORDERS MADE------------------");
		}*/
		userTasks();
		
	}
	void viewPayments() throws SQLException {
		
		System.out.println("ENTER YOUR USERID");
		int id=sc.nextInt();
		stmt=con.createStatement();
		String query="SELECT * FROM payments where customerNumber="+id;
		rs=stmt.executeQuery(query);
		while(rs.next() ) {
			System.out.println("PAYMENTID: "+rs.getInt(1)+" ID OF USER: "+rs.getInt(2)+" PAYMENT DONE FOR: "+rs.getInt(3));
		}
		/*if(!rs.next() ){
			System.out.println("---------------------NO PAYMENTS MADE--------------");
			
		}*/
		userTasks();
		
	}
	void deleteAccount() throws SQLException {
		System.out.println("ENTER YOUT USER ID");
		int id=sc.nextInt();
		String query="delete from user where user_id="+id;
		stmt=con.createStatement();
		int count=stmt.executeUpdate(query);
		if(count>0) {
			System.out.println("record deleted");
		}
		else {
			System.out.println("record not deleted");
		}
		end();
		
	}
	
	

	void openWallet() {
		try {
		System.out.println("YOUR USER ID FOR WALLET IS YOUR USERID FOR LOGIN/THE ID GIVEN TO YOU AFTER REGISTRATION");
		System.out.println("ENTER YOUR ID");
		int walletid=sc.nextInt();
		System.out.println("ENTER YOUR PASSWORD");
		String  password=sc.next();
			
			System.out.println("ENTER AMOUNT: ");
			int amount=sc.nextInt();
				
				PreparedStatement pstmt=con.prepareStatement("insert into wallet(walletid,password,balance) values(?,?,?)");
				pstmt.setInt(1, walletid);
				pstmt.setString(2, password);
				pstmt.setInt(3, amount);
				int rs2=pstmt.executeUpdate();
				if(rs2>0) {
					System.out.println("MONEY TRANSFERED TO WALLET");
					userTasks();
				}
				else
					System.out.println("TRANSACTION FAILED");
				
			
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	void updateWallet()
	{
		try {
		System.out.println("ENTER WALLET ID/USERID");
		int walletid=sc.nextInt();
		System.out.println("ENTER ACCOUNT PASSWORD: ");
		String accountpass=sc.next();
		String tablepass;
			stmt=con.createStatement();
			ResultSet rs1;
			String query="SELECT password FROM wallet where walletId="+walletid;
			rs1=stmt.executeQuery(query);
			
			/*if(!rs1.next()) {
				System.out.println("-------------------------OPEN/CREATE AN ACCOUNT IN WALLET-------------");
				userTasks();
			}*/
			
			
			while(rs1.next()){
				
				if(accountpass.equals(rs1.getString(1))) {
					//System.out.println("IF NOT ASKED FOR THE AMOUNT TO BE ENTERED IT IS UNDERSTOOD THAT U DONT HAVE AN ACCOUNT SO PLEASE OPEN AN ACCOUNT IN OUR WALLET");
					System.out.println("ENTER THE AMOUNT: ");
					int amount=sc.nextInt();
					incrementBalance(amount,walletid);
				}
				else 
					System.out.println("INVALID CREDENTIALS");
					end2();
				
			
				
			}
			userTasks();
			
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	void incrementBalance(int amt,int walletid) {
		int adder=amt;
		int id=walletid;
		int balance=0;
		//int transfer=0;
		try {
			stmt=con.createStatement();
			String query="SELECT balance from wallet where walletid="+id;
			rs=stmt.executeQuery(query);
			while(rs.next()) {
				balance=rs.getInt(1);
			}
			balance+=adder;
			String query2="UPDATE  wallet set balance="+balance+" where walletid="+id;
			
			int update=stmt.executeUpdate(query2);
			if(update>0) {
				System.out.println("AMOUNT ADDED TO YOUR WALLET");
				userTasks();
			}
			else
				System.out.println("TASK UNSUCCESSFULL");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	void end2() {
		System.out.println("TERMINATED PROCESS DUE TO INVALID CREDENTIALS");
		System.exit(1);
	}
	void end() throws SQLException {
		con.close();
		stmt.close();
		rs.close();
		System.out.println("------------------------------------THANKYOU FOR USING BOOK BASEMENT------------------------");
		System.exit(1);
		
	}
	
	
}
