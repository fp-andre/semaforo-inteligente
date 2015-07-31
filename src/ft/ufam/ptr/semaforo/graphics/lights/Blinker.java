package ft.ufam.ptr.semaforo.graphics.lights;

import javax.swing.*;

/** Cria o efeito do piscar das lâmpadas do semáforo
 *  @author Felipe André
 *  @version 1.0, 30/07/2015 */
public abstract class Blinker extends Thread {
	
	/* Classe Pai */
	private LightsManager manager;
	
	/** Apenas recebe o gerenciador de semáforos */
	public Blinker(LightsManager manager) {
		this.manager = manager;
	}
	
	/** Método que "pisca" uma lâmpada do semáforo */
	protected void blink(Icon on, Icon off) {
		final int millis = manager.getSleepTime();
		try {
			manager.setLight(on);
			sleep(millis);
			manager.setLight(off);
			sleep(millis);
		}
		catch (InterruptedException exception) { }
	}
	
	/** Implementação da máquina de estados dos efeitos */
	@Override
	public abstract void run();
	
}