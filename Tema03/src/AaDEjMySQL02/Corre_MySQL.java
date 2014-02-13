package AaDEjMySQL02;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Corre_MySQL {

	/**
	 * Esta clase lleva a cabo el cálculo del factor de correlación calificacion/CI 
	 * de los alumnos cuyo código se le pasa en la línea de comandos 
	 *  
	 * @param args
	 */

	final static String bDAlu ="alumnos13_14", tblAlu="alumnos";
	static Connection cnt;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
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
		try {
			int nA=args.length,nReg=0;
			sent= cnt.createStatement();
			StringBuffer txtSent=new StringBuffer(),
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
				txtSent.append("SELECT "+EscribeLee_MySQL.camposBD[0]+","+
						EscribeLee_MySQL.camposBD[1]+","+
						EscribeLee_MySQL.camposBD[3]+"/"+
						EscribeLee_MySQL.camposBD[4]+" AS 'CoefCorre'"+
				"  FROM "+tblAlu+ "ORDER BY 1");
			} else {
				System.out.print("Se calcularán sólo los alumnos ");
				System.out.println(idAluCalc.toString()+"\n----------------------------");
				txtSent.append("SELECT "+EscribeLee_MySQL.camposBD[0]+","+
						EscribeLee_MySQL.camposBD[1]+","+
						EscribeLee_MySQL.camposBD[3]+"/"+
						EscribeLee_MySQL.camposBD[4]+" AS 'CoefCorre'"+
				"  FROM "+tblAlu+ " WHERE "+EscribeLee_MySQL.camposBD[0]+
				" IN ("+idAluCalc.toString()+") ORDER BY 1");
			}
			System.out.println("Sentencia de consulta:"+txtSent.toString());
			cursor=sent.executeQuery(txtSent.toString());
			if (!cursor.last()) {
				System.out.println("No hay registros de alumnos.");
			}
			else {
				nReg=cursor.getRow();
				cursor.beforeFirst();
				System.out.print("Se han encontrado ");
				System.out.print(nReg);
				System.out.println(" alumnos.");
				while (cursor.next()) {
					System.out.format("Alumno: %s*%s*%f",cursor.getString(EscribeLee_MySQL.camposBD[0]),
							cursor.getString(EscribeLee_MySQL.camposBD[1]),
							cursor.getFloat("CoefCorre")).println();
				}
			}
			cursor.close();
			sent.close();
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

