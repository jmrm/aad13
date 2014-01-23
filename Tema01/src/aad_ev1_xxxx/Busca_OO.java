package aad_ev1_xxxx;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.util.Scanner;


public class Busca_OO {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String resp;
		EscribeLee_OO escribeLee_OO = new EscribeLee_OO();
		try { // try 01
			String nF="OOAlu.dat";
			File fich=new File(nF);
			// flujo de entrada para leer el archivo de objetos
			FileInputStream fis = null;
			// para leer los objetos serializables	
			ObjectInputStream ois = null;
			Scanner entrada=new Scanner(System.in);
			Alumnos al= new Alumnos();
			while (true) {
				System.out.println("Introduzca el código a buscar (=XXX= termina):");
				resp=entrada.next();
				if (resp.equalsIgnoreCase("=XXX=")) {
					System.out.println("Búsqueda terminada.");
					break;//termina
				}
				String idABusc=resp;
				// para leer después los registros
				System.out.println("Código a buscar:"+idABusc);
				fis=new FileInputStream(fich);
				ois=new ObjectInputStream(fis);
				escribeLee_OO.buscaRegOO(ois, idABusc,al);
				switch (escribeLee_OO.getStatusE()){
					case 0: al.println("Datos alumno:", '*');break;
					case 1: System.out.println("Código "+idABusc+" no encontrado.");break;
					case 2: System.out.println(escribeLee_OO.getCadReturn());break;
					default:;
				}
				ois.close();
			}
		} // end try 01
		catch (IOException ioe) {
			System.out.println("Se ha producido una excepción de E/S");
		}		
	}
}

