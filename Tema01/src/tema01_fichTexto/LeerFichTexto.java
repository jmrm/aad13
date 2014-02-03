package tema01_fichTexto;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class LeerFichTexto {

	/**
	 * Programa para trabajar con archivos de texto
	 * recibe el nombre del archivo de la línea de comandos
	 */
	public static void main(String[] args) {
		String pF="El archivo indicado"; //declaramos pF fuera de try para que lo vean en catch
		try {
			int ch;
			String lin;
			pF=args[0];
			File f = new File (pF);
			FileReader fr = new FileReader(f);
			// lectura carácter a carácter
			System.out.println("Lectura carácter a carácter");
			while ((ch=fr.read())!=-1) { // mientras queden caracteres sin leer
				System.out.print((char) ch);
			}
			fr.close();
			// lectura mediante buffer
			System.out.println("--------------");
			System.out.println("Lectura mediante buffer");
			BufferedReader bfr=new BufferedReader(new FileReader(f));
			while ((lin=bfr.readLine())!=null) {
				System.out.println(lin);
			}
			bfr.close();
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
