package ft.ufam.ptr.semaforo.model;

import ft.ufam.ptr.semaforo.*;
import ft.ufam.ptr.semaforo.clock.*;
import ft.ufam.ptr.semaforo.graphics.TelaPrincipal;

/** Executa a simulação do sistema.
 *  @author Felipe André
 *  @author Paulo Henrique
 *  @version 3.5, 15/07/2015 */
public class Simulador {

	public Simulador(TelaPrincipal screen) {
		Clock clock = new Clock(500);
		
		Via entrada = new Via(Local.JAPIIM_ENTRADA_CAMPUS, 5);
		Via saida   = new Via(Local.ENTRADA_CAMPUS);
		
		SemaforoMaster semaforo1 = new SemaforoMaster(entrada.getLocalizacao());
		SemaforoMaster semaforo2 = new SemaforoMaster(saida.getLocalizacao());
		
		semaforo1.setEstadoVerde();
		semaforo1.setTempoVerde(5);
		semaforo1.setSemaforoSimetrico(semaforo2);
		
		semaforo2.setTempoVerde(5);
		semaforo2.setSemaforoSimetrico(semaforo1);
		
		semaforo1.addSemaforoListener(screen);
		semaforo2.addSemaforoListener(screen);
		
		Cruzamento crz = new Cruzamento(entrada, semaforo1,screen);
		
		crz.insereViaSaida(saida);
		
		Gerador gerador = new Gerador(100, 2, Local.ENTRADA_CAMPUS, entrada);
		
		clock.addClockListener(semaforo1);
		clock.addClockListener(semaforo2);
		
		clock.addClockListener(gerador);
		clock.addClockListener(entrada);
		clock.addClockListener(crz);
		
		clock.start();
	}
	
}
