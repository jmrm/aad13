package aad_ev1_xxxx;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;


public class EscribeLee_RA {

	/**
	 * Esta clase permite llevar a cabo el proceso de escritura inicial RandomAccess,
	 * además implementa métodos para la búsqueda a partir
	 * de un codigo de alumno
	 * 
	 * @param args
	 */
	
	final char SEP='\t';
	private boolean statusL; //para guardar el estatus tras una búsqueda true si lo encuentra
	private int statusE=0; //extiende la significación de statusL, 0-> no problem 1->EOF 2-> IOException
	private long pos; //permite guardar una posición para leer en el archivo
	private String cadReturn; //dato devuelto
	private Alumnos al;
	private ArrayList<Alumnos> alAlumnos;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EscribeLee_RA escribeLee_ra = new EscribeLee_RA();
		escribeLee_ra.alAlumnos = new ArrayList<Alumnos>();
		String codigo[]={"A01","A02","A05","C01","E01","E04"};
		String nombre[],fechaN[],nF="RAAlu.dat";
		nombre=new String[6];
		nombre[0]="Álvarez Narváez, Anselmo";
		nombre[1]="Bermudez Calvo, Luisa";
		nombre[2]="Soler Druni, Miguel";
		nombre[3]="Aconta Ciclos, Encarnación";
		nombre[4]="Teruel Almarcha, Dolores";
		nombre[5]="Duarte Felino, Cayetano";
		fechaN=new String[6];
		fechaN[0]="19881121";
		fechaN[1]="19870615";
		fechaN[2]="19880114";
		fechaN[3]="19830506";
		fechaN[4]="19851201";
		fechaN[5]="19860913";
		float califAlum[]={7.56f,8.12f,7,4.7f,4.2f,9,95f,5.1f};
		int coefIntelig[]={120,145,118,98,192,102};
		// Vamos a cargar los datos en una colección ArrayList<Alumnos>
		escribeLee_ra.cargaAlumno(codigo,nombre,fechaN,califAlum,coefIntelig);
		try {
			File fich = new File(nF);
			RandomAccessFile raf01;
			raf01= new RandomAccessFile (fich,"rw");
			// Grabación con un foreach
			for (Alumnos p: escribeLee_ra.alAlumnos) p.grabaRA(raf01);
			/* Con un bucle "normal"
			for (int i=0;i<codigo.length;i++) {
				escribeLee_ra.alAlumnos.get(i).grabaRA(raf01);
			}
			*/
			raf01.close();
			// Abrimos para leer
			raf01= new RandomAccessFile (fich,"r");
			System.out.println("Registros escritos fichero "+nF);
			escribeLee_ra.al=new Alumnos();
			// lee y visualiza el archivo completo
			while ((escribeLee_ra.statusL=escribeLee_ra.al.leeRA(raf01))) {
				escribeLee_ra.al.println("Alumno:",escribeLee_ra.SEP);
			}
			if (!escribeLee_ra.statusL) raf01.close();
		}
		catch (IOException ioe) {
			System.out.println("Se ha producido una excepción de E/S "+ioe.getMessage());
		}		
	}
	
	// Carga las matrices que tienen los datos de productos en el 
	// atributo de la clase EscribeLee_RA alAlumnos (colección de objetos Alumnos)
	public void cargaAlumno(String[] cod, String[] nom, String[] fNac, float[] cal, int[] ci) {
		for (int i=0; i<cod.length;i++) {
			//al.println();
			alAlumnos.add(new Alumnos(cod[i],nom[i],fNac[i],cal[i],ci[i]));
		}
	}
	
	/*
	 * Busca un registro (alumno) a partir de su codigo idABusc y comenzando 
	 * en la posición posic que se le indique.
	 * Pone los atributos miembro de la clase statusL a true y statusE a 0 
	 * si todo es correcto, y statusL a false y statusE a 1 si hay una 
	 * excepción EOFException y, por último, statusL a false y statusE a 2 
	 * si hay una excepción IOException.
	 * Pone en el atributo miembro pos la dirección del código hallado
	 * Cuando se produce una excepción pone en cadReturn, atributo miembro de la clase, 
	 * la cadena "Excepción de E/S "+ioe.getMessage();
	 */
	
	public void buscaRegAA(RandomAccessFile raf,long posic, String idABusc, Alumnos alu) {
		//Alumnos alu=new Alumnos();
		try { // try 01.01
			// No es un bucle eterno porque al llegar al fin de archivo se sale
			statusE=0;
			while ((statusL=alu.leeCoRA(raf, posic))) {
				if (alu.getCodigo().equalsIgnoreCase(idABusc)) break; //encontrado
				posic+=Alumnos.LON_TOT; //salta un registro completo
			}
			pos=posic;// guarda aquí la posición del registro donde ha encontrado lo que buscaba
			//Si lo ha encontrado (no hay EOF), llamamos al método que lee
			if (statusL) alu.leeRA(raf, pos);
			else statusE=1; // EOF
			
		} // end try 01.01
		catch (IOException ioe) {
			statusL=false;statusE=2;
			cadReturn="Excepción de E/S "+ioe.getMessage();
		}
	}
	
	// Estos métodos permiten acceder al valor de los atributos miembro private
	public long getPos() {
		return pos;
	}
	public String getCadReturn(){
		return cadReturn;
	}
	public boolean getStatusL() {
		return statusL;
	}
	public int getStatusE() {
		return statusE;
	}
	public void setStatusL(boolean st) {
		statusL=st;
	}
	public void setStatusE(int st) {
		statusE=st;
	}
}
