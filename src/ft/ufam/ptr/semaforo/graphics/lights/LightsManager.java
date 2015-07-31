package ft.ufam.ptr.semaforo.graphics.lights;

import javax.swing.*;
import ft.ufam.ptr.semaforo.*;

/** Superclasse do sistema de gerenciamento de efeitos
 *  gráficos dos semáforos de veículos e pedestres.
 *  @author Felipe André
 *  @version 1.0/ 30/07/2015 */
public abstract class LightsManager {
	
	/* Atributos da classe */
	private static final int millis = 500;
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
	
	private class SetLight implements Runnable {

		private Icon icone;
		
		public SetLight(Icon icone) {
			this.icone = icone;
		}
		
		@Override
		public void run() {
			semaforo.setIcon(icone);
		}
		
	}
	
	protected void setEstadoAtual(Estado novo) {
		this.atual = novo;
	}
	
	protected Estado getEstadoAtual() {
		return atual;
	}
	
	protected int getSleepTime() {
		return millis;
	}
	
	public abstract void changeState(Estado novo);
	protected abstract void blinkLights();

}
