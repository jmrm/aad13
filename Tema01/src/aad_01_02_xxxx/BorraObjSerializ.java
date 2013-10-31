package aad_01_02_xxxx;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;


public class BorraObjSerializ {

	/**
	 * @param args[0] -> nombre del archivo q se va a usar
	 * 		   args[1] -> número del registro a borrar
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
			String nF=args[0],resp;
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
				ois.close();
				cad.append("Encontrado:"+paciente.getNumero()+" "+paciente.getApellidos()+" ");
				cad.append(paciente.getNombre()+" "+paciente.getAnno()+"/"+paciente.getMes()+"/"+paciente.getDia()+" ");
				cad.append(paciente.getDireccion()+" "+paciente.getPuntuacion());
				System.out.println(cad);
				System.out.println("Borrar [s/n]");
				// Tomamos la respuesta del usuario desde el teclado
				Scanner entrada=new Scanner(System.in);
				resp=entrada.next();
				/*
				 * Para borrar volvemos a abrir el archivo para leer, abrimos otro provisional para escribir,
				 * vamos leyendo registros y los guardamos en el otro provisional. Cuando terminamos borramos
				 * el original y renombramos el provisional.
				 */
				if (resp.equalsIgnoreCase("s")) { //borrar
					/*
					 * Es imprescindible reposicionar el puntero de lectura al comienzo del stream
					 * y la forma más fácil de hacerlo es volver a crear FileInputStream y
					 * ObjectInputStream ya que vamos a ir leyendo desde el inicio y copiando sólo 
					 * los pacientes que no hay que borrar
					 */
					fis=new FileInputStream(fich);
					ois=new ObjectInputStream(fis);
					//Creamos un nuevo fichero con el mismo nombre y la extensión .provis
					File fich2=new File(path+nF+".provis");
					FileOutputStream fos=new FileOutputStream(fich2);
					ObjectOutputStream oos=new ObjectOutputStream(fos); 
					/*
					 * Este try más interno es el que controla el proceso de copia de los registros originales
					 * en el archivo provisional
					 */
					try { //try 01.01.01
						// Bucle que copia todos los registros salvo el que se borra
						System.out.println("Archivo resultante:");
						int c=0;
						while (true) {
							paciente = (Paciente) ois.readObject();
							if (paciente.getNumero()==numABusc); //no hace nada
							else { //copia en el archivo provisional
								c++;
								oos.writeObject(paciente);
								// Reutilizamos el StringBuilder cad para visualizar la información de todos los 
								// pacientes que no borramos
								cad.setLength(0);
								cad.append("Registro nº "+c+":"+paciente.getNumero()+" "+paciente.getApellidos()+" ");
								cad.append(paciente.getNombre()+" "+paciente.getAnno()+"/"+paciente.getMes()+"/"+paciente.getDia()+" ");
								cad.append(paciente.getDireccion()+" "+paciente.getPuntuacion());
								System.out.println(cad);
							}
						}
					}
					catch (EOFException eoe) { //catch 01.01.01.a
						// Ha acabado de leer
						ois.close(); oos.close();
						// Se procede al borrado del antiguo y renombrado del nuevo como el antiguo
						if (fich.delete()) { 
							fich2.renameTo(fich);
							System.out.println("Borrado correcto");
						} else {//no se puede borrar el primer archivo, ni renombrar el otro
							System.out.println("No se borra el registro."); 
							fich2.delete();
						}
					}
				} else // No contesta s ó S a la pregunta 
					System.out.println("No se borra el registro.");
			} // end try 01.01
			catch (EOFException eoe) { // catch 01.01.a
				System.out.println("Número de registro "+numABusc+" no encontrado.");
				ois.close();
			}
			catch (ClassNotFoundException cnfe) { //catch 01.01.b
				System.out.println("Excepción: clase no encontrada.");
			}
		} // end try 01
		catch (ArrayIndexOutOfBoundsException aioobe) { //catch 01.b
			System.out.println("Tiene que indicar el nombre del archivo y el número a buscar separados por espacios, en la línea de comandos.");
		}
		catch (IOException ioe) { //catch 01.c
			System.out.println("Se ha producido una excepción de E/S");
		}
	}
}
