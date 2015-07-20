package ft.ufam.ptr.semaforo;

import java.util.EventObject;

/** Implementação do evento do semáforo.
 *  @author Felipe André
 *  @version 1.0, 15/06/2015 */
public class SemaforoEvent extends EventObject {
	
	private static final long serialVersionUID = 1L;

	/** Repassa o objeto que gerou o evento ao EventObject
	 *  @see EventObject */
	public SemaforoEvent(Semaforo semaforo) {
		super(semaforo);
	}

}
