package RepasoXXXX;

import java.util.Arrays;

public class CopiaSuperficial {

	public static void main(String[] args) throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		int [] m1={0,5,7},m2;
		System.out.println("m2=m1");
		m2=m1;
		System.out.println("m1:"+Arrays.toString(m1));
		System.out.println("m2:"+Arrays.toString(m2));
		System.out.println("m1[2]=18");
		m1[2]=18;
		System.out.println("m1:"+Arrays.toString(m1));
		System.out.println("m2:"+Arrays.toString(m2));
		System.out.println("Ahora con clases:");
		System.out.println("Conten c1=new Conten(m1):");
		Conten c1=new Conten(m1);
		System.out.println("c1.println()");
		c1.println();
		System.out.println("m1[2]=50");
		m1[2]=50;
		System.out.println("c1.println()");
		c1.println();
		System.out.println("Conten c2=new Conten(c1):");
		Conten c2=new Conten(c1);
		System.out.println("c2.println()");
		c2.println();
		System.out.println("m1[2]=100");
		m1[2]=100;
		System.out.println("c1.println()");
		c1.println();
		System.out.println("c2.println()");
		c2.println();
		System.out.println("Conten c3=(Conten) c2.clone()");
		Conten c3=(Conten) c2.clone();
		System.out.println("c3.println()");
		c3.println();
		System.out.println("m1[2]=200");
		m1[2]=200;
		System.out.println("c1.println();c2.println();c3.println()");
		c1.println();c2.println();c3.println();
		System.out.println("m2[0]=0;m2[1]=0;m2[2]=0");
		m2[0]=0;m2[1]=0;m2[2]=0;
		System.out.println("Copia superficial:c3.setm(m2)");
		c3.setm(m2);
		System.out.println("c3.println()");
		c3.println();
		System.out.println("m2[2]=1000");
		m2[2]=1000;
		System.out.println("c3.println()");
		c3.println();
		System.out.println("m2[0]=0;m2[1]=0;m2[2]=0");
		m2[0]=0;m2[1]=0;m2[2]=0;
		Conten c4=(Conten)c2.clone();
		System.out.println("Copia profunda c4.setmProf(m2)");
		c4.setmProf(m2);
		System.out.println("c4.println()");
		c4.println();
		System.out.println("m2[2]=1000");
		m2[2]=1000;
		System.out.println("c4.println()");
		c4.println();
	}

}
