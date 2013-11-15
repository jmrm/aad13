package RepasoXXXX;
//incluye ejemplo de Cloneable

public class E03_Rueda implements Cloneable {

	private String marca; // No se pedía pero la necesitamos para el ejemplo de clonación
	private int diametroPulgadas;
	private int anchuraNominalMm;
	private int ratioAspectoPc;
	private static final int limiteKm = 60000;
	private int rodaduraKm = 0;
	private boolean pinchada = false;
	private boolean cambiar = false;

	// Constructor parametrizado
	E03_Rueda(String marca, int diametroPulgadas, int anchuraNominalMm,
			int ratioAspectoPc) {
		this.marca=marca;
		this.diametroPulgadas = diametroPulgadas;
		this.anchuraNominalMm = anchuraNominalMm;
		this.ratioAspectoPc = ratioAspectoPc;
	}

	// Constructor por defecto que invoca al parametrizado
	E03_Rueda() {
		/*
		 * Lo siguiente es una invocación explícita de un constructor desde otro
		 * constructor. Ha de usar this. Además si está presente ha de ser la primera
		 * línea ejecutable del constructor que invoca
		 */
		this("Marca outlet",17,215,55);
	}
	
	// Constructor por copia
	E03_Rueda(E03_Rueda r) {
		this.marca=r.marca;
		this.diametroPulgadas=r.diametroPulgadas;
		this.anchuraNominalMm=r.anchuraNominalMm;
		this.ratioAspectoPc=r.ratioAspectoPc;
		this.rodaduraKm=r.rodaduraKm;
		this.pinchada=r.pinchada;
		this.cambiar=r.cambiar;
	}
	
    protected Object clone() throws CloneNotSupportedException {
    	/* Invoca al método clone() de la clase ancestro, los métodos clone() devuelven
    	 * instancias de la clase Object por lo que es preciso moldear (cast) hacia la clase
    	 * deseada
    	 */
    	E03_Rueda clon=(E03_Rueda)super.clone();
    	//clonar el resto (shallow copy porque no hay campos mutable)
    	//clon.objeto=(objeto)propiedad.clone();  //para campos "mutable"
		clon.marca=marca;  //no necesita copia defensiva, String es inmutable
		clon.diametroPulgadas=diametroPulgadas;
		clon.anchuraNominalMm=anchuraNominalMm;
		clon.ratioAspectoPc=ratioAspectoPc;
		clon.rodaduraKm=rodaduraKm;
		clon.pinchada=pinchada;
		clon.cambiar=cambiar;
		//salir
		return clon;
    }

	public void rodar(int km) throws E03_RuedaPinchadaException {
		// Una rueda pinchada lanza la excepción correspondiente
		if (! pinchada) {
			rodaduraKm += km;
			if (rodaduraKm >= limiteKm) {
				cambiar = true;
			} else cambiar = false;
		} else
			throw new E03_RuedaPinchadaException("La rueda está pinchada, no puede rodar!");
	}

	public void pinchar() {
		pinchada = true;
	}
	
	public boolean esta_pinchada() {
		return pinchada;
	}

	public void reparar() {
		pinchada = false;
	}
	
	public void print() {
		int difKm=limiteKm-rodaduraKm;
		System.out.println("Marca: \""+marca+'"');
		System.out.println("Diámetro: "+diametroPulgadas+'"');
		System.out.println("Anchura: "+anchuraNominalMm+" mm");
		System.out.println("Relación de Aspecto: "+ratioAspectoPc+'%');
		if ( (difKm) >= 0)
			System.out.println("Km: "+rodaduraKm+" restan "+difKm);
		else
			System.out.println("Km: "+rodaduraKm+" excedidos "+ (-difKm));
		System.out.println("Pinchada: "+pinchada);
		System.out.println("Cambiar: "+cambiar);
	}
	
	public void println() {
		print();
		System.out.print('\n');// System.out.println();
	}
	/*
	 * Método para prueba de valores
	 */
	public static void main(String[] args) {
		E03_Rueda r1=new E03_Rueda(), r2=new E03_Rueda("Michelín",18,225,40);
		try {
			r1.rodar(65000); //excedemos el límite de rodadura
			r1.pinchar();
			r2.rodar(34500);
			r2.rodar(100);
			r2.pinchar();
			r2.reparar();
			r1.rodar(5); // no puede rodar, está pinchada
		}
		catch (E03_RuedaPinchadaException e)
		{
			System.out.println(e.getMessage());
		}
		r1.println();
		r2.println();
		// Invoca al constructor copia
		E03_Rueda r3=new E03_Rueda(r2);	 
		r3.println();
		// Ahora clonamos
		try {
			E03_Rueda r4=(E03_Rueda)r2.clone();
			r4.println();
		}
		catch (CloneNotSupportedException cnse) {
			System.out.println("La clase E03_Rueda no soporta clonación");
		}
	}
}