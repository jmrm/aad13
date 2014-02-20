package AaDEjSQLite02;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class EscribeLee_SQLite {

	/**
	 * Esta clase permite llevar a cabo el proceso de escritura inicial OO,
	 * además implementa métodos para la búsqueda a partir
	 * de un codigo de alumno
	 * 
	 * @param args
	 */
	
	final static String bDAlu ="alumnos13_14.sqlite", tblAlu="alumnos";
	final static char SEP='*';
	final static String[] camposBD= {
			"identificador",
			"nombre",
			"fecha_de_nacimiento",
			"calificacion",
			"coeficiente_de_inteligencia"	
	};
	static Alumnos al;
	static ArrayList<Alumnos> alAlumnos;
	static Connection cnt;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*
		 * En lugar de cargar todo el código del método main con varios bloques anidados
		 * try/catch, ponemos al principio los de la conexión de manera que si ésta no se
		 * puede establecer se termina la ejecución del programa
		 * 
		 */
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
			System.exit(1); // Se termina el programa con código de salida de error
		}
		alAlumnos = new ArrayList<Alumnos>();
		String codigo[]={"A01","A02","A05","C01","E01","E04"};
		String nombre[],fechaN[];
		nombre=new String[6];
		nombre[0]="Álvarez Narváez, Anselmo";
		nombre[1]="Bermudez Calvo, Luisa";
		nombre[2]="Soler Druni, Miguel";
		nombre[3]="Aconta Ciclos, Encarnación";
		nombre[4]="Teruel Almarcha, Dolores";
		nombre[5]="Duarte Felino, Cayetano";
		fechaN=new String[6];
		fechaN[0]="1988/11/21";
		fechaN[1]="1987/06/15";
		fechaN[2]="1988/01/14";
		fechaN[3]="1983/05/06";
		fechaN[4]="1985/12/01";
		fechaN[5]="1986/09/13";
		float califAlum[]={7.56f,8.12f,7,4.7f,4.2f,9,95f,5.1f};
		int coefIntelig[]={120,145,118,98,192,102};
		Statement sent,sent2;
		ResultSet cursor,cursor2;
		// Vamos a cargar los datos en una colección ArrayList<Alumnos>
		cargaAlumno(codigo,nombre,fechaN,califAlum,coefIntelig);
		try {
			sent= cnt.createStatement();
			sent2=cnt.createStatement();
			StringBuffer txtSent=new StringBuffer(); //Para construir la sentencia
			// Borramos los valores anteriores que puedan existir
			// Usamos transacciones
			cnt.setAutoCommit(false);
			txtSent.append("DELETE FROM "+tblAlu);
			sent.executeUpdate(txtSent.toString());
			cnt.commit();
			// Grabación con un foreach
			// Recorre el ArrayList<Alumnos> alAlumnos grabando los datos en la BD
			// Usa transacciones aunque en este caso el autocommit sería suficiente
			cnt.setAutoCommit(false); // Autocommit desactivado
			for (Alumnos alu: alAlumnos) {
				txtSent.setLength(0);
				txtSent.append("INSERT INTO "+tblAlu+" VALUES (");
				txtSent.append("'"+alu.getCodigo()+"',");
				txtSent.append("'"+alu.getNombre()+"',");
				txtSent.append("'"+alu.getFechaNac()+"',");
				txtSent.append(alu.getCalif()+",");
				txtSent.append(alu.getCI()+")");
				System.out.print("Sentencia update:");
				System.out.println(txtSent.toString());
				sent.executeUpdate(txtSent.toString());
				cnt.commit(); //confirmamos la transacción 
			}
			System.out.println("Registros escritos BD SQLite "+bDAlu+"."+tblAlu);
			txtSent.setLength(0);
			txtSent.append("SELECT * FROM "+tblAlu);
			cursor=sent.executeQuery(txtSent.toString());
			txtSent.setLength(0);
			txtSent.append("SELECT count(*) AS 'NumFil' FROM "+tblAlu);
			cursor2=sent2.executeQuery(txtSent.toString());;
			listResult(cursor,cursor2);
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
		System.out.println("Programa terminado");
		System.exit(0);
	}
	
	// Carga las matrices que tienen los datos de productos en el 
	// atributo de la clase EscribeLee_RA alAlumnos (colección de objetos Alumnos)
	public static void cargaAlumno(String[] cod, String[] nom, String[] fNac, float[] cal, int[] ci) {
		for (int i=0; i<cod.length;i++) {
			//al.println();
			alAlumnos.add(new Alumnos(cod[i],nom[i],fNac[i],cal[i],ci[i]));
		}
	}
	
	// Visualiza los resultados obtenidos a partir de un cursor
	// Versión para cursores que son TYPE_FORWARD_ONLY 
	public static int listResult(ResultSet cursor, ResultSet cursor2) throws SQLException{
		int nReg=0;
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
				al=new Alumnos(cursor.getString(camposBD[0]),cursor.getString(camposBD[1]),
						cursor.getString(camposBD[2]),cursor.getFloat(camposBD[3]),
						cursor.getInt(camposBD[4]));
				al.println("Alumno:",SEP);
			}
		}
		return nReg;
	}
}
