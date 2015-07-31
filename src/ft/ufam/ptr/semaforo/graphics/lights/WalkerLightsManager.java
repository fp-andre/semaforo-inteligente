package ft.ufam.ptr.semaforo.graphics.lights;

import javax.swing.*;
import ft.ufam.ptr.semaforo.*;
import ft.ufam.ptr.semaforo.graphics.*;
import ft.ufam.ptr.semaforo.graphics.lights.LightsManager;

public class WalkerLightsManager extends LightsManager {

	public WalkerLightsManager(JLabel semaforo) {
		super(semaforo);
	}

	@Override
	public void changeState(Estado novo) {
		setEstadoAtual(novo);
		
		switch (novo) {
		
			case VERDE:
				setLight(Icone.Veiculo.VERDE);
			break;
			
			case VERMELHO:
				setLight(Icone.Veiculo.VERMELHO);
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

	private class BlinkLights extends Blinker {

		public BlinkLights(LightsManager manager) {
			super(manager);
		}

		@Override
		public void run() {
			Estado atual = getEstadoAtual();
			
			while (!isInterrupted()) {
				switch (atual) {
				
					case VERMELHO_INTERMITENTE:
						blink(Icone.Veiculo.VERMELHO, Icone.Veiculo.OFF);
					break;
					
					default:
						interrupt();
					break;
					
				}
			}
		}
		
	}
	
}
