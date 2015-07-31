package ft.ufam.ptr.semaforo.interpreter;

/** Definição das constantes de fluxo de veículos das vias
 *  @author Felipe André
 *  @version 1.0, 30/07/2015 */
public enum Taxa {
	
	/* Definição das constantes */
	BAIXA(2), MEDIA(4), ALTA(6);
	
	/* Valor do fluxo por ciclo associado a cada constante */
	private final int valor;
	
	/** Apenas setando os valores de fluxo */
	Taxa(int valor) {
		this.valor = valor;
	}
	
	/** Recupera o valor da constante */
	public int getValor() {
		return valor;
	}

}
