package com.valuemomentum.training.training.Book_Basement;

import java.sql.Connection;
import java.sql.DriverManager;


public  class ConnectionEstablishment{
		static Connection con;
		public static Connection getConnection() {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				//System.out.println("Connection to database");
				con=DriverManager.getConnection("jdbc:mysql://localhost:3306/book_basement1","root","root");
				return con;
			}
			catch(Exception e) {
				System.out.println(e);
				return null;
			}
			
		}
			
}

