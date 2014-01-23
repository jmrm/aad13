package ejeDB4o;

import java.io.File;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

public class Main {
	
	final static String BDPer ="DBPersonas.db4o";
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/* ObjectContainer es un interfaz, luego db será un objeto de la clase devuelta por
		 * DB4oEmbedded.openFile() que sabemos que implementa la interfaz ObjectContainer. 
		 * DB4oEmbedded.openFile() devuelve objetos que implementan la interfaz 
		 * EmbeddedObjectContainer que es un interfaz "hijo" del interfaz DB4oEmbedded 
		 * (extiende Db4oEmbedded) En concreto la clase devuelta es 
		 * com.db4o.internal.IoAdaptedObjectContainer, que no está documentada, pero que en
		 * el código fuente de db4o se declara como 
		 * public class IoAdaptedObjectContainer extends LocalObjectContainer implements EmbeddedObjectContainer */
		File nF=new File(BDPer);
		if (nF.exists()) nF.delete();
		//ObjectContainer db= Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(),BDPer);
		/* Según el manual de db4o, invocar a Db4oEmbedded.openFile(BDPer) es equivalente a 
		 * hacerlo mediante Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(),BDPer)
		 */
		ObjectContainer db= Db4oEmbedded.openFile(BDPer); 
		String claseDb= new String(db.getClass().toString());
		System.out.println("La clase de db es:"+claseDb);
		Persona p1=new Persona("Juan","Guadalajara");
		Persona p2=new Persona("Juan","Garrote Gordo");
		Persona p3=new Persona("Ana","Madrid");
		Persona p4=new Persona("Luis","Granada");
		Persona p5=new Persona("Pedro","Asturias");
		
		// Los guardamos en la base de datos.
		
