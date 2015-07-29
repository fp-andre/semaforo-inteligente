package ft.ufam.ptr.semaforo.model;

import java.util.*;
import ft.ufam.ptr.semaforo.clock.*;

/** Implementa uma via do sistema. Nota: o tamanho igual a zero representa
 *  uma via puramente consumidora, ou seja, de tamanho infinito.
 *  @author Felipe André
 *  @author Paulo Henrique
 *  @version 2.0, 15/07/2015 */
public class Via implements ClockListener {

	/** Atributos da classe */
	private final Local localizacao;
	private final int qtdFaixas;
	private HashMap<Integer,Faixa> hashmap;
	
	/** Instancia uma via puramente consumidora
	 *  @param localizacao - Localização da via */
	public Via(Local localizacao) {
		this(localizacao,0,0);
	}
	
	/** Instancia uma via com duas faixas
	 *  @param localizacao - Localização da via
	 *  @param tamanho - Tamanho da via */
	public Via(Local localizacao, int tamanho) {
		this(localizacao,2,tamanho);
	}
	
	/** Construtor principal da classe Via
	 *  @param localizacao - Localização da via
	 *  @param qtdFaixas - Quantidade de faixas que esta via possui
	 *  @param tamanho - Tamanho da via */
	private Via(Local localizacao, int qtdFaixas, int tamanho) {
		this.localizacao = localizacao;
		this.qtdFaixas 	 = qtdFaixas;
		
		if (tamanho > 0) {
			this.hashmap 	 = new HashMap<Integer,Faixa>(qtdFaixas);
			inicializaFaixas(tamanho);
		}
	}
	
	/** Instancia as faixas desta via. Nota: estas estão sendo
	 *  identificadas pela por ordem de 1 a N faixas */
	private void inicializaFaixas(int tamanho) {
		for (int i=1; i<=qtdFaixas; i++)
			hashmap.put(new Integer(i), new Faixa(tamanho));
	}
	
	/** Retorna as faixas que esta via possui */
	public Collection<Faixa> getFaixas() {
		return (hashmap == null) ? null : hashmap.values();
	}
	
	/** Desloca os veículos de todas as faixas da via */
	public void deslocaVeiculos() {
		if (hashmap != null)
			for (Faixa faixa: hashmap.values())
				faixa.deslocaVeiculos();
	}
	
	/** Retorna a localização desta via */
	public Local getLocalizacao() {
		return localizacao;
	}
	
	/** Retorna a quantidade de faixas desta via */
	public int getQtdFaixas() {
		return qtdFaixas;
	}
	
	/** Retorna uma faixa identificada pelo seu ID */
	public Faixa getFaixa(int faixaID) {
		return hashmap.get(faixaID);
	}
	
	/** Imprime todas as faixas desta via */
	public void imprimeVia() {
		for (Faixa faixa: hashmap.values())
			faixa.imprimeFaixa();
		System.out.println();
	}
	
	/** Retorna o veículo do início da fila informada pelo ID */
	public Veiculo getVeiculoFaixa(int viaID) {
		return hashmap.get(viaID).getPrimeiroVeiculo();
	}
	
	/** Retorna a quantidade de veículos existentes nas faixas */
	public int getOcupacaoVia() {
		int ocupacaoVia = 0;

		try {
			for (Faixa faixa: getFaixas())
				ocupacaoVia += faixa.getOcupacaoFaixa();
		}
		catch (NullPointerException exception) {
			return 0;
		}
		
		return ocupacaoVia;
	}
	
	/** Verifica se há espaço na via para a inserção de um novo veículo.
	 *  Nota: vias apenas consumidoras sempre tem espaço! */
	public boolean temEspacoNaVia() {
		
		if (localizacao.isApenasConsumidora())
			return true;
		
		for (Faixa faixa: hashmap.values())
			if (faixa.temEspaco())
				return true;
		
		return false;
	}
	
	/** Remove e retorna o veículo do início da fila informada pelo ID  */
	public Veiculo removeVeiculoFaixa(int faixaID) {
		return hashmap.get(faixaID).removeVeiculo();
	}
	
	/** Tenta inserir um veículo na faixa preferencial.
	 *  Se não for possível, procura um espaço livre. */
	public synchronized boolean insereVeiculoVia(Veiculo veiculo, int faixaPreferencial) {
		
		if (localizacao.isApenasConsumidora())
			return true;
		
		if (hashmap.get(faixaPreferencial).temEspaco()) {
			hashmap.get(faixaPreferencial).insereVeiculo(veiculo);
			return true;
		}
		
		for (Faixa faixa: getFaixas()) {
			if (faixa.temEspaco()) {
				faixa.insereVeiculo(veiculo);
				return true;
			}
		}
		
		return false;
	}

	@Override
	public void evento(ClockEvent event) {
		deslocaVeiculos();
	}
}
