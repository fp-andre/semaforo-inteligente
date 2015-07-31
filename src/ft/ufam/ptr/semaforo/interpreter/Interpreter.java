package ft.ufam.ptr.semaforo.interpreter;

import java.io.*;
import ft.ufam.ptr.semaforo.model.*;
import ft.ufam.ptr.semaforo.utils.*;

/** Implementação do interpretador de configurações do semáforo.
 *  Com este é possível configurar a base de tempo do sistema e
 *  a instanciação dos geradores de fluxo das vias.
 *  @author Felipe André
 *  @version 1.0, 30/07/2015 */
public class Interpreter extends Thread {
	
	/* Atributos da classe */
	private final Simulador simulador;
	private final BufferedReader stream;
	
	/** Inicializa os atributos */
	public Interpreter(Simulador simulador) {
		this.simulador = simulador;
		this.stream = getArquivoComandos();
	}

	/** Cria uma stream de leitura de dados a partir de um arquivo */
	private BufferedReader getArquivoComandos() {
		BufferedReader str = null;
		String path = PropertiesManager.getResource("config/script.ssf");
		
		try {
			str = new BufferedReader(new FileReader(path));
		}
		catch (Exception exception) {
			System.err.println("Falha ao abrir o arquivo!");
		}
		
		return str;
	}
	
	/** Faz a leitura e interpretação dos comandos do sistema */
	@Override
	public void run() {
		String line = null;
		
		try {
			while ( (line = stream.readLine()) != null ) {
				String[] splitted = line.split(" ");
				String comando = splitted[0];
				parseCommand(comando, splitted);
			}
			stream.close();
		}
		
		catch (IOException exception) {
			System.err.println("Falha ao abrir o arquivo de configurações!");
		}
		catch (NumberFormatException exception) {
			System.err.println("Quantidade de Veículos inválida!");
		}
		catch (IllegalArgumentException exception) {
			System.err.println("Via inválida\n" + line);
		}
		catch (Exception exception) {
			System.err.println("Comando não reconhecido:\n" + line);
		}
	}
	
	/** Faz a seleção dos comandos */
	private void parseCommand(String comando, String[] args) throws Exception {
		
		switch (comando) {
		
			case "baseTempo":
				setBaseTempo(args);
			break;
			
			case "gera":
				criaGerador(args);
			break;
			
			default:
				System.out.println("Comando não reconhecido!");
			break;
		}
	}
	
	/** Atualiza a base de tempo do simulador */
	private void setBaseTempo(String[] args) throws Exception {
		int ciclos = Integer.parseInt(args[1]);
		simulador.setBaseTempo(ciclos);
	}
	
	/** Adiciona um gerador de veículos no simulador */
	private void criaGerador(String[] args) throws Exception {
		
		int quantidade = Integer.parseInt(args[1]);
		Taxa  taxa     = Taxa .valueOf(args[2]);
		Local origem   = Local.valueOf(args[3]);
		Local destino  = Local.valueOf(args[4]);
		
		simulador.criaGerador(quantidade, taxa, origem, destino);
		
	}
	
}
