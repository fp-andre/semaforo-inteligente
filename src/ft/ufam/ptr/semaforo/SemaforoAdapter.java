package ft.ufam.ptr.semaforo;

/** API para implementação de um evento de semáforo. */
public abstract class SemaforoAdapter implements SemaforoListener {
	
	@Override
	/** Método a ser sobrescrito */
	public abstract void onStateChange(SemaforoEvent event);

}
