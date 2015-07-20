package ft.ufam.ptr.semaforo.clock;

/** API para implementação de um evento de clock. */
public abstract class ClockAdapter implements ClockListener {

	@Override
	/** Método a ser sobrescrito */
	public abstract void evento(ClockEvent event);

}
