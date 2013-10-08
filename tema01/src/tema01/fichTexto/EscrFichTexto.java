package tema01.fichTexto;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class EscrFichTexto {

	/**
	 * Programa para trabajar con archivos de texto
	 * recibe el nombre del archivo de la línea de comandos
	 */
	public static void main(String[] args) {
		String pF="El archivo indicado"; //declaramos pF fuera de try para que lo vean en catch
		try {
			int i;
			boolean esAppend=false;
			String lin="Éste es el mensaje que le vamos a añadir al archivo.";
			String cadLe;
			char[] aCad = lin.toCharArray();
			pF=args[0];
			File f = new File (pF);
			if (f.exists()) { // el archivo existe
				esAppend=true;
			}
			FileWriter fw = new FileWriter(f,esAppend);
			// escritura carácter a carácter
			System.out.println("Escritura carácter a carácter");
			for (i=0; i<aCad.length; i++) {
				fw.write(aCad[i]); 
			}
			fw.append('*');
			fw.close();
			esAppend=true; // Ya ha pasado la primera escritura
			
			// escritura con buffer
			System.out.println("Escritura con búffer.");
			BufferedWriter fbw = new BufferedWriter (new FileWriter(f,esAppend));
			for (i=0;i<100;i++){
				fbw.write("Esta es la fila número "+i);
				fbw.newLine();
			}
			fbw.close();
			
			// Leemos como queda el archivo
			System.out.println("Lectura con búffer.");
			BufferedReader fbr = new BufferedReader (new FileReader (f));
			while ((cadLe=fbr.readLine())!=null) {
				System.out.println(cadLe);
			}
			fbr.close();
		}	
		catch (ArrayIndexOutOfBoundsException aioobe) {
			System.out.println("Ha de indicar un nombre de archivo en la línea de comandos.");
			System.out.println("Programa terminado.");
		}
		catch (FileNotFoundException fnfe) {
			System.out.println(pF+" no existe o no es un archivo. Programa terminado.");
		}
		catch (IOException ioe) {
			System.out.println("Ha habido algún problema con el archivo:");
			System.out.println(ioe.getMessage());
			System.out.println("Programa terminado.");
		}
	}

}
