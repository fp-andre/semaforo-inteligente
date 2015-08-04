package ft.ufam.ptr.semaforo;

import ft.ufam.ptr.semaforo.clock.*;
import ft.ufam.ptr.semaforo.model.*;

/** Implementação do semáforo mestre do sistema. Este, por sua vez, move
 *  a máquina de estados e sinaliza aos seus escravos o seu estado atual.
 *  Nota: entre os mestres, um sempre é simétrico em relação ao outro, isto
 *  evita inconsistências nos seus estados e torna o sistema mais robusto.
 *  @see Semaforo
 *  @see SemaforoSlave
 *  @author Felipe André
 *  @author Paulo Henrique
 *  @version 3.0, 03/08/2015 */
public class SemaforoMaster extends Semaforo implements ClockListener {

	/* Variáveis de temporização dos semáforos */
	private int tempoVerde;
	private int faixaTempo;
	private int fluxo;
	private boolean smartEnabled = true;
	private final int tempoVerdeIT;
	private final int tempoAmarelo;
	private final int tempoAvisoVermelho;
	
	/* Quantidade máxima de faixas de tempo que o
	 * semáforo pode permanecer em estado verde */
	private static final int FAIXA_TEMPO_MAX = 3;
	
	/** Muito útil no estado {@link #autoControle() VERMELHO} */
	private boolean wait;
	
	/* Semáforos do sistema */
	private SemaforoMaster simetrico;
	
	/* Nome da localização deste semáforo */
	private final String nome;
	
	/** Inicializa as temporizações fixas do semáforo
	 *  @see Semaforo */
	public SemaforoMaster(Local local) {
		super(local);
		this.faixaTempo = 0;
		this.tempoVerdeIT = Tempo.VERDE_INTERMITENTE;
		this.tempoAmarelo = Tempo.AMARELO;
		this.tempoAvisoVermelho = Tempo.AVISO_VERMELHO;
		this.nome = getLocalizacao().getInfos();
		setTempoVerde();
	}
	
	/** Cadastra o semáforo simétrico desta classe */
	public void setSemaforoSimetrico(SemaforoMaster simetrico) {
		this.simetrico = simetrico;
	}
	
	/** Cadastra o semáforo escravo desta classe */
	public void setSemaforoSlave(SemaforoListener slave) {
		addSemaforoListener(slave);
	}
	
	/** Modifica o tempo máximo (dado em ciclos) que
	 *  o semáforo pode permanecer no estado verde  */
	private void setTempoVerde() {
		this.tempoVerde = Tempo.VERDE;
	}
	
	/** Verifica se a quantidade máxima de faixas de tempo que
	 *  o semáforo pode permanecer em verde foi ultrapassada  */
	private boolean IStillCanStayGreen() {
		return (faixaTempo < FAIXA_TEMPO_MAX);
	}
	
	/** Incrementa o contador de faixa de tempo */
	private void incrementaFaixaTempo() {
		faixaTempo++;
	}
	
	/** Reinicia o contador de faixa de tempo */
	private void reiniciaFaixaTempo() {
		faixaTempo = 0;
	}
	
	/** Verifica se o fluxo de veículos está alto */
	private boolean fluxoEstaAlto() {
		int razao = (fluxo / faixaTempo);
		return razao >= Fluxo.MEDIO;
	}
	
	/** Prepara o fechamento do semáforo */
	private void preparaFechamentoSemaforo() {
		reiniciaFaixaTempo();
		setEstadoAtual(Estado.VERDE_INTERMITENTE);
	}
	
	/** Muda o estado do semáforo para vermelho e
	 *  sinaliza aos seus dependentes este evento */
	public void setEstadoVermelho() {
		reiniciaCiclos();
		setEstadoAtual(Estado.VERMELHO);
		disparaEventos();
	}
	
	/** Muda o estado do semáforo para verde e
	 *  sinaliza aos seus dependentes este evento */
	public void setEstadoVerde() {
		reiniciaCiclos();
		setEstadoAtual(Estado.VERDE);
		
		System.out.printf("Semáforo \"%s\" é o líder!\n",getLocalizacao().getInfos());
		simetrico.setEstadoVermelho();
		
		disparaEventos();
		this.wait = true;
	}
	
	/** Verifica se um determinado tempo foi esgotado */
	private boolean tempoEsgotado(int tempo) {
		return (getCiclos() > tempo);
	}
	
	/** Implementação da máquina de estados do semáforo */
	private void runStateMachine() {
		incrementaCiclos();
		
		Estado estadoAtual = getEstadoAtual();
		
		switch (estadoAtual) {
		
			case VERDE:
				simetrico.setEstadoVermelho();
				if (tempoEsgotado(tempoVerde))
					processaEstadoVerde();
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
			
			default:
			break;
		}
		
	}
	
	/** Ativa ou desativa a inteligência do semáforo */
	public void setSmartStatus(boolean status) {
		this.smartEnabled = status;
	}
	
	/** Permite uma maior flexibilidade à inteligência do sistema. Caso esta
	 *  esteja desativada, o semáforo segue a máquina de estados padrão. */
	private void processaEstadoVerde() {
		if (smartEnabled)
			inteligencia();
		else {
			setEstadoAtual(Estado.VERDE_INTERMITENTE);
			reiniciaCiclos();
		}
	}
	
	/** Atualiza o fluxo de veículos */
	public void setFluxo(int fluxo) {
		this.fluxo = fluxo;
	}
	
	/** Adia o estado verde em mais uma faixa de tempo */
	private void reiniciaTempoVerde() {
		String local = getLocalizacao().getInfos();
		int cc = Tempo.VERDE;
		System.out.printf("Como o fluxo está alto, o semáforo \"%s\" permanecerá mais um período de %d ciclos em estado verde!\n",local,cc);
		setTempoVerde();
	}
	
	/** Implementação da inteligência do semáforo */
	private void inteligencia() {
		
		incrementaFaixaTempo();
		
		if (IStillCanStayGreen()) {
			
			if (fluxoEstaAlto())
				reiniciaTempoVerde();
			else {
				System.out.printf("Como o fluxo está normal, o semáforo \"%s\" irá se preparar para fechar!\n",nome);
				preparaFechamentoSemaforo();
			}
		}
		else {
			System.out.printf("Semáforo \"%s\" já passou muito tempo no estado verde, preparando para fechar!\n",nome);
			preparaFechamentoSemaforo();
		}
		
		reiniciaCiclos();
	}
	
	@Override
	public void evento(ClockEvent event) {
		runStateMachine();
		disparaEventos ();
	}
	
}
