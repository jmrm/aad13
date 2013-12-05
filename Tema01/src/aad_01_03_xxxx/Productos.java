package aad_01_03_xxxx;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.io.Serializable;

public class Productos implements Serializable {
	/**
	 * Esta es la clase Serializable para el manejo de los productos. En un enfoque de
	 * OO completa es perfectamente utilizable tanto en el acceso Random como en el de 
	 * objetos persistentes
	 */
	private static final long serialVersionUID = -9186214544396766679L;
	private String codigo, nombre;
	private float precioCatalogo;
	private int unidDispo,unidVend,unidPed;
	
	//constantes para el tamaño de registro en RandomAccess que no se serializan
	public transient static final int LON_CO=3;
	public transient static final int LON_NO=35;
	public transient static final int LON_UV=4;
	public transient static final int LON_UP=4;
	public transient static final int LON_UD=4;
	public transient static final int LON_PC=4;
	public transient static final int LON_TOT=(LON_CO+LON_NO)*2+LON_UV+LON_UP+LON_UD+LON_PC;
	
	// Constructor parametrizado
	public Productos (String codigo, String nombre, int unidVend, float precioCatalogo, int unidPed, int unidDispo) {
		this.codigo=codigo;this.nombre=nombre;this.unidVend=unidVend;
		this.precioCatalogo=precioCatalogo;this.unidPed=unidPed;this.unidDispo=unidDispo;
	}

	public Productos () {
		this(null, null, 0, 0f, 0, 0);
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
	
	public String toString() {
		return (toString(' '));
	}
	
	public void println(String cab,char sep) {
		System.out.println(cab+toString(sep));
	}
	
	public void println() {
		println("",' ');
	}
	
	
	public boolean leeOO(ObjectInputStream ois) throws IOException, ClassNotFoundException {
		boolean valDev=false;
		Productos p = new Productos();
		try {
			p=(Productos) ois.readObject();
			this.codigo=p.codigo;this.nombre=p.nombre;this.unidVend=p.unidVend;
			this.unidPed=p.unidPed;this.unidDispo=p.unidDispo;
			this.precioCatalogo=p.precioCatalogo;
			valDev=true;
		}
		catch (EOFException eoe) {
		}
		return (valDev);
	}
	
	public void grabaOO(ObjectOutputStream oos) throws IOException{
		oos.writeObject(new Productos(this.codigo,this.nombre,this.unidVend,this.precioCatalogo,this.unidPed,this.unidDispo));
	}
	
	public boolean leeRA(RandomAccessFile raf) throws IOException{
		char[] codigo = new char[LON_CO];
		char[] nombre = new char[LON_NO];
		try {
			for (int i = 0; i < LON_CO; i++) {
				codigo[i] = raf.readChar();
			}
			for (int i = 0; i < LON_NO; i++) {
				nombre[i] = raf.readChar();
			}
			this.codigo=(new String(codigo)).replace('\u0000', ' ');
			this.nombre=(new String(nombre)).replace('\u0000', ' ');
			this.unidVend=raf.readInt();
			this.precioCatalogo=raf.readFloat();
			this.unidPed=raf.readInt();
			this.unidDispo=raf.readInt();
			return true;
		}
		catch (EOFException eoe) {
			return false;
		}
	}
	
	public long leeRA(RandomAccessFile raf, long pos) throws IOException{
		long posAnt=raf.getFilePointer(),valDev=-1;
		raf.seek(pos);
		if (leeRA(raf)) valDev=posAnt;
		return(valDev);
	}
	
	// Lee solamente el código del producto y deja el puntero del archivo 
	// en la misma posición
	public boolean leeCoRA(RandomAccessFile raf, long pos) throws IOException{
		boolean valDev=false;
		char[] codigo = new char[LON_CO];
		raf.seek(pos);
		try {
			for (int i = 0; i < LON_CO; i++) {
				codigo[i] = raf.readChar();
			}
			this.codigo=(new String(codigo)).replace('\u0000', ' ');
			raf.seek(pos);
			valDev=true;
		}
		catch (EOFException eoe) {
		}
		return valDev;
	}
	
	public void grabaRA(RandomAccessFile raf) throws IOException{
		StringBuffer buffer;
		buffer=new StringBuffer(this.codigo);
		buffer.setLength(LON_CO);
		raf.writeChars(buffer.toString());
		buffer=new StringBuffer(this.nombre);
		buffer.setLength(LON_NO);
		raf.writeChars(buffer.toString());
		raf.writeInt(this.unidVend);
		raf.writeFloat(this.precioCatalogo);
		raf.writeInt(this.unidPed);
		raf.writeInt(this.unidDispo);
	}
	
	public void grabaRA(RandomAccessFile raf, long pos) throws IOException {
		raf.seek(pos);
		grabaRA(raf);
	}
}

