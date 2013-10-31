package aad_01_02_xxxx;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
public class LectoEscrObjSerializ{

	/**
	 * @param args
	 * Escribe y después lee 10 elementos de la clase serializable Paciente
	 */ 
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*
		 * Cargamos una serie de matrices con los valores que aparecen en la
		 * tabla de pacientes del enunciado
		 */
		String apellidos[]={"Robles Torres","Azpir Tobla","Arrayán Tabla","Pérez Mateo","De la Luz","Tárrega López","Martínez Gálvez","Luna Sobrio","Canales Luciente","Bravo Alta"};
		String nombre[]={"Ana","Rosa","Pedro","Jesús","Mateo","Lucía","Pedro","Borja","María","Celia"};
		String direccion[];
		direccion=new String[10];
		direccion[0]="Plza del Aire 33, Torrebuchea.";
		direccion[1]="Av. del Toro 12, 2º. Alcántara";
		direccion[2]="Rotonda de la luz s/n. Vallehondo";
		direccion[3]="Camino de arriba 112. Altanova";
		direccion[4]="C/ Luces Blancas 31. Motrida";
		direccion[5]="Av. Mayor 1. Cifuentes";
		direccion[6]="Plaza Mayor s/n. Aldealaplana";
		direccion[7]="C/ Avenida 23. Pueblo Grande";
		direccion[8]="Carril del lucero s/n. Argandilla";
		direccion[9]="Camí del Molí 122. Altafurret";
		int annoNacimiento[]={1958,1969,1993,2001,1977,1955,2002,1988,1971,1984};
		int mesNacimiento[]={4,3,2,12,11,2,3,1,9,5};
		int diaNacimiento[]={21,14,7,15,7,16,1,29,22,31};
		float puntuacion[]={4.66f,10.00f,8.14f,5.05f,7.82f,9.07f,2.44f,3.12f,7.48f,6.67f};
		String path="/home/jmrm/tmp/";
		// Instanciamos un objeto de la clase Paciente
		Paciente paciente;
		/*
		 * Este primer nivel de try es para controlar las posibles excepciones que
		 * se produzcan si no se introduce un nombre de archivo, o bien si no es posible
		 * acceder a dicho archivo por algún motivo.
		 */
		try {
			/*
			 *  Ponemos en nF el primer parámetro de la línea de comandos,
			 *  que se supone que es el nombre del archivo a escribir
			 */
			String nF=args[0];
			File fich=new File(path+nF);
			// creamos el flujo de salida, el archivo se crea/reescribe
			FileOutputStream fos = new FileOutputStream(fich);
			// ahora para escribir los objetos serializables
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			/*
			 * Vamos recorriendo las matrices con los datos que aparecen en la tabla del
			 * enunciado e instanciando objetos Paciente que después son guardados en
			 * disco mediante ObjectOutputStream.writeObject()
			 */
			for (int i=0;i<apellidos.length;i++) {
				paciente = new Paciente(i+1,apellidos[i],nombre[i], annoNacimiento[i], mesNacimiento[i],diaNacimiento[i],direccion[i],puntuacion[i]);
				oos.writeObject(paciente);
			}
			oos.close();
			// creamos el flujo de entrada para leer lo escrito
			FileInputStream fis = new FileInputStream(fich);
			// para leer después los objetos serializables
			ObjectInputStream ois = new ObjectInputStream(fis);
			/*
			 * Este try "interior" es para capturar dos clases de excepción, una la que se puede producir cuando
			 * se trabaja con objetos serializables ClassNotFoundException y la otra para controlar el fin de
			 * archivo al leer
			 */
			try {
				/* Ahora los leemos.
				 * No es un bucle infinito porque se dispara una
				 * EOFException al alcanzar el fin de archivo
				 */
				StringBuilder cad;
				int c=0;
				while (true) {
					cad=new StringBuilder();
					c++;
					paciente = (Paciente) ois.readObject();
					cad.append("Registro nº "+c+":"+paciente.getNumero()+" "+paciente.getApellidos()+" ");
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
				// ois.close(); no se ejecutaría si se dispara ClassNotFoundException
			}
			finally {
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
