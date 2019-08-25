package com.collabera.jdbcexample;

import java.sql.*;
import java.sql.Connection;
import java.sql.SQLException;

import com.mysql.cj.protocol.Resultset;


public class ConnectionManager {
  static final String URL = "jdbc:mysql://localhost:3306/testdb?serverTimezone=EST5EDT";
  static final String USERNAME ="root";
  static final String PASSWORD ="abraham27";
   
  
  
  public static Connection getConnection() {
	  Connection conn=null;
	  
	  try {
		conn= DriverManager.getConnection(URL,USERNAME,PASSWORD);
		
		System.out.println("connection was made");
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  return conn;
  }
  
  public static void main(String[] args) {
	Connection conn = ConnectionManager.getConnection();
	
     //execute sql statement;
	
	//sql statement for creating table name persons
	String sqlCommandToCreateTable ="CREATE TABLE Persons (  "
			+ "PersonID int,  "
			+ "LastName varchar(255), "
			+ " FirstName varchar(255),   "
			+ "Address varchar(255),  "
			+ "City varchar(255) );";
	
	//sql statement which add attributes to the table and insert value for each of the attributes 
	String sqlCommandToInsertDataToTable ="insert into persons (PersonID,LastName,FirstName,Address,City) "
			+ "Values ('05', 'Pierrot', 'Abe','110 Allen Road','New Jersey');";
	
	//sql statement which is querying the database for personId and lastName attributes from persons entity 
	String sqlCommandToObtainDataFromTable= "SELECT PersonID, LastName from persons ";
	
	
	
	try {
		
		 Statement statement = conn.createStatement();
		 //create a table 
		 statement.executeUpdate(sqlCommandToCreateTable);
		
		 //insert data in the table 
		 statement.executeUpdate(sqlCommandToInsertDataToTable);
		
		//querying database for data and store the ResultSet [ 
		ResultSet result = statement.executeQuery(sqlCommandToObtainDataFromTable);
		
		//print resultset 
		ResultSetMetaData rsmd = result.getMetaData();
		   int columnsNumber = rsmd.getColumnCount();
		   while (result.next()) {
		       for (int i = 1; i <= columnsNumber; i++) {
		           if (i > 1) System.out.print(",  ");
		           String columnValue = result.getString(i);
		           System.out.print(columnValue + " " + rsmd.getColumnName(i));
		       }
              System.out.println();
		   }
	   
		//remove table 
	   statement.execute("drop table persons");
		   
		   
		conn.close();//To prevent any leak
		System.out.println("connection was closed");
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
}
