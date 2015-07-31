package ft.ufam.ptr.semaforo.clock;

import java.util.ArrayList;

/** Implementa o relógio do sistema.
 *  @author Felipe André
 *  @version 1.5, 30/07/2015 */
public class Clock extends Thread {

	/* Atributos da classe */
	private ArrayList<ClockListener> listaListeners;
	private long ciclos;
	private long sleepTime;
	
	/** Inicializa o clock. Nota: este possui uma contagem de ciclos interna que começa em 1 e é
	 *  incrementada a cada evento de clock.
	 *  @see {@link #getCiclos() getCiclos}
	 *  @param millis - Tempo (em ms) que o Clock deve esperar antes de disparar um evento */
	public Clock() {
		this.listaListeners = new ArrayList<ClockListener>();
		this.ciclos = 1;
	}
	
	/** Inicializa a base de tempo */
	public void setBaseTime(int millis) {
		this.sleepTime = millis;
	}
	
	/** Cadastra um novo objeto ouvinte na lista de listeners */
	public synchronized void addClockListener(ClockListener listener) {
		if (!listaListeners.contains(listener))
			listaListeners.add(listener);
	}
	
	/** Remove um objeto ouvinte da lista de listeners */
	public synchronized void removeClockListener(ClockListener listener) {
		listaListeners.remove(listener);
	}
	
	@SuppressWarnings("unchecked")
	/** Dispara um evento de clock a todos os objetos que estão esperando por este */
	private void alertaMudancaClock() {
		ArrayList<ClockListener> lista;
		
		synchronized (this) {
			lista = (ArrayList<ClockListener>) listaListeners.clone();
		}
		
		ClockEvent evento = new ClockEvent(this);
		
		for (ClockListener listener: lista)
			listener.evento(evento);
	}
	
	/** Retorna a quantidade de ciclos desde que
	 *  o relógio do sistema foi iniciado. */
	public long getCiclos() {
		return ciclos;
	}

	/** Método que dispara eventos de clock a cada unidade
	 *  de tempo informada pelo construtor (sleepTime) */
	@Override
	public void run() {
		
		/** O tratamento realizado por estas variáveis garante maior precisão
		 *  na operação de bloqueio (sleep) e desbloqueio (wake up) da Thread */
		long start, laps;
		
		while (!isInterrupted()) {
			
			start = System.currentTimeMillis();
			alertaMudancaClock();	  ciclos++;
			laps  = System.currentTimeMillis() - start;
			
			try { sleep(sleepTime-laps); }
			catch (InterruptedException exception) { return; }
			
			System.out.println(getCiclos());
			
		}
		
	}
	
}
