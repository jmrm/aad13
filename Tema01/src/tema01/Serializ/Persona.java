package tema01.Serializ;

import java.io.Serializable;

public class Persona implements Serializable {
	private static final long serialVersionUID = 1284219246657339677L;
	private String nombre, ciudad;
	private int edad;
	
	public Persona(String n,int e, String c) {
		nombre=n;
		edad=e;
		ciudad=c;
	}
	
	public Persona() {
		this.nombre=null;
	}
	
	public void setNombre(String n) {
		nombre=n;
	}
	public void setEdad(int e) {
		edad=e;
	}
	public void setCiudad(String c) {
		ciudad=c;
	}
	public String getNombre() {return nombre;}
	public int getEdad() {return edad;}
	public String getCiudad() {return ciudad;}
}
