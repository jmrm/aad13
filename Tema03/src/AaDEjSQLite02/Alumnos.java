package AaDEjSQLite02;


public class Alumnos{
	
	private String codigo, nombre, fechaNac;
	private float calif;
	private int ci;
	
	// Constructor parametrizado
	public Alumnos (String codigo, String nombre, String fechaNac, float calif, int ci) {
		this.codigo=codigo;this.nombre=nombre;this.fechaNac=fechaNac;
		this.calif=calif; this.ci=ci;
	}

	public Alumnos () {
		this(null, null, null, 0f, 0);
	}
	
	public void setCodigo (String co) {
		codigo=co;
	}
	public void setNombre (String no) {
		nombre=no;
	}
	public void setFechaNac (String fN) {
		fechaNac=fN;
	}
	public void setCalif (float Ca) {
		calif=Ca;
	}
	public void setCI (int nCi) {
		ci=nCi;
	}
	
	public String getCodigo () {
		return codigo;
	}
	public String getNombre () {
		return nombre;
	}
	public String getFechaNac () {
		return fechaNac;
	}
	public float getCalif () {
		return calif;
	}
	public int getCI () {
		return ci;
	}
		
	public String toString(char sep) {
		return (codigo+sep+nombre+sep+fechaNac+sep+calif+sep+ci);
	}
	
	public String toString() {
		return (toString(' '));
	}
	
	public void println(String cab,char sep) {
		System.out.println(cab+toString(sep));
	}
	
	public void println() {
		println("",' ');
	}
}

