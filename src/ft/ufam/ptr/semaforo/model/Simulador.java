package ft.ufam.ptr.semaforo.model;

import java.util.*;
import ft.ufam.ptr.semaforo.*;
import ft.ufam.ptr.semaforo.clock.*;
import ft.ufam.ptr.semaforo.graphics.*;

/** Executa a simulação do sistema.
 *  @author Felipe André
 *  @author Paulo Henrique
 *  @version 4.0, 27/07/2015 */
public class Simulador {
	
	private final Random random;
	private SemaforoMaster master_japiim_entrada, master_coroado_entrada;
	
	public void scenario(TelaPrincipal screen) {
		Clock clock = new Clock(1000);
		
		Via via_japiim_entrada_campus  = new Via(Local.JAPIIM_ENTRADA_CAMPUS, 10);
		Via via_entrada_saida_campus   = new Via(Local.ENTRADA_SAIDA_CAMPUS , 10);
		Via via_saida_campus_coroado   = new Via(Local.SAIDA_CAMPUS_COROADO);
		Via via_entrada_campus		   = new Via(Local.ENTRADA_CAMPUS);
		Via via_saida_campus		   = new Via(Local.SAIDA_CAMPUS,10);
		Via via_coroado_entrada_campus = new Via(Local.COROADO_ENTRADA_CAMPUS, 10);
		
		master_japiim_entrada  = new SemaforoMaster(Local.JAPIIM_ENTRADA_CAMPUS );
		master_coroado_entrada = new SemaforoMaster(Local.COROADO_ENTRADA_CAMPUS);
		
		SemaforoSlave slave_entrada_saida = new SemaforoSlave(Local.ENTRADA_SAIDA_CAMPUS);
		SemaforoSlave slave_saida_coroado = new SemaforoSlave(Local.SAIDA_CAMPUS_COROADO);
		
		SemaforoPedestre peds_japiim_entrada   = new SemaforoPedestre(Local.JAPIIM_ENTRADA_CAMPUS);
		SemaforoPedestre peds_coroado_entrada  = new SemaforoPedestre(Local.COROADO_ENTRADA_CAMPUS);
		SemaforoPedestre peds_entrada_saida	   = new SemaforoPedestre(Local.ENTRADA_SAIDA_CAMPUS);
		SemaforoPedestre peds_saida_coroado	   = new SemaforoPedestre(Local.SAIDA_CAMPUS_COROADO);
		
		Cruzamento crz_japiim_entrada  = new Cruzamento(via_japiim_entrada_campus , master_japiim_entrada , screen);
		Cruzamento crz_coroado_entrada = new Cruzamento(via_coroado_entrada_campus, master_coroado_entrada, screen);
		Cruzamento crz_entrada_saida   = new Cruzamento(via_entrada_saida_campus  , slave_entrada_saida   , screen);
		Cruzamento crz_saida_coroado   = new Cruzamento(via_saida_campus_coroado  , slave_saida_coroado   , screen);
		
		master_japiim_entrada .setSemaforoPedestre(peds_coroado_entrada);
		master_coroado_entrada.setSemaforoPedestre(peds_japiim_entrada );
		
		slave_entrada_saida.setSemaforoPedestre(peds_saida_coroado);
		slave_saida_coroado.setSemaforoPedestre(peds_entrada_saida);
		
		crz_japiim_entrada.insereViaSaida(via_entrada_campus);
		crz_japiim_entrada.insereViaSaida(via_entrada_saida_campus);
		
		crz_coroado_entrada.insereViaSaida(via_entrada_campus);
		crz_coroado_entrada.insereViaSaida(via_entrada_saida_campus);
		
		crz_entrada_saida.insereViaSaida(via_saida_campus_coroado);
		
		crz_saida_coroado.insereViaSaida(via_saida_campus_coroado);
		
		clock.addClockListener(crz_japiim_entrada);
		clock.addClockListener(crz_coroado_entrada);
		clock.addClockListener(crz_entrada_saida);
		clock.addClockListener(crz_saida_coroado);
		
		clock.addClockListener(master_coroado_entrada);
		clock.addClockListener(master_japiim_entrada );
		
		clock.addClockListener(via_coroado_entrada_campus);
		clock.addClockListener(via_saida_campus);
		clock.addClockListener(via_entrada_campus);
		clock.addClockListener(via_saida_campus_coroado);
		clock.addClockListener(via_entrada_saida_campus);
		clock.addClockListener(via_japiim_entrada_campus);
		
		master_japiim_entrada .setSemaforoSimetrico(master_coroado_entrada);
		master_coroado_entrada.setSemaforoSimetrico(master_japiim_entrada );
		
		master_japiim_entrada .setSemaforoSlave(slave_entrada_saida);
		master_coroado_entrada.setSemaforoSlave(slave_saida_coroado);
		
		master_coroado_entrada.addSemaforoListener(screen);
		master_japiim_entrada .addSemaforoListener(screen);
		slave_entrada_saida   .addSemaforoListener(screen);
		slave_saida_coroado   .addSemaforoListener(screen);
		
		
		master_coroado_entrada.setTempoVerde(5);
		master_japiim_entrada .setTempoVerde(5);
		
		clock.start();
	}

	public void sincroniza() {
		if (random.nextBoolean())
			master_coroado_entrada.setEstadoVerde();
		else
			master_japiim_entrada .setEstadoVerde();
	}
	
	public Simulador(TelaPrincipal screen) {
		this.random = new Random(System.currentTimeMillis());
		scenario(screen);
	}
	
	public void oldMethod(TelaPrincipal screen) {
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
