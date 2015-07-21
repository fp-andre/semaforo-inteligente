package ft.ufam.ptr.semaforo.model;

/** Implementa uma faixa da via. É esta classe quem realmente possui os
 *  veículos. A orientação da fila de veículos é representada da esquerda
 *  para a direita, ou seja, o menor índice [0] representa o fim da fila e
 *  o maior índice [tamanho-1] representa o início da fila de veículos.  
 *  @author Felipe André
 *  @author Paulo Henrique
 *  @version 2.0, 15/07/2015 */
public class Faixa {

	/* Atributos da classe */
	private Veiculo[] faixa;
	private final int tamanho;
	private final int inicioFila;
	
	/** Instancia um objeto Faixa. Note que este não possui associação
	 *  direta com a via, pois ela é quem manipula este objeto.
	 *  @param tamanho - Comprimento da faixa de veículos (dado por número de posições de vetor) */
	public Faixa(int tamanho) {
		this.tamanho = tamanho;
		this.inicioFila = (tamanho - 1);
		this.faixa = new Veiculo[tamanho];
	}
	
	/** Método de deslocamento de veículos. Este tanto
	 *  desloca quanto imprensa os veículos da faixa. */
	protected synchronized void deslocaVeiculos() {
		for (int i=inicioFila; i>0; i--) {
			if (faixa[i] == null) {
				faixa[i] = faixa[i-1];
				faixa[i-1] = null;
			}
		}
	}
	
	/** Insere um veículo no fim da fila de veículos */
	private void insereVeiculoFimFaixa(Veiculo veiculo) {
		faixa[0] = veiculo;
	}

	/** Remove um veículo do início da fila de veículos */
	private void removePrimeiroVeiculo() {
		faixa[inicioFila] = null;
	}
	
	/** Retorna o veículo do início da fila de veículos */
	protected Veiculo getPrimeiroVeiculo() {
		return faixa[inicioFila];
	}
	
	/** Verifica se há espaço disponível na faixa
	 *  para inserção de um novo veículo */
	protected boolean temEspaco() {
		return (faixa[0] == null);
	}
	
	/** Retorna a quantidade de veículos existentes na faixa */
	protected int getOcupacaoFaixa() {
		int veiculos = 0;
		
		for (Veiculo veiculo: faixa)
			if (veiculo != null)
				veiculos++;
		
		return veiculos;
	}
	
	/** Tenta inserir um veículo na faixa, se tiver espaço na mesma */
	protected synchronized boolean insereVeiculo(Veiculo veiculo) {
		if (temEspaco()) {
			insereVeiculoFimFaixa(veiculo);
			return true;
		}
		return false;
	}
	
	/** Remove e retorna o veículo do início da fila de veículos */
	protected synchronized Veiculo removeVeiculo() {
		Veiculo veiculo = getPrimeiroVeiculo();
		removePrimeiroVeiculo();
		return veiculo;
	}
	
	/** Imprime a faixa de veículos.
	 *  0 representa área vazia e 1 representa área ocupada. */
	public void imprimeFaixa() {
		for (int i=0; i<tamanho; i++)
			if (faixa[i] == null)
				System.out.print("0 ");
			else
				System.out.print("1 ");
		System.out.println();
	}
	
}
