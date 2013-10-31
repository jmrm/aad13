package aad_01_02_xxxx;

import java.io.Serializable;

public class Paciente implements Serializable {
	private int numero;
	private String apellidos,nombre;
	private int annoNacimiento,mesNacimiento,diaNacimiento;
	private String direccion;
	private float puntuacion;
	private static final long serialVersionUID = 1234567890123456789L;
		
	// Constructor parametrizado
	public Paciente (int n, String a, String no, int aN, int mN, int dN, String d, float p) {
		apellidos=a; nombre=no; direccion=d;
		numero=n; annoNacimiento=aN; mesNacimiento=mN; diaNacimiento=dN;
		puntuacion=p;
	}
	// Constructor por defecto (sin parámetros)
	public Paciente () {
		apellidos=nombre=direccion=null;
		numero=annoNacimiento=mesNacimiento=diaNacimiento=0;
		puntuacion=0f;
	}
	// los set ponen valores en los atributos miembro
	public void setNombre (String n) {
		nombre=n;
	}
	public void setApellidos (String a) {
		apellidos=a;
	}
	public void setDireccion (String d) {
		direccion=d;
	}	
	public void setNumero (int n) {
		numero=n;
	}
	public void setAnno (int a) {
		annoNacimiento=a;
	}
	public void setMes (int m) {
		mesNacimiento=m;
	}
	public void setDia (int d) {
		diaNacimiento=d;
	}
	public void setPuntuacion (float p) {
		puntuacion=p;
	}	
	//los get obtienen valores de los atributos miembro
	public String getNombre() {return nombre;}
	public String getApellidos() {return apellidos;}
	public String getDireccion() {return direccion;}
	public int getNumero() {return numero;}
	public int getAnno() {return annoNacimiento;}
	public int getMes() {return mesNacimiento;}
	public int getDia() {return diaNacimiento;}
	public float getPuntuacion() {return puntuacion;}
}
