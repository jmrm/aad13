package AaDEjDb4o02;

import java.util.Scanner;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.ext.Db4oException;

public class Busca_DB4o {

		/**
		 * @param args
		 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String resp;
		ObjectContainer db4o=null;
		try { // try 01
			String nF="DBAlumnos.db4o";
			// accedemos a la base de datos
			db4o=Db4oEmbedded.openFile(nF);
			Scanner entrada=new Scanner(System.in);
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
				Alumnos al= new Alumnos();
				al.setCodigo(idABusc);
				final ObjectSet<Alumnos> result=db4o.queryByExample(al);
				if (result.size()==0) {
					System.out.println("Código "+idABusc+" no encontrado.");
				} else {
					while (result.hasNext()) {
						al=result.next();
						al.println("Datos alumno:", '*');
					}
				}
			}
			entrada.close();
		} // end try 01
		catch (Db4oException DE) {
			System.out.print("Se ha producido una excepción del sistema Db4o:");
			System.out.println(DE.getMessage());
		}
		finally {
			db4o.close();
		}		
	}
}