package ft.ufam.ptr.semaforo.model;

/** Contém as constantes que definem as localizações das entidades do semáforo inteligente.
 *  Nota: O nome das constantes é obtido a partir da concatenação da origem com o destino das vias.
 *  @author Felipe André
 *  @author Paulo Henrique
 *  @version 1.0, 15/07/2015 */
public enum Local {

	/* Definição das constantes */
	JAPIIM_ENTRADA_CAMPUS ("Rodrigo Otávio", "Japiim" , "Entrada do Campus", false),
	ENTRADA_SAIDA_CAMPUS ("Rodrigo Otávio", "Entrada do Campus", "Saída do Campus", false),
	SAIDA_CAMPUS_COROADO ("Rodrigo Otávio", "Saída do Campus", "Coroado", true),
	ENTRADA_CAMPUS ("Estrada do Campus", "Entrada do Campus", "Campus", true),	
	SAIDA_CAMPUS ("Estrada do Campus", "Campus", "Saída do Campus", false),
	COROADO_ENTRADA_CAMPUS ("Rodrigo Otávio", "Coroado", "Entrada do Campus", false);
	
	/* Atributos do enum */
	private final String nome, origem, sentido;
	private final boolean consumidora;
	
	/** Inicializa as constantes com algumas informações úteis. Estas são:
	 *  @param nome - Nome da via
	 *  @param origem - Nome da via ou bairro de origem
	 *  @param sentido - Nome da via ou bairro em que se segue a via
	 *  @param consumidora - Indica se a via é puramente consumidora, ou seja, assume-se que
	 *  esta possui "tamanho infinito" e nesta sempre há espaço para alocação de veículos */
	Local(String nome, String origem, String sentido, boolean consumidora) {
		this.nome = nome;
		this.origem = origem;
		this.sentido = sentido;
		this.consumidora = consumidora;
	}
	
	/** Retorna o nome da via */
	public String getNome() {
		return nome;
	}

	/** Retorna o nome da origem */
	public String getOrigem() {
		return origem;
	}
	
	/** Retorna o nome do sentido em que se segue a via */
	public String getSentido() {
		return sentido;
	}
	
	/** Verifica se a via é puramente consumidora */
	public boolean isApenasConsumidora() {
		return consumidora;
	}
	
	/** Retorna informações completas sobre o local */
	public String getInfos() {
		return "Nome da Via: " + nome + "\nOrigem: " + origem + "\nSentido: " + sentido;
	}
	
}
