package ft.ufam.ptr.semaforo.graphics;

import javax.swing.*;
import ft.ufam.ptr.semaforo.*;

public class LightsManager {
	
	private static final int millis = 500;
	private final JLabel semaforo;
	private Estado atual;
	
	public LightsManager(JLabel semaforo) {
		this.semaforo = semaforo;
	}

	public void changeState(Estado novo) {
		this.atual = novo;
		
		switch (atual) {
		
			case VERDE:
				setLight(Icone.VERDE);
			break;
			
			case AMARELO:
				setLight(Icone.AMARELO);
			break;
			
			case VERMELHO:
				setLight(Icone.VERMELHO);
			break;
				
			case VERDE_INTERMITENTE:
			case VERMELHO_INTERMITENTE:
				new BlinkLights().start();
			break;
		}
	}
	
	private void setLight(Icon icone) {
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
	
	private class BlinkLights extends Thread {
		
		@Override
		public void run() {
			
			while (!isInterrupted()) {
				switch (atual) {
				
					case VERDE_INTERMITENTE:
						blink(Icone.VERDE);
					break;
					
					case VERMELHO_INTERMITENTE:
						blink(Icone.VERMELHO);
					break;
					
					default:
						interrupt();
					break;
					
				}
			}
			
		}
		
		private void blink(Icon icone) {
			try {
				setLight(icone);
				sleep(millis);
				setLight(Icone.OFF);
				sleep(millis);
			}
			catch (InterruptedException exception) { }
		}
		
	}
	
}
