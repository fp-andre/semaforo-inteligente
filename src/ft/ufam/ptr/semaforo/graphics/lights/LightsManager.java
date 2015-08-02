package ft.ufam.ptr.semaforo.graphics.lights;

import javax.swing.*;
import ft.ufam.ptr.semaforo.*;
import ft.ufam.ptr.semaforo.clock.Clock;

/** Superclasse do sistema de gerenciamento de efeitos
 *  gráficos dos semáforos de veículos e pedestres.
 *  @author Felipe André
 *  @version 1.0/ 30/07/2015 */
public abstract class LightsManager {
	
	/* Atributos da classe */
	private final JLabel semaforo;
	private Estado atual;
	
	/** Inicializa o JLabel que contém o semáforo
	 *  @param semaforo - semáforo em modo gráfico
	 *  @see Simulador */
	public LightsManager(JLabel semaforo) {
		this.semaforo = semaforo;
	}
	
	/** Atualiza o estado das lâmpadas do semáforo */
	protected void setLight(Icon icone) {
		SetLight job = new SetLight(icone);
		SwingUtilities.invokeLater(job);
	}
	
	/** Classe que implementa um trabalho a ser executado
	 *  pelo escalonador de Threads do Java Swing. Este
	 *  atualiza a imagem do JLabel. */
	private class SetLight implements Runnable {

		/* Novo ícone */
		private Icon icone;
		
		/** Inicialização do ícone */
		public SetLight(Icon icone) {
			this.icone = icone;
		}
		
		/** Troca a imagem do JLabel */
		@Override
		public void run() {
			semaforo.setIcon(icone);
		}
		
	}
	
	/** Atualiza o estado atual do semáforo gráfico */
	protected void setEstadoAtual(Estado novo) {
		this.atual = novo;
	}
	
	/** Recupera o estado atual do semáforo gráfico */
	protected Estado getEstadoAtual() {
		return atual;
	}
	
	/** Recupera o tempo de sono da Thread */
	protected int getSleepTime() {
		return (Clock.SLEEP_TIME / 2);
	}
	
	/** Implementação da máquina de estados */
	public abstract void changeState(Estado novo);
	
	/** Implementação do efeito de piscar das lâmpadas do semáforo */
	protected abstract void blinkLights();

}
