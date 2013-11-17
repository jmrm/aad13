package RepasoXXXX;

public class E06_Camion extends E05_Coche {
	private double tara;
	private double pma;
	private E03_Rueda [] ruedasRemolque;

	E06_Camion(int Cubicaje, int Potencia, String marca, int diametroPulgadas, int anchuraNominalMm,
			int ratioAspectoPc, Double tara, Double pma, int numRuedasRemolque) {
		super(Cubicaje, Potencia, marca, diametroPulgadas, anchuraNominalMm,
				ratioAspectoPc); //invoca al constructor de E05_Coche
		this.tara=tara;
		this.pma=pma;
		this.ruedasRemolque=new E03_Rueda[numRuedasRemolque];
		for (int i = 0; i < numRuedasRemolque; i++)
			ruedasRemolque[i]=new E03_Rueda(marca, diametroPulgadas, anchuraNominalMm,
					ratioAspectoPc);
	}

	E06_Camion(Double tara, Double pma, int numRuedasRemolque) {
		super(); //invoca al constructor de E05_Coche
		this.tara=tara;
		this.pma=pma;
		this.ruedasRemolque=new E03_Rueda[numRuedasRemolque];
		for (int i = 0; i < numRuedasRemolque; i++)
			ruedasRemolque[i]=new E03_Rueda();
	}

	E06_Camion() {
		this(8500d,24000d,6);
	}

	public void rodar(int km) throws Exception {

		/*
		 * Para "rodar" un camión intentaremos primero "rodar" la parte del "cuerpo" del camión
		 * (la que hereda de coche). Si se dispara alguna excepción las ruedas del remolque no
		 * llegan a rodar. Si alguna rueda del remolque está pinchada y ya han "rodado" alguna
		 * de las anteriores, se vuelven a rodar éstas invirtiendo el sentido de rodadura
		 */
		try {
			super.rodar(km);
		}
		// Si la excepción es que hay alguna rueda pinchada en la parte tractora no intenta 
		// rodar las ruedas del remolque
		catch (E03_RuedaPinchadaException E03_rpe) {
			// Al disparar una nueva excepción retorna directamente al método invocante
			throw new Exception ("No se ha podido rodar. Rueda de parte tractora pinchada.");
		}
		// Si le excepción es de motor agotado simplemente visualiza un mensaje y sigue, pues
		// esta excepción permite rodar con lo que las ruedas de la parte tractora habrán rodado
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		int i=0;
		// Anda con cuidado por si alguna rueda del remolque está pinchada
		try {
			for (i = 0; i < ruedasRemolque.length; i++)
				ruedasRemolque[i].rodar(km);	
		}	
		catch (E03_RuedaPinchadaException E03_rpe) {
			// Si había alguna rueda del remolque pinchada "desrodamos" las que hayan rodado
			//System.out.println(E03_rpe.getMessage());
			super.rodar(-km);
			for (int j = 0; j < i; j++) {
				ruedasRemolque[j].rodar(-km);
			}
			throw new Exception("No ha podido rodar. Pinchada la rueda de repuesto "+i);
		}
	}
		

	public void print() {
		super.print();
		System.out.println("");
		System.out.println("Tara: "+tara+" kg");
		System.out.println("PMA: "+pma+" kg");
		for (int i=0; i<ruedasRemolque.length; i++)
			ruedasRemolque[i].println();
	}

	public void println() {
		this.print();
		System.out.print('\n');
	}

	public static void main(String[] args) {
		E06_Camion c=new E06_Camion();
		E06_Camion c1=new E06_Camion(1500.,6000.,2);
		E06_Camion c2=new E06_Camion(12000,650,"Pirelli",32,415,55,1500.,35000.,8);
		try {
			c.rodar(15000);
			c1.rodar(45000);
			c2.rodar(25477);
			//c.ruedasRemolque[3].pinchar(); // pinchamos una rueda del remolque
			//c2.rdas[3].pinchar(); // pinchamos una rueda de la parte "coche"
			c.rodar(350000);
			c2.rodar(150000);
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		System.out.println("Camión nº 1");c.println();
		System.out.println("Camión nº 2");c1.println();
		System.out.println("Camión nº 3");c2.println();
	}
}
