package RepasoXXXX;

public class E05_Coche {
	private E04_Motor e04_Motor;
	/*
	 * Las ruedas (rdas[]) van a ser protected para que las podamos acceder desde las clases
	 * hijas 
	 */
	protected E03_Rueda [] rdas=new E03_Rueda[4];

	E05_Coche(int Cubicaje, int Potencia, String marca, int diametroPulgadas, int anchuraNominalMm,
			int ratioAspectoPc) {
		e04_Motor = new E04_Motor(Cubicaje, Potencia);
		for (int i = 0; i < rdas.length; i++) {
			rdas[i] = new E03_Rueda(marca,diametroPulgadas,anchuraNominalMm, ratioAspectoPc);
		}
	}
	
	E05_Coche() {
		e04_Motor = new E04_Motor();
		for (int i = 0; i < rdas.length; i++) {
			rdas[i] = new E03_Rueda();
		}
	}

	public void rodar(int km) throws Exception {
		/* 
		 * Vamos a controlar que ni el motor ni ninguna rueda rueden si hay alguna rueda pinchada. 
		 * Si, por ejemplo, la rueda dos está pinchada, la 1 rodará (porque en el bucle siguiente 
		 * va delante de la 2), con lo que una vez que salte la excepción de la rueda 2 habrá 
		 * que restarle los kilómetros que ha rodado. El motor sólo rueda si no salta la excepción
		 * de ruedas pinchadas
		 */
		int i=0;
		try {
			for (i = 0; i < rdas.length; i++) {
				rdas[i].rodar(km);
			}
			// El motor sólo rueda si no hay ruedas pinchadas
			e04_Motor.rodar(km);
		}
		catch (E03_RuedaPinchadaException E03_rpe) {
			// Si había alguna rueda pinchada "desrodamos" las que hayan rodado
			//System.out.println(E03_rpe.getMessage());
			for (int j = 0; j < i; j++) {
				rdas[j].rodar(-km);
			}
			throw new E03_RuedaPinchadaException("No ha podido rodar. Pinchada la rueda "+i);
		}
	}

	public void print() {
		e04_Motor.println();
		for (int i=0; i<rdas.length; i++)
			rdas[i].println();
	}

	public void println() {
		this.print();
		System.out.print('\n');
	}

	public static void main(String[] args) {
		E05_Coche c=new E05_Coche();
		E05_Coche c1=new E05_Coche(3200,280,"Michelín",18,225,40);
		try {
			c.rodar(10000);
			c.rodar(45000);
			c.rodar(5120);
			c1.rodar(35455);
			//Pinchamos una rueda
			c.rdas[3].pinchar();
			//Intentamos rodar
			c.rodar(1000000);
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		System.out.println("Coche nº 1");
		c.println();
		System.out.println("Coche nº 2");
		c1.println();
	}
}
