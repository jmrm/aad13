package usoDB4o;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.ext.Db4oException;


public class WR_DB4o {

	// TODO Auto-generated method stub

	/**
	 * Esta clase permite llevar a cabo el proceso de escritura inicial en la base
	 * de datos DB4o
	 * 
	 * @param args
	 */
	
	final static char SEP='\t';
	private Articulos ar;
	private ArrayList<Articulos> alArticulos;
	public static void main(String[] args) {
		
		// TODO Auto-generated method stub
		WR_DB4o wr_db4o = new WR_DB4o();
		wr_db4o.alArticulos = new ArrayList<Articulos>();
		String codigo[]={"a01","a02","a05","b01","b05","e01"};
		String nombre[],nF="Art.dbo4";
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
		File pf=new File(nF);
		if (pf.exists()) pf.delete(); // para borrar el archivo si ya existe
		// La siguiente línea crea un nuevo archivo
		ObjectContainer db4o=Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(),nF);
		wr_db4o.cargaProducto(codigo,nombre,unidadesVendidas,precioCatalogo,unidadesPedidas,unidadesDisponibles);
		try {
			// Grabación con un foreach
			for (Articulos a: wr_db4o.alArticulos) db4o.store(a);
			/* Con un bucle "normal"
			for (int i=0;i<codigo.length;i++) {
				db4o.store(wr_db4o.alArticulos.get(i));
			}
			*/
			
			System.out.println("Registros escritos fichero DB4o "+nF);
			wr_db4o.ar=new Articulos();
			// recupera todos los artículos mediante QBE
			ObjectSet<Articulos> result = db4o.queryByExample(wr_db4o.ar);
			// Invocamos al método que visualiza los resultados
			listResult(result);
		}
		catch (Db4oException DE) {
			System.out.print("Se ha producido una excepción del sistema Db4o:");
			System.out.println(DE.getMessage());
		}
		finally {
			db4o.close();
		}
	}
	
	// Carga la matriz de productos en el miembro alArticulos
	public void cargaProducto(String[] cod, String[] nom, int[] uv, float[] pc, int[] up, int[] ud) {
		for (int i=0; i<cod.length;i++) {
			//ar.println();
			alArticulos.add(new Articulos(cod[i],nom[i],uv[i],pc[i],up[i],ud[i]));
		}
	}
	
	// Visualiza los resultados obtenidos
	public static void listResult(List<Articulos> result){
		if (result.size()==0) System.out.println("No hay registros de artículos.");
		else {
			System.out.print("Se han encontrado ");
			System.out.print(result.size());
			System.out.println(" artículos.");
	        for (Articulos ar : result) {
	        	ar.println();
	        }
		}
	}

}