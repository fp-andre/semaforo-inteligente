package ft.ufam.ptr.semaforo.model;

import ft.ufam.ptr.semaforo.*;
import ft.ufam.ptr.semaforo.clock.*;

/** Executa a simulação do sistema.
 *  @author Felipe André
 *  @author Paulo Henrique
 *  @version 3.5, 15/07/2015 */
public class Simulador {

	/** Função principal */
	public static void main(String[] args) {
		simulacaoAnterior();
	}
	
	/*public static void instanciarCenario() {
		Via via_japiim_campus  = new Via(Local.JAPIIM_ENTRADA_CAMPUS,20);
		Via via_frente_campus  = new Via(Local.ENTRADA_SAIDA_CAMPUS,10);
		Via via_campus_coroado = new Via(Local.ENTRADA_SAIDA_CAMPUS);
		Via via_entrada_campus = new Via(Local.ENTRADA_CAMPUS);
		Via via_saida_campus   = new Via(Local.SAIDA_CAMPUS_COROADO,20);
		Via via_coroado_campus = new Via(Local.COROADO_ENTRADA_CAMPUS,30);
		
		Semaforo sem_japiim_campus  = new Semaforo(Semaforo.VERDE);
		Semaforo sem_frente_campus  = new Semaforo(Semaforo.VERDE);
		Semaforo sem_saida_campus   = new Semaforo(Semaforo.VERMELHO);
		Semaforo sem_coroado_campus = new Semaforo(Semaforo.VERMELHO);
		
		Cruzamento crz_japiim_campus  = new Cruzamento(via_japiim_campus , sem_japiim_campus );
		Cruzamento crz_frente_coroado = new Cruzamento(via_frente_campus , sem_frente_campus );
		Cruzamento crz_saida_campus   = new Cruzamento(via_saida_campus  , sem_saida_campus  );
		Cruzamento crz_coroado_campus = new Cruzamento(via_coroado_campus, sem_coroado_campus);
		
		crz_japiim_campus.insereViaSaida(via_frente_campus  );
		crz_japiim_campus.insereViaSaida(via_entrada_campus );
		
		crz_frente_coroado.insereViaSaida(via_campus_coroado);
		
		crz_saida_campus.insereViaSaida(via_campus_coroado  );
		
		crz_coroado_campus.insereViaSaida(via_entrada_campus);
		crz_coroado_campus.insereViaSaida(via_frente_campus );
	}*/
	
	public static void simulacaoAnterior() {
		Clock clock = new Clock(500);
		SemaforoMaster semaforo1 = new SemaforoMaster();
		SemaforoMaster semaforo2 = new SemaforoMaster();
		
		semaforo1.setEstadoVerde();
		semaforo1.setTempoVerde(5);
		semaforo1.setSemaforoSimetrico(semaforo2);
		
		semaforo2.setTempoVerde(5);
		semaforo2.setSemaforoSimetrico(semaforo1);
		
		Via entrada = new Via(Local.JAPIIM_ENTRADA_CAMPUS, 5);
		Via saida   = new Via(Local.ENTRADA_CAMPUS);
		
		Cruzamento crz = new Cruzamento(entrada, semaforo1);
		
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
