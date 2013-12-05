package aad_01_03_xxxx;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;


public class P_OO {

	/**
	 * Esta clase lee desde el archivo RandomAccess y escribe los objetos resultantes
	 * en un archivo para que se pueda implementar su persistencia. Finalmente vuelve a
	 * leer para mostrar los valores guardados.
	 *  
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		WR_RA wr_ra=new WR_RA();
		try {
			String nF[]={"RAPro.dat","OOPro.dat"};
			File fich0=new File(nF[0]);
			File fich1=new File(nF[1]);
			//creamos el flujo para leer el archivo en modo randomAccess
			RandomAccessFile raf01=new RandomAccessFile(fich0,"r");
			// creamos los flujos de salida y entrada para leer y escribir el archivo de objetos
			FileOutputStream fos = new FileOutputStream(fich1);
			FileInputStream fis = new FileInputStream(fich1);
			// para escribir y leer los objetos serializables
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			ObjectInputStream ois = new ObjectInputStream(fis);
			System.out.println("Registros leídos de fichero:"+nF[0]);
			Productos pr=new Productos();
			wr_ra.setStatusL(pr.leeRA(raf01));
			while (wr_ra.getStatusL()) {
				if (pr.getCodigo().trim().length()!=0) {
					pr.println("Artículo:",wr_ra.SEP);
					pr.grabaOO(oos);
				}
				wr_ra.setStatusL(pr.leeRA(raf01));
			}
			if (!wr_ra.getStatusL()) raf01.close();
			System.out.println("Registros escritos en fichero:"+nF[1]);
			wr_ra.setStatusL(pr.leeOO(ois));
			while (wr_ra.getStatusL()) {
				pr.println("Artículo:", wr_ra.SEP);
				wr_ra.setStatusL(pr.leeOO(ois));
			}
			if (!wr_ra.getStatusL()) ois.close();
		}
		catch (IOException ioe) {
			System.out.println("Se ha producido una excepción de E/S");
		}
		catch (ClassNotFoundException cnfe) {
			System.out.println("Se ha producido una excepción de ClassNotFound");
		}
	}

}
