package aad_ev1_xxxx;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.io.Serializable;

public class Alumnos implements Serializable {
	/**
	 * Esta es la clase Serializable para el manejo de los alumnos. En un enfoque de
	 * OO completa es perfectamente utilizable tanto en el acceso Random como en el de 
	 * objetos persistentes
	 */
	private static final long serialVersionUID = -918621444539676667L;
	private String codigo, nombre, fechaNac;
	private float calif;
	private int ci;
	
	//constantes para el tamaño de registro en RandomAccess que no se serializan
	public transient static final int LON_CO=3;
	public transient static final int LON_NO=30;
	public transient static final int LON_FN=8;
	public transient static final int LON_CA=4;
	public transient static final int LON_CI=4;
	public transient static final int LON_TOT=(LON_CO+LON_NO+LON_FN)*2+LON_CA+LON_CI;
	
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
	
	
	public boolean leeOO(ObjectInputStream ois) throws EOFException, IOException, ClassNotFoundException {
		boolean valDev=false;
		Alumnos a = new Alumnos();
		a=(Alumnos) ois.readObject();
		this.codigo=a.codigo;this.nombre=a.nombre;this.fechaNac=a.fechaNac;
		this.calif=a.calif;
		this.ci=a.ci;
		valDev=true;
		return (valDev);
	}
	
	public void grabaOO(ObjectOutputStream oos) throws IOException{
		oos.writeObject(new Alumnos(this.codigo,this.nombre,this.fechaNac,this.calif,this.ci));
	}
	
	public boolean leeRA(RandomAccessFile raf) throws IOException{
		char[] codigo = new char[LON_CO];
		char[] nombre = new char[LON_NO];
		char[] fecha_Nac = new char[LON_FN];
		try {
			for (int i = 0; i < LON_CO; i++) {
				codigo[i] = raf.readChar();
			}
			for (int i = 0; i < LON_NO; i++) {
				nombre[i] = raf.readChar();
			}
			for (int i = 0; i < LON_FN; i++) {
				fecha_Nac[i] = raf.readChar();
			}
			this.codigo=(new String(codigo)).replace('\u0000', ' ');
			this.nombre=(new String(nombre)).replace('\u0000', ' ');
			this.fechaNac=(new String(fecha_Nac)).replace('\u0000', ' ');
			this.calif=raf.readFloat();
			this.ci=raf.readInt();
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
	
	// Lee solamente el código del alumno y deja el puntero del archivo 
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
		buffer=new StringBuffer(this.fechaNac);
		buffer.setLength(LON_FN);
		raf.writeChars(buffer.toString());
		raf.writeFloat(this.calif);
		raf.writeInt(this.ci);
	}
	
	public void grabaRA(RandomAccessFile raf, long pos) throws IOException {
		raf.seek(pos);
		grabaRA(raf);
	}
}