		db.store(p1);db.store(p2);db.store(p3);db.store(p4);db.store(p5);
		System.out.println("Primero visualizaremos todas las personas");
		verPersonas(db,0,0);
		System.out.println("Luego todas aquellas cuyo nombre es 'Juan'");
		verPersonasPorNombre(db,"Juan",0,0);
		System.out.println("Y finalmente todas aquellas que viven en 'Guadalajara'");
		verPersonasPorCiudad(db,"Guadalajara",0,0);
		System.out.println("Ahora cambiaremos la ciudad los juanes a Houston y visualizaremos el resultado");
		cambiaCiudadPersona(db,"Juan","Houston",0,0);
		System.out.println("Y finalmente borramos la segunda persona cuyo nombre es 'Juan'");
		borraPersonaPorNombre(db,"Juan",2,1);
		System.out.println("El resultado final es:");
		verPersonas(db,0,0);
		db.close();
	}
	
	public static void verPersonas(ObjectContainer db,long inicio,long cuantas) {
		// Visualiza todas las tuplas mediante Query By Example
		Persona persEjemplo= new Persona(null,null);
		ObjectSet<Persona> result= db.queryByExample(persEjemplo);
		if (result.size()==0) System.out.println("No hay ningún registro en 'Persona'");
		else { 
			System.out.println("Número de registros encontrados en 'Persona':"+result.size());
			System.out.println("Procedemos a la visualización a partir del "+inicio+" y visualizando "+cuantas+".");
			Persona p=null; long i=0,c=0;
			while (result.hasNext()) {
				p=result.next();
				i++;
				if (inicio!=0) { 
					if ((i>=inicio) && ((c<cuantas)||(cuantas==0))) {
						System.out.println("Registro nº"+i+":"+"Nombre -> "+p.getNombre()+" Ciudad ->"+p.getCiudad());
						c++;
					}
				} else System.out.println("Registro nº"+i+":"+"Nombre -> "+p.getNombre()+" Ciudad ->"+p.getCiudad());
			}
		}
	}
	public static void verPersonasPorNombre(ObjectContainer db,String nombre,long inicio,long cuantas) {
		// Visualiza todas las tuplas mediante Query By Example
		Persona persEjemplo= new Persona(nombre,null);
		ObjectSet<Persona> result= db.queryByExample(persEjemplo);
		if (result.size()==0) System.out.println("No hay ningún registro en 'Persona' con el nombre:"+nombre);
		else { 
			System.out.println("Número de registros encontrados en 'Persona' con nombre "+nombre+":"+result.size());
			System.out.println("Procedemos a su visualización a partir del "+inicio+" y visualizando "+cuantas+".");
			Persona p=null; long i=0,c=0;
			while (result.hasNext()) {
				p=result.next();
				i++;
				if (inicio!=0) { 
					if ((i>=inicio) && ((c<cuantas)||(cuantas==0))) {
						System.out.println("Registro nº"+i+":"+"Nombre -> "+p.getNombre()+" Ciudad ->"+p.getCiudad());
						c++;
					}
				} else System.out.println("Registro nº"+i+":"+"Nombre -> "+p.getNombre()+" Ciudad ->"+p.getCiudad());
			}
		}
	}
	public static void verPersonasPorCiudad(ObjectContainer db,String ciudad,long inicio,long cuantas) {
		// Visualiza todas las tuplas mediante Query By Example
		Persona persEjemplo= new Persona(null,ciudad);
		ObjectSet<Persona> result= db.queryByExample(persEjemplo);
		if (result.size()==0) System.out.println("No hay ningún registro en 'Persona' con la ciudad:"+ciudad);
		else { 
			System.out.println("Número de registros encontrados en 'Persona' con ciudad "+ciudad+":"+result.size());
			System.out.println("Procedemos a su visualización a partir del "+inicio+" y visualizando "+cuantas+".");
			Persona p=null; long i=0,c=0;
			while (result.hasNext()) {
				p=result.next();
				i++;
				if (inicio!=0) { 
					if ((i>=inicio) && ((c<cuantas)||(cuantas==0))) {
						System.out.println("Registro nº"+i+":"+"Nombre -> "+p.getNombre()+" Ciudad ->"+p.getCiudad());
						c++;
					}
				} else System.out.println("Registro nº"+i+":"+"Nombre -> "+p.getNombre()+" Ciudad ->"+p.getCiudad());
			}
		}
	}
	public static void cambiaCiudadPersona(ObjectContainer db,String nombre, String ciudad,long inicio,long cuantas) {
		// Visualiza todas las tuplas mediante Query By Example
		Persona persEjemplo= new Persona(nombre,null);
		ObjectSet<Persona> result= db.queryByExample(persEjemplo);
		if (result.size()==0) System.out.println("No hay ningún registro en 'Persona' con el nombre:"+nombre);
		else { 
			System.out.println("Número de registros encontrados en 'Persona' con nombre "+nombre+":"+result.size());
			System.out.println("Procedemos a su visualización a partir del "+inicio+" y visualizando "+cuantas+".");
			Persona p=null; long i=0,c=0;
			while (result.hasNext()) {
				p=result.next();
				i++;
				if (inicio!=0) { 
					if ((i>=inicio) && ((c<cuantas)||(cuantas==0))) {
						System.out.println("Registro nº"+i+":"+"Nombre -> "+p.getNombre()+" Ciudad ->"+p.getCiudad());
						c++;
					}
				} else System.out.println("Registro nº"+i+":"+"Nombre -> "+p.getNombre()+" Ciudad ->"+p.getCiudad());
			}
			System.out.println("Ahora cambiaremos su ciudad por "+ ciudad +" a partir del "+inicio+" y cambiando "+cuantas+".");
			result= db.queryByExample(persEjemplo);
			p=null; i=0; c=0;
			while (result.hasNext()) {
				p=result.next();
				i++;
				if (inicio!=0) {
					if ((i>=inicio) && ((c<cuantas)||(cuantas==0))) {
						p.setCiudad(ciudad); db.store(p);
						c++;
					}
				} else {p.setCiudad(ciudad); db.store(p);};
			}
			System.out.println("Y visualizamos el cambio");
			verPersonas(db,0,0);
		}
	}
	
	public static void borraPersonaPorNombre(ObjectContainer db, String nombre, long inicio,long cuantas) {
		Persona persABorrar=new Persona(nombre,null);
		ObjectSet<Persona> result=db.queryByExample(persABorrar);
		if (result.size()==0) System.out.println("No hay ninguna persona con nombre:"+nombre);
		else {
			Persona p=null; long i=0,c=0;
			while (result.hasNext()) {
				p=result.next();
				i++;
				if (inicio!=0) { 
					if ((i>=inicio) && ((c<cuantas)||(cuantas==0))) {
						System.out.println("Borrando persona: "+p.getNombre()+", "+p.getCiudad());
						db.delete(p);
						c++;
					}
				}
				else {
					System.out.println("Borrando persona: "+p.getNombre()+", "+p.getCiudad());
					db.delete(p);};
			}
		}
	}

}
