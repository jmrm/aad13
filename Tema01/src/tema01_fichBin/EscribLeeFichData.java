package tema01_fichBin;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class EscribLeeFichData {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String nombres[]= {"Alberto","Ana","Anselmo","Juan","Manuel","Pedro"},n;
		int edades[]={21,12,33,14,67,9},e,c;
		String path="/home/jmrm/tmp/";
		File fich = new File (path+"FichData.dat");
		try {
			// creamos el flujo de salida
			FileOutputStream fos = new FileOutputStream(fich);
			// creamos el flujo de entrada
			FileInputStream fis = new FileInputStream(fich);
			// Creamos los flujos para escribir y leer datos primitivos
			DataOutputStream dos = new DataOutputStream(fos);
			DataInputStream dis = new DataInputStream(fis);
			// Grabamos los datos (edades.length elementos)
			for (int i=0;i<edades.length;i++) {
				dos.writeUTF(nombres[i]);
				dos.writeInt(edades[i]);
			}
			dos.close();
			c=0;
			try {
				/*
				 * El siguiente no es un bucle infinito porque los métodos
				 * readUTF() y readInt() disparan la excepción EOFException
				 * si alcanzan el fin de archivo 
				 */
				while (true) {
					c++;
					n=dis.readUTF();
					e=dis.readInt();
					System.out.println("Registro nº "+c+":"+n+" "+e);
				}
			}
			catch (EOFException eofe) {
				System.out.println("Se ha terminado de leer");
			}
			dis.close();
		}
		catch (IOException ioe) {
			System.out.println("Se ha producido una excepción de E/S");
		}
	}
}
