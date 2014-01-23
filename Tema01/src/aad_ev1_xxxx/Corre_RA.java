package aad_ev1_xxxx;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Corre_RA {

	/**
	 * Esta clase lleva a cabo el cálculo del factor de correlación calificacion/CI 
	 * de los alumnos cuyo código se le pasa en la línea de comandos 
	 *  
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EscribeLee_RA esle_ra=new EscribeLee_RA();
		String nF="RAAlu.dat",idABusc="";
		float factor;
		try { // try 01
			int nA=args.length,unidAPed;
			Alumnos al=new Alumnos();
			File fich=new File(nF);
			// creamos el acceso aleatorio para leer
			RandomAccessFile raf01 = new RandomAccessFile(fich,"r");
			// para leer después los registros
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
				esle_ra.buscaRegAA(raf01, 0, idABusc,al);
				switch (esle_ra.getStatusE()){
					case 0: //lo ha encontrado
						factor=al.getCalif()/al.getCI();
						System.out.print("Alumno "+al.getCodigo());
						System.out.print(" factor Calificación/CI:");
						System.out.println(factor);
						break;
					case 1: System.out.println("Código "+idABusc+" no encontrado.");break;
					case 2: System.out.println(esle_ra.getCadReturn());break;
					default: ;
				}
			}
			// Todos los alumnos
			if (nA==0) {
				esle_ra.setStatusL(al.leeRA(raf01));
				while (esle_ra.getStatusL()) {
					// Sólo si no está borrado
					if (al.getCodigo().trim().length()!=0) {
						factor=al.getCalif()/al.getCI();
						System.out.print("Alumno "+al.getCodigo());
						System.out.print(" factor Calificación/CI:");
						System.out.println(factor);
					}
					esle_ra.setStatusL(al.leeRA(raf01));
				}
			}
			raf01.close();		
		} // end try 01
		catch (ArrayIndexOutOfBoundsException aioobe) {
			System.out.println("Tiene que indicar el nombre del archivo y los códigos de los alumnos a calcular separados por espacios, en la línea de comandos.");
		}
		catch (IOException ioe) {
			System.out.println("Se ha producido una excepción de E/S");
		}
	}

}

