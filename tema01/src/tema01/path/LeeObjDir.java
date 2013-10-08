package tema01.path;

import java.io.*;

public class LeeObjDir {

	public static void main(String[] args) {
		LeeObjDir fich=new LeeObjDir();
		System.out.println("Ficheros en el directorio "+args[0]+":");
		File f=new File(args[0]);
		if (!f.exists()) {
			System.out.println(args[0]+" no existe;");
		}
		else 	if (!f.isDirectory()) {
					System.out.println(args[0]+" no es un directorio;");
			 	}
				else {
					fich.lista(f);
				}
	}

	public void lista(File f) {
		boolean isDir;
		File[] aArchivos = f.listFiles();
		for (int i=0; i<aArchivos.length; i++) {
			System.out.println(aArchivos[i]);
			System.out.println("Nombre      :"+aArchivos[i].getName());
			System.out.println("Path        :"+aArchivos[i].getPath());
			System.out.println("Path abs    :"+aArchivos[i].getAbsolutePath());
			isDir=aArchivos[i].isDirectory();
			if (isDir) {
				System.out.println("Es directorio");
				this.lista(aArchivos[i]);
			} else {
				System.out.println("Es archivo   ");
				System.out.println("Es escribibl:"+aArchivos[i].canWrite());
				System.out.println("Es legible  :"+aArchivos[i].canRead());
				System.out.println("Tamaño      :"+aArchivos[i].length());
			}
		}
	}

}
