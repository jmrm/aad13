package aad_01_02_xxxx;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class LeeAleat {

	/**
	 * Lee el archivo de pacientes detectando los registros borrados
	 * @param args[0] -> nombre del archivo q se va a usar
	 */
	public static void main(String[] args) { 
		// TODO Auto-generated method stub
		String path=LectoEscrAleat.getPath();
		try { // try 01
			String nF=args[0];
			File fich=new File(path+nF);
			// creamos el acceso aleatorio para leer
			RandomAccessFile raf01 = new RandomAccessFile(fich,"r");
			// para leer después los registros
			System.out.println("Registros del archivo "+nF);
			try { // try 01.01
				StringBuffer cad;
				// No es un bucle eterno porque al llegar al fin de archivo se sale
				while (true) {
					if (raf01.readInt()==0) { //borrado (número de paciente = 0)
						System.out.println("Registro borrado.");
						//Saltamos hasta el comienzo del siguiente registro
						raf01.seek(raf01.getFilePointer()-4+LectoEscrAleat.TAM_TOTAL);
					}
					else {
						cad=BuscaAleat.leeReg(raf01);
						System.out.println(cad);
					}	
				}
			} // end try 01.01
			catch (EOFException eoe) { // catch 01.01.a
				raf01.close();
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

