package ft.ufam.ptr.semaforo;

/** Define a forma mais genérica de um semáforo.
 *  @see SemaforoMaster
 *  @see SemaforoSlave
 *  @author Felipe André
 *  @author Paulo Henrique
 *  @version 5.0, 15/07/2015 */
public class Semaforo {
	
	/* Atributos da classe */
	private Estado estadoAtual;
	private int ciclos = 1;
	
	/** Inicializa o semáforo com estado inicial em "ATENCAO" */
	public Semaforo() {
		setEstadoAtual(Estado.VERMELHO_INTERMITENTE);
	}
	
	/** Modifica o estado atual do semáforo */
	protected void setEstadoAtual(Estado novoEstado) {
		this.estadoAtual = novoEstado;
	}
	
	/** Recupera o estado atual do semáforo */
	public Estado getEstadoAtual() {
		return this.estadoAtual;
	}

	/** Verifica se o semáforo está aberto */
	public boolean aberto() {
		return (estadoAtual != Estado.VERMELHO) && (estadoAtual != Estado.VERMELHO_INTERMITENTE);
	}
	
	/** Incrementa o contador de ciclos de clock */
	protected void incrementaCiclos() {
		this.ciclos++;
	}
	
	/** Reinicia o contador de ciclos de clock */
	protected void reiniciaCiclos() {
		this.ciclos = 1;
	}
	
	/** Recupera o contador de ciclos de clock */
	protected int getCiclos() {
		return this.ciclos;
	}

	/** Imprime o estado atual do semáforo */
	protected void imprimeEstadoAtual() {
		System.out.println(estadoAtual.name());
	}
	
}
