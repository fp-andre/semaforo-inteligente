package ft.ufam.ptr.semaforo.model;

/** Objeto que representa um carro.
 *  @see Veiculo
 *  @author Felipe André
 *  @author Paulo Henrique
 *  @version 3.0, 02/08/2015 */
public class Carro implements Veiculo {

	/* Atributos da classe */
	private Local origem, destino;
	
	/** Instancia um objeto Carro.
	 *  @param origem - Constante da via de origem do veículo
	 *  @param destino - Constante da via de destino do veículo
	 *  @see Local */
	public Carro(Local origem, Local destino) {
		this.origem  = origem;
		this.destino = destino;
	}
	
	@Override
	public Local getOrigem() {
		return origem;
	}
	
	@Override
	public Local getDestino() {
		return destino;
	}

	@Override
	public void setOrigem(Local origem) {
		this.origem = origem;
	}

	@Override
	public void setDestino(Local destino) {
		this.destino = destino;
	}

	@Override
	public void printInfos() {
		System.err.println("Origem: " + origem + " - Destino: " + destino);
	}

}
