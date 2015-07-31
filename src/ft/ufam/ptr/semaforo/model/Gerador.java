package ft.ufam.ptr.semaforo.model;

import java.util.Random;
import ft.ufam.ptr.semaforo.clock.*;

/** Implementa um gerador de fluxo aleatório para uma via específica.
 *  @see Local
 *  @see Via
 *  @author Felipe André
 *  @author Paulo Henrique
 *  @version 2.0, 15/07/2015 */
public class Gerador implements ClockListener {

	/* Atributos da classe */
	private int quantidade;
	private final int taxa;
	private final Local origem, destino;
	private final Random random;
	private final Via via;
	
	/** Instancia um objeto Gerador de fluxo aleatório. Nota: a origem dos veículos é obtida a partir da localização da via
	 *  @param quantidade - Quantidade de veículos (fluxo) que o gerador deve injetar na via ao longo do tempo
	 *  @param taxa - Quantidade de veículos que o gerador deve injetar na via por ciclo de tempo (Clock)
	 *  @param destino - Destino dos veículos gerados
	 *  @param via - Via a ser contemplada com o fluxo do gerador */
	public Gerador(int quantidade, int taxa, Via origem, Local destino) {
		this.quantidade = quantidade;
		this.taxa		= taxa;
		this.origem 	= origem.getLocalizacao();
		this.destino 	= destino;
		this.random		= new Random();
		this.via = origem;
	}
	
	/** Verifica se ainda existe fluxo a ser gerado */
	private boolean restamVeiculos() {
		return (quantidade > 0);
	}
	
	/** Insere um veículo ou um espaço em branco (veiculo = null) na faixa informada*/
	private void geraVeiculoFaixa(Faixa faixa, Veiculo veiculo) {
		if (restamVeiculos())
			if (faixa.insereVeiculo(veiculo) && (veiculo != null))
				quantidade--;
	}
	
	/** Cria um veículo ou um espaço em branco aleatoriamente */
	private Veiculo getVeiculoAleatorio() {
		final int aleatorio = new Random().nextInt(2);
		return (aleatorio == 1) ? null : new Carro(origem,destino);
	}
	
	/** Gera uma taxa de fluxo na via */
	public void geraFluxo() {
		
		final int qtdFaixas = via.getQtdFaixas();
		
		for (int i=0; i<taxa; i++) {
			Veiculo veiculo = getVeiculoAleatorio();
			final int faixaID = random.nextInt(qtdFaixas) + 1;
			final Faixa faixa = via.getFaixa(faixaID);
			geraVeiculoFaixa(faixa, veiculo);
		}
		
	}
	
	/** Imprime a via e a quantidade de fluxo remanescente */
	protected void imprimeInformacoes() {
		via.imprimeVia();
		System.out.println("Restantes: " + quantidade + "\n");
	}

	@Override
	public void evento(ClockEvent event) {
		geraFluxo();
	}
}
