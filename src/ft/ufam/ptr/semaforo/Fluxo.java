package ft.ufam.ptr.semaforo;

/** Contém as constantes que parametrizam
 *  a quantidade de fluxo das vias.
 *  @author Felipe André
 *  @version 1.0, 01/08/2015 */
public class Fluxo {
	
	/* Definição das constantes */
	public static final int BAIXO = getFluxo(33);
	public static final int MEDIO = getFluxo(66);
	public static final int ALTO  = getFluxo(100);

	/** Retorna a quantidade de fluxo correspondente à porcentagem informada.
	 *  Toma como base o tempo de semáforo verde e a quantidade de faixas da
	 *  via (no caso adotamos 2, pois todas as vias possuem 2 faixas).
	 *  @see Tempo  */
	private static int getFluxo(int porcentagem) {
		double partial = ((2 * Tempo.VERDE) * porcentagem) / 100.0;
		return (int) Math.round(partial);
	}
	
}
