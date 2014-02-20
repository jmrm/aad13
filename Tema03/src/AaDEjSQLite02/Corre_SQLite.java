package AaDEjSQLite02;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Corre_SQLite {

	/**
	 * Esta clase lleva a cabo el cálculo del factor de correlación calificacion/CI 
	 * de los alumnos cuyo código se le pasa en la línea de comandos 
	 *  
	 * @param args
	 */

	final static String bDAlu ="alumnos13_14.sqlite", tblAlu="alumnos";
	static Connection cnt;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
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
		try {
			int nA=args.length,nReg=0;
			sent= cnt.createStatement();
			sent2=cnt.createStatement();
			StringBuffer txtSent=new StringBuffer(), txtSentCount= new StringBuffer(),
				idAluCalc=new StringBuffer(); 
			txtSent.setLength(0);
			// accedemos a la base de datos
			System.out.print("Alumnos a calcular:");
			/* Montamos en idAluCalc la secuencia de identificadores de alumnos
			 * que vamos a usar en el cálculo para el caso de sólo unos cuantos 
			 * alumnos (parámetros que se pasan en la línea de comandos). Para
			 * su consulta, dado el caso, emplearemos la sintaxis 
			 * SELECT <expresionColumn> FROM <tabla> 
			 * WHERE <valorBusqueda> IN <conjuntoValores> 
			*/
			
			for (int i=0;i<nA;i++) {
				idAluCalc.append("'"+args[i]+"'");
				if (i<(nA-1)) idAluCalc.append(",");
			}
			if (nA==0) { 
				System.out.print("Se calcularán todos los alumnos");
				System.out.println("\n----------------------------");
				txtSent.append("SELECT "+EscribeLee_SQLite.camposBD[0]+","+
						EscribeLee_SQLite.camposBD[1]+","+
						EscribeLee_SQLite.camposBD[3]+"/"+
						EscribeLee_SQLite.camposBD[4]+" AS 'CoefCorre'"+
				"  FROM "+tblAlu+ " ORDER BY 1");
				txtSentCount.append("SELECT COUNT(*) AS 'NumReg' FROM "+tblAlu);
			} else {
				System.out.print("Se calcularán sólo los alumnos ");
				System.out.println(idAluCalc.toString()+"\n----------------------------");
				txtSent.append("SELECT "+EscribeLee_SQLite.camposBD[0]+","+
						EscribeLee_SQLite.camposBD[1]+","+
						EscribeLee_SQLite.camposBD[3]+"/"+
						EscribeLee_SQLite.camposBD[4]+" AS 'CoefCorre'"+
				"  FROM "+tblAlu+ " WHERE "+EscribeLee_SQLite.camposBD[0]+
				" IN ("+idAluCalc.toString()+") ORDER BY 1");
				txtSentCount.append("SELECT COUNT(*) AS 'NumReg' FROM "+tblAlu+ " WHERE "+EscribeLee_SQLite.camposBD[0]+
						" IN ("+idAluCalc.toString()+")");
			}
			System.out.println("Sentencia de consulta:"+txtSent.toString());
			cursor=sent.executeQuery(txtSent.toString());
			cursor2=sent2.executeQuery(txtSentCount.toString());
			while (cursor2.next()) {
				nReg=cursor2.getInt(1); //Nº de filas que tiene el cursor de datos
			}
			if (nReg==0) {
				System.out.println("No hay registros de alumnos.");
			}
			else {
				System.out.print("Se han encontrado ");
				System.out.print(nReg);
				System.out.println(" alumnos.");
				while (cursor.next()) {
					System.out.format("Alumno: %s*%s*%f",
							cursor.getString(EscribeLee_SQLite.camposBD[0]),
							cursor.getString(EscribeLee_SQLite.camposBD[1]),
							cursor.getFloat("CoefCorre")).println();
				}
			}
			cursor.close();
			cursor2.close();
			sent.close();
			sent2.close();
			cnt.close();
		}
		catch (SQLException sqle) {
			System.out.println("Se ha producido un error BD.");
			System.out.println("Estado SGBD:"+sqle.getSQLState());
			System.out.println("Código error:"+sqle.getErrorCode());
			System.out.println("Mensaje error:"+sqle.getMessage());
			System.exit(1);
		}	
		System.exit(0);
	}
}

