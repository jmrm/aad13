package AaDEjSQLite02;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Busca_SQLite {

		/**
		 * @param args
		 */
	final static String bDAlu ="alumnos13_14.sqlite", tblAlu="alumnos";
	static Connection cnt;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String resp;
		
		try {
			Class.forName("org.sqlite.JDBC");
			cnt=DriverManager.getConnection("jdbc:sqlite:"+bDAlu);
		}
		catch (ClassNotFoundException cnfe) {
			System.out.println("No ha sido posible cargar el driver SQLite JDBC");
			System.exit(1);
		}
		catch (SQLException sqle) {
			System.out.println("Se ha producido un error de conexión en la BD.");
			System.out.println("Estado SGBD:"+sqle.getSQLState());
			System.out.println("Código error:"+sqle.getErrorCode());
			System.out.println("Mensaje error:"+sqle.getMessage());
			System.exit(1);
		}
		Statement sent,sent2;
		ResultSet cursor,cursor2;
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
				sent2= cnt.createStatement();
				StringBuffer txtSent=new StringBuffer(); 
				txtSent.setLength(0);
				txtSent.append("SELECT * FROM "+tblAlu+" WHERE "+EscribeLee_SQLite.camposBD[0]+
						"='"+idABusc+"'");
				System.out.println("Sentencia consulta:"+txtSent.toString());
				cursor=sent.executeQuery(txtSent.toString());
				txtSent.setLength(0);
				txtSent.append("SELECT count(*) AS 'NumFil' FROM "+tblAlu+" WHERE "+EscribeLee_SQLite.camposBD[0]+
						"='"+idABusc+"'");
				cursor2=sent2.executeQuery(txtSent.toString());;
				EscribeLee_SQLite.listResult(cursor,cursor2);
				cursor.close();
				cursor2.close();
				sent.close();
				sent2.close();
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