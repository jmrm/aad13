package RepasoXXXX;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class E02_TablaMult {

	private static BufferedReader br;

	public static void main(String[] args) throws IOException {

		br = new BufferedReader(new InputStreamReader(System.in));
		try {
			System.out.println("Introduca el numero a sacar la tabla [1-30000]:");
			Integer n=Integer.parseInt(br.readLine());
			rango(n);
			for (int i=0; i<=10 ; i++){
				int t=n * i;
				System.out.println(n+" * " + i + " = " + t);
			}
		}
		catch (E0201_ExcepcionFueraRango E0201) {
			System.out.println(E0201.getMessage());
		}
		finally {
			System.out.println("Programa terminado");
		}
	}
	
	static void rango(int num)throws E0201_ExcepcionFueraRango{
		if((num<1)||(num>30000)){
		throw new E0201_ExcepcionFueraRango("Error, el número ha de estar entre 1 y 30000");
		}
	}
}

