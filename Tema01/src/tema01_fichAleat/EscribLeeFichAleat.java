package tema01_fichAleat;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class EscribLeeFichAleat {

	private static void leeVisua(RandomAccessFile fraf, int tamBuff) throws IOException {
		int dpt,nE;
		double sal;
		char apellido[]=new char[tamBuff];
		nE=fraf.readInt(); //lee el número de empleado
		for (int k=0;k<tamBuff;k++) { //lee el apellido en una matriz de char
			apellido[k]=fraf.readChar(); // carácter por carácter
		}
		String ape=new String(apellido); //convierte el apellido a string
		ape=ape.trim(); //este paso es necesario para quitar caracteres Unicode espurios en ape
						// que luego estropean la visualización.
		dpt=fraf.readInt(); //lee el número de departamento
		sal=fraf.readDouble(); //lee el salario
		System.out.print("ID:"+nE+" Apellido:"+ape);
		System.out.println(" Dpto:"+dpt+" Salario:"+sal);
	}
	
	public static void main(String[] args) {
		/* 
		 * Un registro del archivo va a ocupar:
		 * Número de empleado. int -> 4 bytes
		 * Apellido. 10 caract Unicode (2xUnicode) -> 20 bytes
		 * Número de dpto. int -> 4 bytes
		 * Salario. double -> 8 bytes
		 * 
		 * Tamaño total del registro: 4 + 20 + 4 + 8  = 36 bytes 
		 * 
		 * */
		String apellidos[]={"CASILLA","REY","ARNALDOS","PÉREZ","MARTÍNEZ","GABILONDO"};
		int dptos[]={10,20,10,10,30,20};
		Double salarios[]={1000.45,2500.60,3000.0,1500.44,2200.0,1493.88};
		int nR=apellidos.length,tamBuff=10,tamReg=36;
		StringBuffer buffer= null;
		try {
			File pf= new File ("/home/jmrm/tmp/FichAleat.dat");
			// Abrimos/creamos en modo escritura/lectura
			RandomAccessFile faa = new RandomAccessFile(pf,"rw");
			// Proceso de escritura
			for (int i=0;i<nR;i++) {				
				faa.writeInt(i+1); //escribe el número del empleado
				buffer = new StringBuffer(apellidos[i]);
				buffer.setLength(tamBuff);
				faa.writeChars(buffer.toString()); //escribe el apellido
				faa.writeInt(dptos[i]);
				faa.writeDouble(salarios[i]); //escribe el salario
			}
			faa.close();
			// A partir de aquí lee
			faa = new RandomAccessFile(pf,"r");
			long tamArch=faa.length(); //tamaño del archivo en bytes.
			nR=(int)tamArch/tamReg; //número de registros
			System.out.println("Datos de empleados. Tamaño del archivo: "+tamArch);
			for (int i=1;i<=nR;i++) {
				leeVisua(faa,tamBuff);		
			}
			/* Ahora los va a leer hacia atrás desde el último al primero
			 * No cerramos faa porque vamos a reutilizarlo
			 */
			System.out.println("Datos de empleados leídos en sentido inverso.");
			for (int i=nR;i>0;i--) {
				// va posicionando 
				faa.seek((i-1)*tamReg);
				leeVisua(faa,tamBuff);
			}
			faa.close();
		}
		catch (IOException ioe) {
			System.out.println("Se ha producido la excepción "+ioe.getMessage());
		}
	}

}
