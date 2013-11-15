package RepasoXXXX;

public class E04_Motor {
	private int cubicajeCC;
	private int potenciaCV;
	private static final int limiteKm = 300000;
	private int rodaduraKm = 0;

	E04_Motor(int cubicajeCC, int potenciaCV) {
		this.cubicajeCC=cubicajeCC;
		this.potenciaCV=potenciaCV;
	}

	// E04_Motor Estándar
	E04_Motor() {
		this(1900,105);
	}

	public void rodar(int km) throws Exception {
		rodaduraKm+=km;
		if (rodaduraKm>limiteKm)
			throw new Exception("Motor agotado!");
	}

	public void print() {
		int difKm=limiteKm-rodaduraKm;
		System.out.println("Cubicaje: "+cubicajeCC+" cc");
		System.out.println("Potencia: "+potenciaCV+" CV");
		if ( difKm >= 0)
			System.out.println("Km: "+rodaduraKm+" restan "+difKm);
		else
			System.out.println("Km: "+rodaduraKm+" excedidos "+ (-difKm));
	}

	public void println() {
		this.print();
		System.out.print('\n');
	}

	public static void main(String[] args) {
		E04_Motor m=new E04_Motor();
		E04_Motor m1=new E04_Motor(5000,575);
		try {
			m1.rodar(120000);
			m.rodar(1000);
			m.rodar(250000);
			m.rodar(50000);
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		m.println();m1.println();
	}
}
