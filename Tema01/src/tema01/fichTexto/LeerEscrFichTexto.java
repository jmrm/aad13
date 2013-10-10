package tema01.fichTexto;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class LeerEscrFichTexto {
		
	
	// Método estático que copiará el archivo caracter por caracter.
	private static boolean copiarPorCaracteres(File aCopiar, File aEscribir) {

		// Declaramos las instancias del lector y el escritor.
		FileReader entrada = null;
		FileWriter salida = null;

		// Contendrá el caracter leído (en tipo entero).
		int caracterLeido = 0;

		try {

			// Instanciamos el lector y el escritor.
			entrada = new FileReader(aCopiar);
			salida = new FileWriter(aEscribir);

			while (!((caracterLeido = entrada.read())==-1)) {		
				salida.write(caracterLeido);
			}
			// Cerramos la entrada y salida de datos.
			entrada.close();
			salida.close();
			return true;
		}
		catch (IOException e) {
			System.out.println("Por alguna razón indeterminada no se ha podido efectuar la copia.");
			return false;
		}
	}

	// Método estático que hará la copia del fichero mediante bufers de lectura y escritura.
	private static boolean copiarPorBuffer(File aCopiar, File aEscribir) {
		// Declaramos las instancias de los búfers.
		BufferedReader buferLectura = null;
		BufferedWriter buferEscritura = null;


		// Contendrá la linea leída por el bufer de lectura.
		String lineaLeida = "";

		try {

			// Instanciamos los bufers
			buferLectura = new BufferedReader(new FileReader(aCopiar));
			buferEscritura = new BufferedWriter(new FileWriter(aEscribir));

			// Leemos la línea y comprobamos que no se haya alcanzado el fin
			// de archivo
			while (!((lineaLeida = buferLectura.readLine())==null)) {
				buferEscritura.write(lineaLeida);
				buferEscritura.newLine();
			}

			// Cerramos los búfers.
			buferLectura.close();
			buferEscritura.close();	
			return true;
		} 
		catch (IOException e) {
			System.out.println("Por alguna razón indeterminada no se ha podido efectuar la copia.");
			return false;
		}
	}

	// Método estático que imprimirá un fichero
	private static void imprimirFichero(File fichero) {

		// Declaramos la instancia del lector.
		FileReader entrada = null;

		// Contendrá el caracter leído (en tipo entero).
		int caracterLeido = 0;

		try {

			// Instanciamos el lector
			entrada = new FileReader(fichero);

			while (true) {

				caracterLeido = entrada.read();

				if (caracterLeido == -1) { // -1 indica el fin de fichero.

					break;
				}

				//Imprimimos
				System.out.print((char) caracterLeido);
			}

			// Cerramos la entrada de datos.
			entrada.close();

		}
		catch (IOException e) {

			e.printStackTrace();
		}
	}

	// Las rutas de fichero y la opción de copia se insertaran por argumento
	// Método invocable desde la línea de comandos
	public static void main(String[] args) {

		// Creamos los objetos Files de los ficheros que utilizaremos y/o crearemos
		
		/*
		 * Fase 1: filtrado de parámetros aportados en la línea de comandos
		 */
		
		File aCopiar = null;
		File aEscribir = null;
		String tipo = null;
		boolean resultado = false;
		
		try { 
			aCopiar = new File(args[0]);
			aEscribir = new File(args[1]);
			tipo = args[2].toLowerCase();
		}
		catch (ArrayIndexOutOfBoundsException aioobe) {
			System.out.println("Ha de indicar parámetros correctos en la línea de comandos.");
			System.out.println("Programa terminado.");
			return;
		}
		if ((!aCopiar.exists())||(!aCopiar.canRead())) {
			System.out.println("El archivo a copiar no existe o no se puede leer.");
			System.out.println("Programa terminado.");
			return;
		}
		if ((aEscribir.exists())&&(aEscribir.isDirectory())) {
			System.out.println("No se puede realizar la copia porque no tiene permiso para ");
			System.out.println("borrar "+args[1]);
			System.out.println("Programa terminado.");
			return;
		}
		
		/*
		 * Según el argumento de opción de copia:
		 * 
		 * c o C --> copia caracter por caracter
		 * b o B --> copia mediante búfers
		 */
		
		if (!(tipo.equals("c")||tipo.equals("b"))) {
			System.out.println("El tercer parámetro ha de ser {c|C|b|B}");
			System.out.println("Programa terminado.");
			return;
		}
		
		/*
		 * Fase 2: copiado
		 */
		
		if (tipo.equals("c")) {
			resultado=LeerEscrFichTexto.copiarPorCaracteres(aCopiar, aEscribir);
		}
		else if (tipo.equals("b")) {
			resultado=LeerEscrFichTexto.copiarPorBuffer(aCopiar, aEscribir);
		}
		
		/*
		 * Fase 3: visualización de resultados
		 */
		if (resultado) {
			System.out.println("\n-ARCHIVO A COPIAR-\n");
			LeerEscrFichTexto.imprimirFichero(aCopiar);
			System.out.println("\n\n-ARCHIVO A ESCRIBIR-\n");
			LeerEscrFichTexto.imprimirFichero(aEscribir);	
		} 

		System.out.println();
		
		System.exit(0);
	}
}
