package aad_ev1_xxxx;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.util.Scanner;


public class Borra_OO {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String resp;
		EscribeLee_OO escribeLee_OO = new EscribeLee_OO();
		try { // try 01
			String nF="OOAlu.dat",nF2="OOAlu.tmp",nF3="OOAlu.dat.bak";
			File fich=new File(nF); File fich1=new File(nF2); File fich2=new File(nF3);
			// flujos de entrada/salida para leer/escribir el archivo de objetos
			FileInputStream fis = null;
			FileOutputStream fos = null;
			// para leer/escribir los objetos serializables	
			ObjectInputStream ois = null;
			ObjectOutputStream oos = null;
			Scanner entrada=new Scanner(System.in);
			Alumnos al= new Alumnos();
			while (true) {
				System.out.println("Introduzca el código a buscar (=XXX= termina):");
				resp=entrada.next();
				if (resp.equalsIgnoreCase("=XXX=")) {
					System.out.println("Búsqueda/borrado terminado.");
					break;//termina
				}
				String idABusc=resp;
				// para leer después los registros
				System.out.println("Código a buscar:"+idABusc);
				fis=new FileInputStream(fich);
				ois=new ObjectInputStream(fis);
				escribeLee_OO.buscaRegOO(ois, idABusc,al);
				ois.close();
				System.out.println("El estatus devuelto es "+escribeLee_OO.getStatusE());
				switch (escribeLee_OO.getStatusE()){
					case 0: al.println("Datos alumno:", '*');
					Scanner entrada2=new Scanner(System.in);
					System.out.println("Borrar s/n:");
					resp=entrada2.next();
					if (resp.equalsIgnoreCase("s")) {
						try {
							fis=new FileInputStream(fich);
							fos=new FileOutputStream(fich1);
							ois=new ObjectInputStream(fis);
							oos=new ObjectOutputStream(fos);
							escribeLee_OO.setStatusL(al.leeOO(ois));
							while (escribeLee_OO.getStatusL()) {
								if (al.getCodigo().equalsIgnoreCase(idABusc)) {
									al.setCodigo("");
								}
								al.grabaOO(oos);
								System.out.println("Grabado el registro de código: "+al.getCodigo());
								escribeLee_OO.setStatusL(al.leeOO(ois));
							}
						}
						finally {
							ois.close();
							oos.close();
							if (fich2.exists()) fich2.delete();
							fich.renameTo(fich2);
							fich1.renameTo(fich);
							System.out.println("Alumno borrado");
						}
					}
					break;
					case 1: System.out.println("Código "+idABusc+" no encontrado.");break;
					case 2: System.out.println(escribeLee_OO.getCadReturn());break;
					default:;
				}
			}
		} // end try 01
		catch (ClassNotFoundException cnfe) {
			System.out.println("Se ha producido una excepción de clase no encontrada");
		}
		catch (IOException ioe) {
			System.out.println("Se ha producido una excepción de E/S");
		}		
	}
}

