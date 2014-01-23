package aad_ev1_xxxx;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.RandomAccessFile;

public class Corre_OO {

	/**
	 * Esta clase lleva a cabo el cálculo del factor de correlación calificacion/CI 
	 * de los alumnos cuyo código se le pasa en la línea de comandos 
	 *  
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EscribeLee_OO escribeLee_OO=new EscribeLee_OO();
		String nF="OOAlu.dat",idABusc="";
		float factor;
		try { // try 01
			int nA=args.length,unidAPed;
			Alumnos al=new Alumnos();
			File fich=new File(nF);
			// flujo de entrada para leer el archivo de objetos
			FileInputStream fis = null;
			// para leer los objetos serializables	
			ObjectInputStream ois = null;
			System.out.print("Alumnos a calcular:");
			for (int i=0;i<nA;i++) {
				System.out.print(args[i]);
				if (i<(nA-1)) System.out.print(",");
			}
			if (nA==0) System.out.print("Se calcularán todos los alumnos");
			System.out.println("\n----------------------------");
			for (int i=0;i<nA;i++) {
				// Siempre comienza a buscar desde el principio
				// En este caso podría ser más efectivo recorrer el archivo y para cada
				// registro buscar con un for si coinciden los parámetros pasados en args[]
				idABusc=args[i];	
				fis=new FileInputStream(fich);
				ois=new ObjectInputStream(fis);
				escribeLee_OO.buscaRegOO(ois, idABusc,al);
				switch (escribeLee_OO.getStatusE()){
					case 0: //lo ha encontrado
						factor=al.getCalif()/al.getCI();
						System.out.print("Alumno "+al.getCodigo());
						System.out.print(" factor Calificación/CI:");
						System.out.println(factor);
						break;
					case 1: System.out.println("Código "+idABusc+" no encontrado.");break;
					case 2: System.out.println(escribeLee_OO.getCadReturn());break;
					default: ;
				}
				ois.close();
			}
			// Todos los alumnos
			if (nA==0) {
				fis=new FileInputStream(fich);
				ois=new ObjectInputStream(fis);
				escribeLee_OO.setStatusL(al.leeOO(ois));
				while (escribeLee_OO.getStatusL()) {
					// Sólo si no está borrado
					if (al.getCodigo().trim().length()!=0) {
						factor=al.getCalif()/al.getCI();
						System.out.print("Alumno "+al.getCodigo());
						System.out.print(" factor Calificación/CI:");
						System.out.println(factor);
					}
					escribeLee_OO.setStatusL(al.leeOO(ois));
				}
				ois.close();
			}	
		} // end try 01
		catch (ArrayIndexOutOfBoundsException aioobe) {
			System.out.println("Tiene que indicar el nombre del archivo y los códigos de los alumnos a calcular separados por espacios, en la línea de comandos.");
		}
		catch (ClassNotFoundException cnfe) {
			System.out.println("Se ha producido una excepción de clase no encontrada");
		}
		catch (IOException ioe) {
			System.out.println("Se ha producido una excepción de E/S");
		}
	}

}

