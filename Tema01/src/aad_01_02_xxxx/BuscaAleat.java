package aad_01_02_xxxx;


import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class BuscaAleat {

	/**
	 * @param args[0] -> nombre del archivo q se va a usar
	 * 		   args[1] -> número del registro a buscar
	 */
	public static void main(String[] args) { 
		// TODO Auto-generated method stub
		String path=LectoEscrAleat.getPath();
		/*
		 *  posic lo vamos a usar para llevar la posición del puntero de
		 *  acceso aleatorio. Apuntará al inicio de cada registro
		 */
		long posic=0;
		try { // try 01
			String nF=args[0];
			int numABusc=Integer.parseInt(args[1]);
			File fich=new File(path+nF);
			// creamos el acceso aleatorio para leer
			RandomAccessFile raf01 = new RandomAccessFile(fich,"r");
			// para leer después los registros
			System.out.println("Número a buscar:"+numABusc);
			try { // try 01.01
				StringBuffer cad;
				// No es un bucle eterno porque al llegar al fin de archivo se sale
				int n;
				while (true) {
					if (((n=raf01.readInt())==numABusc)) break; //encontrado
					//System.out.println("Número:"+n);
					raf01.seek(posic+=LectoEscrAleat.TAM_TOTAL); //salta un registro completo
				}
				//Llamamos al método que lee
				cad=leeReg(raf01);
				System.out.println(cad);
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
	/*
	 * Recibe un objeto de tipo RandomAccessFile y devuelve un string conteniendo
	 * el contenido completo del registro leído
	 */
	static public StringBuffer leeReg(RandomAccessFile raf) {
		StringBuffer cadB;
		try {
			//Retrocedemos 4 posiciones para empezar a leer desde el principio del
			//	registro, porque antes, en la búsqueda, hemos leído el número de paciente que es
			//  un entero de 4 bytes y eso ha desplazado el puntero
			raf.seek(raf.getFilePointer()-4);
			cadB=new StringBuffer();
			cadB.append("Paciente:");
			cadB.append(Integer.toString(raf.readInt())+LectoEscrAleat.SEP);
			for (int i=0;i<LectoEscrAleat.TAM_APELLIDOS;i++) cadB.append(raf.readChar());
			cadB.append(LectoEscrAleat.SEP);
			for (int i=0;i<LectoEscrAleat.TAM_NOMBRE;i++) cadB.append(raf.readChar());
			cadB.append(LectoEscrAleat.SEP);
			//Leemos los campos de la fecha
			for (int i=0;i<3;i++) cadB.append(Integer.toString(raf.readInt())+LectoEscrAleat.SEP);
			for (int i=0;i<LectoEscrAleat.TAM_DIRECCION;i++) cadB.append(raf.readChar());
			cadB.append(LectoEscrAleat.SEP);
			// Puntuación
			cadB.append(Float.toString(raf.readFloat()));
			//Eliminamos los nulos para que no molesten en la visualización
			return new StringBuffer(cadB.toString().replace('\u0000', ' '));
		}
		catch (IOException ioe) {
			return new StringBuffer("Se ha producido una excepción de ES.");
		}
	}
}
