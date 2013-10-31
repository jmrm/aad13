package aad_01_02_xxxx;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;


public class BuscaObjSerializ {

	/**
	 * @param args[0] -> nombre del archivo q se va a usar
	 * 		   args[1] -> número del registro a buscar
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String path="/home/jmrm/tmp/"; // insti
		Paciente paciente;	
		/*
		 * Este primer nivel de try es para controlar las posibles excepciones que
		 * se produzcan si no se introduce un nombre de archivo, o un nº de paciente
		 * a buscar, o bien si no es posible acceder a dicho archivo por algún motivo.
		 */
		try { // try 01
			String nF=args[0];
			// guardo en numABusc el nº de paciente que buscamos
			int numABusc=Integer.parseInt(args[1]);
			File fich=new File(path+nF);
			// creamos el flujo de entrada para leer y buscar
			FileInputStream fis = new FileInputStream(fich);
			// para leer después los objetos serializables
			ObjectInputStream ois = new ObjectInputStream(fis);
			// Búsqueda del elemento
			try { // try 01.01
				StringBuilder cad=new StringBuilder();
				/* No es un bucle eterno porque al llegar al fin de archivo se sale
				 * también se sale si encuentra el paciente buscado
				 */
				while (true) {
					paciente = (Paciente) ois.readObject();
					if (paciente.getNumero()==numABusc) break; //encontrado, salgo del while
				}
				cad.append("Encontrado:"+paciente.getNumero()+" "+paciente.getApellidos()+" ");
				cad.append(paciente.getNombre()+" "+paciente.getAnno()+"/"+paciente.getMes()+"/"+paciente.getDia()+" ");
				cad.append(paciente.getDireccion()+" "+paciente.getPuntuacion());
				System.out.println(cad);
			} // end try 01.01
			catch (EOFException eoe) { // catch 01.01.a
				System.out.println("Número de registro: "+numABusc+" no encontrado.");
			}
			catch (ClassNotFoundException cnfe) { //catch 01.01.b
				System.out.println("Excepción: clase no encontrada.");
			}
			finally {
				ois.close();
				System.out.println("Programa terminado.");
			}
		} // end try 01
		catch (ArrayIndexOutOfBoundsException aioobe) {
			System.out.println("Tiene que indicar el nombre del archivo y el número a buscar separados por espacios, en la línea de comandos.");
		}
		catch (IOException ioe) {
			System.out.println("Se ha producido una excepción de E/S");
		}
	}
}
