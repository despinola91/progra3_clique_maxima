package negocio;

import java.util.Comparator;

public class ComparadorPorCociente implements Comparator<Objeto>
{
	@Override
	public int compare(Objeto uno, Objeto otro)
	{
		double cocienteUno = uno.getBeneficio() / (double)uno.getPeso();
		double cocienteOtro = otro.getBeneficio() / (double)otro.getPeso();
		
		if( cocienteUno < cocienteOtro )
			return 1;
		else if( cocienteUno == cocienteOtro )
			return 0;
		else
			return -1;
	}
}
