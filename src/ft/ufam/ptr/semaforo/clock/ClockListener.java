package ft.ufam.ptr.semaforo.clock;

import java.util.EventListener;

/** Interface principal na implementação dos eventos de clock. Esta
 *  deve ser implementada por todas as classes que dependem do clock.
 *  @see EventListener
 *  @author Felipe André
 *  @version 1.0, 15/06/2015 */
public interface ClockListener extends EventListener {
	
	/** Método executado a cada evento do Clock
	 *  @param event - Objeto que gerou este evento, no caso o próprio Clock */
	void evento(ClockEvent event);

}
