package aad_01_02_xxxx;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class BorraAleat {

	/**
	 * @param args
	 */
	/**
	 * @param args[0] -> nombre del archivo q se va a usar
	 * 		   args[1] -> número del registro a buscar
	 */
	public static void main(String[] args) { 
		// TODO Auto-generated method stub
		String path=LectoEscrAleat.getPath();
		long posic=0;
		try { // try 01
			String nF=args[0];
			int numABusc=Integer.parseInt(args[1]);
			File fich=new File(path+nF);
			// creamos el acceso aleatorio para leer/escribir
			RandomAccessFile raf01 = new RandomAccessFile(fich,"rw");
			// para leer después los registros
			System.out.println("Número a borrar:"+numABusc);
			try { // try 01.01
				String resp;
				StringBuffer cad;
				// No es un bucle eterno porque al llegar al fin de archivo se sale
				int n,borra=0;
				while (true) {
					if (((n=raf01.readInt())==numABusc)) break; //encontrado
					//System.out.println("Número:"+n);
					//posic contiene el offset del inicio del registro actual
					raf01.seek(posic+=LectoEscrAleat.TAM_TOTAL); //salta un registro completo
				}
				//Llamamos al método (éste es estático) que lee
				cad=BuscaAleat.leeReg(raf01);
				System.out.println(cad);
				System.out.println("Borrar [s/n]");
				Scanner entrada=new Scanner(System.in);
				resp=entrada.next();
				/*
				 * Para borrar tras poner el puntero del archivo en la posición adecuada,
				 * podíamos optar simplemente por escribir un 0 en el número de paciente.
				 * Ese 0 haría de marca de borrado (éste es el borrado "virtual" al que se refiere el enunciado)
				 * Pero así dejaríamos el resto de datos sin borrar realmente. 
				 * La segunda opción es borrarlos físicamente todos escribiendo encima
				 * valores en blanco/cero.
				 */
				//System.out.println("La respuesta a la entrada es:"+resp);
				if (resp.equalsIgnoreCase("s")) { //borrar
					//Hemos de posicionar el puntero al comienzo del registro
					// porque la última lectura lo ha desplazado al inicio del
					// registro siguiente
					raf01.seek(raf01.getFilePointer()-LectoEscrAleat.TAM_TOTAL);
					/* El código que sigue (está comentado) sería la opción de borrar solamente
					 * el número de paciente. Al leer el registro lo consideraríamos
					 * borrado solamente con que el número fuera 0.
					 */
					// raf01.writeInt(borra); //escribimos un 0 en el número
					
					/* Pero vamos a usar borrado físico de verdad para lo que
					 * emplearemos el método (estático) sobrecargado escrReg de la 
					 * clase LectoEscrAleat, pasándole solamente el valor del
					 * RandomAccessFile pues el método pone todo lo demás
					 */
					LectoEscrAleat.escrReg(raf01);
					System.out.println("Registro "+numABusc+" borrado.");
				} else System.out.println("Número de registro no borrado.");
				raf01.close();
			} // end try 01.01
			catch (EOFException eoe) { // catch 01.01.a
				System.out.println("Número de registro no encontrado.");
				raf01.close();
			}
		} // end try 01
		catch (ArrayIndexOutOfBoundsException aioobe) {
			System.out.println("Tiene que indicar el nombre del archivo y el número a buscar separados por espacios, en la línea de comandos.");
		}
		catch (IOException ioe) {
			System.out.println("Se ha producido una excepción de E/S");
		}
		finally {
			System.out.println("Programa terminado.");
		}
	}
}

