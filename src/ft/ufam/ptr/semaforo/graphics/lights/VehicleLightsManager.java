package ft.ufam.ptr.semaforo.graphics.lights;

import javax.swing.*;
import ft.ufam.ptr.semaforo.*;
import ft.ufam.ptr.semaforo.graphics.*;

/** Classe responsável pelos efeitos gráficos dos
 *  semáforos de veículos.
 *  @author Felipe André
 *  @version 1.0, 30/07/2015 */
public class VehicleLightsManager extends LightsManager {

	/** Apenas repassa o label à classe pai */
	public VehicleLightsManager(JLabel semaforo) {
		super(semaforo);
	}

	@Override
	public void changeState(Estado novo) {
		setEstadoAtual(novo);
		
		switch (novo) {
		
			case VERDE:
				setLight(Icone.Veiculo.VERDE);
			break;
			
			case AMARELO:
				setLight(Icone.Veiculo.AMARELO);
			break;
			
			case VERMELHO:
				setLight(Icone.Veiculo.VERMELHO);
			break;
				
			case VERDE_INTERMITENTE:
			case VERMELHO_INTERMITENTE:
				blinkLights();
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
			
				case VERDE_INTERMITENTE:
					blink(Icone.Veiculo.VERDE, Icone.Veiculo.OFF);
				break;
				
				case VERMELHO_INTERMITENTE:
					blink(Icone.Veiculo.VERMELHO, Icone.Veiculo.OFF);
				break;
				
				default:
				break;
			
			}
		}
		
	}

}
