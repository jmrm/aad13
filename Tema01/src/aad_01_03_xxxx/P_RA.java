package aad_01_03_xxxx;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class P_RA {

	/**
	 * Esta clase lleva a cabo el cálculo de las unidades a pedir, calculado para que 
	 * Unidades Disponibles + Unidades a Pedir - Unidades Pedidas arroje como mínimo un valor
	 * de 3. 
	 * Si el producto no existe lo dice y no lo tiene en cuenta
	 * para el cálculo. Si se repite un producto lo incluye tantas veces en el cálculo como
	 * se repita. 
	 *  
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		WR_RA wr_ra=new WR_RA();
		String nF="RAPro.dat",idABusc="";
		int stockDisp;
		try { // try 01
			int nA=args.length,unidAPed;
			Productos pr=new Productos();
			File fich=new File(nF);
			// creamos el acceso aleatorio para leer
			RandomAccessFile raf01 = new RandomAccessFile(fich,"r");
			// para leer después los registros
			System.out.print("Productos a calcular:");
			for (int i=0;i<nA;i++) {
				System.out.print(args[i]);
				if (i<(nA-1)) System.out.print(",");
			}
			if (nA==0) System.out.print("Se calcularán todos los productos");
			System.out.println("\n----------------------------");
			for (int i=0;i<nA;i++) {
				// Siempre comienza a buscar desde el principio
				// En este caso podría ser más efectivo recorrer el archivo y para cada
				// registro buscar con un for si coinciden los parámetros pasados en args[]
				idABusc=args[i];	
				wr_ra.buscaRegAA(raf01, 0, idABusc,pr);
				switch (wr_ra.getStatusE()){
					case 0: //lo ha encontrado
						stockDisp=pr.getUnidDispo()-pr.getUnidPed();
						unidAPed=0;
						if (stockDisp<3) {
							unidAPed=3-stockDisp;
						}
						System.out.print("Producto "+pr.getCodigo());
						System.out.print(" a pedir:");
						System.out.println(unidAPed);
						break;
					case 1: System.out.println("Código "+idABusc+" no encontrado.");break;
					case 2: System.out.println(wr_ra.getCadReturn());break;
					default: ;
				}
			}
			// Todos los productos
			if (nA==0) {
				wr_ra.setStatusL(pr.leeRA(raf01));
				while (wr_ra.getStatusL()) {
					// Sólo si no está borrado
					if (pr.getCodigo().trim().length()!=0) {
						stockDisp=pr.getUnidDispo()-pr.getUnidPed();
						unidAPed=0;
						if (stockDisp<3) {
							unidAPed=3-stockDisp;
						}
						System.out.print("Producto "+pr.getCodigo());
						System.out.print(" a pedir:");
						System.out.println(unidAPed);
					}
					wr_ra.setStatusL(pr.leeRA(raf01));
				}
			}
			raf01.close();		
		} // end try 01
		catch (ArrayIndexOutOfBoundsException aioobe) {
			System.out.println("Tiene que indicar el nombre del archivo y los códigos de los productos a calcular separados por espacios, en la línea de comandos.");
		}
		catch (IOException ioe) {
			System.out.println("Se ha producido una excepción de E/S");
		}
	}

}

