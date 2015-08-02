package ft.ufam.ptr.semaforo.graphics.lights;

import javax.swing.*;
import ft.ufam.ptr.semaforo.*;
import ft.ufam.ptr.semaforo.graphics.*;

/** Classe responsável pelos efeitos gráficos dos
 *  semáforos de pedestres.
 *  @author Felipe André
 *  @version 1.0, 30/07/2015 */
public class WalkerLightsManager extends LightsManager {

	/** Apenas repassa o label à classe pai */
	public WalkerLightsManager(JLabel semaforo) {
		super(semaforo);
	}

	@Override
	public void changeState(Estado novo) {
		setEstadoAtual(novo);
		
		switch (novo) {
		
			case VERDE:
				setLight(Icone.Pedestre.VERDE);
			break;
			
			case VERMELHO:
				setLight(Icone.Pedestre.VERMELHO);
			break;
				
			case VERMELHO_INTERMITENTE:
				blinkLights();
			break;
			
			default:
			break;
		}
	}

	@Override
	protected void blinkLights() {
		new BlinkLights(this).start();
	}

	/** Implementa a máquina dos estados intermitentes do semáforo */
	private class BlinkLights extends Blinker {

		/** Apenas repassa o gerenciador ao pai */
		public BlinkLights(LightsManager manager) {
			super(manager);
		}

		@Override
		public void run() {
			Estado atual = getEstadoAtual();
		
			switch (atual) {
			
				case VERMELHO_INTERMITENTE:
					blink(Icone.Pedestre.VERMELHO, Icone.Pedestre.OFF);
				break;
				
				default:
				break;
				
			}
			
		}
		
	}
	
}
