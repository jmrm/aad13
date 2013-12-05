package aad_01_03_xxxx;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;


public class WR_RA {

	/**
	 * Esta clase permite llevar a cabo el proceso de escritura inicial RandomAccess,
	 * además implementa métodos para la búsqueda a partir
	 * de un codigo de producto
	 * 
	 * @param args
	 */
	
	final char SEP='\t';
	private boolean statusL; //para guardar el estatus tras una búsqueda true si lo encuentra
	private int statusE=0; //extiende la significación de statusL, 0-> no problem 1->EOF 2-> IOException
	private long pos; //permite guardar una posición para leer en el archivo
	private String cadReturn; //dato devuelto
	private Productos pr;
	private ArrayList<Productos> alProductos;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		WR_RA wr_ra = new WR_RA();
		wr_ra.alProductos = new ArrayList<Productos>();
		String codigo[]={"a01","a02","a05","b01","b05","e01"};
		String nombre[],nF="RAPro.dat";
		nombre=new String[6];
		nombre[0]="ACER Packard Bell DOTS-C";
		nombre[1]="ACER Aspire ONE D270";
		nombre[2]="ACER Aspire TimelineU M3-581TG";
		nombre[3]="ACER ICONIA Tab A200";
		nombre[4]="ACER Veriton X2610G";
		nombre[5]="PORTATIL HP DM1-4120SS AMD E450";
		float precioCatalogo[]={185.97f,201.11f,441.03f,250.98f,455.5f,358.99f};
		int unidadesPedidas[]={8,3,14,9,2,10};
		int unidadesDisponibles[]={9,12,11,6,2,16};
		int unidadesVendidas[]={6,33,21,9,14,28};
		// Vamos a cargar los datos en una colección ArrayList<Productos>
		wr_ra.cargaProducto(codigo,nombre,unidadesVendidas,precioCatalogo,unidadesPedidas,unidadesDisponibles);
		try {
			File fich = new File(nF);
			RandomAccessFile raf01;
			raf01= new RandomAccessFile (fich,"rw");
			// Grabación con un foreach
			for (Productos p: wr_ra.alProductos) p.grabaRA(raf01);
			/* Con un bucle "normal"
			for (int i=0;i<codigo.length;i++) {
				wr_ra.alProductos.get(i).grabaRA(raf01);
			}
			*/
			raf01.close();
			// Abrimos para leer
			raf01= new RandomAccessFile (fich,"r");
			System.out.println("Registros escritos fichero "+nF);
			wr_ra.pr=new Productos();
			// lee y visualiza el archivo completo
			while ((wr_ra.statusL=wr_ra.pr.leeRA(raf01))) {
				wr_ra.pr.println("Artículo:",wr_ra.SEP);
			}
			if (!wr_ra.statusL) raf01.close();
		}
		catch (IOException ioe) {
			System.out.println("Se ha producido una excepción de E/S "+ioe.getMessage());
		}		
	}
	
	// Carga las matrices que tienen los datos de productos en el 
	// atributo de la clase WR_RA alProductos (colección de objetos Productos)
	public void cargaProducto(String[] cod, String[] nom, int[] uv, float[] pc, int[] up, int[] ud) {
		for (int i=0; i<cod.length;i++) {
			//pr.println();
			alProductos.add(new Productos(cod[i],nom[i],uv[i],pc[i],up[i],ud[i]));
		}
	}
	
	/*
	 * Busca un registro (producto) a partir de su codigo idABusc y comenzando 
	 * en la posición posic que se le indique.
	 * Pone los atributos miembro de la clase statusL a true y statusE a 0 
	 * si todo es correcto, y statusL a false y statusE a 1 si hay una 
	 * excepción EOFException y, por último, statusL a false y statusE a 2 
	 * si hay una excepción IOException.
	 * Pone en el atributo miembro pos la dirección del código hallado
	 * Pone en el atributo miembro de la clase cadReturn la cadena leída a no 
	 * ser que se produzca una excepción, en cuyo caso le asigna la cadena 
	 * "Se ha producido una excepción de ES."
	 */
	public void buscaRegAA(RandomAccessFile raf,long posic, String idABusc, Productos pr) {
		try { // try 01.01
			// No es un bucle eterno porque al llegar al fin de archivo se sale
			statusE=0;
			while ((statusL=pr.leeCoRA(raf, posic))) {
				if (pr.getCodigo().equalsIgnoreCase(idABusc)) break; //encontrado
				posic+=Productos.LON_TOT; //salta un registro completo
			}
			pos=posic;// guarda aquí la posición del registro donde ha encontrado lo que buscaba
			//Si lo ha encontrado (no hay EOF), llamamos al método que lee
			if (statusL) pr.leeRA(raf, pos);
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
