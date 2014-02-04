package AaDEjDb4o02;

import java.util.Iterator;
import java.util.List;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.ext.Db4oException;

public class Corre_DB4o {

	/**
	 * Esta clase lleva a cabo el cálculo del factor de correlación calificacion/CI 
	 * de los alumnos cuyo código se le pasa en la línea de comandos 
	 *  
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String nF="DBAlumnos.db4o",idABusc="";
		ObjectContainer db4o=null;
		Alumnos al=null;
		try { // try 01
			int nA=args.length;
			// accedemos a la base de datos
			db4o=Db4oEmbedded.openFile(nF);
			System.out.print("Alumnos a calcular:");
			for (int i=0;i<nA;i++) {
				System.out.print(args[i]);
				if (i<(nA-1)) System.out.print(",");
			}
			if (nA==0) System.out.print("Se calcularán todos los alumnos");
			System.out.println("\n----------------------------");
			// Sólo algunos alumnos
			for (int i=0;i<nA;i++) {
				idABusc=args[i];	
				al=new Alumnos();
				al.setCodigo(idABusc);
				ObjectSet<Alumnos> result=db4o.queryByExample(al);
				obtenFactor(result);
			}
			// Todos los alumnos
			if (nA==0) {
				al=new Alumnos();
				ObjectSet<Alumnos> result=db4o.queryByExample(al);
				obtenFactor(result);
				
			}	
		} // end try 01
		catch (ArrayIndexOutOfBoundsException aioobe) {
			System.out.println("Tiene que indicar el nombre del archivo y los códigos de los alumnos a calcular separados por espacios, en la línea de comandos.");
		}
		catch (Db4oException DE) {
			System.out.print("Se ha producido una excepción del sistema Db4o:");
			System.out.println(DE.getMessage());
		}
		finally {
			db4o.close();
		}
	}
	
	private static void obtenFactor (List<Alumnos> result) {
		Alumnos al=new Alumnos();
		float factor;
		Iterator<Alumnos> it=result.iterator();
		if (result.size()==0) {
			System.out.println("Alumno no encontrado.");
		} else {
			while (it.hasNext()) {
				al=it.next();
				factor=al.getCalif()/al.getCI();
				System.out.print("Alumno "+al.getCodigo());
				System.out.print(" factor Calificación/CI:");
				System.out.println(factor);
			}
		}
	}

}

