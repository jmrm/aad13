package usoDB4o;

import java.util.Scanner;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

public class F_DB4o {

		/**
		 * @param args
		 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String resp;
		ObjectContainer db4o=null;
		try { // try 01
			String nF="Art.dbo4";
			//File fich=new File(nF);
			// accedemos a la base de datos
			db4o=Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(),nF);
			Scanner entrada=new Scanner(System.in);
			while (true) {
				System.out.println("Introduzca el código a buscar (=+= termina):");
				resp=entrada.next();
				if (resp.equalsIgnoreCase("=+=")) {
					System.out.println("Búsqueda terminada.");
					break;//termina
				}
				String idABusc=resp;
				// para leer después los registros
				System.out.println("Código a buscar:"+idABusc);
				Articulos ar= new Articulos();
				ar.setCodigo(idABusc);
				ObjectSet<Articulos> result=db4o.queryByExample(ar);
				if (result.size()==0) {
					System.out.println("Código "+idABusc+" no encontrado.");
				} else {
					while (result.hasNext()) {
						Articulos ar2=result.next();
						ar2.println("Datos artículo:", '*');
					}
				}
			}
		} // end try 01
		finally {
			db4o.close();
		}		
	}
}