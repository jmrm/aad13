package AaDEjDb4o02;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.ext.Db4oException;


public class EscribeLee_Db4o {

	/**
	 * Esta clase permite llevar a cabo el proceso de escritura inicial OO,
	 * además implementa métodos para la búsqueda a partir
	 * de un codigo de alumno
	 * 
	 * @param args
	 */
	
	final static String BDAlu ="DBAlumnos.db4o";
	final char SEP='\t';
	private Alumnos al;
	private ArrayList<Alumnos> alAlumnos;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EscribeLee_Db4o escribeLee_OO = new EscribeLee_Db4o();
		escribeLee_OO.alAlumnos = new ArrayList<Alumnos>();
		String codigo[]={"A01","A02","A05","C01","E01","E04"};
		String nombre[],fechaN[];
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
		escribeLee_OO.cargaAlumno(codigo,nombre,fechaN,califAlum,coefIntelig);
		File fich = new File(BDAlu);
		if (fich.exists()) fich.delete();
		ObjectContainer db4o= Db4oEmbedded.openFile(BDAlu);
		try {
			// Grabación con un foreach
			for (Alumnos alu: escribeLee_OO.alAlumnos) db4o.store(alu);;
			/* Con un bucle "normal"
			for (int i=0;i<codigo.length;i++) {
				db4o.store(escribeLee_OO.alAlumnos.get(i));
			}
			*/
			System.out.println("Registros escritos BD DB4o "+fich);
			escribeLee_OO.al=new Alumnos();
			// recupera todos los alumnos mediante QBE
			ObjectSet<Alumnos> result = db4o.queryByExample(escribeLee_OO.al);
			// Invocamos al método que visualiza los resultados
			listResult(result);		}
		catch (Db4oException DE) {
			System.out.print("Se ha producido una excepción del sistema Db4o:");
			System.out.println(DE.getMessage());
		}
		finally {
			db4o.close();
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
	
	// Visualiza los resultados obtenidos
	public static void listResult(List<Alumnos> result){
		if (result.size()==0) System.out.println("No hay registros de alumnos.");
		else {
			System.out.print("Se han encontrado ");
			System.out.print(result.size());
			System.out.println(" alumnos.");
	        for (Alumnos al : result) {
	        	al.println();
	        }
		}
	}

}