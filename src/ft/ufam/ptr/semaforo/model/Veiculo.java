package ft.ufam.ptr.semaforo.model;

/** Representa a abstração de um veículo.
 *  @author Felipe André
 *  @author Paulo Henrique
 *  @version 2.0, 14/07/2015 */
public interface Veiculo {

	/** Retorna a origem do veículo */
	Local getOrigem();
	
	/** Retorna o destino do veículo */
	Local getDestino();
	
}
