package main;

import java.sql.SQLException;

import database.DBConnection;

public class Program {

	public static void main(String[] args) {
		
		try {
			DBConnection dbc = new DBConnection();
			dbc.printDatabase();
			//dbc.addPerson("IMIE", "NAZWISKO");
			//dbc.deletePerson(INDEX);
			//dbc.updatePerson(INDEX, "IMIE", "NAZWISKO");
			//dbc.printDatabase();
		} 
		
		catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
