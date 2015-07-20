package ft.ufam.ptr.semaforo;

import ft.ufam.ptr.semaforo.clock.*;

/** Implementação do semáforo mestre do sistema. Este, por sua vez, move
 *  a máquina de estados e sinaliza aos seus escravos o seu estado atual.
 *  Nota: entre os mestres, um sempre é simétrico em relação ao outro, isto
 *  evita inconsistências nos seus estados e torna o sistema mais robusto.
 *  @see Semaforo
 *  @see SemaforoSlave
 *  @author Felipe André
 *  @author Paulo Henrique
 *  @version 1.0, 15/07/2015 */
public class SemaforoMaster extends Semaforo implements ClockListener {

	/* Variáveis de temporização dos semáforos */
	private int tempoVerde;
	private final int tempoVerdeIT;
	private final int tempoAmarelo;
	private final int tempoAvisoVermelho;
	
	/** Muito útil no estado {@link #autoControle() VERMELHO} */
	private boolean wait;
	
	/* Semáforos do sistema */
	private SemaforoListener semaforoSlave;
	private SemaforoMaster simetrico;
	
	/** Inicializa as temporizações fixas do semáforo */
	public SemaforoMaster() {
		this.tempoVerdeIT = Tempo.VERDE_INTERMITENTE;
		this.tempoAmarelo = Tempo.AMARELO;
		this.tempoAvisoVermelho = Tempo.AVISO_VERMELHO;
	}
	
	/** Cadastra o semáforo simétrico desta classe */
	public void setSemaforoSimetrico(SemaforoMaster simetrico) {
		this.simetrico = simetrico;
	}
	
	/** Cadastra o semáforo escravo desta classe */
	public void setSemaforoSlave(SemaforoListener slave) {
		this.semaforoSlave = slave;
	}
	
	/** Modifica o tempo máximo (dado em ciclos) que
	 *  o semáforo pode permanecer no estado verde  */
	public void setTempoVerde(int ciclos) {
		this.tempoVerde = ciclos;
	}
	
	/** Modifica o estado atual do semáforo para verde */
	public void setEstadoVerde() {
		reiniciaCiclos();
		setEstadoAtual(Estado.VERDE);
		this.wait = true;
	}
	
	/** Dispara um evento do semáforo mestre no seu respectivo escravo */
	private void alertaSemaforoSlave() {
		if (semaforoSlave != null) {
			SemaforoEvent evento = new SemaforoEvent(this);
			semaforoSlave.onStateChange(evento);
		}
	}
	
	/** Verifica se um determinado tempo foi esgotado */
	private boolean tempoEsgotado(int tempo) {
		return (getCiclos() > tempo);
	}
	
	/** Implementação da máquina de estados do semáforo */
	private void runStateMachine() {
		incrementaCiclos();
		imprimeEstadoAtual();
		
		Estado estadoAtual = getEstadoAtual();
		
		switch (estadoAtual) {
		
			case VERDE:
				simetrico.setEstadoAtual(Estado.VERMELHO);
				if (tempoEsgotado(tempoVerde)) {
					setEstadoAtual(Estado.VERDE_INTERMITENTE);
					reiniciaCiclos();
				}
			break;
				
			case VERDE_INTERMITENTE:
				if (tempoEsgotado(tempoVerdeIT)) {
					setEstadoAtual(Estado.AMARELO);
					reiniciaCiclos();
				}
			break;
			
			case AMARELO:
				if (tempoEsgotado(tempoAmarelo)) {
					setEstadoAtual(Estado.VERMELHO);
					reiniciaCiclos();
				}
			break;
				
			case VERMELHO:
				if (tempoEsgotado(tempoAvisoVermelho) && wait) {
					simetrico.setEstadoVerde();
					this.wait = false;
				}
			break;
			
			case VERMELHO_INTERMITENTE:
				System.out.println("Solicitando sincronização");
			break;
		}
		
		alertaSemaforoSlave();
	}
	
	@Override
	public void evento(ClockEvent event) {
		runStateMachine();
	}
	
}