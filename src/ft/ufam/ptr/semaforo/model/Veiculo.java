package ft.ufam.ptr.semaforo.model;

/** Representa a abstração de um veículo.
 *  @author Felipe André
 *  @author Paulo Henrique
 *  @version 2.5, 02/08/2015 */
public interface Veiculo {

	/** Retorna a origem do veículo */
	Local getOrigem();
	
	/** Retorna o destino do veículo */
	Local getDestino();
	
	/** Atualiza a origem do veívulo */
	void setOrigem(Local origem);
	
	/** Atualiza o destino do veículo */
	void setDestino(Local destino);
	
	/** Imprime as informações de origem e destino do veículo */
	void printInfos();
	
}
