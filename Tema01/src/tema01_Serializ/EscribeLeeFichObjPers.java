package tema01_Serializ;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class EscribeLeeFichObjPers {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Persona persona; // objeto de la clase serializable
		String nombres[]= {"Alberto","Ana","Anselmo","Juan","Manuel","Pedro"};
		int edades[]={21,12,33,14,67,9},c;
		String ciudades[]= {"Murcia","Alhama","Cartagena","New York","Córdoba","Asunción"};
		File fich = new File ("/home/jmrm/tmp/FichObjPers.dat");
		try {
			// creamos el flujo de salida
			FileOutputStream fos = new FileOutputStream(fich);
			// creamos el flujo de entrada
			FileInputStream fis = new FileInputStream(fich);
			// instanciamos las clases para escritura y lectura de objetos
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			ObjectInputStream ois = new ObjectInputStream(fis);
			// Grabamos los datos
			for (int i=0;i<edades.length;i++) {
				persona = new Persona(nombres[i],edades[i],ciudades[i]);
				oos.writeObject(persona);
			}
			oos.close();
			c=0;
			try {
				/* Esta es la lectura y visualización.
				 * No es un bucle infinito porque se dispara una
				 * EOFException al alcanzar el fin de archivo
				 */
				while (true) {
					c++;
					persona = (Persona) ois.readObject();
					System.out.println("Registro nº "+c+":"+persona.getNombre()+" "+persona.getEdad()+" "+persona.getCiudad());
				}
			}
			catch (ClassNotFoundException cnfe) {
				System.out.println("Excepción: clase no encontrada.");
			}
			catch (EOFException eofe) {
				System.out.println("Se ha terminado de leer");
			}
			ois.close();
		}
		catch (IOException ioe) {
			System.out.println("Se ha producido una excepción de E/S");
		}
	}


}
