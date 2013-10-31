package aad_01_02_xxxx;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class LeeObjSerializ {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int c;
		String path="/home/jmrm/tmp/"; // insti
		Paciente paciente;
		try {
			String nF=args[0];
			File fich=new File(path+nF);
			// creamos el flujo de entrada para leer el archivo
			FileInputStream fis = new FileInputStream(fich);
			// para leer los objetos serializables
			ObjectInputStream ois = new ObjectInputStream(fis);
			c=0;
			try {
				/* No es un bucle infinito porque se dispara una
				 * EOFException al alcanzar el fin de archivo
				 */
				while (true) {
					StringBuilder cad=new StringBuilder();
					c++;
					paciente = (Paciente) ois.readObject();
					cad.append("Registro "+c+":"+paciente.getNumero()+" "+paciente.getApellidos()+" ");
					cad.append(paciente.getNombre()+" "+paciente.getAnno()+"/"+paciente.getMes()+"/"+paciente.getDia()+" ");
					cad.append(paciente.getDireccion()+" "+paciente.getPuntuacion());
					System.out.println(cad);
				}
			}
			catch (ClassNotFoundException cnfe) {
				System.out.println("Excepción: clase no encontrada.");
			}
			catch (EOFException eofe) {
				System.out.println("Se ha terminado de leer");
				ois.close();
			}
		} // end try
		catch (ArrayIndexOutOfBoundsException aioobe) {
			System.out.println("Tiene que indicar el nombre del archivo en la línea de comandos.");
		}
		catch (IOException ioe) {
			System.out.println("Se ha producido una excepción de E/S");
		}
	}

}
