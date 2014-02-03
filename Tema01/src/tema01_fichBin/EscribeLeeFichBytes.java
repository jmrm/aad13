package tema01_fichBin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class EscribeLeeFichBytes {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String path="/home/jmrm/tmp/";
		File fich = new File (path+"Fich.byt");
		boolean append;
		append=(fich.exists() && (fich.length()<500));		
		if (append) System.out.printf("Tamaño del archivo:%d\n",fich.length());
		try {
			FileOutputStream fo = new FileOutputStream(fich,append);
			FileInputStream fi = new FileInputStream(fich);
			int i;
			for (i=0;i<100; i++) fo.write(i);
			fo.close();
			while ((i=fi.read())!=-1) {
				System.out.println(i);
			}
			fi.close();
		}
		catch (IOException ioe) {
			System.out.println ("Se ha producido una excepción de ES");
		}
		

	}

}
