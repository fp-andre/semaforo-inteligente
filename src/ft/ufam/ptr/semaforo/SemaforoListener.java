package ft.ufam.ptr.semaforo;

import java.util.EventListener;

/** Interface principal na implementação dos Listeners. Este serve para
 *  avisar os semáforos escravos sobre o estado atual do seu mestre.
 *  @see EventListener
 *  @author Felipe André
 *  @version 1.0, 15/06/2015 */
public interface SemaforoListener extends EventListener {
	
	/** Método executado a cada mudança de estado do semáforo
	 *  @param event - Semáforo que gerou este evento */
	void onStateChange(SemaforoEvent event);

}
