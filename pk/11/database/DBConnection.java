package database;

import java.sql.*;

public class DBConnection {
	
	private Connection con;
	private Statement stmt;
	
	public DBConnection() throws SQLException{
	    con = DriverManager.getConnection(
				 "jdbc:mysql://localhost/javaDB?useSSL=false",
                 "root",
                 "wiadro123");

		stmt = con.createStatement();
	}
	
	public void printDatabase() throws SQLException{

		ResultSet rs = stmt.executeQuery("SELECT * FROM osoby");

	    while (rs.next()) {
	        int id = rs.getInt("id");
	        String name = rs.getString("imie");
	        String surname = rs.getString("nazwisko");
	        System.out.println("ID:" + id + " " + name + " " + surname);
	    }
	}
	
	public void addPerson(String name, String surname) throws SQLException{
		
		PreparedStatement prepStmt = con.prepareStatement("INSERT INTO osoby VALUES (NULL, ?, ?)");;
        prepStmt.setString(1, name);
        prepStmt.setString(2, surname);
        prepStmt.executeUpdate();
	}
	
	public void deletePerson(int index) throws SQLException{
		
		String query = "DELETE FROM osoby WHERE id = " + index;
		stmt.executeUpdate(query);

	}
	
	public void updatePerson(int index, String newName, String newSurname) throws SQLException{
		String query = "UPDATE osoby SET imie = '" + newName + "', nazwisko = '" + newSurname + "' WHERE id = " + index;
		stmt.executeUpdate(query);
	}
}
