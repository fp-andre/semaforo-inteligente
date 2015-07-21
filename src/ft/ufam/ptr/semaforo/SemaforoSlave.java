package ft.ufam.ptr.semaforo;

import ft.ufam.ptr.semaforo.model.Local;

/** Implementação do semáforo escravo do sistema. Este, por sua vez,
 *  apenas copia o estado atual do semáforo mestre associado a este.
 *  @see Semaforo
 *  @see SemaforoMaster
 *  @author Felipe André
 *  @author Paulo Henrique
 *  @version 1.0, 15/07/2015 */
public class SemaforoSlave extends Semaforo implements SemaforoListener {

	public SemaforoSlave(Local local) {
		super(local);
	}

	/** Copia o estado atual do semáforo mestre */
	private void copiaEstadoAtual(Semaforo master) {
		Estado estadoMaster = master.getEstadoAtual();
		setEstadoAtual(estadoMaster);
	}
	
	@Override
	public void onStateChange(SemaforoEvent event) {
		Semaforo master = (Semaforo) event.getSource();
		copiaEstadoAtual(master);
		
		disparaEventos();
	}
	
}
