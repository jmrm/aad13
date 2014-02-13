package AaDEjMySQL02;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Busca_MySQL {

		/**
		 * @param args
		 */
	final static String bDAlu ="alumnos13_14", tblAlu="alumnos";
	static Connection cnt;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String resp;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			cnt=DriverManager.getConnection("jdbc:mysql://localhost/"+bDAlu,"root","root");
		}
		catch (ClassNotFoundException cnfe) {
			System.out.println("No ha sido posible cargar el driver MySQL JDBC");
			System.exit(1);
		}
		catch (SQLException sqle) {
			System.out.println("Se ha producido un error de conexión en la BD.");
			System.out.println("Estado SGBD:"+sqle.getSQLState());
			System.out.println("Código error:"+sqle.getErrorCode());
			System.out.println("Mensaje error:"+sqle.getMessage());
			System.exit(1);
		}
		Statement sent;
		ResultSet cursor;
		Scanner entrada=new Scanner(System.in);
		try {
			while (true) {
				System.out.println("Introduzca el código a buscar (X===X termina):");
				resp=entrada.next();
				if (resp.equalsIgnoreCase("X===X")) {
					System.out.println("Búsqueda terminada.");
					break;//termina
				}
				String idABusc=resp;
				// para leer después los registros
				System.out.println("Código a buscar:"+idABusc);

				// lectura
				sent= cnt.createStatement();
				StringBuffer txtSent=new StringBuffer(); 
				txtSent.setLength(0);
				txtSent.append("SELECT * FROM "+tblAlu+" WHERE "+EscribeLee_MySQL.camposBD[0]+
						"='"+idABusc+"'");
				System.out.println("Sentencia consulta:"+txtSent.toString());
				cursor=sent.executeQuery(txtSent.toString());
				EscribeLee_MySQL.listResult(cursor);
				cursor.close();
				sent.close();
			}
			cnt.close();
		}
		catch (SQLException sqle) {
			System.out.println("Se ha producido un error BD.");
			System.out.println("Estado SGBD:"+sqle.getSQLState());
			System.out.println("Código error:"+sqle.getErrorCode());
			System.out.println("Mensaje error:"+sqle.getMessage());
			System.exit(1);
		}	
		entrada.close();
		System.exit(0);
	} 	
}