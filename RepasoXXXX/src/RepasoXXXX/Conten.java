package RepasoXXXX;

import java.util.Arrays;

public class Conten implements Cloneable {
	private int[] m;
	// Constructor mediante copia shallow de atributo
	public Conten(int[] m) {
		this.m=m;
	}; 
	// Constructor copia mediante copia shallow del objeto
	public Conten(Conten c) {
		m=c.m;
	};
	/*
	 * Clonación de un objeto de la clase Conten
	 */
	protected Object clone() throws CloneNotSupportedException {
    	/* Invoca al método clone() de la clase ancestro, los métodos clone() devuelven
    	 * instancias de la clase Object por lo que es preciso moldear (cast) hacia la clase
    	 * deseada
    	 */
    	Conten clon=(Conten)super.clone();
    	//clonar el resto (shallow copy porque no hay campos mutable)
    	//clon.objeto=(objeto)propiedad.clone();  //para campos "mutable"
		clon.m=(int[])m.clone();
		//salir
		return clon;
    }
	// Setter por copia shallow
	public void setm(int[] m) {
		this.m=m;
	}
	// Setter por copia profunda
	public void setmProf(int[] m) {
		for (int i=0;i<m.length;i++) this.m[i]=m[i];
	}
	public int[] getm () {
		return m;
	}
	public void println() {
		System.out.println(Arrays.toString(m));
	}
}
