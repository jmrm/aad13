package usoDB4o;

public class Articulos {
	/**
	 * Esta es la clase para el manejo de los artículos. Objetos de esta clase van a ser
	 * almacenados en la base de datos DB4o
	 */
	private static final long serialVersionUID = -9186214541296766679L;
	private String codigo, nombre;
	private float precioCatalogo;
	private int unidDispo,unidVend,unidPed;
	
	
	public Articulos (String codigo, String nombre, int unidVend, float precioCatalogo, int unidPed, int unidDispo) {
		this.codigo=codigo;this.nombre=nombre;this.unidVend=unidVend;
		this.precioCatalogo=precioCatalogo;this.unidPed=unidPed;this.unidDispo=unidDispo;
	}

	public Articulos () {
		codigo=nombre=null;
		precioCatalogo=0f;
		unidDispo=unidVend=unidPed=0;
	}
	
	public void setCodigo (String co) {
		codigo=co;
	}
	public void setNombre (String no) {
		nombre=no;
	}
	public void setPrecioCatalogo (float pc) {
		precioCatalogo=pc;
	}
	public void setUnidDispo (int ud) {
		unidDispo=ud;
	}
	public void setUnidVend (int uv) {
		unidVend=uv;
	}
	public void setUnidPed (int up) {
		unidPed=up;
	}
	public String getCodigo () {
		return codigo;
	}
	public String getNombre () {
		return nombre;
	}
	public float getPrecioCatalogo () {
		return precioCatalogo;
	}
	public int getUnidDispo () {
		return unidDispo;
	}
	public int getUnidVend () {
		return unidVend;
	}
	public int getUnidPed () {
		return unidPed;
	}
	
	public String toString(char sep) {
		return (codigo+sep+nombre+sep+unidVend+sep+precioCatalogo+sep+unidPed+sep+unidDispo);
	}
	
	@Override
	public String toString() {
		String sep=" ";
		return (codigo+sep+nombre+sep+unidVend+sep+precioCatalogo+sep+unidPed+sep+unidDispo);
	}
	
	public void println(String cab,char sep) {
		System.out.println(cab+toString(sep));
	}
	
	public void println() {
		System.out.println(toString());
	}
	
}

