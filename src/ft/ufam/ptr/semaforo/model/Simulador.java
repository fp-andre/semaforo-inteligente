package ft.ufam.ptr.semaforo.model;

import java.util.*;
import javax.swing.*;
import ft.ufam.ptr.semaforo.*;
import ft.ufam.ptr.semaforo.utils.*;
import ft.ufam.ptr.semaforo.clock.*;
import ft.ufam.ptr.semaforo.graphics.*;
import ft.ufam.ptr.semaforo.interpreter.*;

/** Executa a simulação do sistema.
 *  @author Felipe André
 *  @author Paulo Henrique
 *  @version 4.0, 27/07/2015 */
public class Simulador {
	
	private JFrame screen;
	private final Random random;
	private final Clock clock;
	private SemaforoMaster master_japiim_entrada, master_coroado_entrada;
	private final ArrayList<Via> listaVias;
	
	public void setBaseTempo(int millis) {
		System.out.println("Base de Tempo: " + millis + "s");
		clock.setBaseTime(millis);
	}
	
	public void criaGerador(int quantidade, Taxa taxa, Local origem, Local destino) {
		int tx = taxa.getValor();
		Via via_origem = findViaByID(origem);
		Gerador gerador = new Gerador(quantidade,tx,via_origem,destino);
		addGerador(gerador);
		
		System.out.printf("Gerador de fluxo criado: máximo %d veículos em taxa %s indo de %s para %s\n",quantidade,taxa.name(),origem.name(),destino.name());
	}
	
	private Via findViaByID(Local origem) {
		for (Via via: listaVias)
			if (via.getLocalizacao().equals(origem))
				return via;
		return null;
	}
	
	private synchronized void addGerador(Gerador gerador) {
		clock.addClockListener(gerador);
	}
	
	public void scenario(TelaPrincipal screen) {
		
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
		
		peds_coroado_entrada  .addSemaforoListener(screen);
		peds_entrada_saida    .addSemaforoListener(screen);
		peds_japiim_entrada   .addSemaforoListener(screen);
		peds_saida_coroado    .addSemaforoListener(screen);
		
		listaVias.add(via_coroado_entrada_campus);
		listaVias.add(via_entrada_campus);
		listaVias.add(via_entrada_saida_campus);
		listaVias.add(via_japiim_entrada_campus);
		listaVias.add(via_saida_campus);
		listaVias.add(via_saida_campus_coroado);
		
		clock.addClockListener(screen);
		
	}

	public void start() {
		if (clock.semBaseTempo())
			AlertDialog.erro("Base de Tempo do Simulador","A base de tempo do simulador\nnão está configurada!");
		else {
			clock.start();
			screen.setTitle("Semáforo Inteligente - Running");
		}
	}
	
	public void sincroniza() {
		if (random.nextBoolean())
			master_coroado_entrada.setEstadoVerde();
		else
			master_japiim_entrada .setEstadoVerde();
	}
	
	public Simulador(TelaPrincipal screen) {
		this.screen = screen;
		this.random = new Random(System.currentTimeMillis());
		this.listaVias = new ArrayList<Via>();
		this.clock = new Clock();
		scenario(screen);
	}
	
}
