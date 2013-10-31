package aad_01_02_xxxx;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class LectoEscrAleat {

	/**
	 * @param args
	 */
	static final int TAM_APELLIDOS=16, TAM_NOMBRE=6, TAM_DIRECCION=35, TAM_TOTAL=134; 
	static final char SEP='*';
	static final String SOP = System.getProperty("os.name"), PATH_WIN="C:/tmp/", PATH_LIN="/home/jmrm/tmp/";
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//LectoEscrAleat LEA = new LectoEscrAleat(); 
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
		int n;
		float puntuacion[]={4.66f,10.00f,8.14f,5.05f,7.82f,9.07f,2.44f,3.12f,7.48f,6.67f};
		// cargo en path el adecuado según el sistema operativo anfitrión
		String path=getPath();
		/*
		 * try para las posibles excepciones de array fuera de límites (args) y error de E/S
		 */
		try { // try 01
			String nF=args[0],cad;
			StringBuffer cadB;
			File fich = new File(path+nF);
			RandomAccessFile raf01;
			raf01= new RandomAccessFile (fich,"rw");
			n=apellidos.length;
			for (int i=0;i<n;i++) {
				escrReg(raf01,i+1,apellidos[i],nombre[i],annoNacimiento[i],mesNacimiento[i],diaNacimiento[i],direccion[i],puntuacion[i]);
			}
			raf01.close();
			// Abrimos para leer
			raf01= new RandomAccessFile (fich,"r");
			System.out.println("Registros escritos fichero "+path+nF);
			//Este try es para controlar automáticamente el fin de archivo
			try { //try 0101
				while (true) {
					cadB=new StringBuffer();
					cadB.append("Paciente:");
					cadB.append(Integer.toString(raf01.readInt())+SEP);
					for (int i=0;i<TAM_APELLIDOS;i++) cadB.append(raf01.readChar());
					cadB.append(SEP);
					for (int i=0;i<TAM_NOMBRE;i++) cadB.append(raf01.readChar());
					cadB.append(SEP);
					//Leemos los campos de la fecha
					for (int i=0;i<3;i++) cadB.append(Integer.toString(raf01.readInt())+SEP);
					for (int i=0;i<TAM_DIRECCION;i++) cadB.append(raf01.readChar());
					cadB.append(SEP);
					// Puntuación
					cadB.append(Float.toString(raf01.readFloat()));
					//Eliminamos los nulos para que no molesten en la visualización
					cad=cadB.toString().replace('\u0000', ' ');
					System.out.println(cad);
				}
			}
			catch (EOFException eofe) { //catch /0101a
				// Fin de archivo
				raf01.close();
			}	
		}
		catch (ArrayIndexOutOfBoundsException aioobe) {
			System.out.println("Tiene que indicar el nombre del archivo en la línea de comandos.");
		}
		catch (IOException ioe) {
			System.out.println("Se ha producido una excepción de E/S "+ioe.getMessage());
		}

	}
	
	/*
	 *  Lleva a cabo todo el proceso de escritura de un registro de Paciente
	 *  es muy importante saber desde dónde hasta dónde ocupa cada campo y cuál es el tamaño
	 *  total del registro.
	 */
	static void escrReg (RandomAccessFile raf, int i, String ape, String nom, int a, int m, int d, String dir, float p) {
		StringBuffer buffer;
		try {
			raf.writeInt(i); //número de paciente 4 bytes
			buffer = new StringBuffer(ape);
			buffer.setLength(TAM_APELLIDOS); // 16 caracteres para el apellido = 32 bytes
			raf.writeChars(buffer.toString()); // apellido
			buffer = new StringBuffer(nom);
			buffer.setLength(TAM_NOMBRE); // 6 caracteres para el nombre = 12 bytes
			raf.writeChars(buffer.toString()); // apellido
			raf.writeInt(a);// anno, mes, día = 4 x 3 = 12 bytes
			raf.writeInt(m);
			raf.writeInt(d);
			buffer = new StringBuffer(dir);
			buffer.setLength(TAM_DIRECCION); // 35 caracteres para la dirección = 70 bytes
			raf.writeChars(buffer.toString()); // direccion
			raf.writeFloat(p); // puntuación = 4 bytes
			// Tamaño total 4+32+12+12+70+4=134
		}
		catch (IOException ioe) {
			System.out.println("Se ha producido una excepción de E/S "+ioe.getMessage());
		}		
	}
	// Sobrecargamos escrReg para no necesitar pasar parámetros en los borrados
	static void escrReg (RandomAccessFile raf) {
		escrReg(raf, 0, "", "", 0, 0, 0, "", 0f);;
	}
	/**
	 * @return boolean true si el sistema operativo subyacente es Windows
	 */
	 
	static boolean esWin() {
		boolean res=false;
		/*
		 *  busca en el valor devuelto por System.getProperty("os.name") (guardado
		 *  en SOP) la subcadena "windows"
		 */
		if(SOP.toLowerCase().indexOf("windows")!=-1) res=true;
		return res;
	}
	
	/**
	 * 
	 * @return String con el path por defecto para colocar el archivo en el que
	 * se guardan los datos. Supone que el sistema operativo es Windows o Linux. En 
	 * el primer caso el path es del tipo "C:/..." y en el segundo del tipo "/home/..."
	 */
	
	static String getPath() {
		String path;
		if (esWin()) path=PATH_WIN; // insti windows
		else path=PATH_LIN; // insti
		return path;
	}

}
