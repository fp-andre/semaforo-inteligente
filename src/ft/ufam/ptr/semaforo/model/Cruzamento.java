package ft.ufam.ptr.semaforo.model;

import java.util.*;
import ft.ufam.ptr.semaforo.*;
import ft.ufam.ptr.semaforo.clock.*;
import ft.ufam.ptr.semaforo.graphics.*;

/** Implementa um Cruzamento, onde há uma Via de viaEntrada,
 *  um Semaforo de referência e N Via's de saída, informadas
 *  via método público.
 *  @see Via
 *  @see Semaforo
 *  @author Felipe André
 *  @author Paulo Henrique
 *  @version 2.5, 01/08/2015 */
public class Cruzamento implements ClockListener {

	/* Atributos da classe */
	private int fluxo;
	private final Via viaEntrada;
	private final Semaforo semaforo;
	private Estado anterior, atual;
	private HashMap<Local,Via> viasSaida;
	private TelaPrincipal screen;
	
	/** Instancia um objeto Cruzamento.
	 *  @param viaEntrada - Via de viaEntrada (origem)
	 *  @param semaforo - Semáforo associado a este cruzamento */
	public Cruzamento(Via viaEntrada, Semaforo semaforo, TelaPrincipal screen) {
		this.viasSaida = new HashMap<Local,Via>();
		this.viaEntrada   = viaEntrada;
		this.semaforo  = semaforo;
		this.fluxo = 0;
		this.screen = screen;
	}

	/** Cadastra uma via de saída no cruzamento.
	 *  Esta é identificada pela sua localização. */
	public void insereViaSaida(Via via) {
		Local localizacao = via.getLocalizacao();
		viasSaida.put(localizacao, via);
	}
	
	/** Reinicia o contador de fluxo */
	private void reiniciaFluxo() {
		this.fluxo = 0;
	}
	
	/** Recupera o contador de fluxo */
	public int getFluxoVeiculos() {
		return this.fluxo;
	}
	
	/** Incrementa o contado de fluxo */
	private void incrementaFluxo() {
		fluxo++;
	}
	
	/** Reinicia o contador de fluxo de veículos na transição
	 *  do estado vermelho para o estado verde do semáforo */
	private void detectaTransicao() {
		anterior = atual;
		atual = semaforo.getEstadoAtual();
		
		if ((anterior == Estado.VERMELHO) && (atual == Estado.VERDE))
			reiniciaFluxo();
	}
	
	/** Repassa veículos da via de viaEntrada para uma das
	 *  vias de saída (analisando o destino do veículo). */
	public void movimentaVeiculos() {
		final int qtd = viaEntrada.getQtdFaixas();
		
		for (int i=1; i<=qtd; i++) {
			Veiculo veiculo = viaEntrada.getVeiculoFaixa(i);
			
			if (veiculo != null) {
				Local destino  = veiculo.getDestino();
				
				Via viaSaida = viasSaida.get(destino);
				
				if (viaSaida.temEspacoNaVia()) {
					viaEntrada.removeVeiculoFaixa(i);
					viaSaida.insereVeiculoVia(veiculo, i);
					incrementaFluxo();
				}
			}
		}
	}
	
	/** Mantém os Semáforos Master atualizados com fluxo das
	 *  vias, isto serve para o processamento da inteligência
	 *  @see SemaforoMaster */
	private void atualizaFluxoMaster() {
		if (semaforo instanceof SemaforoMaster)
			((SemaforoMaster) semaforo).setFluxo(fluxo);
	}
	
	@Override
	public void evento(ClockEvent event) {
		detectaTransicao();
		
		if (semaforo.aberto())
			movimentaVeiculos();
		atualizaFluxoMaster();
		
		screen.fireFluxoUpdate(viaEntrada.getLocalizacao(), getFluxoVeiculos());
		screen.fireOcupacaoUpdate(viaEntrada.getLocalizacao(), viaEntrada.getOcupacaoVia());
		
	}
	
}
