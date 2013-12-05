package aad_01_03_xxxx;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;


public class D_RA {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String resp;
		WR_RA wr_ra=new WR_RA();
		try { // try 01
			String nF="RAPro.dat";
			File fich=new File(nF);
			// creamos el acceso aleatorio para leer
			RandomAccessFile raf01 = new RandomAccessFile(fich,"rw");
			Scanner entrada=new Scanner(System.in);
			Productos pr= new Productos();
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
				wr_ra.buscaRegAA(raf01, 0, idABusc, pr);
				switch (wr_ra.getStatusE()){
					case 0: pr.println("Datos producto:", '*');
						Scanner entrada2=new Scanner(System.in);
						System.out.println("Borrar s/n:");
						resp=entrada.next();
						if (resp.equalsIgnoreCase("s")) {
							pr.setCodigo("");
							pr.grabaRA(raf01,wr_ra.getPos());
						System.out.println("Producto borrado");
						}
					break;
					case 1: System.out.println("Código "+idABusc+" no encontrado.");break;
					case 2: System.out.println(wr_ra.getCadReturn());break;
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

