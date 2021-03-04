package com.valuemomentum.training.training.Book_Basement;

import java.sql.SQLException;
import java.util.Scanner;




/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws SQLException
    {
    	Scanner sc=new Scanner(System.in);
       System.out.println("*********************************************************************************************************");
       System.out.println("                                     BOOK STORE MANAGEMENT                                              ");       
       System.out.println("*********************************************************************************************************");
       System.out.println("MAKE YOUR CHOICE");
       boolean k=true;
       while(k) {
    	   System.out.println("1. ADMIN\t\t\t2. USER");
    	   int choice=sc.nextInt();
    	   switch(choice){
    	   case 1:
    		   System.out.println("PRESS 1 TO LOGIN");
    		   System.out.println("1. LOGIN");
    		   int l=sc.nextInt();
		       Admin a=new Admin();
		       a.adminLogin(); 
		       k=false;
		       break;
    	   case 2:
    		   User u=new User();
    		   k=false;
    		   break;
    		default:
    			System.out.println("INVALID INPUT");
    			break;
    	   }
       }
    }
}
