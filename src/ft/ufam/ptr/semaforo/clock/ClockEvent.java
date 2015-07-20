package ft.ufam.ptr.semaforo.clock;

import java.util.EventObject;

/** Implementação do evento de clock.
 *  @see EventObject
 *  @author Felipe André
 *  @version 1.0, 15/06/2015 */
public class ClockEvent extends EventObject {

	private static final long serialVersionUID = 1L;

	/** Repassa o objeto que gerou o evento ao EventObject
	 *  @see EventObject */
	public ClockEvent(Clock clock) {
		super(clock);
	}

}
