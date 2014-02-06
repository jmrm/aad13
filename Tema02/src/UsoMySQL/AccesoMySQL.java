package UsoMySQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class AccesoMySQL {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			// Cargar el driver
			Class.forName("com.mysql.jdbc.Driver");
			// Establecemos la conexión con la BD
			Connection cnx=DriverManager.getConnection("jdbc:mysql://localhost/tema03_07","root","root");
			// Preparamos la consulta
			Statement sentencia = cnx.createStatement();
			ResultSet result = sentencia.executeQuery("SELECT * FROM EMPLE NATURAL JOIN DEPART");
			// Recorremos el resultado para visualizar cada fila mediante
			// un bucle que no termina mientras haya registros
			while (result.next()) {
				System.out.print(result.getInt("EMP_NO"));
				System.out.print("*");
				System.out.print(result.getString("APELLIDO"));
				System.out.print("*");
				System.out.print(result.getString("OFICIO"));
				System.out.print("*");
				System.out.print(result.getDate("FECHA_ALT"));
				System.out.print("*");
				System.out.print(result.getFloat("SALARIO"));
				System.out.print("*");
				System.out.print(result.getInt("DEPT_NO"));
				System.out.print("*");
				System.out.print(result.getString("DNOMBRE"));
				System.out.print("*");
				System.out.println(result.getString("LOC"));
			}
			result.close(); //Cierra el ResultSet
			sentencia.close(); // Cierra el Statement
			cnx.close(); // Cierra el Connection
		}
		catch (ClassNotFoundException cnfe)  {cnfe.printStackTrace();}
		catch (SQLException sqle)  {sqle.printStackTrace();}

	}
}
