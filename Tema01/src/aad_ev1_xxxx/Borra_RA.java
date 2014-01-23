package aad_ev1_xxxx;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;


public class Borra_RA {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String resp;
		EscribeLee_RA esle_ra=new EscribeLee_RA();
		try { // try 01
			String nF="RAAlu.dat";
			File fich=new File(nF);
			// creamos el acceso aleatorio para leer
			RandomAccessFile raf01 = new RandomAccessFile(fich,"rw");
			Scanner entrada=new Scanner(System.in);
			Alumnos al= new Alumnos();
			while (true) {
				System.out.println("Introduzca el código a buscar (=+= termina):");
				resp=entrada.next();
				if (resp.equalsIgnoreCase("=+=")) {
					System.out.println("Búsqueda terminada.");
					break;//termina
				}
				String idABusc=resp;
				// para leer después los registros
				System.out.println("Código a buscar:"+idABusc);
				esle_ra.buscaRegAA(raf01, 0, idABusc, al);
				switch (esle_ra.getStatusE()){
					case 0: al.println("Datos alumno:", '*');
						Scanner entrada2=new Scanner(System.in);
						System.out.println("Borrar s/n:");
						resp=entrada2.next();
						if (resp.equalsIgnoreCase("s")) {
							al.setCodigo("");
							al.grabaRA(raf01,esle_ra.getPos());
						System.out.println("Alumno borrado");
						}
					break;
					case 1: System.out.println("Código "+idABusc+" no encontrado.");break;
					case 2: System.out.println(esle_ra.getCadReturn());break;
					default:;
				}
			}
			raf01.close();
		} // end try 01
		catch (IOException ioe) {
			System.out.println("Se ha producido una excepción de E/S");
		}		
	}
}

