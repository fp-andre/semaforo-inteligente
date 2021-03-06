package ft.ufam.ptr.semaforo;

import ft.ufam.ptr.semaforo.model.*;

/** Implementação do semáforo de pedestres do sistema. Este, por sua vez,
 *  realiza a cópia de seu mestre, mas de forma adaptada à sua realidade.
 *  @author Felipe André
 *  @author Paulo Henrique
 *  @version 1.0, 20/07/2015 */
public class SemaforoPedestre extends Semaforo implements SemaforoListener {

	/** Apenas repassa atributos ao pai
	 *  @see Semaforo */
	public SemaforoPedestre(Local local) {
		super(local);
	}
	
	/** Comportamento adaptado ao semáforo de pedestres */
	private void processaEstado(Estado estado) {
		
		switch (estado) {
		
			case VERDE:
			case VERDE_INTERMITENTE:
				setEstadoAtual(Estado.VERDE);
				break;
				
			case AMARELO:
				setEstadoAtual(Estado.VERMELHO_INTERMITENTE);
				break;
				
			case VERMELHO:
				setEstadoAtual(Estado.VERMELHO);
				break;
				
			default:
				setEstadoAtual(Estado.VERMELHO_INTERMITENTE);
				break;
				
		}
		
	}
	
	@Override
	public void onStateChange(SemaforoEvent event) {
		Semaforo base = (Semaforo) event.getSource();
		Estado atual = base.getEstadoAtual();
		processaEstado(atual);
		disparaEventos();
	}

}
